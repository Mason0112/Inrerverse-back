package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.UserDto;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserDetail;
import com.interverse.demo.service.UserService;
import com.interverse.demo.util.JwtUtil;
import com.interverse.demo.util.NullOrEmptyUtil;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private NullOrEmptyUtil noeUtil;

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public String register(@RequestBody UserDto userDto) throws JSONException {
		JSONObject responseJson = new JSONObject();

		if (noeUtil.determineString(userDto.getAccountNumber()) || noeUtil.determineString(userDto.getPassword())
				|| noeUtil.determineString(userDto.getEmail()) || noeUtil.determineString(userDto.getNickname())
				|| noeUtil.determineString(userDto.getPhoneNumber()) || noeUtil.determineString(userDto.getCountry())
				|| noeUtil.determineString(userDto.getCity()) || noeUtil.determineLocalDate(userDto.getBirthday())
				|| noeUtil.determineString(userDto.getGender())) {

			responseJson.put("success", false);
			responseJson.put("message", "請輸入必填欄位");
			
			return responseJson.toString();
		}

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

		user.setUserDetail(userDetail);

		try {
			userService.register(user);

			responseJson.put("success", true);
			responseJson.put("message", "註冊成功");

		} catch (Exception e) {

			responseJson.put("success", false);
			responseJson.put("message", "註冊失敗");
		}

		return responseJson.toString();
	}

	@PostMapping("/login")
	public String login(@RequestBody String userJson) throws JSONException {

		JSONObject responseJson = new JSONObject();

		JSONObject userObj = new JSONObject(userJson);
		String accountNumber = userObj.isNull("accountNumber") ? null : userObj.getString("accountNumber");
		String password = userObj.isNull("password") ? null : userObj.getString("password");

		// 先檢查是否有輸入值
		if (accountNumber == null || accountNumber.length() == 0 || password == null || password.length() == 0) {
			responseJson.put("success", false);
			responseJson.put("message", "請輸入帳號與密碼");
			return responseJson.toString();
		}

		// 上面有輸入值的話，執行登入邏輯
		User user = userService.login(accountNumber, password);

		// 判斷登入結果
		if (user == null) {
			responseJson.put("success", false);
			responseJson.put("message", "登入失敗");
		} else {
			responseJson.put("success", true);
			responseJson.put("message", "登入成功");

			JSONObject loggedInUser = new JSONObject().put("id", user.getId())
					.put("accountNumber", user.getAccountNumber()).put("nickname", user.getNickname());

			String token = jwtUtil.generateEncryptedJwt(loggedInUser.toString());

			responseJson.put("token", token);
			responseJson.put("id", user.getId());
			responseJson.put("nickname", user.getNickname());
		}

		return responseJson.toString();

	}

	@DeleteMapping("/secure/{id}")
	public String deleteUserById(@PathVariable Integer id) {
		userService.deleteUserById(id);

		return "ok";
	}

	@PutMapping("/secure/{id}")
	public String updateUserById(@PathVariable Integer id, @RequestBody UserDto userDto) {

		User user = userService.findUserById(id);
		UserDetail userDetail = user.getUserDetail();

		user.setId(id);
		user.setAccountNumber(userDto.getAccountNumber());
		user.setEmail(userDto.getEmail());
		user.setNickname(userDto.getNickname());

		userDetail.setPhoneNumber(userDto.getPhoneNumber());
		userDetail.setCountry(userDto.getCountry());
		userDetail.setCity(userDto.getCity());
		userDetail.setBirthday(userDto.getBirthday());
		userDetail.setGender(userDto.getGender());

		user.setUserDetail(userDetail);

		userService.updateUserDetail(user);

		return "ok";
	}

//	@GetMapping("/user/{id}")
//	public String getUserById(@PathVariable Integer id) {
//		
//		User user = userService.findUserById(id);
//		
//		
//		
//	}
//	
}
