package com.dohi.StoreReservation.Entity;

import jakarta.persistence.*;

@Entity(name = "SHOP")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    private String location;     // 상점 위치
    private String description;  // 상점 설명

    @OneToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;  // 상점 등록한 파트너 회원


}
