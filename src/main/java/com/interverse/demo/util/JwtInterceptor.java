package com.interverse.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwtUtil;

	private JSONObject processAuthorizationHeader(String auth) throws JSONException {

		if (auth != null && auth.length() != 0) {
			// 取得token淨值
			String token = auth.substring(7); // 'Bearer '
			// 呼叫驗證token方法來取得加密資訊
			String loggedInUserData = jwtUtil.validateJWT(token);
			System.out.println("data=" + loggedInUserData);

			if (loggedInUserData != null && loggedInUserData.length() != 0) {

				// 方法直接回傳JSONObject 因為login Controller 塞入JSONObject型別資料
				return new JSONObject(loggedInUserData);
			}
		}
		return null;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// OPTIONS 請求不需要進一步處理
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			return true;
		}

		// 處理其他請求
		String auth = request.getHeader("Authorization");
		String authhClient = request.getHeader("X-User-ID");
		System.out.println("authhClient:"+ authhClient);

		JSONObject loggedInUser = processAuthorizationHeader(auth);
		
		// 如果JWT payload為空，則中止請求處理
		if (loggedInUser == null || loggedInUser.length() == 0) {
			System.out.println("loggedInUser" + loggedInUser);
			setForbiddenResponse(response);
			return false;
		}

		String authh = loggedInUser.optString("auth", null);
		System.out.println("auth:" +auth);

		// 如果請求API的用戶id 和 JWT payload的用戶id 不一致，也中止請求處理
		if (authh == null || !authh.equals(authhClient)) {
			setForbiddenResponse(response);
			return false;
		}
		
		return true;
	}

	private void setForbiddenResponse(HttpServletResponse response) throws Exception {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
	}
}
