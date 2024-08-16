package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.interverse.demo.dto.UserDto;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserDetail;
import com.interverse.demo.service.UserService;
import com.interverse.demo.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;
	
	

	@PostMapping("/register")
	public String register(@RequestBody UserDto userDto) {
		
		User user = new User();
		UserDetail userDetail = new UserDetail();
		
		user.setAccountNumber(userDto.getAccountNumber());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setNickname(userDto.getNickname());
		
		userDetail.setPhoneNumber(userDto.getPhoneNumber());
		userDetail.setCountry(userDto.getCountry());
		userDetail.setCity(userDto.getCity());
		userDetail.setBirthday(userDto.getBirthday());
		userDetail.setGender(userDto.getGender());
		userDetail.setPhoto(userDto.getPhoto());
		userDetail.setBio(userDto.getBio());
		
		user.setUserDetail(userDetail);
		
		userService.register(user);
		
		return "ok";
	}

	@PostMapping("/login")
	public String login(@RequestBody String userLoginJson) throws JSONException {

		JSONObject userResponseJson = new JSONObject(userLoginJson);
		String accountNumber = userResponseJson.isNull("accountNumber") ? null : userResponseJson.getString("accountNumber");
		String password = userResponseJson.isNull("password") ? null : userResponseJson.getString("password");

		// 先檢查是否有輸入值
		if (accountNumber == null || accountNumber.length() == 0 || password == null || password.length() == 0) {
			userResponseJson.put("success", false);
			userResponseJson.put("message", "請輸入帳號與密碼");
			return userResponseJson.toString();
		}

		// 上面有輸入值的話，執行登入邏輯
		User user = userService.login(accountNumber, password);

		// 判斷登入結果
		if (user == null) {
			userResponseJson.put("success", false);
			userResponseJson.put("message", "登入失敗");
		} else {
			userResponseJson.put("success", true);
			userResponseJson.put("message", "登入成功");

			JSONObject loggedInUser = new JSONObject()
					.put("id", user.getId())
					.put("accountNumber", user.getAccountNumber())
					.put("nickname", user.getNickname());

			String token = jwtUtil.generateEncryptedJwt(loggedInUser.toString());
			userResponseJson.put("token", token);
			userResponseJson.put("id", user.getId());
		}

		return userResponseJson.toString();

	}

}
