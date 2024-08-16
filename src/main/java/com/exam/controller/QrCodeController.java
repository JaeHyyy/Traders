package com.exam.controller;

import com.exam.dto.MovementDTO;

import com.exam.service.MovementService;
import com.exam.service.QrCodeService;
import com.mysql.cj.log.Log;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class QrCodeController {
	public QrCodeService qrCodeService;
    public MovementService movementService;

    public QrCodeController(QrCodeService qrCodeService, MovementService movementService) {
        this.qrCodeService = qrCodeService;
        this.movementService = movementService;
    }

    @GetMapping("/{branchid}/qrcode")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String branchid, @RequestParam("date") String date) {
        try {
            LocalDate movdate = LocalDate.parse(date);
            List<MovementDTO> movements = movementService.findByMovdate(branchid, movdate);
            String text = movements.stream()
                    .map(MovementDTO::toString)
                    .collect(Collectors.joining("\n"));
            
            // 이동할 URL을 포함한 QR 코드 텍스트 생성 
            String qrCodeText = "http://10.10.10.61:3000/mobile/main?data=" + URLEncoder.encode(text, "UTF-8");
            
            System.out.println("Generated QR Code URL: " + qrCodeText);
            // qr코드 이미지 생성
            byte[] qrCodeImage = qrCodeService.generateQRCode(qrCodeText, 250, 250);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qrcode.png");

            return ResponseEntity.ok().headers(headers).body(qrCodeImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // 모바일 status 변경 (대기 -> 완료)
    @PostMapping("/updateMovStatus")
    public ResponseEntity<?> updateMovStatus(@RequestBody List<Map<String, Object>> itemsToUpdate) {
        try {
            for (Map<String, Object> item : itemsToUpdate) {
                Long movidx = Long.parseLong((String) item.get("movidx"));
                String newStatus = (String) item.get("newStatus");
                movementService.updateMovStatus(movidx, newStatus);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error updating movement statuses: "+ e);
            return ResponseEntity.status(500).body("Error updating movement statuses: " + e.getMessage());
        }
    }

}
