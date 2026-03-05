package com.spring.app.jh.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.app.jh.security.domain.AdminDTO;
import com.spring.app.jh.security.domain.Session_AdminDTO;
import com.spring.app.jh.security.service.AdminService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/* ===== (#스프링시큐리티07-ADMIN-BRANCH) ===== */
@Controller
@RequiredArgsConstructor
@RequestMapping(value="/admin/branch/")
@PreAuthorize("hasRole('ADMIN_BRANCH')")  // ★ BRANCH 전용 컨트롤러(클래스 레벨로 고정)
public class AdminBranchController {

	private final AdminService adminService;

	/*
	   BRANCH 전용 기능

	   1) BRANCH 대시보드
	      - /admin/branch/dashboard

	   2) BRANCH 내 정보(프로필)
	      - /admin/branch/account/myInfo
	      - /admin/branch/account/profileEdit (GET/POST)
	*/


	// ============================================================
	// 0. BRANCH 대시보드
	// ============================================================

	@GetMapping("dashboard")
	public String dashboard() {

		return "admin/branch/branch_dashboard";
		// src/main/resources/templates/admin/branch/branch_dashboard.html
	}


	

}