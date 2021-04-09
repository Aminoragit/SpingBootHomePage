package com.example.study.controller.api;


import com.example.study.controller.CrudController;
import com.example.study.model.entity.User;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/user2")
public class EmailDuplicatedApiController extends CrudController<UserApiRequest,UserApiResponse, User> {
}
