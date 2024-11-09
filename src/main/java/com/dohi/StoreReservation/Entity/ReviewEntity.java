package com.dohi.StoreReservation.Entity;

import jakarta.persistence.*;

@Entity(name = "REVIEW")
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

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;  // 관련된 예약 정보

    private String content;  // 리뷰 내용
    private int rating;  // 평점 (1~5 등급)
}
