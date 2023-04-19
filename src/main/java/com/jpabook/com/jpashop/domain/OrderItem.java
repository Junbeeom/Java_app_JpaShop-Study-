package com.jpabook.com.jpashop.domain;

import com.jpabook.com.jpashop.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {


    //다른 곳에서 new OerderItem 하는 것을 방지 할 수 있음.
    //Class위에 NoArgsConstructor(access = AccessLevel.PROTECTED) 하면 생성자 방지 해줌.
    protected OrderItem() {

    }

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    //주문 가격
    private int orderPrice;
    //주문 수량
    private int count;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회로직//

    /**
     * 주문상품 전체 가격 조회
     * @return
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
