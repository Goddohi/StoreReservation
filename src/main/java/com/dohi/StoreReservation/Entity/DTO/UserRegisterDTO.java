package com.dohi.StoreReservation.Entity.DTO;

import com.dohi.StoreReservation.Entity.Enum.UserType;
import com.dohi.StoreReservation.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
public class UserRegisterDTO {
    String userID;
    String password;
    UserType type;


    UserRegisterDTO(String userID, String password) {
        this.userID = userID;
        this.password = password;
        this.type = UserType.GENERAL;
    }


    UserRegisterDTO(String userID, String password,UserType type) {
        this.userID = userID;
        this.password = password;
        this.type = type;
    }


    public UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(this.userID);
        userEntity.setPassword(this.password);

        //해당 로직으로 확실하게 Partner일때만 파트너 제공
        this.type = this.type==UserType.PARTNER ? UserType.PARTNER : UserType.GENERAL;
        userEntity.setType(this.type);

        userEntity.setCreatedDate(LocalDateTime.now());
        return userEntity;
    }
}
