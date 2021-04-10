package com.example.study.controller.api;


import com.example.study.controller.CrudController;
import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api")
public class EmailDuplicatedApiController extends CrudController<UserApiRequest,UserApiResponse, User> {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @PostMapping("/user2")
    public Header<UserApiResponse> checkEmailCreate(@RequestBody Header<UserApiRequest> request) {
        return userApiLogicService.checkEmailCreate(request);
    }


}
