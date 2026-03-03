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
    private IndexService service; // 데이터 조회를 위한 서비스

    @GetMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        // 1. 배너 목록 가져오기
        List<BannerDTO> bannerList = service.getBannerList();
        // 2. 객실 목록 (상위 2개) 가져오기
        List<RoomTypeDTO> roomList = service.getMainRoomList();
        // 3. 다이닝 목록 (상위 3개) 가져오기
        List<DiningDTO> diningList = service.getMainDiningList();
        // 4. 프로모션 목록 가져오기
        List<PromotionDTO> promoList = service.getPromoList();

        // 뷰(HTML)로 전달
        model.addAttribute("bannerList", bannerList);
        model.addAttribute("roomList", roomList);
        model.addAttribute("diningList", diningList);
        model.addAttribute("promoList", promoList);

        return "js/index/index";
    }
}