package com.jpabook.com.jpashop.domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//그냥 두면 기본 Album으로 들어가지만 학습을 위해
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
