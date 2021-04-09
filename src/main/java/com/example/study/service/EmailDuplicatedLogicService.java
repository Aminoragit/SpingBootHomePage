package com.example.study.service;

import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import org.springframework.stereotype.Service;

@Service
public abstract class EmailDuplicatedLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        return null;
    }
}
