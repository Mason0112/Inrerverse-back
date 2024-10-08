package com.interverse.demo.service;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interverse.demo.dto.OrderDTO;
import com.interverse.demo.dto.PaypalDTO;
import com.interverse.demo.dto.PaypalUrlDTO;


@Service
public class PaypalService {
	
	//不要在service中new一個新的自己來操作別的方法 會導致properties沒有辦法讀取到
	
	String clientSecret;	
	String clientId;
    String atoken ;
    
    
    
    private final ObjectMapper objectMapper;
    
    public PaypalService(@Value("${paypal.client-secret}") String clientSecret,
            			@Value("${paypal.client-id}") String clientId,
            			ObjectMapper objectMapper) {
				    		this.clientSecret = clientSecret;
				    		this.clientId = clientId;
				    		this.objectMapper = objectMapper;
    }
    
    
    public static PaypalUrlDTO extractUrls(String jsonResponse) {
        PaypalUrlDTO urlDto = new PaypalUrlDTO();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            
            JsonNode linksNode = rootNode.get("links");
            if (linksNode != null && linksNode.isArray()) {
                for (JsonNode linkNode : linksNode) {
                    String rel = linkNode.get("rel").asText();
                    String href = linkNode.get("href").asText();
                    
                    if ("payer-action".equals(rel)) {
                        urlDto.setPayerAction(href);
                    } else if ("self".equals(rel)) {
                        urlDto.setSelf(href);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("解析 JSON 時出錯：" + e.getMessage());
        }
        return urlDto;
    }
    
	
    

    public PaypalDTO getToken() {
        String response = this.getTokenResponse();
        try {
            return objectMapper.readValue(response, PaypalDTO.class);
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
            return null;
        }
    }
	
	public String  getTokenResponse() {
		String auth = clientId + ":" + clientSecret;
		System.out.println(auth);
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        RestClient restClient = RestClient.create();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        String response = restClient.post()
                .uri("https://api-m.sandbox.paypal.com/v1/oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .body(body)
                .retrieve()
                .body(String.class);
        
        System.out.println("Response: " + response);
        
        return response;
	}
	

	public PaypalUrlDTO sendRequest(OrderDTO dto) {
        RestClient restClient = RestClient.create();

        try {
        	
        	 Map<String, Object> experience_context = Map.of(
                     "return_url", "http://localhost:5173/",
                     "cancel_url", "http://localhost:5173/product/Cart"
                  );

        	 Map<String, Object> paypal = Map.of(
                "experience_context",experience_context
             );
        	
        	 Map<String, Object> payment_source = Map.of(
                     "paypal",paypal
                  );
        	 
        	 
            Map<String, Object> amount = Map.of(
                "currency_code", "TWD",
                "value", dto.getTotalAmount().toString()
            );

            Map<String, Object> purchaseUnit = Map.of(
                "amount", amount
            );

            Map<String, Object> requestBody = Map.of(
                "intent", "CAPTURE",
                "purchase_units", List.of(purchaseUnit),
                "payment_source", payment_source
                
            );
            
    		PaypalDTO token = this.getToken();
    		String Access_token = "Bearer " +token.getAccess_token();

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            System.out.println("token"+Access_token);
            atoken = Access_token;
            System.out.println("AAAtoken "+atoken);
            String response = restClient.post()
                .uri("https://api-m.sandbox.paypal.com/v2/checkout/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, Access_token)
                .body(jsonBody)
                .retrieve()
                .body(String.class);
            
            PaypalUrlDTO urls = this.extractUrls(response);
            
            
            return urls;

        } catch (Exception e) {
            System.err.println("Error in sendRequest: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
	
	
	public String comfirm(String url) {
		
		RestClient restClient = RestClient.create();
		
		PaypalDTO token = this.getToken();
		String Access_token = "Bearer " +token.getAccess_token();
		
		String response = restClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION,Access_token)
                .retrieve()
                .body(String.class);
		
		return response;
	}
	
	
}


