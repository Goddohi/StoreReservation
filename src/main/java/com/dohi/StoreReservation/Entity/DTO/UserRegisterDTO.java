package com.dohi.StoreReservation.Entity.DTO;

import com.dohi.StoreReservation.Entity.Enum.UserStatus;
import com.dohi.StoreReservation.Entity.Enum.UserType;
import com.dohi.StoreReservation.Entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
public class UserRegisterDTO {
    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 4, max = 255, message = "아이디는 4자 이상 25자 이하여야 합니다.")
    String userID;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 255, message = "비밀번호는 8자 이상 25자 이하여야 합니다.")
    String password;

    @NotBlank(message = "핸드폰 번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "^01[0-9]-[0-9]{4}-[0-9]{4}$", message = "핸드폰 번호의 양식을 확인해주세요. 예: 010-1234-5678")
    String phoneNumber;
    UserType type;


    UserRegisterDTO(String userID, String password, String phoneNumber) {
        this.userID = userID;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.type = UserType.GENERAL;
    }


    UserRegisterDTO(String userID, String password, String phoneNumber,UserType type) {
        this.userID = userID;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }


    public UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(this.userID);
        userEntity.setPassword(this.password);
        userEntity.setPhoneNumber(this.phoneNumber);

        //해당 로직으로 확실하게 Partner일때만 파트너 제공
        this.type = this.type==UserType.PARTNER ? UserType.PARTNER : UserType.GENERAL;
        userEntity.setType(this.type);
        userEntity.setStatus(UserStatus.ACTIVE);

        userEntity.setCreatedDate(LocalDateTime.now());
        return userEntity;
    }
}
