package com.spring.app.jh.ops.admin.controller;

import java.time.LocalDate;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.spring.app.jh.ops.admin.service.AdminHqShuttleOpsService;
import com.spring.app.jh.security.domain.CustomAdminDetails;
import com.spring.app.jh.security.domain.Session_AdminDTO;
import com.spring.app.jh.security.auth.domain.JwtPrincipalDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/hq/shuttle")
@PreAuthorize("hasRole('ADMIN_HQ')")
public class AdminHqShuttleOpsController {

    private final AdminHqShuttleOpsService shuttleOpsService;

    @GetMapping
    public String shuttleManagePage(@RequestParam(value = "hotelId", required = false) Integer hotelId,
                                    HttpSession session,
                                    Model model) {

        Integer adminNo = getSessionAdminNo(session);
        if (adminNo == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("hotelList", shuttleOpsService.getHotelList());
        model.addAttribute("placeList", shuttleOpsService.getPlaceList());
        model.addAttribute("selectedHotelId", hotelId);

        if (hotelId != null) {
            model.addAttribute("routeList", shuttleOpsService.getRouteList(hotelId));
            model.addAttribute("timetableList", shuttleOpsService.getTimetableList(hotelId));
            model.addAttribute("blockList", shuttleOpsService.getBlockList(hotelId));
        }

        return "admin/hq/shuttle_manage";
    }

    @PostMapping("/route/add")
    public String addRoute(@RequestParam("hotelId") int hotelId,
                           @RequestParam("routeType") String routeType,
                           @RequestParam("startPlaceCode") String startPlaceCode,
                           @RequestParam("endPlaceCode") String endPlaceCode,
                           @RequestParam("routeName") String routeName,
                           HttpSession session) {

        Integer adminNo = getSessionAdminNo(session);
        if (adminNo == null) {
            return "redirect:/admin/login";
        }

        shuttleOpsService.addRoute(hotelId, routeType, startPlaceCode, endPlaceCode, routeName);

        return "redirect:/admin/hq/shuttle?hotelId=" + hotelId;
    }

    @PostMapping("/route/{routeId}/deactivate")
    public String deactivateRoute(@PathVariable("routeId") long routeId,
                                  @RequestParam("hotelId") int hotelId,
                                  HttpSession session) {

        Integer adminNo = getSessionAdminNo(session);
        if (adminNo == null) {
            return "redirect:/admin/login";
        }

        shuttleOpsService.deactivateRoute(hotelId, routeId);

        return "redirect:/admin/hq/shuttle?hotelId=" + hotelId;
    }

    @PostMapping("/block/add")
    public String addBlock(@RequestParam("hotelId") int hotelId,
                           @RequestParam("routeId") long routeId,
                           @RequestParam(value = "timetableId", required = false) Long timetableId,
                           @RequestParam("startDate") LocalDate startDate,
                           @RequestParam("endDate") LocalDate endDate,
                           @RequestParam(value = "reason", required = false) String reason,
                           HttpSession session) {

        Integer adminNo = getSessionAdminNo(session);
        if (adminNo == null) {
            return "redirect:/admin/login";
        }

        shuttleOpsService.addBlock(
                adminNo,
                hotelId,
                routeId,
                timetableId,
                startDate,
                endDate,
                reason
        );

        return "redirect:/admin/hq/shuttle?hotelId=" + hotelId;
    }

    @PostMapping("/block/{blockId}/deactivate")
    public String deactivateBlock(@PathVariable("blockId") long blockId,
                                  @RequestParam("hotelId") int hotelId,
                                  HttpSession session) {

        Integer adminNo = getSessionAdminNo(session);
        if (adminNo == null) {
            return "redirect:/admin/login";
        }

        shuttleOpsService.deactivateBlock(hotelId, blockId);

        return "redirect:/admin/hq/shuttle?hotelId=" + hotelId;
    }

    @PostMapping("/extend")
    public String extendPeriod(@RequestParam("hotelId") int hotelId,
                               @RequestParam("endDate") LocalDate endDate,
                               HttpSession session) {

        Integer adminNo = getSessionAdminNo(session);
        if (adminNo == null) {
            return "redirect:/admin/login";
        }

        shuttleOpsService.extendSlotStock(hotelId, endDate);

        return "redirect:/admin/hq/shuttle?hotelId=" + hotelId;
    }

    @PostMapping("/purge")
    public String purgeOldData(@RequestParam("hotelId") int hotelId,
                               HttpSession session) {

        Integer adminNo = getSessionAdminNo(session);
        if (adminNo == null) {
            return "redirect:/admin/login";
        }

        shuttleOpsService.purgeOldShuttleData(hotelId);

        return "redirect:/admin/hq/shuttle?hotelId=" + hotelId;
    }

    private Integer getSessionAdminNo(HttpSession session) {

        if (session == null) return null;

        Object obj = session.getAttribute("sessionAdminDTO");
        if (obj instanceof Session_AdminDTO dto) {
            return dto.getAdmin_no();
        }

        SecurityContext context =
                (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");

        if (context != null && context.getAuthentication() != null) {
            Object principal = context.getAuthentication().getPrincipal();

            if (principal instanceof JwtPrincipalDTO jwtPrincipal) {
                if ("ADMIN".equals(jwtPrincipal.getPrincipalType())) {
                    return jwtPrincipal.getPrincipalNo().intValue();
                }
            }

            if (principal instanceof CustomAdminDetails cad) {
                return cad.getAdminDto().getAdmin_no();
            }
        }

        return null;
    }
}