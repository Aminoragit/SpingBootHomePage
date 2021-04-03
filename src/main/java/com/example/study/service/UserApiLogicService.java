package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    //create 동작과정
    //1. request data 가져오기
    //2. user 생성
    //3. 생성된 데이터 -> UserApiResponse return
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        //1.
        UserApiRequest userApiRequest = request.getData();

        //2.
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status("REGISTERED")
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser= userRepository.save(user);

        //3.여러번 사용하므로 맨 아래에 메서드 작성했음
        
        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        //1. id -> repository getOne, getById
        Optional<User> optional = userRepository.findById(id);


        //2. user-> userApiResponse return
        //map()으로 user가 있는지 확인
        //orElseGet없을경우 뭘할지 Header의 ERROR메서드 실행
       return optional
               .map(user->response(user))
               .orElseGet(()->Header.ERROR("데이터없음"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
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
}