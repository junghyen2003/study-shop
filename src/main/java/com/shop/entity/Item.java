package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Item {

    /**
     * 상품 코드
     */
    private Long id;

    /**
     * 상품명
     */
    private String itemNm;

    /**
     * 가격
     */
    private int price;

    /**
     * 재고 수량
     */
    private int stockNumber;

    /**
     * 상품 상세 설명
     */
    private String itemDetail;

    /**
     * 상품 판매 상태
     */
    private ItemSellStatus itemSellStatus;

    /**
     * 등록 시간
     */
    private LocalDateTime regTime;

    /**
     * 수정 시간
     */
    private LocalDateTime updateTime;
}
