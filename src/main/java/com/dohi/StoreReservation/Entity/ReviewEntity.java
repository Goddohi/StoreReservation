package com.dohi.StoreReservation.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity(name = "REVIEW")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;  // 리뷰 작성자 (일반 회원)

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;  // 리뷰 대상 상점

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;  // 관련된 예약 정보

    private String content;  // 리뷰 내용
    @Min(1)
    @Max(5)
    private Integer rating;  // 평점 (1~5 등급)

    private LocalDateTime createdDate;  // 등록일시
}
