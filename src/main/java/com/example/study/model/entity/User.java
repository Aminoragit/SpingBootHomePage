package com.example.study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data //Data를 붙이면 toString으로 sql전달시 깔끔하게 정의해줌
@AllArgsConstructor
@NoArgsConstructor
@Entity //@Table(name="user")가 자동생성되므로 굳이 선언안해도됨,
@ToString(exclude = {"orderGroupList"})        // 단, class명이랑 db의 테이블명이 같아야함 다르면 @table 생성필요함
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String account;
    private String password;
    private String status;
    private String email;
    private String phoneNumber;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    // User 1 : N OrderGroup
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderGroup> orderGroupList;
}