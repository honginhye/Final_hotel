package com.spring.app.js.promotion.domain;

import lombok.Data;

@Data
public class PromotionDTO {
    
    // --- 1. PROMOTION_MASTER (비즈니스 로직 / 정책) ---
    private int promotion_id;      // PK
    private int fk_hotel_id;       // 호텔 지점 ID
    private int price;             // [중요] 대표 가격 (마스터에서 관리)
    private String start_date;     // 프로모션 시작일
    private String end_date;       // 프로모션 종료일
    private int is_active;         // 마스터 활성 여부 (1: 활성, 0: 비활성)
    private double discount_rate;  // 할인율 (필요 시)
    private int discount_amount;   // 할인 금액 (필요 시)

    // --- 2. PROMOTION_BANNER (전시 / UI 레이아웃) ---
    private int banner_id;         // 배너 PK
    private String banner_type;    // [변경] MAIN(상단 슬라이드), CARD(하단 프로모션) 구분
    private String title;          // 배너 노출 제목
    private String subtitle;       // [추가] 배너 보조 문구 (메인 슬라이드용)
    private String image_url;      // 이미지 파일 경로
    private int sort_order;        // 노출 순서
    private String active_yn;      // 배너 개별 활성 여부 (Y/N)
    
    // --- 추가 편의 필드 ---
    private String benefits;       // 혜택 요약 (CLOB 대응)
    private String hotel_name;     // 조인 시 호텔 이름을 담기 위한 필드 (선택사항)
}