package com.spring.app.js.cs.controller;

import java.util.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.spring.app.js.cs.service.CsService;

@Controller
@RequestMapping("/cs")
public class CsController {

    @Autowired
    private CsService service;

    /**
     * [사용자] FAQ + QnA 통합 고객지원 메인 페이지
     * @param hotelId : 1(호텔 시엘), 2(르시엘) - 기본값 1
     */
    @GetMapping("/list")
    public ModelAndView csMain(ModelAndView mav, 
                               @RequestParam(value = "hotelId", defaultValue = "1") String hotelId,
                               HttpServletRequest request) {

        // 1. 페이징 설정
        String str_currentShowPageNo = request.getParameter("currentShowPageNo");
        int currentShowPageNo = (str_currentShowPageNo == null) ? 1 : Integer.parseInt(str_currentShowPageNo);

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("hotelId", hotelId);

        // 2. FAQ 조회
        List<Map<String, String>> faqList = service.getFaqListByHotel(hotelId);

        // 3. QnA 조회 및 페이징 계산
        int totalCount = service.getQnaTotalCount(paraMap);
        int sizePerPage = 10; 
        
        // [추가] 전체 페이지 수 계산
        int totalPage = (int) Math.ceil((double) totalCount / sizePerPage);
        
        int startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
        int endRno = startRno + sizePerPage - 1;
        
        paraMap.put("startRno", String.valueOf(startRno));
        paraMap.put("endRno", String.valueOf(endRno));
        
        List<Map<String, String>> qnaList = service.getQnaListWithPaging(paraMap);

        // 4. 뷰 전달 데이터 설정 (이 부분이 중요합니다!)
        mav.addObject("faqList", faqList);
        mav.addObject("qnaList", qnaList);
        mav.addObject("hotelId", hotelId); 
        mav.addObject("totalCount", totalCount);
        
        // [수정 포인트] HTML에서 사용하는 변수명과 일치시켜야 함
        mav.addObject("curPage", currentShowPageNo);  // HTML의 ${curPage}
        mav.addObject("totalPage", totalPage);        // HTML의 ${totalPage}
        
        mav.setViewName("js/cs/csList");
        
        return mav;
    }

    /**
     * [사용자] 1:1 문의 작성 페이지 이동
     */
    @GetMapping("/qnaWrite")
    public ModelAndView qnaWrite(ModelAndView mav, 
                                 // 수정: value 속성 추가
                                 @RequestParam(value = "hotelId") String hotelId) {
        
        mav.addObject("hotelId", hotelId);
        mav.setViewName("js/cs/qnaWrite");
        return mav;
    }
    
    @PostMapping("/qnaWriteEnd")
    public ModelAndView qnaWriteEnd(ModelAndView mav, HttpServletRequest request) {
        
        String hotelId = request.getParameter("fk_hotel_id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String writer_name = request.getParameter("writer_name");
        
        // [수정 포인트 1] DB 제약조건 CHECK (is_secret IN ('Y', 'N'))에 맞춤
        String is_secret = request.getParameter("is_secret"); 
        
        // 체크박스 value가 "1"로 설정되어 있다면 "Y", 아니면 "N"으로 변환
        if("1".equals(is_secret)) {
            is_secret = "Y";
        } else {
            is_secret = "N";
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("fk_hotel_id", hotelId);
        paraMap.put("title", title);
        paraMap.put("content", content);
        paraMap.put("writer_name", writer_name);
        paraMap.put("is_secret", is_secret);
        
        // [참고] 만약 서비스/매퍼에서 status를 직접 넣는다면 여기서 "WAITING"을 추가하세요.
        // paraMap.put("status", "WAITING"); 

        int n = service.insertQna(paraMap);

        if(n == 1) {
            mav.addObject("message", "문의가 성공적으로 등록되었습니다.");
            mav.addObject("loc", request.getContextPath() + "/cs/list?hotelId=" + hotelId);
        } else {
            mav.addObject("message", "등록에 실패했습니다.");
            mav.addObject("loc", "javascript:history.back()");
        }

        mav.setViewName("msg"); 
        return mav;
    }

    /**
     * [사용자] 1:1 문의 상세 보기
     */
    @GetMapping("/qnaDetail")
    public ModelAndView qnaDetail(ModelAndView mav, 
                                  // 수정: value 속성 추가
                                  @RequestParam(value = "qnaId") String qnaId) {
        
        Map<String, String> qnaDetail = service.getQnaDetail(qnaId);
        
        mav.addObject("qna", qnaDetail);
        mav.setViewName("js/cs/qnaDetail.tiles1");
        return mav;
    }
}