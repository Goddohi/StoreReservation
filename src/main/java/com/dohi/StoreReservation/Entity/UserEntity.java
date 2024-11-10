package com.dohi.StoreReservation.Entity;

import com.dohi.StoreReservation.Entity.Enum.UserStatus;
import com.dohi.StoreReservation.Entity.Enum.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.lang.model.element.Name;
import java.time.LocalDateTime;

@Entity(name = "USER")
@Getter
@Setter
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber; // 핸드폰 번호

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status; // 회원 상태 (예: 활성, 탈퇴 등)

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "created_date")
    private LocalDateTime createdDate; // 등록 날짜 및 시간
}


