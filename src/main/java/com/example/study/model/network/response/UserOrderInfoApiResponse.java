package com.example.study.model.network.response;


import com.example.study.model.entity.OrderGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderInfoApiResponse {

    private UserApiResponse userApiResponse;

    //    UserApiResponse -> private List<OrderGroupApiResponse> orderGroupApiResponseList;
    //    OrderGroupApiResponse-> private List<ItemApiResponse> itemApiResponseList;


}
