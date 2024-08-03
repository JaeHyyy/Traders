package com.exam.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.exam.dto.DisUseDTO;
import com.exam.entity.DisUse;
import com.exam.entity.OrderCart;
import com.exam.entity.Stock;
import com.exam.repository.DisUseRepository;
import com.exam.repository.StockRepository;

@Service
@Transactional
public class DisUseServiceImpl implements DisUseService {
	@Autowired
	StockRepository stockRepository;
	@Autowired
	DisUseRepository disUseRepository;

	public DisUseServiceImpl(DisUseRepository disUseRepository) {
		this.disUseRepository = disUseRepository;
	}
    
	//sql disuse 테이블 값 전체 조회
	@Override
	public List<DisUseDTO> findAll() {
		 ModelMapper mapper = new ModelMapper();
			
		 List<DisUse> list = disUseRepository.findAll();
			
		 List<DisUseDTO> disuseList = list.stream()
					.map(e->mapper.map(e, DisUseDTO.class))
					.collect(Collectors.toList());
			return disuseList;
	}
	
	
	//유통기한 지난 재고 상품들 disuse테이블로 데이터 저장 시키기
	@Override
	public void moveExpiredStocksToDisuse() {
	    Date currentDate = new Date(System.currentTimeMillis());

	    try {
	        if (stockRepository == null) {
	            System.out.println("StockRepository is null");
	        }
	        if (disUseRepository == null) {
	            System.out.println("DisuseRepository is null");
	        }

	        List<Stock> expiredStocks = stockRepository.findByExpdate(currentDate);
	        if (expiredStocks == null) {
	            System.out.println("Expired stocks list is null");
	        } else {
	            System.out.println("Found expired stocks: " + expiredStocks);
	        }

	        for (Stock stock : expiredStocks) {
	            if (stock == null) {
	                System.out.println("Stock is null");
	                continue;
	            }
	            System.out.println("Processing stock: " + stock);

	            // stock 엔티티가 데이터베이스에 저장되어 있는지 확인
	            if (!stockRepository.existsById(stock.getStockid())) {
	                stockRepository.save(stock);
	                stockRepository.flush();
	            }
	            
                // disuse 테이블에 이미 존재하는지 확인(중복 저장 방지)
                if (disUseRepository.existsByStock(stock)) {
                    System.out.println("Stock " + stock.getStockid() + " is already in disuse table");
                    continue;
                }

	            DisUse disuse = DisUse.builder()
	                    .stock(stock)
	                    .disdate(null) // 일단 disdate를 null로 설정
	                    .build();

	            System.out.println("Saving disuse record: " + disuse);

	            // 먼저 DisUse 엔티티를 저장합니다.
	            disUseRepository.save(disuse);
	            disUseRepository.flush(); // Ensure disuse records are saved

//	            System.out.println("Deleting stock record: " + stock);
//	            stockRepository.delete(stock);

	            System.out.println("Moved stock " + stock + " to disuse");
	        }

	    } catch (Exception e) {
	        System.out.println("Error occurred while moving expired stocks: " + e.getMessage());
	        throw e; // rethrow the exception to be handled in the controller
	    }
	}
	
	//해당 시간에 자동으로 유통기한 지난 재고상품 유통기한관리 페이지에 담기게 하기
    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void scheduleExpiredStockCheck() {
        moveExpiredStocksToDisuse();
    }

    
    //유통기한관리페이지에서 폐기완료 버튼 클릭시 stock테이블의 해당 데이터 삭제
	@Override
	public void delete(int disid) {
		  DisUse disUse = disUseRepository.findById(disid).orElse(null);
			if(disUse!=null) {
				disUseRepository.delete(disUse);
			}
	}

	
	//유통기한관리페이지에서 폐기완료 버튼 클릭시 disdate에 현재 날짜 나오게 하기
	@Override
	public void update(int disid, DisUseDTO dto) {
		//우선 찾기 먼저 하고
		DisUse disUse = disUseRepository.findById(disid).orElse(null);
		//더티체킹(수정)
		if (disUse != null) {
			disUse.setDisdate(dto.getDisdate());
			disUseRepository.save(disUse);
	     }
	}

	


}//end