package com.exam.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.GoodsDTO;
import com.exam.dto.StockDTO;
import com.exam.entity.Stock;
import com.exam.repository.StockRepository;

@Service
@Transactional
public class StockServiceImpl implements StockService{
	
	StockRepository stockRepository;
    private final ModelMapper mapper;  // ModelMapper를 클래스 레벨 필드로 선언

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
        List<Stock> list = stockRepository.findByGcode(gcode); // 새로운 메서드를 호출합니다.
        return list.stream()
                   .map(stock -> {
                       StockDTO stockDTO = mapper.map(stock, StockDTO.class);
                       GoodsDTO goodsDTO = mapper.map(stock.getGoods(), GoodsDTO.class);
                       stockDTO.setGoods(null); // goods 엔티티를 제거
                       stockDTO.setGoodsData(goodsDTO); // GoodsDTO를 StockDTO에 설정
                       stockDTO.setGcode(goodsDTO.getGcode()); // gcode 가져옴.
                       goodsDTO.setGname(goodsDTO.getGname());
                       
                       return stockDTO;
                   })
                   .collect(Collectors.toList());
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
	public void delete(int stockid) {
	    Stock stock = stockRepository.findById(stockid).orElse(null);
		if(stock!=null) {
			stockRepository.delete(stock);
		}
	}


	// 모바일 - 상세정보페이지의 위치업데이트
	@Override
	public void mobileUpdateStockLocation(String gcode, String loc1, String loc2, String loc3) {
		stockRepository.mobileUpdateLocationByGcode(gcode, loc1, loc2, loc3);
	}

}

