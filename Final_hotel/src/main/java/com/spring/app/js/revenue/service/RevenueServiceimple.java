package com.spring.app.js.revenue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.js.revenue.model.RevenueDAO;

import java.util.List;
import java.util.Map;

@Service
public class RevenueServiceimple implements RevenueService {

    @Autowired
    private RevenueDAO reDao;

    @Override
    public Map<String, Object> getRevenueSummary(String month) {
        return reDao.selectRevenueSummary(month);
    }

    @Override
    public List<Map<String, Object>> getDailyRevenue(String month) {
        return reDao.selectDailyRevenue(month);
    }

    @Override
    public List<Map<String, Object>> getRoomTypePortion(String month) {
        return reDao.selectRoomTypePortion(month);
    }

    @Override
    public List<Map<String, Object>> getMonthlyPaymentList(String month) {
        return reDao.selectMonthlyPaymentList(month);
    }
}