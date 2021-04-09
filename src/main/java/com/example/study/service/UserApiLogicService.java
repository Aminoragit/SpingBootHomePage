package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.ErrorManager;


//409 중복 충돌 status code
@ResponseStatus(code = HttpStatus.CONFLICT)
class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

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

        User newUser= userRepository.save(user);

        //3.여러번 사용하므로 맨 아래에 메서드 작성했음
        
        return response(newUser);
    }


    ///////////////////
    // *   READ   *  //
    ///////////////////    @Override
    public Header<UserApiResponse> read(Long id) {
        //1. id -> repository getOne, getById
        //2. user-> userApiResponse return
        //Optianl<User> optianl로 생성후 해도 되지만
        //아래처럼 람다식으로 해도 정상작동한다.
        return userRepository.findById(id)
                .map(user->response(user))
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
        Optional<User> optional = userRepository.findById(userApiRequest.getId());

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
                .map(user->userRepository.save(user)) //update실행 DB에 반영
                .map(user->response(user)) //userApiResponse
                .orElseGet(()->Header.ERROR("데이터없음")); //위쪽에서 1개라도 데이터가 없다면 데이터없음으로 실행
    }




    ////////////////////
    // *   DELETE   * //
    ////////////////////
    @Override
    public Header delete(Long id) {
        //id로 repository의 user 데이터 가져오고

        Optional<User> optional = userRepository.findById(id);


        //2. repository의 delte 실행
       return optional.map(user->{
            userRepository.delete(user);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));


    }



    private Header<UserApiResponse> response(User user){
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
        return Header.OK(userApiResponse);
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
        else {User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

            User newUser= userRepository.save(user);
            return response(newUser);
        }
    }
}