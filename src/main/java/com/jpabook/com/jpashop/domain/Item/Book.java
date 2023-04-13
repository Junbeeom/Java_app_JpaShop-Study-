package com.jpabook.com.jpashop.domain.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//그냥 두면 기본 Book으로 들어가지만 학습을 위해
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;
}
