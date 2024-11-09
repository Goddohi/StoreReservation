package com.dohi.StoreReservation.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "SHOP")
public class ShopEntity {
    @Id
    public Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;  // 상점 등록한 파트너 회원
    public String ShopName;

}
