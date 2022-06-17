package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    // @Query 어노테이션 안에 JPQL(Java Persistence Query Language)로 작성한 쿼리문 삽입
    // 데이터베이스에 대해 독립적
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
    // @Param 어노테이션을 이용하여 파라미터로 넘어온 값을 JPQL에 들어갈 변수로 지정
    // itemDetail 변수를 "like % %" 사이에 ":itemDetail"로 값이 들어가도록 작성

    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    // @Query에 nativeQuery를 true로 함으로써 네이티브 쿼리문을 실행
    // 쿼리문이 데이터베이스에 종속되어 사용
    // 기존에 사용하던 통계용 쿼리처럼 복잡한 쿼리를 그대로 사용해야하는 경우 활용
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
