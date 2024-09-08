package com.interverse.demo.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interverse.demo.dto.PaypalDTO;


@Service
public class PaypalService {
	
	String clientId = "ATHTvA6shc6QEv8hx_c9BP_VrRzCIx0PzbBLHVbtu7d5l_3q_7GyjZ-Zve1AeRzmTqT7N01URj0EjSK7";
    String clientSecret = "EAmCFzhc0B8NlDZnjsqY8IQdwXfkAAUNTVS4qFAvPHPmwmaA8xJyBNPFJF9cfERnO6ZYzBeQxNkzdIgg";
	
    String atoken ;
	
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaypalDTO getToken() {
        String response = getTokenResponse();
        try {
            return objectMapper.readValue(response, PaypalDTO.class);
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
            return null;
        }
    }
	
	public String  getTokenResponse() {
		String auth = clientId + ":" + clientSecret;
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
	

	public String sendRequest(PaypalDTO dto) {
        RestClient restClient = RestClient.create();

        try {
        	
        	 Map<String, Object> experience_context = Map.of(
                     "return_url", "https://www.youtube.com/",
                     "cancel_url", "https://www.youtube.com/watch?v=-NMfs3w_iNs"
                  );

        	 Map<String, Object> paypal = Map.of(
                "experience_context",experience_context
             );
        	
        	 Map<String, Object> payment_source = Map.of(
                     "paypal",paypal
                  );
        	 
        	 
            Map<String, Object> amount = Map.of(
                "currency_code", "USD",
                "value", "10"
            );

            Map<String, Object> purchaseUnit = Map.of(
                "amount", amount
            );

            Map<String, Object> requestBody = Map.of(
                "intent", "CAPTURE",
                "purchase_units", List.of(purchaseUnit),
                "payment_source", payment_source
                
            );

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            System.out.println("token"+dto.getAccess_token());
            atoken = "Bearer " +dto.getAccess_token();
            System.out.println("AAAtoken "+atoken);
            String response = restClient.post()
                .uri("https://api-m.sandbox.paypal.com/v2/checkout/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dto.getAccess_token())
                .body(jsonBody)
                .retrieve()
                .body(String.class);

            return response;

        } catch (Exception e) {
            System.err.println("Error in sendRequest: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
	
	
	
}


