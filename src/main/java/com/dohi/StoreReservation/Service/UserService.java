package com.dohi.StoreReservation.Service;

import com.dohi.StoreReservation.Entity.UserEntity;
import com.dohi.StoreReservation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // 사용자 인증
    public UserEntity authenticate(String user_id, String password) {
        Optional<UserEntity> user = userRepository.findById(user_id);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.orElse(null);
        }
        return null;
    }

    // 회원가입
    public boolean register(UserEntity user) {
        // 사용자 중복 확인
        if (userRepository.findById(user.getId()).isPresent()) {
            return false; // 이미 존재하는 사용자ID일경우
        }
        userRepository.save(user);
        return true;
    }
}
