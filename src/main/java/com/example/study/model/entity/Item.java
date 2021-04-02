package com.example.study.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor //기본생성자
@Entity
public class Item {


    @Id //인덱스 컬럼이름 id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SQl에서 쓸거라는뜻
    private Long id;
    private String status;

    private String name;

    private String title;

    private String content;

    private Integer price;

    private String brandName;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

//
//    //1:N
//
//    //LAZY = 지연로딩 , EAGER = 즉시로딩
//    //LAZY = SELECT * FROM item where id=?
//    //EAGER-> 모든 JOIN을 실행 //성능저하 조심 1:1일때만 추천
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
//    private List<OrderDetail> orderDetailList;
}
