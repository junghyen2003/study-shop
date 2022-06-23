package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    // 회원 엔티티와 일대일(단방향)로 매핑
    // 회원 엔티티에는 장바구니에 연관된 소스가 없음
    @JoinColumn(name = "member_id")
    // @JoinColumn 어노테이션을 이용해 매핑할 외래키를 지정. name 속성에는 매핑할 외래키의 이름을 설정
    // @JoinColumn의 name을 명시하지 않으면 JPA가 알아서 ID를 찾지만 컬럼명이 원하는대로 생성되지 않는 경우가 있기 때문에 직접 지정
    private Member member;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}
