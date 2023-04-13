package com.jpabook.com.jpashop.domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//구현체를 가지고 작업할 것이기 때문에 추상클래스로 만듬.
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

}
