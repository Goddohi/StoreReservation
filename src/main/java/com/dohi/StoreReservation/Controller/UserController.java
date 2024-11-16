package com.dohi.StoreReservation.Controller;

import com.dohi.StoreReservation.Entity.DTO.UserLoginDTO;
import com.dohi.StoreReservation.Entity.DTO.UserRegisterDTO;
import com.dohi.StoreReservation.Entity.Enum.UserStatus;
import com.dohi.StoreReservation.Entity.Enum.UserType;
import com.dohi.StoreReservation.Entity.UserEntity;
import com.dohi.StoreReservation.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /************************************************************************************
     * 함  수  명      : login
     * 내      용      : User 로그인 요청
     * 설      명      : UserLoginDTO을 받아 유저아이디와 비번이 동일할경우에 Ok
     * 예제  http://localhost:8080/api/users/login
     *
         {
         "id": "test1",
         "password": "a12345678"
         }
     ====
         {
         "id": "testShop",
         "password": "a12345678"
         }

     ************************************************************************************/
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO) {

        if (userLoginDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("입력해주세요");
        }
        if (userLoginDTO.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("입력해주세요");
        }

        UserEntity user = userService.authenticate(userLoginDTO.getId(),userLoginDTO.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("다시 확인하여 실행해주세요.");
        }else if (user.getStatus() == UserStatus.ACTIVE){
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("계정이 활성화 상태가 아닙니다. 확인부탁드립니다.");
        }


    }
    /************************************************************************************
     * 함  수  명      : UserRegister
     * 내      용      : User 회원가입
     * 설      명      : UserRegisterDTO 받아 유저아이디가 존재하지 않을경우에 회원가입(Insert)
     *  예  제
     *   http://localhost:8080/api/users/register
     *
         {
         "userID": "test1",
         "password": "a12345678",
         "phoneNumber": "010-1234-1234"

         }
         == 혹은
         {
         "userID": "testShop",
         "password": "a12345678",
         "phoneNumber": "010-1234-5678",
         "type" : "PARTNER"
         }
     ************************************************************************************/
    @PostMapping("/register")
    public ResponseEntity<String> UserRegister(@RequestBody @Valid UserRegisterDTO user) {
        // 유효성 검사: ID와 비밀번호가 null이 아닌지 확인
        if (user.getUserID() == null || user.getUserID().isEmpty()) {
            return ResponseEntity.badRequest().body("ID를 입력해 주세요.");
        }
        // 비밀번호
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("비밀번호를 입력해 주세요.");
        }
        // 회원가입 처리
        if (userService.register(user.getUserEntity())) {
            return ResponseEntity.ok(user.getUserEntity().getId() + "로 회원가입이 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }
    }

}