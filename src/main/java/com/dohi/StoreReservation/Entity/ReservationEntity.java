package com.dohi.StoreReservation.Entity;

import com.dohi.StoreReservation.Entity.Enum.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "RESERVATION")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    @Min(value = 1, message = "방문 인원수는 최소 1명 이상이어야 합니다.")
    @Column(nullable = false)
    private Integer numberOfGuests;  // 방문 인원수

    @Column(nullable = false)
    private LocalDateTime reservationDate;  // 방문 예약 일시

    private LocalDateTime createdDate;  // 예약이 들어간 시간

    @Setter
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;      // 예약 상태 (예: 예약 완료, 취소 등)


}
