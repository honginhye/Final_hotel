package com.spring.app.js.promotion.service;

import java.util.List;
import com.spring.app.js.promotion.domain.PromotionDTO;

public interface PromotionService {

    /*
     * 특정 호텔의 현재 진행 중인 프로모션 목록 조회
     */
    List<PromotionDTO> getPromotionList(int hotelId);

    /*
     * 프로모션 상세 정보 조회
     */
    PromotionDTO getPromotionDetail(int promotionId);
}