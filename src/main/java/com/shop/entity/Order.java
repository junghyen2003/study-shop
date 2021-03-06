package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
// 정렬 시 사용하는 키워드 "order"을 회피하기 위한 "orders"로 테이블 이름 변경
@Getter
@Setter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // 한 명의 회원이 여러번 주문을 할 수 있으므로 주문 엔티티 기준에서 다대일(n:1) 단방향 매핑
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 주문일
     */
    private LocalDateTime orderDate;

    /**
     * 주문 상태
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    // 주문 상품 엔티티와 일대다 매핑
    // 외래키(order_id)가 order_item 테이블에 있으므로 연관관계의 주인은 OrderItem 엔티티
    // Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정
    // mappedBy = "order" 속성 값의 의미는 OrderItem에 있는 Order에 의해 관리된다는 의미
    // 즉 연관 관계의 주인 필드인 order를 mappedBy의 값으로 세팅
    // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeType.ALL 옵션 설정
    // orphanRemoval = true 옵션을 통해 고아 객체 제거
    private List<OrderItem> orderItems = new ArrayList<>();
    // 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        // Order 엔티티와 OrderItem 엔티티가 양방향 참조 관계 이므로, orderItem 객체에도 order 객체를 세팅
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);

        for (OrderItem orderItem : orderItemList) {
            // 상품 페이지에서는 1개의 상품만 주문하지만, 장바구니 페이지에서는 한번에 여러 개의 상품을 주문 할 수 있음
            // 따라서 여러 개의 주문 상품을 담을 수 있도록 리스트 형태로 파라미터 값을 받으며, 주문 객체에 orderItem 객체를 추가
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
