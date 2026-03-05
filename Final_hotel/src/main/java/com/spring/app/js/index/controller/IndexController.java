package com.spring.app.js.index.controller;

import com.spring.app.hk.room.domain.RoomTypeDTO;
import com.spring.app.ih.dining.model.DiningDTO;
import com.spring.app.js.banner.domain.BannerDTO;
import com.spring.app.js.index.service.IndexService;
import com.spring.app.js.promotion.domain.PromotionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IndexService service; 

    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        
        // 1. [수정] 메인 비주얼 슬라이더용 (BANNER_TYPE = 'MAIN')
        // 기존 bannerList 대신 통합된 PromotionDTO 리스트 사용
        List<BannerDTO> mainBannerList = service.getMainBannerList();
        
        // 2. [수정] 하단 프로모션 카드용 (BANNER_TYPE = 'CARD')
        // 기존 promoList 대신 통합된 PromotionDTO 리스트 사용
        List<PromotionDTO> promoCardList = service.getPromoCardList();

        // 3. 객실 목록 (상위 2개) 가져오기
        List<RoomTypeDTO> roomList = service.getMainRoomList();
        
        // 4. 다이닝 목록 (상위 3개) 가져오기
        List<DiningDTO> diningList = service.getMainDiningList();

        // 뷰(HTML)로 전달
        // HTML에서 사용하는 th:each 명칭과 일치시켜야 합니다.
        model.addAttribute("mainBannerList", mainBannerList); // 슬라이더용
        model.addAttribute("promoCardList", promoCardList);   // 하단 카드용
        model.addAttribute("roomList", roomList);
        model.addAttribute("diningList", diningList);

        return "js/index/index";
    }
}