package com.dohi.StoreReservation.Service;

import com.dohi.StoreReservation.Entity.DTO.ReviewRequestDto;
import com.dohi.StoreReservation.Entity.Enum.ReservationStatus;
import com.dohi.StoreReservation.Entity.ReservationEntity;
import com.dohi.StoreReservation.Entity.ReviewEntity;
import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Entity.UserEntity;
import com.dohi.StoreReservation.Repository.ReservationRepository;
import com.dohi.StoreReservation.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReviewRepository reviewRepository;

    /************************************************************************************
     * 함  수  명      : addReview
     * 내      용      : 리뷰 저장
     * 설      명      :
     ************************************************************************************/
    public void addReview(Long reservationId, ReviewRequestDto dto) {
        Optional<ReservationEntity> reservationOptional = reservationRepository.findById(reservationId);

        if (reservationOptional.isPresent()) {
            ReservationEntity reservation = reservationOptional.get();
            UserEntity user = reservation.getUser();
            if(!user.getId().equals(dto.getUserId())){
                throw new IllegalArgumentException("예약한 사용자와 리뷰를 다는 사용자가 다릅니다.");
            }

            ShopEntity shop = reservation.getShop();

            if(!shop.getId().equals(dto.getStoreId())){
                throw new IllegalArgumentException("예약한 상점과 리뷰가 달리는 상점이 다릅니다.");
            }

            // 예약 상태가 COMPLETED일 경우에만 리뷰 추가 가능
            if (reservation.getStatus() == ReservationStatus.COMPLETED) {
                // 리뷰 추가 로직
                ReviewEntity review = dto.getReviewEntity(user,shop);
                reviewRepository.save(review);
            } else {
                // 예약이 완료되지 않은 경우 리뷰 불가능 메시지 처리
                throw new IllegalStateException("리뷰는 완료된 예약에 대해서만 작성할 수 있습니다.");
            }
        } else {
            // 예약이 존재하지 않을 경우
            throw new IllegalArgumentException("해당 예약을 찾을 수 없습니다.");
        }
    }
}
