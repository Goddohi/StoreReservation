package com.dohi.StoreReservation.Entity.DTO;

import com.dohi.StoreReservation.Entity.Enum.ReservationStatus;
import com.dohi.StoreReservation.Entity.ReservationEntity;
import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationRegisterDTO {

    @NotBlank(message = "전화번호는 필수입니다.")
    private String userPhoneNumber;  // 예약한 일반 회원

    @NotNull(message = "상점 ID는 필수입니다.")
    private Long shop;  // 예약 대상 상점

    @Min(1)
    private Integer numberOfGuests;

    @NotNull
    private LocalDateTime reservationDate;

    public ReservationEntity getReservationEntity(ShopEntity shopEntity, UserEntity userEntity) {
       return ReservationEntity.builder()
                    .user(userEntity)
                    .shop(shopEntity)
                    .reservationDate(this.reservationDate)
                    .numberOfGuests(this.numberOfGuests)
                    .createdDate(LocalDateTime.now())
                    .status(ReservationStatus.PENDING)
                    .build();

    }
}
