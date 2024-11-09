package com.dohi.StoreReservation.Entity;

import com.dohi.StoreReservation.Entity.Enum.ReservationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "RESERVATION")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;  // 예약한 일반 회원

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;  // 예약 대상 상점

    private LocalDateTime reservationDate;  // 예약 일시
    private ReservationStatus status;      // 예약 상태 (예: 예약 완료, 취소 등)
}
