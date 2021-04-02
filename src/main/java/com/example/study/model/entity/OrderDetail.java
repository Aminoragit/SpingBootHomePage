package com.example.study.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor //기본생성자
@Entity
//@ToString(exclude = {"user","item"})
public class OrderDetail {

    @Id //인덱스 컬럼이름 id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SQl에서 쓸거라는뜻
    private Long id;
    private String status;
    private LocalDateTime order_at;
    private LocalDateTime arrivalDate;
    private Integer quantity;
    private BigDecimal totalPrice;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;


//    //N:1
//    //자신은 N 유저는 1이다 이떄는
    @ManyToOne
    private User user;
    //private Long user_id;가 위에처럼 변경된거임

    @ManyToOne
    private Item item;

}

