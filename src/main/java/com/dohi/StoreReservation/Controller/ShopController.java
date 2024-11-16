package com.dohi.StoreReservation.Controller;

import com.dohi.StoreReservation.Entity.DTO.ShopRegisterDTO;
import com.dohi.StoreReservation.Entity.Enum.UserType;
import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Entity.UserEntity;
import com.dohi.StoreReservation.Service.ShopService;
import com.dohi.StoreReservation.Service.UserService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    /************************************************************************************
     * 함  수  명      : ShopResgister
     * 내      용      : 상점 등록
     * 설      명      : ShopRegisterDTO를 Json으로 받아 상점이 존재하지 않는 계정일 경우 상점등록을 처리
     *                  불가능 한경우 - 계정이 존재하지 않음 , 파트너 계정이 아님
     *  예제
     *  http://localhost:8080/api/shops/register
         {
         "user_id": "testShop",
         "shop_name": "테스트 상점",
         "latitude": 37.5665,
         "longitude": 126.9780,
         "description": "이곳은 테스트 상점입니다. 다양한 상품을 판매합니다."
         }
     ************************************************************************************/
    @PostMapping("/register")
    public ResponseEntity<Object> ShopResgister(@RequestBody ShopRegisterDTO shopRegisterDTO){
        UserEntity owner = userService.getUser(shopRegisterDTO.getUser_id());

        //============= 계정이 존재하지 않음
        if(owner==null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("계정이 존재하지 않습니다.");
        }
        //============ 파트너 계정이 아님
        else if (owner.getType() != UserType.PARTNER){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("파트너 계정만 상점이 등록 가능합니다.");
        }

        //=========== 등록 여부
        boolean isRegistered = shopService.register(shopRegisterDTO.getShopEntity(owner));
        if (isRegistered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("상점 등록이 완료 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 상점이 존재하는 계정입니다.");
        }
    }

    /************************************************************************************
     * 함  수  명      : getSortedShops
     * 내      용      : 상점 정렬
     * 설      명      :
     *
     *  http://localhost:8080/api/shops/sorted
     *  http://localhost:8080/api/shops/sorted?sortBy=name
     *  http://localhost:8080/api/shops/sorted?sortBy=name&userLatitude=37.5665&userLongitude=126.9780
     *
     ************************************************************************************/
    @GetMapping("/sorted")
    public List<ShopEntity> getSortedShops(@RequestParam(required = false) String sortBy,
                                           @RequestParam(required = false) Double userLatitude,
                                           @RequestParam(required = false) Double userLongitude) {
        // sortBy가 null이면 기본값 "name"으로 설정
        if (sortBy == null) {
            sortBy = "name";
        }

        switch (sortBy) {
            case "name":
                return shopService.getShopsSortedByName();
            case "rating":
                return shopService.getShopsSortedByRating();
            case "distance":
                if (userLatitude == null || userLongitude == null) {
                    throw new IllegalArgumentException("사용자의 위치 정보가 필요합니다.");
                }
                return shopService.getShopsSortedByDistance(userLatitude, userLongitude);
            default:
                throw new IllegalArgumentException("잘못된 정렬 기준입니다.");
        }
    }







}





