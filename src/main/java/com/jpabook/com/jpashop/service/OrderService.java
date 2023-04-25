package com.jpabook.com.jpashop.service;

import com.jpabook.com.jpashop.domain.Delivery;
import com.jpabook.com.jpashop.domain.Item.Item;
import com.jpabook.com.jpashop.domain.Member;
import com.jpabook.com.jpashop.domain.Order;
import com.jpabook.com.jpashop.domain.OrderItem;
import com.jpabook.com.jpashop.repository.ItemRepository;
import com.jpabook.com.jpashop.repository.MemberRepository;
import com.jpabook.com.jpashop.repository.OrderRepository;
import com.jpabook.com.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        /**
         *cascade 범위의 대해서..
         * Order가 delivery, oderItem을 관리하기 때문에 이 그림에서만 사용해야 한다..
         * 참조하는 경우,, 주인이 딱 private일 경우에만 사용해야 한다.
         * 정리하자면 Delivery, orderItem은 현재 order 말고는 아무도 사용 안함.(Order 말고는 참조를 하지 않음.)
         * 다른곳에서 참조하는 경우가 있다면 CAScade를 사용 하면 안됨. > 별도의 repository를 생성해서 persist 해야 한다.
         * 감을 잡고 나서.. cascade를 사용하는 것이 더 나을 수도 있음.
         */
        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }


    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 Entity 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();

        /**
         * JPA는 변경감지를 통해 알아서 변경된 데이터를 update 해줌..
         * Mybatis 같은 경우.. 취소후에 변경된 데이터를 다시 가져와서. udpate 해줘야 하지만.. jpa는 알아서 처리해줌.
         */
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
