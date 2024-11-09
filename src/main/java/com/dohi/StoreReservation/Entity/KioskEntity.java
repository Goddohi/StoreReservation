package com.dohi.StoreReservation.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "KIOSK")
public class KioskEntity {
    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name = "SHOP_ID")
    private ShopEntity shop;
}
