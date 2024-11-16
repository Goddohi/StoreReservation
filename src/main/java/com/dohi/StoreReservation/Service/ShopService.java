package com.dohi.StoreReservation.Service;

import com.dohi.StoreReservation.Entity.ReviewEntity;
import com.dohi.StoreReservation.Entity.ShopEntity;
import com.dohi.StoreReservation.Repository.ReviewRepository;
import com.dohi.StoreReservation.Repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ReviewRepository reviewRepository;


    /************************************************************************************
     * 함  수  명      : register
     * 내      용      : 상점등록
     * 설      명      :
     ************************************************************************************/
    public boolean register(ShopEntity shop) {
        // 사용자 중복 확인
        if (shopRepository.findByOwner(shop.getOwner()).isPresent()) {
            return false; // 이미 존재하는 상점이 존재하는 사용자ID 일경우 불가능
        }
        shopRepository.save(shop);
        return true;
    }




    /************************************************************************************
     * 함  수  명      : calculateDistance
     * 내      용      : 사용자와 상점간 거리계산
     * 설      명      :
     ************************************************************************************/
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (km)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // 거리 (단위: km)
    }

    /************************************************************************************
     * 함  수  명      : getShopsSortedByDistance
     * 내      용      : 사용자와 상점간 거리순으로 정렬
     * 설      명      :
     ************************************************************************************/
    public List<ShopEntity> getShopsSortedByDistance(double userLatitude, double userLongitude) {
        List<ShopEntity> shops = shopRepository.findAll();
        return shops.stream()
                .sorted((shop1, shop2) -> {
                    double distance1 = calculateDistance(userLatitude, userLongitude, shop1.getLatitude(), shop1.getLongitude());
                    double distance2 = calculateDistance(userLatitude, userLongitude, shop2.getLatitude(), shop2.getLongitude());
                    return Double.compare(distance1, distance2);
                })
                .collect(Collectors.toList());
    }
    /************************************************************************************
     * 함  수  명      : getShopsSortedByName
     * 내      용      : 상점의 이름순으로 정렬
     * 설      명      :
     ************************************************************************************/
    public List<ShopEntity> getShopsSortedByName() {
        return shopRepository.findAll(Sort.by(Sort.Order.asc("shopName")));
    }
    /************************************************************************************
     * 함  수  명      : getShopsSortedByRating
     * 내      용      : 상점의 평점순으로 정렬
     * 설      명      :
     ************************************************************************************/
    public List<ShopEntity> getShopsSortedByRating() {
        return shopRepository.findAllSortedByAverageRating();
    }



}
