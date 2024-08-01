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

	            // 먼저 stock 엔티티가 데이터베이스에 저장되어 있는지 확인합니다.
	            if (!stockRepository.existsById(stock.getStockid())) {
	                stockRepository.save(stock);
	                stockRepository.flush();
	            }

	            DisUse disuse = DisUse.builder()
	                    .stock(stock)
	                    .disdate(currentDate)
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

	
//	//현재 날짜 기준 유통기한 지난 상품 stock 테이블에서 삭제 및 disuse 테이블에 저장
//	@Override
//	public void moveExpiredStocksToDisuse() {
//		
//		 Date currentDate = new Date(System.currentTimeMillis());
//
//		 try {
//	            if (stockRepository == null) {
//	                System.out.println("StockRepository is null");
//	            }
//	            if (disUseRepository == null) {
//	                System.out.println("DisuseRepository is null");
//	            }
//		 
//	        List<Stock> expiredStocks = stockRepository.findByExpdate(currentDate);//currentDate
//	        if (expiredStocks == null) {
//                System.out.println("Expired stocks list is null");
//            } else {
//                System.out.println("Found expired stocks: " + expiredStocks);
//            }
//	        
//	     
//	        
//	        for (Stock stock : expiredStocks) {
//	        	
//	     	   if (stock == null) {
//                   System.out.println("Stock is null");
//                   continue;
//               }
//               System.out.println("Processing stock: " + stock);
//               
//               // 먼저 stock 엔티티가 데이터베이스에 저장되어 있는지 확인합니다.
//               if (stock.getStockid() == 0) {
//                   stockRepository.save(stock);
//                   stockRepository.flush();
//               }
//               
//           DisUse disuse = DisUse.builder()
//           		                 .stock(stock)
//                                 .disdate(currentDate)//currentDate
//                                 .build();
//           
//           System.out.println("Saving disuse record: " + disuse);
//           // DisUse 엔티티 저장 전에 Stock 엔티티를 명시적으로 persist
//           if (!stockRepository.existsById(stock.getStockid())) {
//               stockRepository.save(stock);
//               stockRepository.flush();
//           }
//           disUseRepository.save(disuse); // DisUse 레코드 먼저 저장
//           
//          
//           System.out.println("Deleting stock record: " + stock);
//           stockRepository.delete(stock); // Stock 레코드 삭제
//         
//           System.out.println("Moved stock " + stock + " to disuse");
//         }
//       
//       disUseRepository.flush(); // Ensure disuse records are saved
//	        
//	       
//		 }catch(Exception e) {
//			 System.out.println("Error occurred while moving expired stocks"+e.getMessage());
//	            throw e; // rethrow the exception to be handled in the controller
//	        }
//	   }
	
    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void scheduleExpiredStockCheck() {
        moveExpiredStocksToDisuse();
    }



}//end