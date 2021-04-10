package com.example.study.controller.api;


import com.example.study.model.network.Header;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import com.example.study.model.network.response.UserTotalPriceInfoApiResponse;
import com.example.study.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
public class UserTotalPriceApiController {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @GetMapping("/settlement/{id}")
    public Header<UserTotalPriceInfoApiResponse> orderTotalPriceInfo(@PathVariable Long id)
    {
        return userApiLogicService.orderTotalPriceInfo(id);
    }
}
