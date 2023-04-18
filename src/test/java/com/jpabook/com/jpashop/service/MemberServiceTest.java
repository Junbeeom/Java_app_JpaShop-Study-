package com.jpabook.com.jpashop.service;

import com.jpabook.com.jpashop.domain.Member;
import com.jpabook.com.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {


    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

     @Test
      public void 회원가입() throws Exception {
         //given
         Member member = new Member();
         member.setName("kim");

         //when
         Long savedId = memberService.join(member);

         //then
         assertEquals(member, memberRepository.findOne(savedId));
         
     }
      //try catch문에 적은 것 처럼 @Test(expected = IllegalStateException.class)로 적을 수 있음.
      @Test
       public void 중복_회원_예외() throws Exception {
          //given
          Member member1 = new Member();
          member1.setName("kim");

          Member member2 = new Member();
          member2.setName("kim");

          //when
          memberService.join(member1);
          //예외가 발생해야 한다.


          try {
              memberService.join(member2);
          } catch(IllegalStateException e) {
              return;
          }

          //then
          fail("예외가 발생해야 한다.");
          
      }

}