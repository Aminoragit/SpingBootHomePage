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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String status;
    private LocalDateTime arrivalDate;
    private Integer quantity;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    private Long itemId;
    private Long orderGroupId;




}

