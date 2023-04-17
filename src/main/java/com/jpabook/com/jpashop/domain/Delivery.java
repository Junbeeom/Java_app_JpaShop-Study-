package com.jpabook.com.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    //READY, COMP
    //ORDINAL은 숫자로 나오고 중간에 추가되는 값이 있을때는 사용 못함, String 이용 하기
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
