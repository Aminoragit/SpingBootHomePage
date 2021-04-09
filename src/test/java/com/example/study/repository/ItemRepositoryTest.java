package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.enumclass.ItemStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class ItemRepositoryTest extends StudyApplicationTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){
        Item item = new Item();
        item.setStatus(ItemStatus.UNREGISTERED);
        item.setName("삼성 노트북");
        item.setTitle("삼성 노트북 A100");
        item.setContent("2021년형 노트북입니다.");
        item.setBrandName("삼성");
        item.setRegisteredAt(LocalDateTime.now());
        item.setCreatedAt(LocalDateTime.now());
        item.setCreatedBy("Partner01");
//        item.setPartner_id(1L);

        //아이템이 제대로 생성됬는지 확인하기 위한것+sql에 적용하기 위한것
        Item newItem = itemRepository.save(item);
        //제대로 저장됬다면 세이브된 아이템을 돌려줌
        Assert.assertNotNull(newItem);
    }


    @Test
    public void read(){
        Long id =1L;
        Optional<Item> item = itemRepository.findById(id);
        
        //값이 있는지 없는지만 확인하기
        Assert.assertTrue(item.isPresent());
        
        //직접보기
//        item.ifPresent(i->{
//            System.out.println(i);
//        });

    }

}
