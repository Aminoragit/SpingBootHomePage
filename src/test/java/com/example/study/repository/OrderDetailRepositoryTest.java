package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


public class OrderDetailRepositoryTest extends StudyApplicationTests {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Test
    public void create(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder_at(LocalDateTime.now());
        
        //어떤 User가 User테이블의 id항목
//        orderDetail.setUser_id(1L);

        //어떤 아이템을 Item테이블의 item_id항목
//        orderDetail.setItem_id(1L);

        OrderDetail newOrderdetail = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(newOrderdetail);

    }
}
