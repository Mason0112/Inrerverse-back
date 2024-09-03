package com.interverse.demo.service;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interverse.demo.dto.CartResponseDTO;
import com.interverse.demo.dto.LinePayDTO;
import com.interverse.demo.model.linepay.CheckoutPaymentRequestForm;
import com.interverse.demo.model.linepay.ProductForm;
import com.interverse.demo.model.linepay.ProductPackageForm;
import com.interverse.demo.model.linepay.RedirectUrls;



public class LinePayService{
	
	
	public static String encrypt(final String keys, final String data) {
        return toBase64String(HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, keys.getBytes()).doFinal(data.getBytes()));
    }

    public static String toBase64String(byte[] bytes) {
        byte[] byteArray = Base64.encodeBase64(bytes);
        return new String(byteArray);
    }
	
	
	public LinePayDTO LinePayPost(CartResponseDTO DTO) {
		
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
		redirectUrls.setConfirmUrl("");
		redirectUrls.setCancelUrl("");
		form.setRedirectUrls(redirectUrls);
		
		ObjectMapper mapper = new ObjectMapper();
		
		LinePayDTO linePayDTO = new LinePayDTO();
		
		try {
			String ChannelSecret = "6ae54e020168ccb8f6af8f34e21e2efe";
			String requestUri = "/v3/payments/request";
			String nonce = UUID.randomUUID().toString();
			String signature = encrypt(ChannelSecret, ChannelSecret + requestUri + mapper.writeValueAsString(form) + nonce);
			
			linePayDTO.setChannelSecret(ChannelSecret);
			linePayDTO.setRequestUri(requestUri);
			linePayDTO.setNonce(nonce);
			linePayDTO.setSignature(signature);
			
			return linePayDTO;
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
			
	}
	
	
	
}
