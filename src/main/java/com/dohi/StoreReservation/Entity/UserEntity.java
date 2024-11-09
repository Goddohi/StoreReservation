package com.dohi.StoreReservation.Entity;

import com.dohi.StoreReservation.Entity.Enum.UserStatus;
import com.dohi.StoreReservation.Entity.Enum.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

    @Id
    private String Id;

    private String Name;

    private String PhoneNM; //핸드폰 번호
    private String Password;

    @Enumerated(EnumType.STRING)
    private UserType Type;


    @Enumerated(EnumType.STRING)
    private UserStatus status;  // 회원 상태 (예: 활성, 탈퇴 등)

    private String createdDate;
}


