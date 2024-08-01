package com.exam.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.exam.service.DisUseService;

@Component
public class ExpiredStockScheduler {

	
	@Autowired
    private DisUseService disUseService;

    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void scheduleExpiredStock() {
    	disUseService.moveExpiredStocksToDisuse();
    }
}
