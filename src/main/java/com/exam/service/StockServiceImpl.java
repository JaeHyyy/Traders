package com.exam.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.exam.dto.GnameSummaryDTO;
import com.exam.dto.GoodsDTO;
import com.exam.dto.StockDTO;
import com.exam.dto.UserStockDTO;
import com.exam.entity.Goods;
import com.exam.entity.Stock;
import com.exam.entity.User;
import com.exam.repository.GoodsRepository;
import com.exam.repository.StockRepository;
import com.exam.repository.UserRepository;

@Service
@Transactional
public class StockServiceImpl implements StockService{
	
	StockRepository stockRepository;
	GoodsRepository goodsRepository;
    private final ModelMapper mapper;  // ModelMapper를 클래스 레벨 필드로 선언
    
    @Autowired
	UserRepository userRepository;

	public StockServiceImpl(StockRepository stockRepository, ModelMapper mapper) {
		this.stockRepository = stockRepository;
		this.mapper = mapper;
	}
	
	//stock테이블에서 유통기한 안 지난 재고 상품들만 보여주기
	@Override
	public List<StockDTO> findAll(){
		ModelMapper mapper = new ModelMapper();
		 LocalDate currentDate = LocalDate.now();
		List<Stock> list = stockRepository.findAllValidStocks(currentDate);
		List<StockDTO> stockList = list.stream()
										 .map(e->mapper.map(e, StockDTO.class))
										 .collect(Collectors.toList());
		return stockList;
	}

	// stock 테이블 + goods 테이블 데이터 조회
    @Override
    public List<StockDTO> findAllData() {
        List<Stock> list = stockRepository.findAllWithGoods();
        return list.stream()
                   .map(stock -> {
                       StockDTO stockDTO = mapper.map(stock, StockDTO.class);
                       GoodsDTO goodsDTO = mapper.map(stock.getGoods(), GoodsDTO.class);
                       stockDTO.setGoods(null);
                       stockDTO.setGoodsData(goodsDTO);  // 필드 이름 변경에 맞게 설정
                       stockDTO.setGcode(goodsDTO.getGcode()); // gcode를 goodsDTO에서 가져오기
                       return stockDTO;
                   })
                   .collect(Collectors.toList());
    }
    
    // 특정 gcode에 대한 stock 데이터 + goods 데이터 조회
    @Override
    public List<StockDTO> findGcodeData(String gcode) {
        List<Stock> list = stockRepository.findByGcode(gcode); // 이 메서드가 올바른 데이터를 반환하는지 확인하세요
        return list.stream()
                   .map(stock -> {
                       StockDTO stockDTO = mapper.map(stock, StockDTO.class);
                       GoodsDTO goodsDTO = mapper.map(stock.getGoods(), GoodsDTO.class);
                       stockDTO.setGoods(null); // goods 데이터를 올바르게 설정하고 있는지 확인하세요
                       stockDTO.setGoodsData(goodsDTO);
                       stockDTO.setGcode(goodsDTO.getGcode());
                       goodsDTO.setGname(goodsDTO.getGname());
                       return stockDTO;
                   })
                   .collect(Collectors.toList());
    }

    
    // 특정 gcode 와 branchId 에 대한 stock 데이터 + goods 데이터 조회
    @Override
	public List<StockDTO> findByGcodeDataAndBranchId(String gcode, String branchId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
    public void updateStockLocation(String gcode, String loc1, String loc2, String loc3) {
        stockRepository.updateLocationByGcode(gcode, loc1, loc2, loc3);
    }
	
	@Override
	public List<StockDTO> findByGoodsGcode(String gcode){
		ModelMapper mapper = new ModelMapper();
		
		List<Stock> list = stockRepository.findByGoodsGcode(gcode);
		List<StockDTO> stockList = list.stream()
										 .map(e->mapper.map(e, StockDTO.class))
										 .collect(Collectors.toList());
		return stockList;
	}


	//유통기한관리페이지에서 폐기완료 버튼 클릭시 stock테이블의 해당 데이터 삭제
	@Override
	public void deleteByBranchIdStock(int stockid, String branchId) {
	    Stock stock = stockRepository.findById(stockid).orElse(null);
		if(stock!=null) {
			stockRepository.delete(stock);
		}
	}


	// 모바일 - 상세정보페이지의 위치업데이트
	@Override
	public void mobileUpdateStockLocation(String gcode, String branchId, String loc1, String loc2, String loc3, Integer price) {
	    if (price == null) {
	        // Goods에서 gcostprice를 가져옴
	        price = goodsRepository.findGcostpriceByGcode(gcode);
	        
	        if (price == null) {
	            System.out.println("gcostprice is null for gcode " + gcode);
	            throw new RuntimeException("gcostprice not found for gcode: " + gcode);
	        }
	    }
	    
	    // 디버깅을 위한 로그 출력
	    System.out.println("gcode: " + gcode);
	    System.out.println("branchId: " + branchId);
	    System.out.println("loc1: " + loc1);
	    System.out.println("loc2: " + loc2);
	    System.out.println("loc3: " + loc3);
	    System.out.println("price: " + price);

	    // 가져온 또는 제공된 가격으로 stock 위치를 업데이트
	    stockRepository.updateLocationWithPrice(gcode, branchId, loc1, loc2, loc3, price);
	}




	
	// branchId 로 Stock 조회
    @Override
    public List<StockDTO> findByBranchIdStock(String branchId) {
    	 ModelMapper mapper = new ModelMapper();
         LocalDate currentDate = LocalDate.now();
         List<Stock> list = stockRepository.findByBranchIdStock(currentDate, branchId);
         return list.stream()
                    .map(e -> mapper.map(e, StockDTO.class))
                    .collect(Collectors.toList());

    }//end

	
	@Override //ADMIN 막대그래프
    public List<UserStockDTO> countStocksByBranch() {
        List<Object[]> results = stockRepository.countStocksByBranch();
        
        return results.stream()
            .map(result -> {
                String branchId = (String) result[0];
                String branchName = (String) result[1];
                long count = (long) result[2];

                UserStockDTO userStockDTO = new UserStockDTO();
                userStockDTO.setBranchId(branchId);
                userStockDTO.setBranchName(branchName);
                userStockDTO.setCount(count);
                return userStockDTO;
            })
            .collect(Collectors.toList());
    }
	

  
    //발주하기 버튼
    @Override
    public void saveAll(String branchId, List<StockDTO> dtos) {
        User user = userRepository.findByBranchId(branchId);
        if (user != null) {
            List<Stock> Stocks = dtos.stream()
                                             .map(dto -> {
                                                 Stock stock = mapper.map(dto, Stock.class);
                                                 stock.setUser(user);
                                                 return stock;
                                             })
                                             .collect(Collectors.toList());
            stockRepository.saveAll(Stocks);
        }
    }
    
    @Override //ADMIN 그래프2
    public List<GnameSummaryDTO> findGnameSummaries() {
        List<Object[]> results = stockRepository.findGnameSummaries();
        
        return results.stream()
            .map(result -> {
                String gname = (String) result[0];
                Long summ = (Long) result[1];

                GnameSummaryDTO gnameSummaryDTO = new GnameSummaryDTO();
                gnameSummaryDTO.setGname(gname);
                gnameSummaryDTO.setSumm(summ);
                return gnameSummaryDTO;
            })
            .collect(Collectors.toList());
    }

    //검색창 입력값으로 stock조회
	@Override
	public List<StockDTO> searchStock(String branchId, String keyword) {
		 LocalDate currentDate = LocalDate.now();
		 String likePattern = "%" + keyword + "%"; // LIKE 패턴
		    List<Stock> stocks = stockRepository.stockSearchByKeyword(currentDate, branchId, likePattern);
	        return stocks.stream()
	                     .map(stock -> mapper.map(stock, StockDTO.class))
	                     .collect(Collectors.toList());
	}
    
    
	
	///////////////////////////////////////////////////////////////////////////////////
	
	// branchId 로 stock 테이블 + goods 테이블 데이터 조회
    @Override
    public List<StockDTO> findAllDataByBranchId(String branchId) {
        LocalDate currentDate = LocalDate.now();
        List<Stock> list = stockRepository.findAllWithGoodsByBranchId(branchId, currentDate);

        // 데이터 확인을 위한 로그 출력
        System.out.println("Fetched Stocks: " + list.size());
        list.forEach(stock -> System.out.println(stock.toString()));

        return list.stream()
                   .map(stock -> {
                       StockDTO stockDTO = mapper.map(stock, StockDTO.class);
                       GoodsDTO goodsDTO = mapper.map(stock.getGoods(), GoodsDTO.class);
                       stockDTO.setGoods(null);
                       stockDTO.setGoodsData(goodsDTO);
                       stockDTO.setGcode(goodsDTO.getGcode());
                       return stockDTO;
                   })
                   .collect(Collectors.toList());
    }
    
    public void saveStock(StockDTO stockDTO) {
        System.out.println("stockDTO: " + stockDTO);

        User user = userRepository.findByBranchId(stockDTO.getBranchid());
        System.out.println("user: " + user);
        
        // Stock 엔티티 설정
        Stock stock = new Stock();
        // stock.setGoods(goods); // goods는 설정하지 않음
        stock.setUser(user);
        stock.setStockquantity(stockDTO.getStockquantity());
        stock.setExpdate(stockDTO.getExpdate());
        stock.setGprice(stockDTO.getGprice());
        stock.setLoc1(stockDTO.getLoc1());
        stock.setLoc2(stockDTO.getLoc2());
        stock.setLoc3(stockDTO.getLoc3());
        stock.setGcode(stockDTO.getGcode()); // gcode를 설정

        System.out.println("stock: " + stock);
        
        // Stock 저장
        stockRepository.save(stock);
    }








	
    

}//end



