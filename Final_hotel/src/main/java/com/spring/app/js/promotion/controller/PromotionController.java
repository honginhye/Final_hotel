package com.spring.app.js.promotion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.app.js.promotion.domain.PromotionDTO;
import com.spring.app.js.promotion.service.PromotionService;

@Controller
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // 프로모션 목록 페이지
    @GetMapping("/list")
    public String list(@RequestParam(name="hotelId", defaultValue="1") int hotelId, Model model) {
        
        // 서비스에서 현재 진행 중인 프로모션 리스트를 가져옵니다.
        // (서비스 인터페이스의 메소드명을 getPromotionList로 맞췄습니다.)
        List<PromotionDTO> list = promotionService.getPromotionList(hotelId);
        
        model.addAttribute("promoList", list);
        model.addAttribute("hotelId", hotelId);
        
        // 타임리프에서 제목 처리를 쉽게 하기 위해 호텔 이름을 넘겨줄 수도 있습니다.
        model.addAttribute("hotelName", (hotelId == 1) ? "호텔 시엘 (SEOUL)" : "르 시엘 (BUSAN)");
        
        return "js/promotion/list"; // promotion 폴더 안의 list.html 호출
    }
    
    // 프로모션 상세 페이지
    @GetMapping("/detail/{id}")
    public String promotionDetail(@PathVariable("id") int id, Model model) {
        // 1. 상세 데이터 가져오기
        PromotionDTO promotion = promotionService.getPromotionDetail(id);
        
        if (promotion != null) {
            // 2. 할인 가격 계산 (소수점 반올림 후 정수화)
            double discountMultiplier = (100.0 - promotion.getDiscount_rate()) / 100.0;
            int finalPrice = (int) Math.round(promotion.getPrice() * discountMultiplier);
            
            // 3. 모델에 담기
            model.addAttribute("promo", promotion);
            model.addAttribute("finalPrice", finalPrice); // 계산된 최종가
        }
        
        return "js/promotion/detail"; 
    }
}