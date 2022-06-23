package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // 하나의 상품은 여러 주문 상품으로 들어갈 수 있으므로 주문 상품 기준으로 다대일(n:1) 단방향 매핑
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    // 한 번의 주문에 여러 개의 상품을 주문할 수 있으므로 주문 상품 엔티티와 주문 엔티티를 다대일 단방향 매핑을 먼저 설정
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * 주문 가격
     */
    private int orderPrice;

    /**
     * 수량
     */
    private int count;

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }
}
