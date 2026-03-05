package com.spring.app.hk.hotel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.app.common.FileManager;
import com.spring.app.hk.hotel.service.HotelService;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/hotel/")
public class HotelController {

    private final HotelService hotelService;
    private final FileManager fileManager;

    @Value("${file.images-dir}")
    private String imagesDir;

    //  ======================== 1. 호텔 CRUD ============================
    // 호텔 리스트 가져오기
    @GetMapping("list")
    public String hotelList(Model model){

        List<Map<String,Object>> activeHotelList = hotelService.getApprovedHotelList();
        List<Map<String,Object>> pendingHotelList = hotelService.getPendingHotelList();

        model.addAttribute("activeHotelList", activeHotelList);
        model.addAttribute("pendingHotelList", pendingHotelList);

        return "hk/admin/hotel/list";
    }
    
    
    // 호텔 상세페이지 이동
    @GetMapping("detail")
    public String hotelDetail(@RequestParam("hotel_id") Long hotelId,
                              Model model){

        Map<String,Object> hotel = hotelService.getHotelDetail(hotelId);

        System.out.println("호텔 데이터 = " + hotel);
        
        model.addAttribute("hotel", hotel);

        return "hk/admin/hotel/detail";
    }
    
    
    // 호텔 상세페이지 내 수정하기
    @PostMapping("update")
    @ResponseBody
    public Map<String,Object> updateHotel(@RequestBody Map<String,Object> param){ // 호텔 상세페이지 내 수정 완료 클릭 후 json 받는 용도

        int result = hotelService.updateHotel(param);

        Map<String,Object> map = new HashMap<>();
        map.put("result", result);

        return map;
    }
    

    // 호텔 상세페이지 내 비활성화하기
    @PostMapping("delete")
    @ResponseBody
    public Map<String,Object> deleteHotel(@RequestBody Map<String,Object> param){

        int hotel_id = Integer.parseInt(param.get("hotel_id").toString());

        int result = hotelService.deleteHotel(hotel_id);

        Map<String,Object> map = new HashMap<>();
        map.put("result", result);

        return map;
    }
      
    
    // 등록 페이지 이동
	//@PreAuthorize("hasRole('ROLE_HQ')")
    @GetMapping("register")
    public String registerPage() {
        return "hk/admin/hotel/register";
    }

    
    // 호텔 등록
	//@PreAuthorize("hasRole('ROLE_HQ')")
    @PostMapping("register")
    @ResponseBody
    public Map<String, Object> register(
            @RequestParam Map<String, String> map,
            @RequestParam("mainImage") MultipartFile mainImage
          /*  Authentication authentication*/
    ) {

        Map<String, String> paraMap = new HashMap<>(map);

        try {

            // 로그인한 관리자 ID 저장
			/* paraMap.put("created_by", authentication.getName()); */
        	paraMap.put("created_by", "DEV_TEST");

            // 대표 이미지 업로드
            if(!mainImage.isEmpty()) {

            	// 업로드 위치 : file_images/hotel/파일명.jpg
            	String hotelPath = imagesDir + File.separator + "hotel";

            	String savedName =
            	        fileManager.doFileUpload(
            	                mainImage.getBytes(),
            	                mainImage.getOriginalFilename(),
            	                hotelPath);

                paraMap.put("main_image", savedName);
            }

            // 서비스 호출 (Reservation 구조 동일)
            hotelService.saveHotel(paraMap);

        } catch(Exception e) {
            e.printStackTrace();
            return Map.of("result", 0);
        }

        return Map.of("result", 1);
    }


    
//  ======================== 2. 호텔 승인관리 ============================
    // 승인 요청 (지점 관리자)
    @PostMapping("requestApproval")
    @ResponseBody
    public Map<String,Object> requestApproval(@RequestBody Map<String,Object> param){

        Long hotelId = Long.parseLong(param.get("hotel_id").toString());

        hotelService.requestApproval(hotelId);

        return Map.of("result",1);
    }


    // 검토 시작 (총괄 관리자)
    @PostMapping("review")
    @ResponseBody
    public Map<String,Object> review(@RequestBody Map<String,Object> param){

        Long hotelId = Long.parseLong(param.get("hotel_id").toString());

        hotelService.changeStatus(hotelId,"UNDER_REVIEW",null);

        return Map.of("result",1);
    }


    // 승인
    @PostMapping("approve")
    @ResponseBody
    public Map<String,Object> approve(@RequestBody Map<String,Object> param){

        Long hotelId = Long.parseLong(param.get("hotel_id").toString());

        hotelService.changeStatus(hotelId,"APPROVED",null);

        return Map.of("result",1);
    }


    // 수정 요청
    @PostMapping("needRevision")
    @ResponseBody
    public Map<String,Object> needRevision(@RequestBody Map<String,Object> param){

        Long hotelId = Long.parseLong(param.get("hotel_id").toString());
        String reason = param.get("reason").toString();

        hotelService.changeStatus(hotelId,"NEED_REVISION",reason);

        return Map.of("result",1);
    }


    // 반려
    @PostMapping("reject")
    @ResponseBody
    public Map<String,Object> reject(@RequestBody Map<String,Object> param){

        Long hotelId = Long.parseLong(param.get("hotel_id").toString());
        String reason = param.get("reason").toString();

        hotelService.changeStatus(hotelId,"REJECTED",reason);

        return Map.of("result",1);
    }


    // 재신청 (브랜치 관리자)
    @PostMapping("resubmit")
    @ResponseBody
    public Map<String,Object> resubmit(@RequestBody Map<String,Object> param){

        Long hotelId = Long.parseLong(param.get("hotel_id").toString());

        hotelService.changeStatus(hotelId,"PENDING",null);

        return Map.of("result",1);
    }
    
    
    
    
    
    
    
    
    
    
    
    
}