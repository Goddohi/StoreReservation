package com.dohi.StoreReservation.Repository;


import com.dohi.StoreReservation.Entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {


    // 특정 상점의 모든 예약 조회
    List<ReservationEntity> findByShopId(Long shopId);

    // 필터 조건에 따른 예약 조회
    @Query("SELECT r FROM RESERVATION r WHERE r.shop.id = :shopId " +
            "AND (:status IS NULL OR r.status = :status) " +
            "AND (:startDate IS NULL OR r.reservationDate >= :startDate) " +
            "AND (:endDate IS NULL OR r.reservationDate <= :endDate)")
    List<ReservationEntity> findFilteredAllReservations(@Param("shopId") Long shopId,
                                                     @Param("status") String status,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);

    // 필터 조건에 따른 예약 조회
    @Query("SELECT r FROM RESERVATION r WHERE r.shop.id = :shopId " +
            "AND (:startDate IS NULL OR r.reservationDate >= :startDate) " +
            "AND (:endDate IS NULL OR r.reservationDate <= :endDate)")
    List<ReservationEntity> findFilteredReservations(@Param("shopId") Long shopId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);
    // 필터 조건에 따른 예약 조회
    @Query("SELECT r FROM RESERVATION r WHERE r.shop.id = :shopId " +
            "AND (:status IS NULL OR r.status = :status) ")
    List<ReservationEntity> findFilteredStateReservations(@Param("shopId") Long shopId,
                                                          @Param("status") String status);
}
