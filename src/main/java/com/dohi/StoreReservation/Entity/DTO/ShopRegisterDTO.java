package com.dohi.StoreReservation.Entity.DTO;


import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ShopRegisterDTO {


    private String user_id;  // 상점 등록하는 아이디

    @NotBlank(message = "상점이름은 필수입니다.")
    @Size(min = 2, max = 25, message = "상점이름은 2자 이상 25자 이하여야 합니다.")
    private String shop_name; //상점 이름

    @NotBlank(message = "위치 설정이 잘못된 값입니다. 다시 확인 해주세요.")
    private double latitude;   // 위도

    @NotBlank(message = "위치 설정이 잘못된 값입니다. 다시 확인 해주세요.")
    private double longitude;  // 경도 - 해당 홈페이지에서 위치찾기 할것

    @NotBlank(message = "상점설명은 필수입니다.")
    @Size(min = 2, max = 255, message = "상점설명은 2자 이상 255자 이하여야 합니다.")
    private String description;  // 상점 설명

    public ShopEntity getShopEntity(UserEntity owner) {
        return ShopEntity.builder()
                .shopName(this.shop_name)
                .owner(owner)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .description(this.description).build();
    }
}
