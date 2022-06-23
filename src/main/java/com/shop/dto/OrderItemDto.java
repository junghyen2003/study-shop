package com.shop.dto;

import com.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

// 조회한 주문 데이터를 화면에 보낼 때 사용할 DTO
@Getter
@Setter
public class OrderItemDto {

    // OrderItemDto 클래스의 생성자로 OrderItem 객체와 이미지 경로를 파라미터로 받아서 멤버 변수 값을 세팅
    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }

    /**
     * 상품명
     */
    private String itemNm;

    /**
     * 주문 수량
     */
    private int count;

    /**
     * 주문 금액
     */
    private int orderPrice;

    /**
     * 상품 이미지 경로
     */
    private String imgUrl;
}
