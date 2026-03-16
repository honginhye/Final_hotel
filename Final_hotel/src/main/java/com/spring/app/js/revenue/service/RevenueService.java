package com.spring.app.js.revenue.service;

import java.util.List;
import java.util.Map;

public interface RevenueService {
    
    // 1. 월간 수익 요약 정보 조회 (총 매출, 예약 건수, 취소 건수 등)
    Map<String, Object> getRevenueSummary(String month);
    
    // 2. 차트용 일별 매출 추이 데이터 조회 (Line Chart)
    // 기존 getDailyRevenueTrends와 컨트롤러의 호출명을 통일함
    List<Map<String, Object>> getDailyRevenue(String month);
    
    // 3. 객실 등급별 매출 비중 조회 (Doughnut Chart)
    List<Map<String, Object>> getRoomTypePortion(String month);
    
    // 4. 월간 결제 상세 내역 리스트 조회 (Table)
    // AJAX에서 JSON으로 다루기 쉽도록 Map 리스트로 반환
    List<Map<String, Object>> getMonthlyPaymentList(String month);
}