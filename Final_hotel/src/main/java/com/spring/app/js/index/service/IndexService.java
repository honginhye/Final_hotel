package com.spring.app.js.index.service;

import com.spring.app.hk.room.domain.RoomTypeDTO;
import com.spring.app.ih.dining.model.DiningDTO;
import com.spring.app.js.banner.domain.BannerDTO;
import com.spring.app.js.promotion.domain.PromotionDTO;

import java.util.List;

public interface IndexService {
    // 메인 배너 리스트 조회
    List<BannerDTO> getBannerList();

    // 메인용 객실 리스트 (최상위 2개)
    List<RoomTypeDTO> getMainRoomList();

    // 메인용 다이닝 리스트 (최상위 3개)
    List<DiningDTO> getMainDiningList();

    // 메인용 프로모션 리스트
    List<PromotionDTO> getPromoList();
}