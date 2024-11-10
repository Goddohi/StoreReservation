package com.dohi.StoreReservation.Controller;

import com.dohi.StoreReservation.Entity.DTO.UserLoginDTO;
import com.dohi.StoreReservation.Entity.DTO.UserRegisterDTO;
import com.dohi.StoreReservation.Entity.Enum.UserType;
import com.dohi.StoreReservation.Entity.UserEntity;
import com.dohi.StoreReservation.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 로그인 요청
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {
        if (userLoginDTO.getId() != null && userLoginDTO.getPassword() != null) {
            UserEntity user = userService.authenticate(userLoginDTO.getId(),userLoginDTO.getPassword());
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("다시 확인하여 실행해주세요.");
            }
        } else { //공란존재시
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("입력해주세요");
        }


    }

    // 회원가입 요청
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO user) {
        // 유효성 검사: ID와 비밀번호가 null이 아닌지 확인
        if (user.getUserID() == null || user.getUserID().isEmpty()) {
            return ResponseEntity.badRequest().body("ID를 입력해 주세요.");
        }
        // 비밀번호
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("비밀번호를 입력해 주세요.");
        }

        // UserType 기본값 설정 해당 로직 getUserEntity에 집어넣음
        /*
        if (user.getType() == null) {
            user = UserRegisterDTO.builder()
                    .userID(user.getUserID())
                    .password(user.getPassword())
                    .type(UserType.GENERAL)
                    .build();
        }
        */

        // 회원가입 처리
        if (userService.register(user.getUserEntity())) {
            return ResponseEntity.ok(user.getUserEntity().getId() + "로 회원가입이 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }
    }

}