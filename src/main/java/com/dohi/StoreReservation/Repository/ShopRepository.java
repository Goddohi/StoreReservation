package com.dohi.StoreReservation.Repository;

import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity,Long> {
    Optional<ShopEntity> findByOwner(UserEntity owner);


    @Query("SELECT s FROM SHOP s LEFT JOIN s.reviews r GROUP BY s.id ORDER BY COALESCE(AVG(r.rating), 0) DESC")
    List<ShopEntity> findAllSortedByAverageRating();

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM REVIEW r WHERE r.shop.id = :shopId")
    Double findAverageRatingByShopId(@Param("shopId") Long shopId);
}
