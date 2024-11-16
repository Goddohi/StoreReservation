package com.dohi.StoreReservation.Service;

import com.dohi.StoreReservation.Entity.ReservationEntity;
import org.springframework.stereotype.Service;

@Service
public class KioskService {
    /************************************************************************************
     * 함  수  명      : activateKioskForCheckIn
     * 내      용      : 키오스크에서 방문확인 (출력으로 확인용)
     * 설      명      :
     ************************************************************************************/
    public void activateKioskForCheckIn(ReservationEntity reservation) {
        System.out.println("키오스크에서 방문 확인 진행: " + reservation.getId());
    }
}
