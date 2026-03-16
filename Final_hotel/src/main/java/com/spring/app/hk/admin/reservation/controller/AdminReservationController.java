package com.spring.app.hk.admin.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.spring.app.hk.admin.reservation.service.AdminReservationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/reservation")
public class AdminReservationController {

	private final AdminReservationService reservationService;

	// ======= 지점 관리자 ========= //
	/*
	 * // 예약 캘린더 (객실 배정)
	 * 
	 * @PreAuthorize("hasRole('ADMIN_BRANCH')")
	 * 
	 * @GetMapping("/calendar") public String reservationCalendar() {
	 * 
	 * return "hk/admin/reservation/calendar"; }
	 */

	// 예약 관리 페이지
	@PreAuthorize("hasRole('ADMIN_BRANCH')")
	@GetMapping("/manage")
	public String reservationManage(Model model) {

	    List<Map<String, Object>> checkinList = reservationService.getTodayCheckinList();
	    List<Map<String, Object>> checkoutList = reservationService.getTodayCheckoutList();
	    List<Map<String, Object>> stayList = reservationService.getStayList();
	    List<Map<String, Object>> checkoutCompleteList = reservationService.getCheckoutCompleteList();

	    // ===== KPI 계산 =====

	    // 오늘 체크인 예정
	    int todayCheckinReserved = checkinList.size();

	    // 오늘 체크인 완료 (투숙중 중에서 오늘 체크인한 것)
	    int todayCheckinDone = 0;

	    for(Map<String,Object> stay : stayList) {

	        java.sql.Timestamp checkinDate = (java.sql.Timestamp) stay.get("CHECKIN_DATE");

	        java.time.LocalDate today = java.time.LocalDate.now();

	        if(checkinDate.toLocalDateTime().toLocalDate().equals(today)) {
	            todayCheckinDone++;
	        }
	    }

	    int todayCheckoutCount = checkoutList.size();
	    int stayCount = stayList.size();

	    // 점유율 계산
	    int totalRoomCount = 100;
	    int occupancyRate = 0;

	    if(totalRoomCount > 0) {
	        occupancyRate = (int)(((double) stayCount / totalRoomCount) * 100);
	    }

	    // ===== 체크인 진행률 =====
	    int checkinProgress = 0;

	    int totalCheckin = todayCheckinReserved + todayCheckinDone;

	    if(totalCheckin > 0) {
	        checkinProgress = (int)(((double) todayCheckinDone / totalCheckin) * 100);
	    }

	    // ===== 리스트 =====
	    model.addAttribute("checkinList", checkinList);
	    model.addAttribute("checkoutList", checkoutList);
	    model.addAttribute("stayList", stayList);
	    model.addAttribute("checkoutCompleteList", checkoutCompleteList);

	    // ===== KPI =====
	    model.addAttribute("todayCheckinReserved", todayCheckinReserved);
	    model.addAttribute("todayCheckinDone", todayCheckinDone);
	    model.addAttribute("todayCheckoutCount", todayCheckoutCount);
	    model.addAttribute("stayCount", stayCount);
	    model.addAttribute("occupancyRate", occupancyRate);

	    model.addAttribute("checkinProgress", checkinProgress);

	    return "hk/admin/reservation/reservationManage";
	}

	// 체크인 처리
	@PreAuthorize("hasRole('ADMIN_BRANCH')")
	@PostMapping("/checkin")
	public String checkin(@RequestParam("reservationId") int reservationId) {

		reservationService.checkinReservation(reservationId);

		return "redirect:/admin/reservation/manage";
	}

	// 체크아웃 처리
	@PreAuthorize("hasRole('ADMIN_BRANCH')")
	@PostMapping("/checkout")
	public String checkout(@RequestParam("reservationId") int reservationId) {

		reservationService.checkoutReservation(reservationId);

		return "redirect:/admin/reservation/manage";
	}

	// ======= 총괄 관리자 ========= //
	// 전체 객실 예약 리스트 조회 + 검색
	@PreAuthorize("hasRole('ADMIN_HQ')")
	@GetMapping("/list")
	public String adminReservationList(
	        @RequestParam(value="name", required=false) String name,
	        @RequestParam(value="status", required=false) String status,
	        Model model) {

	    Map<String,Object> param = new HashMap<>();
	    param.put("name", name);
	    param.put("status", status);

	    List<Map<String,Object>> reservationList =
	            reservationService.selectAdminReservationList(param);

	    model.addAttribute("reservationList", reservationList);
	    model.addAttribute("name", name);
	    model.addAttribute("status", status);

	    return "hk/admin/reservation/reservationList";
	}

}