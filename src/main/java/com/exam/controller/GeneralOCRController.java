package com.exam.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/uploadForm")
//@CrossOrigin(origins = "http://localhost:3000")
public class GeneralOCRController {

	String apiURL = "https://4930zazour.apigw.ntruss.com/custom/v1/33486/396c66c8c7d0d10efa5a4278b961555e08f3efb6439a845a7cf472c43c3e9200/general";
	String secretKey = "aUFvUXhEV1ljcmRMTGNxcFhuT3pYcXNlbVdTY3pLbko=";
	
	 @GetMapping
    public String uploadForm() throws Exception {
        return "uploadForm";
    }
	 
	 //parseData에서 지점명만 추출해내기
	 private String extractBranchName(List<String> parseData) {
		    if (parseData != null) {
		        for (int i = 0; i < parseData.size(); i++) {
		            String data = parseData.get(i);
		            if (data.contains("지점명")) {
		                if (i + 1 < parseData.size()) {
		                    return parseData.get(i + 1).trim(); // "지점명:" 다음에 있는 항목을 반환
		                } else {
		                    System.err.println("지점명 다음에 데이터가 없습니다.");
		                }
		            }
		        }
		    }
		    return ""; // "지점명"이 없으면 빈 문자열 반환
		}
	
	  
	@PostMapping("/uploadOcr")
	@ResponseBody // 프론트엔드로 JSON 응답을 보내기 위해 @ResponseBody 추가
    public Map<String, Object> uploadAndOcr(@RequestParam("theFile") MultipartFile multipartFile, Model model) throws IOException {


        String naverSecretKey = secretKey; 

        File imageFile = File.createTempFile("temp", multipartFile.getOriginalFilename());
        multipartFile.transferTo(imageFile);

        
        /////////////////////////////////////////////////////////////////////////////////
        List<String> parseData = null;
        
    	try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setReadTimeout(30000);
			con.setRequestMethod("POST");
			String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			con.setRequestProperty("X-OCR-SECRET", secretKey);

			JSONObject json = new JSONObject();
			json.put("version", "V2");
			json.put("requestId", UUID.randomUUID().toString());
			json.put("timestamp", System.currentTimeMillis());
			json.put("lang", "ko");
			JSONObject image = new JSONObject();
			image.put("format", "png");
			image.put("name", "demo");
			
			JSONArray images = new JSONArray();
			images.add(image);
			json.put("images", images);
			String postParams = json.toString();

			con.connect();
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			long start = System.currentTimeMillis();
			File file = new File(imageFile.getPath());
			writeMultiPart(wr, postParams, file, boundary);
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();

			System.out.println(response);
	
			
			// JSON 파싱하여 List<String>로 변환
			parseData = jsonparse(response);
			System.out.println("parseData: " + parseData);
			/*
			   출력결과:
			   parseData: [larana, 이수진, 팀장 | 마케팅팀, M, +123-456-7890, E, hello@reallyreatsite.com, A, 123, Anywhere, St.., Any, City]
			*/

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
    	
         /////////////////////////////////////////////////////////////////////////////////

    	imageFile.delete(); // 임시 파일 삭제
    	
    	// 지점명 추출
        String branchName = extractBranchName(parseData);
        System.out.println("branchName: " + branchName);
        
    	 Map<String, Object> responseMap = new HashMap<>();
    	    responseMap.put("parseData", parseData);
    	    responseMap.put("branchName", branchName);

//        model.addAttribute("parseData", parseData); 

        return responseMap; // JSON 형태로 데이터 반환
    }
	
	private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws
	IOException {
	StringBuilder sb = new StringBuilder();
	sb.append("--").append(boundary).append("\r\n");
	sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
	sb.append(jsonMessage);
	sb.append("\r\n");

	out.write(sb.toString().getBytes("UTF-8"));
	out.flush();

	if (file != null && file.isFile()) {
		out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
		StringBuilder fileString = new StringBuilder();
		fileString
			.append("Content-Disposition:form-data; name=\"file\"; filename=");
		fileString.append("\"" + file.getName() + "\"\r\n");
		fileString.append("Content-Type: application/octet-stream\r\n\r\n");
		out.write(fileString.toString().getBytes("UTF-8"));
		out.flush();

		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] buffer = new byte[8192];
			int count;
			while ((count = fis.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			out.write("\r\n".getBytes());
		}

		out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
	}
	out.flush();
  }
    private static List<String> jsonparse(StringBuffer response) throws ParseException {
        //json 파싱
        JSONParser jp = new JSONParser();
        JSONObject jobj = (JSONObject) jp.parse(response.toString());
        //images 배열 obj 화
        JSONArray JSONArrayPerson = (JSONArray)jobj.get("images");
        JSONObject JSONObjImage = (JSONObject)JSONArrayPerson.get(0);
        JSONArray s = (JSONArray) JSONObjImage.get("fields");
        //
        List<Map<String, Object>> m = JsonUtils.getListMapFromJsonArray(s);
        List<String> result = new ArrayList<>();
        for (Map<String, Object> as : m) {
            result.add((String) as.get("inferText"));
        }
        return result;
    }
}



@Slf4j
class JsonUtils {

    public static Map<String, Object> getMapFromJSONObject(JSONObject obj) {
        if (ObjectUtils.isEmpty(obj)) {
            log.error("BAD REQUEST obj : {}", obj);
            throw new IllegalArgumentException(String.format("BAD REQUEST obj %s", obj));
        }

        try {
            return new ObjectMapper().readValue(obj.toJSONString(), Map.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static List<Map<String, Object>> getListMapFromJsonArray(JSONArray jsonArray) {
        if (ObjectUtils.isEmpty(jsonArray)) {
            log.error("jsonArray is null.");
            throw new IllegalArgumentException("jsonArray is null");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object jsonObject : jsonArray) {
            list.add(getMapFromJSONObject((JSONObject) jsonObject));
        }
        return list;
    }
}