package com.jpabook.com.jpashop.domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//그냥 두면 기본 Movie으로 들어가지만 학습을 위해
@DiscriminatorValue("M")
@Getter @Setter
public class Movie extends Item {

    private String director;
    private String actor;
}
