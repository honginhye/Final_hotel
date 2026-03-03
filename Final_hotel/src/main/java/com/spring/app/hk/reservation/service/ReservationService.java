package com.spring.app.hk.reservation.service;

import java.util.Map;

public interface ReservationService {

	// 예약 db 저장 후 reservation_code 반환
    String saveReservation(Map<String, String> map);

    // 예약 완료 페이지 조회
	Map<String, Object> getReservationByCode(String code);

	// 예약 페이지 내 객실 기본 정보 조회
	Map<String, Object> getRoomInfo(int room_type_id);

}