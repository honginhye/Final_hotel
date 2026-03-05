package com.spring.app.js.promotion.model;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.spring.app.js.promotion.domain.PromotionDTO;

@Mapper
public interface PromotionDAO {

    // 1. 특정 호텔의 진행 중인 프로모션 목록 조회
    List<PromotionDTO> getPromotionList(int hotelId);

    // 2. 프로모션 상세 조회 (필요 시)
    PromotionDTO getPromotionDetail(int promotionId);
}