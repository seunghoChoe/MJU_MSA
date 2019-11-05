package com.springboot.demo.global;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Class: 상수 클래스
 * @Memo: 실서버/로컬 연동 시, 2개의 createBaseURI() 메소드 중 1개를 선택하여 사용한다.
 */
public class Constants {

	/**
	 * @Memo: 공통 상수
	 */
	public static final String SERVER_MESSAGE = "serverMessage";
	public static final String LOGGED_USER  = "loggedUser";

	/**
	 * @Memo: 실서버 테스트
	 * (URI 예시: http://52.78.148.181:8080/xxx-service)
	 * (URI 호출 예시: http://52.78.148.181:8080/xxx-service/xxx)
	 */
	public static final String API_GATEWAY_IP_ADDRESS = "52.78.148.181";
	public static final String API_GATEWAY_PORT_NUMBER = "8080";

	public static String createBaseURI(String serviceName) {
		return "http://" + Constants.API_GATEWAY_IP_ADDRESS
				         + ":"
				         + Constants.API_GATEWAY_PORT_NUMBER
				         + "/"
				         + serviceName;
	}

	/**
	 * @Memo: 로컬 테스트
	 * (URI 예시: http://localhost:808X/xxx)
	 * (URI 호출 예시: http://localhost:808X/xxx)
	 */
	private static final String COMMON_IP_ADDRESS = "localhost";
	private static final String USER_API_PORT = "8081"; // 계정 서비스 포트
	private static final String BOARD_API_PORT = "8082"; // 게시판 서비스 포트
	private static final String RESTAURANT_API_PORT = "8083"; // 맛집 서비스 포트

//	public static String createBaseURI(String serviceName) {
//		String apiPort = "";
//		switch (serviceName) {
//			case "user-service":
//				apiPort = USER_API_PORT;
//				break;
//			case "board-service":
//				apiPort = BOARD_API_PORT;
//				break;
//			case "restaurant-service":
//				apiPort = RESTAURANT_API_PORT;
//				break;
//		}
//		return "http://" + COMMON_IP_ADDRESS
//				+ ":"
//				+ apiPort;
//	}

	/**
	 * @Method: IMG 태그의 SRC 리스트 추출
	 */
//	public static List<String> getImaSrcList(String str) {
//		Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
//
//		List<String> result = new ArrayList<>();
//		Matcher matcher = pattern.matcher(str);
//
//		while (matcher.find()) {
//			result.add(matcher.group(1));
//		}
//		return result;
//	}

	/**
	 * @Method: IMG 태그의 SRC 1개 추출
	 */
	public static String getImaSrc(String str) {
		Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

		List<String> result = new ArrayList<>();
		Matcher matcher = pattern.matcher(str);

		while (matcher.find()) {
			result.add(matcher.group(1)); // 참고: https://enterkey.tistory.com/353
		}

		if (result.isEmpty()) { // 이미지를 첨부하지 않은 경우
			return "http://via.placeholder.com/350x150"; // 기본 이미지
		} else { // 이미지를 첨부한 경우, 첫 번째 이미지 태그의 이미지 경로
			return result.get(0);
		}
	}
}