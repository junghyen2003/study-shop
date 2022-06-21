package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
// Auditing을 적용하기 위해 @EntityListeners 어노테이션 적용
@MappedSuperclass
// 공통 매핑정보가 필요 할 때 사용하는 어노테이션. 부모 클래스를 상속받는 자식클래스에 매핑 정보만! 제공.
// JPA Entity는 Entity 혹은 MappedSuperclass 만 상속 받을 수 있다.
@Getter
@Setter
public abstract class BaseTimeEntity {

    @CreatedDate
    // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장
    // 등록일
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate
    // 엔티티의 값을 변경 할 때 시간을 자동으로 저장
    // 수정일
    private LocalDateTime updateTime;
}
