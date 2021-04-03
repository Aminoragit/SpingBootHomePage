package com.example.study.model.network;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    // api 통신시간
    //일반적으로 API작성시 스네이크 케이스를 쓰지만 카멜케이스이므로 아래처럼 JsonProperty로 일일히 바꿀수도 있지만
    //resources->application_properties에서 설정하면 한번에 수정이 가능하다.
    @JsonProperty("transaction_time")
    private LocalDateTime transactionTime;

    // api 응답코드
    private String resultCode;

    // api 부가설명
    private String description;
    
    //<T>제네릭 사용으로 data에 유연성을 줌
    private T data;


    //OK
    public static <T> Header<T> OK(){
        return (Header<T>) Header
                .builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    //DATA OK
    public static <T> Header<T> OK(T data){
        return (Header<T>) Header
                .builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    //Error
    public static <T> Header<T> ERROR(String description){
        return (Header<T>) Header
                .builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }

    //중복반환
    public static<T> Header<T> EXISTSemail(){
        return (Header<T>) Header
                .builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("중복된 이메일입니다")
                .description("중복된")
                .build();
    }
}
