package com.dohi.StoreReservation.Entity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
public class UserLoginDTO {
    @NotBlank(message = "아이디를 입력해주세요.")
    String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;

}
