package com.example.study.model.network;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public aspect Header {

    // api 통신시간
    private String transactionTime;

    // api 응답코드
    private String resultCode;

    // api 부가설명
    private String description;
}
