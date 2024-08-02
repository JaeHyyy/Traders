package com.exam.controller;

import com.exam.dto.MovementDTO;
import com.exam.service.MovementService;
import com.exam.service.QrCodeService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
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

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam("date") String date) {
        try {
            LocalDate movdate = LocalDate.parse(date);
            List<MovementDTO> movements = movementService.findByMovdate(movdate);
            String text = movements.stream()
                    .map(MovementDTO::toString)
                    .collect(Collectors.joining("\n"));
            
            // 이동할 URL을 포함한 QR 코드 텍스트 생성 (ssg wifi : 10.10.10.197)
            String qrCodeText = "http://10.10.10.197:3000/mobile/main?data=" + URLEncoder.encode(text, "UTF-8");
            
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
	
}
