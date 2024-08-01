package com.exam.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dto.GoodsDTO;
import com.exam.entity.Goods;
import com.exam.repository.GoodsRepository;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
	
	GoodsRepository goodsRepository;
	ModelMapper modelMapper;


	public GoodsServiceImpl(GoodsRepository goodsRepository, ModelMapper modelMapper) {
		this.goodsRepository = goodsRepository;
		this.modelMapper = modelMapper;
	}

	//본사 상품 전체 조회
	@Override
	public List<GoodsDTO> findAll() {
        ModelMapper mapper = new ModelMapper();
		
		List <Goods> list = goodsRepository.findAll();
		
		List<GoodsDTO> goodsList = list.stream()
				.map(e->mapper.map(e, GoodsDTO.class))
				.collect(Collectors.toList());
		return goodsList;
	}
	
	
	
	//상품명, 카테고리명, 상품코드로 검색 조회
	@Override
	public List<GoodsDTO> search(String keyword) {
		ModelMapper mapper = new ModelMapper();
		List<Goods> list = goodsRepository.search(keyword);
		
		List<GoodsDTO> goodsList = list.stream()
				.map(e->mapper.map(e, GoodsDTO.class))
				.collect(Collectors.toList());
		return goodsList;
	}
	
    private GoodsDTO convertToDTO(Goods goods) {
        GoodsDTO dto = new GoodsDTO();
        dto.setGcode(goods.getGcode());
        dto.setGcategory(goods.getGcategory());
        dto.setGname(goods.getGname());
        dto.setGcostprice(goods.getGcostprice());
        return dto;
    }
    
    //저장
    @Override
    public Goods save(GoodsDTO goodsDTO, MultipartFile file) {
        Goods goods = new Goods();
        goods.setGcode(goodsDTO.getGcode());
        goods.setGcategory(goodsDTO.getGcategory());
        goods.setGname(goodsDTO.getGname());
        goods.setGcostprice(goodsDTO.getGcostprice());
        goods.setGcompany(goodsDTO.getGcompany());
        goods.setGunit(goodsDTO.getGunit());
        

        if (file != null && !file.isEmpty()) {
        	String filename = System.currentTimeMillis() + "_"+file.getOriginalFilename();
        	goods.setGimage(filename);
            try {
                file.transferTo(new File("C:/springboot_study/sts-4.22.1.RELEASE/workspace/Traders/src/main/resources/static/images/items/",filename));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       
        return goodsRepository.save(goods);
    }

    // 모바일용 데이터 - 전체조회
	@Override
	public List<GoodsDTO> findAllData() {
        // 저장소에서 모든 goods 데이터를 가져옵니다
        List<Goods> goodsList = goodsRepository.findAll();
        
        // Goods 엔티티 리스트를 GoodsDTO 리스트로 변환합니다
        return goodsList.stream()
                        .map(goods -> modelMapper.map(goods, GoodsDTO.class))
                        .collect(Collectors.toList());
    }
	
	// 모바일용 데이터 - gcode 로 찾기
	@Override
	public GoodsDTO findByGcode(String gcode) {
	    Goods goods = goodsRepository.findByGcode(gcode);
	    return modelMapper.map(goods, GoodsDTO.class);
	}

    
    @Override
	public List<GoodsDTO> findByGcode(String gcode) {
		ModelMapper mapper = new ModelMapper();
		List<Goods> list = goodsRepository.findByGcode(gcode);
		
		List<GoodsDTO> goodsList = list.stream()
				.map(e->mapper.map(e, GoodsDTO.class))
				.collect(Collectors.toList());
		return goodsList;
	}
    
    
}//end
	
	
	
	

