//package com.exam.controller;
//
//import com.exam.dto.MovementDTO;
//import com.exam.service.MovementService;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.qrcode.QRCodeWriter;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.ByteArrayOutputStream;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//public class QrCodeDivisionDataController2 {
//
//    @Autowired
//    private MovementService movementService;
//
//    @GetMapping("/qrcodeDivision")
//    public ResponseEntity<ByteArrayResource> getQrCodeDivision(@RequestParam String date) {
//        try {
//            // 날짜 형식 정의
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate parsedDate;
//
//            // 날짜 파싱
//            try {
//                parsedDate = LocalDate.parse(date, formatter);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(new ByteArrayResource("날짜형식을 확인해주세요. Use yyyy-MM-dd.".getBytes()));
//            }
//
//            // 날짜별로 그룹화된 데이터 조회
//            Map<LocalDate, List<MovementDTO>> groupedData = movementService.findAllGroupedByDate();
//
//            // 요청된 날짜의 데이터 가져오기
//            List<MovementDTO> movements = groupedData.get(parsedDate);
//
//            if (movements == null || movements.isEmpty()) {
//                return ResponseEntity.notFound().build(); // 요청한 날짜의 데이터가 없는 경우
//            }
//
//            // QR 코드에 포함할 데이터 생성
//            StringBuilder data = new StringBuilder();
//            for (MovementDTO movement : movements) {
//                data.append(movement.toString()).append("\n");
//            }
//
//            // QR 코드 생성
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(data.toString(), BarcodeFormat.QR_CODE, 300, 300);
//
//            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//            ByteArrayResource byteArrayResource = new ByteArrayResource(pngOutputStream.toByteArray());
//
//            // 응답 반환
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qrcode_division_" + parsedDate + ".png\"")
//                    .contentType(MediaType.IMAGE_PNG)
//                    .contentLength(byteArrayResource.contentLength())
//                    .body(byteArrayResource);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).build();
//        }
//    }
//}
