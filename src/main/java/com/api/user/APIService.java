package com.api.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class APIService {
	
	@Autowired
	private User1Repository user1Repository;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseEntity<User1> createUser(User1 user) {
        String url = "https://pre-openbanking-uat.hdbank.com.vn/eks/dms/v1/vendor/login";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", "dms-vendor-path-login");
        headers.set("X-IBM-CLIENT-SECRET", "dms-vendor-path-login");
        
        // Tạo body của request
        String requestBody = "{\n" +
                "    \"requestId\": \"54b0b972-acd7-4652-8505-TruongMinhHuu2001\",\n" +
                "    \"requestTime\": \"1667200102200\",\n" +
                "    \"channelId\": \"1667200102200\",\n" +
                "    \"signature\": \"\",\n" +
                "    \"data\": {\n" +
                "       \"username\": \"vnpay\",\n" +
                "       \"password\": \"vnpay\",\n" +
                "       \"partnerCode\": \"mobile\"\n" +
                "    }\n" +
                "}";
        
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        
        // Tạo RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        
        // Thực hiện yêu cầu POST
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        
        // Kiểm tra phản hồi
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            // Xử lý phản hồi ở đây
            // Ví dụ: bạn có thể chuyển responseBody thành đối tượng JSON và kiểm tra responseCode
            // Đảm bảo rằng responseCode=00 và status=200
            // Nếu phản hồi đúng, tiếp tục tạo user
            
            
            
            if (isResponseValid(responseBody)) {
                User1 newUser = user1Repository.save(user);
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } else {
                // Xử lý lỗi
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            // Xử lý lỗi
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 // Phương thức này kiểm tra responseCode=00
    private boolean isResponseValid(String responseBody) {
        try {
            // Chuyển đổi responseBody thành một đối tượng JSON
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            // Lấy giá trị của thuộc tính responseCode
            String responseCode = jsonNode.get("responseCode").asText();
            // Kiểm tra xem responseCode có bằng "00" hay không
            return "00".equals(responseCode);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}


