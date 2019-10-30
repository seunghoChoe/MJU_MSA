package com.springboot.demo.global;

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
}