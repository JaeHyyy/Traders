package com.exam.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
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
	
	private static final String UPLOAD_DIR = "C:/springboot_study/sts-4.22.1.RELEASE/workspace2/Traders/src/main/resources/static/images/items/";


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
	
//    private GoodsDTO convertToDTO(Goods goods) {
//        GoodsDTO dto = new GoodsDTO();
//        dto.setGcode(goods.getGcode());
//        dto.setGcategory(goods.getGcategory());
//        dto.setGname(goods.getGname());
//        dto.setGcostprice(goods.getGcostprice());
//        return dto;
//    }
//    
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
//                file.transferTo(new File("C:/springboot_study/sts-4.22.1.RELEASE/workspace/Traders/src/main/resources/static/images/items/",filename));
                String filePath = "C:/springboot_study/sts-4.22.1.RELEASE/workspace/Traders/src/main/resources/static/images/items/" + filename; // 환경변수로 변경 추천
                File dest = new File(filePath);
                file.transferTo(dest);
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
    public GoodsDTO addGoods(GoodsDTO goodsDTO) {
        Goods goods = Goods.builder()
                .gcode(goodsDTO.getGcode())
                .gcategory(goodsDTO.getGcategory())
                .gname(goodsDTO.getGname())
                .gcostprice(goodsDTO.getGcostprice())
                .gimage(uploadImage(goodsDTO.getMultipartFile())) // 이미지 업로드 처리
                .gcompany(goodsDTO.getGcompany())
                .gunit(goodsDTO.getGunit())
                .build();

        Goods savedGoods = goodsRepository.save(goods);
        return convertToDTO(savedGoods);
    }

	private String uploadImage(MultipartFile multipartFile) {
	    if (multipartFile != null && !multipartFile.isEmpty()) {
	        try {
	            // 파일 이름을 UUID로 설정하여 중복 및 특수 문자 문제 해결
	            String originalFileName = multipartFile.getOriginalFilename();
	            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));  // .png 등 확장자 추출

	            String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf("."));  // 파일 이름
	            
	            Path uploadPath = Paths.get(UPLOAD_DIR + fileExtension);  //실제 저장 경로

	            // 디렉토리가 존재하지 않으면 생성
	            if (!Files.exists(uploadPath.getParent())) {
	                Files.createDirectories(uploadPath.getParent());
	            }
	            
	            // 파일을 해당 경로에 저장
	            Files.write(uploadPath, multipartFile.getBytes());

	            return fileNameWithoutExtension;  // 확장자를 제외한 파일 이름만 반환
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    return null;
	}

    private GoodsDTO convertToDTO(Goods goods) {
        return GoodsDTO.builder()
                .gcode(goods.getGcode())
                .gcategory(goods.getGcategory())
                .gname(goods.getGname())
                .gcostprice(goods.getGcostprice())
                .gimage(goods.getGimage())
                .gcompany(goods.getGcompany())
                .gunit(goods.getGunit())
                .build();
    }
    
}//end
	
	
	
	

