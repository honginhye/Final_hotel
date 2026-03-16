package com.spring.app.js.revenue.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.app.js.revenue.service.RevenueService;

@Controller
@RequestMapping("/admin")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    // 1. 처음 페이지 접속 (value = "month" 추가)
    @GetMapping("/revenue")
    public String revenueMain(Model model, 
                               @RequestParam(value = "month", defaultValue = "") String month) {
        if(month.equals("")) {
            month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }
        model.addAttribute("selectedMonth", month);
        return "js/revenue/revenue"; 
    }

    // 2. AJAX 요청 시 데이터 반환 (value = "month" 명시)
    @GetMapping("/revenue/data")
    @ResponseBody
    public Map<String, Object> getRevenueData(@RequestParam(value = "month") String month) {
        Map<String, Object> data = new HashMap<>();
        
        // 뷰(JS)에서 사용하는 키값과 일치시킴
        data.put("summary", revenueService.getRevenueSummary(month));
        data.put("dailyTrends", revenueService.getDailyRevenue(month));
        data.put("roomTypePortion", revenueService.getRoomTypePortion(month));
        data.put("revenueList", revenueService.getMonthlyPaymentList(month));
        
        return data; 
    }
}