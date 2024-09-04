package com.interverse.demo.service;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interverse.demo.dto.CartResponseDTO;
import com.interverse.demo.dto.LinePayDTO;
import com.interverse.demo.dto.OrderDTO;
import com.interverse.demo.model.linepay.CheckoutPaymentRequestForm;
import com.interverse.demo.model.linepay.ProductForm;
import com.interverse.demo.model.linepay.ProductPackageForm;
import com.interverse.demo.model.linepay.RedirectUrls;


@Service
public class LinePayService{
	
	
	public static String encrypt(final String keys, final String data) {
        return toBase64String(HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, keys.getBytes()).doFinal(data.getBytes()));
    }

    public static String toBase64String(byte[] bytes) {
        byte[] byteArray = Base64.encodeBase64(bytes);
        return new String(byteArray);
    }
	
	
	public LinePayDTO LinePayPost(OrderDTO dto) {
		
		CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();
		

		form.setAmount(100);
		form.setCurrency("TWD");
		form.setOrderId("merchant_order_id");
		
		ProductPackageForm productPackageForm = new ProductPackageForm();
		productPackageForm.setId("package_id");
		productPackageForm.setName("shop_name");
		productPackageForm.setAmount(100);
		
		ProductForm productForm = new ProductForm();
		productForm.setId("product_id");
		productForm.setName("product_name");
		productForm.setImageUrl("");
		productForm.setQuantity(10);
		productForm.setPrice(10);
		ArrayList<ProductForm> ProductFormList = new ArrayList<>();
		ProductFormList.add(productForm);
		productPackageForm.setProducts(ProductFormList);
		
		
		
		ArrayList<ProductPackageForm> ProductPackageFormList = new ArrayList<>();
		ProductPackageFormList.add(productPackageForm);
		form.setPackages(ProductPackageFormList);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setAppPackageName("");
		redirectUrls.setConfirmUrl("https://www.google.com");
		redirectUrls.setCancelUrl("");
		form.setRedirectUrls(redirectUrls);
		
		ObjectMapper mapper = new ObjectMapper();
		
		LinePayDTO linePayDTO = new LinePayDTO();
		
		String channelId = "2005966585";
		String channelSecret = "6ae54e020168ccb8f6af8f34e21e2efe";
		String requestUri = "/v3/payments/request";
		String requestHttpUri = "https://sandbox-api-line.me/v3/payments/request";
		String nonce = UUID.randomUUID().toString();
		try {
			System.out.println("body=>" +mapper.writeValueAsString(form));
			System.out.println("nonce=>" + nonce);
			String signature = encrypt(channelSecret, channelSecret + requestUri + mapper.writeValueAsString(form) + nonce);
			System.out.println("signature=>"+ signature );
			
			linePayDTO.setChannelId(channelId);
			linePayDTO.setChannelSecret(channelSecret);
			linePayDTO.setRequestUri(requestUri);
			linePayDTO.setNonce(nonce);
			linePayDTO.setSignature(signature);
			linePayDTO.setBody(mapper.writeValueAsString(form));
			linePayDTO.setRequestHttpUri(requestHttpUri);
			return linePayDTO;
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
			
	}
	
	
	
}
