package com.spring.app.hk.hotel.model;

import java.util.List;
import java.util.Map;

public interface HotelDAO {

	// 호텔 리스트 가져오기
	// List<Map<String,Object>> selectHotelList();
	List<Map<String, Object>> selectApprovedHotelList();
	List<Map<String, Object>> selectPendingHotelList();
	
    // 호텔 상세페이지 이동
	Map<String, Object> selectHotelDetail(Long hotelId);
	
    // 호텔 insert
    int insertHotel(Map<String, Object> paraMap);

    // 호텔 이미지 insert
    int insertHotelImage(Map<String, Object> paraMap);

    // 호텔 상세페이지 내 수정하기
	int updateHotel(Map<String, Object> param);

	// 호텔 상세페이지 내 비활성화하기
	int deleteHotel(int hotel_id);

	
	
	// 호텔 승인 상태 변경
	void updateHotelStatus(Long hotelId, String string);

	// 호텔 승인/반려 이력 저장
	void insertApprovalHistory(Long hotelId, String string, Object object);

	
	


}