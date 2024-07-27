package com.exam.controller;

import com.exam.dto.MovementDTO;
import com.exam.service.MovementService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.util.List;

// 전체데이터를 조회 - QR
@RestController
@RequestMapping("/api")
public class QrCodeAllDataController {
    @Autowired
    private MovementService movementService;

    @GetMapping("/qrcode")
    public ResponseEntity<ByteArrayResource> getQrCode() {
        try {
        	// movement 데이터 전체조회
            List<MovementDTO> movements = movementService.findAllSortedByDate();
            
            // 데이터문자열 생성
            StringBuilder data = new StringBuilder();
            for (MovementDTO movement : movements) {
                data.append(movement.toString()).append("\n");
            }

            // QR 코드 생성
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data.toString(), BarcodeFormat.QR_CODE, 300, 300);

            // QR 코드를 PNG 이미지로 변환
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            ByteArrayResource byteArrayResource = new ByteArrayResource(pngOutputStream.toByteArray());

            // HTTP 응답 생성 및 반환
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qrcode.png\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .contentLength(byteArrayResource.contentLength())
                    .body(byteArrayResource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
