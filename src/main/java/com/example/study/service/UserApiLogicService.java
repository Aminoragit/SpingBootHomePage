package com.example.study.service;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.Settlement;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.*;
import com.example.study.repository.SettlementRepository;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//409 중복 충돌 status code
@ResponseStatus(code = HttpStatus.CONFLICT)
class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse,User> {


    @Autowired
    private SettlementRepository settlementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService ;



    //create 동작과정
    //1. request data 가져오기
    //2. user 생성
    //3. 생성된 데이터 -> UserApiResponse return
    /////////////////////
    // *   CREATE   *  //
    /////////////////////
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        //1.
        UserApiRequest userApiRequest = request.getData();

        //2.
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser= baseRepository.save(user);

        //3.여러번 사용하므로 맨 아래에 메서드 작성했음

        return Header.OK(response(newUser));
    }


    ///////////////////
    // *   READ   *  //
    ///////////////////    @Override
    public Header<UserApiResponse> read(Long id) {
        //1. id -> repository getOne, getById
        //2. user-> userApiResponse return
        //Optianl<User> optianl로 생성후 해도 되지만
        //아래처럼 람다식으로 해도 정상작동한다.
        return baseRepository.findById(id)
                .map(user->response(user))
                .map(Header::OK)
               .orElseGet(()->Header.ERROR("데이터없음"));
    }

    ////////////////////
    // *   UPDATE   * //
    ////////////////////
    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        //1. data를 가져오고
        UserApiRequest userApiRequest = request.getData();

        //2. id에 맞는 user데이터를 가져오고
        Optional<User> optional = baseRepository.findById(userApiRequest.getId());

        //데이터 유무에 따른 동작 정의
        return optional.map(user -> {
            //3. data -> update하고
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
            //아직 save를 안해서 DB에 반영되진 않음
        })
                .map(user->baseRepository.save(user)) //update실행 DB에 반영
                .map(user->response(user)) //userApiResponse
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터없음")); //위쪽에서 1개라도 데이터가 없다면 데이터없음으로 실행
    }


    ////////////////////
    // *   DELETE   * //
    ////////////////////
    @Override
    public Header delete(Long id) {
        //id로 repository의 user 데이터 가져오고

        Optional<User> optional = baseRepository.findById(id);


        //2. repository의 delte 실행
       return optional.map(user->{
           baseRepository.delete(user);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));


    }



    private static UserApiResponse response(User user){
        // user -> userApiResponse
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword()) // todo 암호화, 길이
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .UnregisteredAt(user.getUnregisteredAt())
                .build();

        //Header + data return
        return userApiResponse;
    }

    public Header<List<UserApiResponse>> search(Pageable pageable){
        Page<User> users = baseRepository.findAll(pageable);

        List<UserApiResponse> userApiResponseList = users.stream().map(user->
                response(user)
        ).collect(Collectors.toList());


        //페이지 갯수를 확인하기 // password의 노출금지와 ApiResponse의 변경문제를 위해 아래처럼 따로 만들어준것이다.
        Pagination pagination=Pagination.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .currentElements(users.getNumberOfElements())
                .build();


        return Header.OK(userApiResponseList,pagination);
    }

    public Header<UserOrderInfoApiResponse> orderInfo(Long id){
        //사용자를 찾아오고
        User user = userRepository.getOne(id);

        UserApiResponse userApiResponse = response(user);

        //orderGroup을 가져온후
        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList=orderGroupList.stream()
                .map(orderGroup -> {
                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup).getData();

                    //해당 orderGroup에 해당하는 아이템들을 찾아와서 지정
                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail->detail.getItem())
                            .map(item->itemApiLogicService.response(item).getData())
                            .collect(Collectors.toList());

                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;
                })
                .collect(Collectors.toList());


                //마지막으로 user에 그룹을 지정하고
                //userorderInfoApiResponse에 빌드해주고 반환
        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);


        UserOrderInfoApiResponse userOrderInfoApiResponse=UserOrderInfoApiResponse
                .builder()
                .userApiResponse(userApiResponse)
                .build();
        return Header.OK(userOrderInfoApiResponse);
    }


    public Header<UserApiResponse> checkEmailCreate(Header<UserApiRequest> request) {
        //DB에 있는 User 데이터 가져오고
        UserApiRequest userApiRequest = request.getData();

        //위의 userApiRequest에서 Email부분만 가져와서 있는지 여부를 확인하고
        Optional<User> checkEmail = userRepository.findByEmail(userApiRequest.getEmail());

        //map을 쓰려고 했는데 에러가 발생해서 if문으로 대체
        if (checkEmail.isPresent()){
            throw new AlreadyExistsException("중복된 이메일 입니다.");
        }
        else {
            User user = User.builder()
                    .account(userApiRequest.getAccount())
                    .password(userApiRequest.getPassword())
                    .status(UserStatus.REGISTERED)
                    .phoneNumber(userApiRequest.getPhoneNumber())
                    .email(userApiRequest.getEmail())
                    .registeredAt(LocalDateTime.now())
                    .build();

            User newUser= baseRepository.save(user);

            //3.여러번 사용하므로 맨 아래에 메서드 작성했음

            return Header.OK(response(newUser));

        }
    }




    //힘들당
    public Header<UserTotalPriceInfoApiResponse> orderTotalPriceInfo(Long id) {
        //1) id로 사용자의 데이터를 가져온다.(baseData)
        User baseData = userRepository.getOne(id);

        //2) 사용자가 주문한 OrderGroup의 리스트를 가져온다.
        List<OrderGroup> orderGroupList = baseData.getOrderGroupList();
        //일단 프린트로 테스트 결과 oderGroup에 해당 사용자 id에 맞는 데이터들이 PersistentBag 형태로 출력되었다.

        //3) 가져올때 total_price와 user_id만 가져온다.
        //호출한 orderGroupList의 값들중 totlaPirce들만 불러온후 List형태로 변환
        List<BigInteger> resultList=orderGroupList.stream().map(getDetail->getDetail.getTotalPrice().toBigInteger()).collect(Collectors.toList());
        System.out.println(resultList.stream().mapToLong(BigInteger::intValue).sum());


        //4) 가져온 리스트의 total_price의 총합 stream().maToInt.sum으로 총합을 구해준다.
        //리스트 내부값들을 모두 합친값 == TotalPrice BigDecimal로 캐스팅
        BigDecimal  totalPrice = new BigDecimal(resultList.stream().mapToLong(BigInteger::intValue).sum());

        //user_id는 그냥 Get 호출때 사용한 baseData의 id를 사용하자
        long userId = baseData.getId();



        //수정사항 발생
        //1) 먼저 user_id가 있는지 확인부터 하고 있으면 update 없으면 create (settlementcreate) 해주자.
        return settlementRepository.findByUserId(userId)
                .map(updateSettlement -> {
                    updateSettlement.setUserId(userId)
                            .setPrice(totalPrice);
                    return updateSettlement;
                })
                .map(changeSettlement->settlementRepository.save(changeSettlement))
                .map(uSettlement->TotalPriceResponse(uSettlement))

                //만일 해당 userId로 찾을수 없다면 Create실행->null
                .orElseGet(
                        ()->createSettlement(totalPrice,userId)
                        );
    }








    public Header<UserTotalPriceInfoApiResponse> TotalPriceResponse(Settlement settlement){


        UserTotalPriceInfoApiResponse body = UserTotalPriceInfoApiResponse.builder()
                .price(settlement.getPrice())
                .userId(settlement.getUserId())
                .build();

        return Header.OK(body);
    }

    //settlement crate 메서드
    public Header<UserTotalPriceInfoApiResponse> createSettlement(BigDecimal totalPrice,Long userId) {
        Settlement settlement = Settlement.builder()
                .price(totalPrice)
                .userId(userId)
                .build();
        Settlement newSettlement = settlementRepository.save(settlement);
        return TotalPriceResponse(newSettlement);
    }

}