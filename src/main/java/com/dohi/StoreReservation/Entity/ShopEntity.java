package com.dohi.StoreReservation.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "SHOP")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String shopName;
    private double latitude;   // 위도
    private double longitude;  // 경도
    private String description;  // 상점 설명

    @OneToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;  // 상점 등록한 파트너 회원

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;  // 상점에 대한 여러 리뷰들


}
