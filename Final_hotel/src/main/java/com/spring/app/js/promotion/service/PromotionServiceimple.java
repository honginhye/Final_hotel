package com.spring.app.js.promotion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.js.promotion.domain.PromotionDTO;
import com.spring.app.js.promotion.model.PromotionDAO;

@Service
public class PromotionServiceimple implements PromotionService {

    @Autowired
    private PromotionDAO proDao;

    @Override
    public List<PromotionDTO> getPromotionList(int hotelId) {
        // Mapper를 통해 DB에서 조인된 프로모션 목록을 가져옵니다.
        // 현재 날짜 기준 필터링 및 활성화 여부는 Mapper SQL(WHERE절)에서 처리됩니다.
        return proDao.getPromotionList(hotelId);
    }

    @Override
    public PromotionDTO getPromotionDetail(int promotionId) {
        // 특정 프로모션의 상세 내용을 조회합니다.
        return proDao.getPromotionDetail(promotionId);
    }
}