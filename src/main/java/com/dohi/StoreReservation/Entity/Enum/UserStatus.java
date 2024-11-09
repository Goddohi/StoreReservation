package com.dohi.StoreReservation.Entity.Enum;

public enum UserStatus {
    ACTIVE,     // 활성 상태 (사용 가능한 계정)
    INACTIVE,   // 비활성 상태 (일시적으로 사용 중지)
    SUSPENDED,  // 정지 상태 (규칙 위반 등으로 인한 계정 정지)
    DELETED;     // 탈퇴 상태 (사용자가 계정을 삭제)
}
