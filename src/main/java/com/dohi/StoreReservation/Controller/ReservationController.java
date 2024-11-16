package com.dohi.StoreReservation.Controller;

import com.dohi.StoreReservation.Entity.DTO.ReservationRegisterDTO;
import com.dohi.StoreReservation.Entity.DTO.ReviewRequestDto;
import com.dohi.StoreReservation.Entity.DTO.ShopRegisterDTO;
import com.dohi.StoreReservation.Entity.Enum.ReservationStatus;
import com.dohi.StoreReservation.Entity.ReservationEntity;
import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Entity.UserEntity;
import com.dohi.StoreReservation.Service.ReservationService;
import com.dohi.StoreReservation.Service.ReviewService;
import com.dohi.StoreReservation.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    UserService userService;
    @Autowired
    private ReviewService reviewService;

    /************************************************************************************
     * 함  수  명      : ShopResgister
     * 내      용      : 상점 등록
     * 설      명      : ShopRegisterDTO를 Json으로 받아 상점이 존재하지 않는 계정일 경우 상점등록을 처리
     *                  불가능 한경우 - 계정이 존재하지 않음 , 파트너 계정이 아님
     *  예제
     *  http://localhost:8080/api/reservations/register
     {
     "userPhoneNumber": "010-1234-1234",
     "shop": 1,
     "reservationDate": "2024-11-17T21:00:00",
     "numberOfGuests": 3
     }
     ************************************************************************************/
    @PostMapping("/register")
    public ResponseEntity<Object> createReservation(@Valid @RequestBody ReservationRegisterDTO dto) {
        System.out.println("예약 생성 시작: " + dto);
        try {
            ReservationEntity reservation = reservationService.createReservation(dto);
            return ResponseEntity.ok("예약이 성공적으로 생성되었습니다. 예약 ID: " + reservation.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("예약 생성 중 오류가 발생했습니다.");
        }

    }


    /************************************************************************************
     * 함  수  명      : getReservationsForShop
     * 내      용      : 예약 상황을 상점별로 확인 가능
     * 설      명      : 예상상태 필터링, 날짜,종료로 시간테이블별 가능
     *  예제
     *  http://localhost:8080/api/reservations/1/reservations
     ************************************************************************************/
    @GetMapping("/{shopId}/reservations")
    public ResponseEntity<List<ReservationEntity>> getReservationsForShop(
            @PathVariable Long shopId,
            @RequestParam(required = false) String status, // 예약 상태 필터링(Optional)
            @RequestParam(required = false) LocalDateTime startDate, // 시작 날짜 필터링(Optional)
            @RequestParam(required = false) LocalDateTime endDate // 종료 날짜 필터링(Optional)
    ) {
        try {
            List<ReservationEntity> reservations = reservationService.getReservationsForShop(shopId, status, startDate, endDate);
            return ResponseEntity.ok(reservations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 서버 오류
        }
    }

    /************************************************************************************
     * 함  수  명      : checkReservation
     * 내      용      : 방문등록
     * 설      명      : 예약했을 경우 방문등록을 해야 취소가 되지않음
     *  예제
     *  http://localhost:8080/api/reservations/check/1
     ************************************************************************************/
    @PostMapping("/check/{reservationId}")
    public ResponseEntity<String> checkReservation(@PathVariable Long reservationId) {
        try {
            String text = reservationService.checkReservationBeforeArrival(reservationId);
            return ResponseEntity.ok(text);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }
    }


    /************************************************************************************
     * 함  수  명      : completedReservation
     * 내      용      : 상점 등록
     * 설      명      : 상점에서 예약을 완료처리
     *  예제
     *  http://localhost:8080/api/reservations/check/1
     ************************************************************************************/
    @PostMapping("/completed/{reservationId}")
    public ResponseEntity<String> completedReservation(@PathVariable Long reservationId) {
        try {
            String text = reservationService.completedReservation(reservationId);
            return ResponseEntity.ok(text);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
        }
    }

    // 예약에 리뷰 추가
    /************************************************************************************
     * 함  수  명      : addReview
     * 내      용      : 리뷰등록
     * 설      명      : 예약이 완료가 되었을 경우 리뷰를 달 수 있음.
     *  예제
     *  http://localhost:8080/api/reservations/1/review
     {
     "content": "이 상점의 서비스가 매우 좋았습니다. 다시 방문할 예정입니다.",
     "rating": 5,
     "userId": "test",
     "storeId": 1
     }
     ************************************************************************************/
    @PostMapping("/{reservationId}/review")
    public ResponseEntity<String> addReview(
            @PathVariable Long reservationId,
            @Valid @RequestBody ReviewRequestDto dto) {

        try {
            reviewService.addReview(reservationId, dto);
            return ResponseEntity.ok("리뷰가 성공적으로 추가되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("리뷰는 완료된 예약에 대해서만 작성할 수 있습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 예약을 찾을 수 없습니다.");
        }
    }
}
