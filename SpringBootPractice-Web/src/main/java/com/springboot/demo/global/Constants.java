package com.springboot.demo.global;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @version 0.9
 * @date 2019-12-13
 * @description 프로젝트 내에서 공통적으로 사용되는 Utility 클래스
 * (실서버/로컬 연동 시, 2개의 createBaseURI() 메소드 중 1개를 선택하여 사용한다.)
 */
public class Constants {

    // 공통 상수
    public static final String SERVER_MESSAGE = "serverMessage";
    public static final String LOGGED_USER = "loggedUser";

    // API 게이트웨이 주소 및 포트
    public static final String API_GATEWAY_IP_ADDRESS = "52.78.148.181";
    public static final String API_GATEWAY_PORT_NUMBER = "8080";

	/**
	 * 실서버 연동 시, 리소스 서버 URI 생성
	 * (URI 예시: http://52.78.148.181:8080/xxx-service)
	 * (URI 호출 예시: http://52.78.148.181:8080/xxx-service/xxx)
	 * @param serviceName
	 *  리소스 서버 이름
	 * @return String
	 *  리소스 서버 URI
	 */
	 public static String createApiUri(String serviceName) {
	     return "http://" + Constants.API_GATEWAY_IP_ADDRESS
	             + ":"
	             + Constants.API_GATEWAY_PORT_NUMBER
	             + "/"
	             + serviceName;
	 }

    // 로컬 주소 및 포트
    private static final String COMMON_IP_ADDRESS = "localhost";
    private static final String USER_API_PORT = "8081";
    private static final String BOARD_API_PORT = "8082";
    private static final String RESTAURANT_API_PORT = "8083";

	/**
	 * 로컬 연동 시, 리소스 서버 URI 생성
	 * (URI 예시: http://localhost:808X/xxx)
	 * (URI 호출 예시: http://localhost:808X/xxx)
	 * @param serviceName
	 *  리소스 서버 이름
	 * @return String
	 *  리소스 서버 URI
	 */
//    public static String createApiUri(String serviceName) {
//        String apiPort = "";
//        switch (serviceName) {
//            case "user-service":
//                apiPort = USER_API_PORT;
//                break;
//            case "board-service":
//                apiPort = BOARD_API_PORT;
//                break;
//            case "restaurant-service":
//                apiPort = RESTAURANT_API_PORT;
//                break;
//        }
//        return "http://" + COMMON_IP_ADDRESS
//                + ":"
//                + apiPort;
//    }

	/**
	 * img 태그의 src 목록 추출
	 * @param str
	 *  게시글 또는 식당 내용
	 * @return List<String>
	 *  img 태그 src 목록
	 */
	public static List<String> getImaSrcList(String str) {
        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

        List<String> result = new ArrayList<>();
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }

	/**
	 * img 태그의 src 목록 중, 1개 추출
	 * @param str
	 *  게시글 또는 식당 내용
	 * @return String
	 *  img 태그의 src 목록 중, 1개
	 */
    public static String getImaSrc(String str) {
        // 게시글 및 식당 내용에서 img 태그를 찾는 정규식
        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

        List<String> result = new ArrayList<>();
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            // img 태그 목록 중, 첫 번째 img 태그를 썸네일 이미지로 사용한다.
            result.add(matcher.group(1));
        }

        if (result.isEmpty()) {
        	// 이미지를 첨부하지 않은 경우, 기본 이미지 지정
            return "http://via.placeholder.com/350x150";

        } else {
        	// 이미지를 첨부한 경우, 첫 번째 이미지 태그의 이미지 경로
            return result.get(0);
        }
    }
    
    /**
     * 게시글 또는 식당 내용에서 태그 제거
     * @param str
     *  게시글 또는 식당 내용
     * @return String
     *  태그가 제거된 내용
     * @throws Exception
     */
    public static String removeTag(String str) throws Exception {
    	return str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }

	/**
	 * Alert 스크립트
	 * @param response
	 *  HttpServletResponse 객체
	 * @param message
	 *  출력될 Alert 메시지
	 * @param uri
	 *  Redirect URI
	 * @throws IOException
	 */
    public static void alertMessage(HttpServletResponse response, String message, String uri) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('" + message + "'); ");
        out.println("try { window.location.replace('" + uri + "'); }");
        out.println("catch (e) { window.location = '" + uri + "'; }");
        out.println("</script>");
        out.flush();
    }
}