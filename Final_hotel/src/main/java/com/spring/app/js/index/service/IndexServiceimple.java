package com.spring.app.js.index.service;

import com.spring.app.hk.room.domain.RoomTypeDTO;
import com.spring.app.ih.dining.model.DiningDTO;
import com.spring.app.js.banner.domain.BannerDTO;
import com.spring.app.js.index.model.IndexDAO;
import com.spring.app.js.promotion.domain.PromotionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IndexServiceimple implements IndexService {

    @Autowired
    private IndexDAO inDao; // 데이터베이스 접근 객체

    @Override
    public List<BannerDTO> getMainBannerList() {
        return inDao.getMainBannerList();
    }

    @Override
    public List<RoomTypeDTO> getMainRoomList() {
        // 나중에 여기서 "브랜드별 노출 순위" 등의 로직을 추가할 수 있습니다.
        return inDao.getMainRoomList();
    }

    @Override
    public List<DiningDTO> getMainDiningList() {
        return inDao.getMainDiningList();
    }

    @Override
    public List<PromotionDTO> getPromoCardList() {
        return inDao.getPromoCardList();
    }
}