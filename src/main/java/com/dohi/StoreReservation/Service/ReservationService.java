package com.dohi.StoreReservation.Service;

import com.dohi.StoreReservation.Entity.DTO.ReservationRegisterDTO;
import com.dohi.StoreReservation.Entity.Enum.ReservationStatus;
import com.dohi.StoreReservation.Entity.ReservationEntity;
import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Entity.UserEntity;
import com.dohi.StoreReservation.Repository.ReservationRepository;
import com.dohi.StoreReservation.Repository.ShopRepository;
import com.dohi.StoreReservation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShopRepository shopRepository;


    /************************************************************************************
     * 함  수  명      : createReservation
     * 내      용      :  예약생성
     * 설      명      : 상점과 회원정보가 확인이 되면 예약을 등록해준다.
     ************************************************************************************/

    public ReservationEntity createReservation(ReservationRegisterDTO dto) {
        // UserEntity 조회
        UserEntity user = userRepository.findByPhoneNumber(dto.getUserPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        //ShopEntity 조회
        ShopEntity shop = shopRepository.findById(dto.getShop())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상점 ID입니다."));

        // ReservationEntity 생성
        return reservationRepository.save(dto.getReservationEntity(shop, user));
    }

    /************************************************************************************
     * 함  수  명      : getReservationsForShop
     * 내      용      : 예약조회
     * 설      명      :
     ************************************************************************************/
    public List<ReservationEntity> getReservationsForShop(Long shopId, String status, LocalDateTime startDate, LocalDateTime endDate) {
        List<ReservationEntity> reservations;

        // 필터 조건 적용
        if (status != null && startDate != null && endDate != null) {
            reservations = reservationRepository.findFilteredAllReservations(shopId, status, startDate, endDate);
        } else if(startDate != null && endDate != null){
            reservations = reservationRepository.findFilteredReservations(shopId, startDate, endDate);
        }else if (status != null) {
            reservations = reservationRepository.findFilteredStateReservations(shopId, status);
        }
        else {
            reservations = reservationRepository.findByShopId(shopId);
        }

        return reservations;
    }



    @Autowired
    private KioskService kioskService;

    // 예약 10분 전 체크 및 방문 확인
    /************************************************************************************
     * 함  수  명      : checkReservationBeforeArrival
     * 내      용      : 방문등록
     * 설      명      :
     ************************************************************************************/
    public String checkReservationBeforeArrival(Long reservationId) {
        Optional<ReservationEntity> reservationOptional = reservationRepository.findById(reservationId);

        if (reservationOptional.isPresent()) {
            ReservationEntity reservation = reservationOptional.get();
            if( reservation.getStatus() == ReservationStatus.CANCELLED ){
                return "이미 취소 된 건입니다.";
            }
            if( reservation.getStatus() == ReservationStatus.CONFIRMED ){
                return "이미 방문 등록이 되었습니다.";
            }
            if( reservation.getStatus() == ReservationStatus.COMPLETED ){
                return "이미 완료된 예약입니다.";
            }
            // 예약시간보다 10분이전에 왔을경우
            if (reservation.getReservationDate().minusMinutes(10).isAfter(LocalDateTime.now())) {
                kioskService.activateKioskForCheckIn(reservation);
                reservation.setStatus(ReservationStatus.CONFIRMED);
                reservationRepository.save(reservation);
                return "방문 등록이 완료 되었습니다.";
            } else if(reservation.getReservationDate().isAfter(LocalDateTime.now())){
                //그냥 10분과 예약시간 사이
                kioskService.activateKioskForCheckIn(reservation);
                reservation.setStatus(ReservationStatus.CONFIRMED);
                reservationRepository.save(reservation);
                return "방문 등록이 완료 순서대로 안내 도와드리겠습니다.";
            }
            else {
                reservation.setStatus(ReservationStatus.CANCELLED);  // 취소 상태로 변경
                reservationRepository.save(reservation);  // 상태 업데이트
                return "시간이 지나 자동 취소 된 예약 건입니다.";
            }
        }else {
            // 예약이 없는 경우 메시지 전송
            return "해당 예약은 없습니다";
        }
    }

    /************************************************************************************
     * 함  수  명      : completedReservation
     * 내      용      : 예약완료처리
     * 설      명      :
     ************************************************************************************/
    public String completedReservation(Long reservationId) {
        Optional<ReservationEntity> reservationOptional = reservationRepository.findById(reservationId);

        if (reservationOptional.isPresent()) {

            ReservationEntity reservation = reservationOptional.get();
            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                reservation.setStatus(ReservationStatus.COMPLETED);
                reservationRepository.save(reservation);
                return "예약 완료를 진행 하였습니다.";
            }

            else if (reservation.getStatus() == ReservationStatus.CANCELLED) {
                return "이미 취소 된 건입니다.";
            }
            else if (reservation.getStatus() == ReservationStatus.COMPLETED) {
                return "이미 완료된 예약입니다.";
            }
            else{   // (reservation.getStatus() == ReservationStatus.PENDING)
                return "아직 방문 등록이 되지 않은 예약입니다.";
            }

        } else {
            // 예약이 없는 경우 메시지 전송
            return "해당 예약은 없습니다";
        }
    }
}
