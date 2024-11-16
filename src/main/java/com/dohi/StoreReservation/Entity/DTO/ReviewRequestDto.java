package com.dohi.StoreReservation.Entity.DTO;

import com.dohi.StoreReservation.Entity.ReviewEntity;
import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Entity.UserEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewRequestDto{
    @NotBlank(message = "리뷰 내용은 비어 있을 수 없습니다.")
    private String content; // 리뷰 내용
    @Min(1)
    @Max(5)
    private Integer rating;  // 평점 (1~5 등급)

    @NotBlank(message = "사용자는 비어 있을 수 없습니다.")
    private String userId;   // 사용자

    @NotNull(message = "상점 정보는 비어 있을 수 없습니다.")
    private Long storeId;

    public ReviewEntity getReviewEntity(UserEntity user, ShopEntity shop){
        return ReviewEntity.builder()
                .content(this.content)
                .rating(this.rating)
                .user(user)
                .shop(shop)
                .createdDate(LocalDateTime.now())
                .build();

    }
}
