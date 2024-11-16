package com.dohi.StoreReservation.Repository;

import com.dohi.StoreReservation.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    // 상점 ID로 해당 상점에 대한 모든 리뷰 조회
    List<ReviewEntity> findByShopId(Long shopId);
}
