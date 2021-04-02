package com.example.study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Data //Data를 붙이면 toString으로 sql전달시 깔끔하게 정의해줌
@AllArgsConstructor
@NoArgsConstructor
@Entity //@Table(name="user")가 자동생성되므로 굳이 선언안해도됨,
        // 단, class명이랑 db의 테이블명이 같아야함 다르면 @table 생성필요함
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Table처럼 @Column(name="account")로 선언안해도 변수명이 동일하면 알아서 생성해줌
    private String account;
    private String password;
    private String status;

    private String email;
    private String phoneNumber;
    private LocalDateTime registeredAt;
    private String unregisteredAt;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;


}
