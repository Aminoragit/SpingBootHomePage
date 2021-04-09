package com.example.study.model.entity;

import com.example.study.model.enumclass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data //Data를 붙이면 toString으로 sql전달시 깔끔하게 정의해줌
@AllArgsConstructor
@NoArgsConstructor
@Entity //@Table(name="user")가 자동생성되므로 굳이 선언안해도됨,
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"orderGroupList"})        // 단, class명이랑 db의 테이블명이 같아야함 다르면 @table 생성필요함
@Builder //롬복에서 제공하는 생성자 형식을 구성할때 원하는 형식 User(address,age)을 일일히 적용해줘야하는걸 자동으로 해준다.
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String account;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status; // REGISTERED / UNREGISTERED / WAITING 같이 정해져 있음


    private String email;
    private String phoneNumber;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;


    // User 1 : N OrderGroup
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderGroup> orderGroupList;
}