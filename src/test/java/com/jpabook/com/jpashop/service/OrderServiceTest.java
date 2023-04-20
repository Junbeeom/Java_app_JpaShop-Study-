package com.jpabook.com.jpashop.service;

import com.jpabook.com.jpashop.domain.Address;
import com.jpabook.com.jpashop.domain.Item.Book;
import com.jpabook.com.jpashop.domain.Item.Item;
import com.jpabook.com.jpashop.domain.Member;
import com.jpabook.com.jpashop.domain.Order;
import com.jpabook.com.jpashop.domain.OrderStatus;
import com.jpabook.com.jpashop.exception.NotEnoughStockException;
import com.jpabook.com.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    /**
     * 기능 단위 테스트가 아니라 통합 테스트이기 때문에 좋은 테스트라고 보긴 어렵다.
     * JPA가 엮여서 전체적인 통합 테스트를 학습하는 것이기 때문..
     */
     @Test
      public void 상품주문() throws Exception {
         //given
         Member member = createMember();

         Book book = createBook("시골 JPA", 10000, 10);

         int orderCount = 2;

         //when
         Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

         //then
         Order getOrder = orderRepository.findOne(orderId);

         assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
         assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
         assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
         assertEquals("주문 수량만큼 재고가 줄어야한다.", 8, book.getStockQuantity());

     }

    @Test
      public void 주문취소() throws Exception {
         //given
        Member member = createMember();
        Book item = createBook("시골JPA", 10000,10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

         //when(실제 테스트 하고 싶은 것.)
        orderService.cancelOrder(orderId);

         //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }


      @Test(expected = NotEnoughStockException.class)
       public void 상품주문_재고수량초과() throws Exception {
          //given
          Member member = createMember();

          Item item = createBook("시골JPA", 10000, 10);

          int orderCount = 11;

          //when
          orderService.order(member.getId(), item.getId(), orderCount);

          //then
          fail("재고수량 부족 예외가 발생해야 한다.");

      }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    //옵션+커맨드+M
    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }


}