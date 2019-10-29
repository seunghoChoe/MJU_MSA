package com.springboot.demo.global;

public class Constants {
	public static String SERVER_MESSAGE = "serverMessage";

	public static final String API_GATEWAY_IP_ADDRESS = "52.78.148.181";
	public static final String API_GATEWAY_PORT_NUMBER = "8080";

	public static String createBaseURI(String serviceName) {
		// return http://52.78.148.181:8080/xxx-service
		// use http://52.78.148.181:8080/xxx-service/xxx
		return "http://" + Constants.API_GATEWAY_IP_ADDRESS
				         + ":"
				         + Constants.API_GATEWAY_PORT_NUMBER
				         + "/"
				         + serviceName;
	}

	// 로컬 테스트
//	public static final String API_GATEWAY_IP_ADDRESS = "localhost";
//	public static final String API_GATEWAY_PORT_NUMBER = "8081"; // 계정 서비스 포트
//
//	public static String createBaseURI(String serviceName) {
//		return "http://" + Constants.API_GATEWAY_IP_ADDRESS
//				+ ":"
//				+ Constants.API_GATEWAY_PORT_NUMBER
//				+ "/"
//				+ serviceName;
//	}
}