package com.example.study.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Data //Data를 붙이면 toString으로 sql전달시 깔끔하게 정의해줌
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = {"settlement"})
@EntityListeners(AuditingEntityListener.class)
@Builder //롬복에서 제공하는 생성자 형식을 구성할때 원하는 형식 User(address,age)을 일일히 적용해줘야하는걸 자동으로 해준다.
@Accessors(chain = true)
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal price;

}
