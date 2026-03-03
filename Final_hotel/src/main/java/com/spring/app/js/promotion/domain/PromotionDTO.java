package com.spring.app.js.promotion.domain;

import lombok.Data;

@Data
public class PromotionDTO {
    private int promotion_id;      // 프로모션 ID
    private String title;          // 제목
    private String subtitle;       // 소제목 (promotion_type 활용)
    private String poster_img;     // 포스터 이미지 경로
    private String tags;           // 태그 정보
    private int is_active;         // 활성화 여부 (1: 활성, 0: 비활성)
}