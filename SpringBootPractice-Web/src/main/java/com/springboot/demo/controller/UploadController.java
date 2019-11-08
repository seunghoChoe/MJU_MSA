package com.springboot.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class: : CKEditor 업로드 컨트롤러 클래스 (GET/POST 메소드만 사용한다.)
 * @Memo: CKEditor 를 통해 웹 서버에 이미지를 업로드한다. 그 결과, 해당 게시글/행사에 IMG 태그와 업로드 한 이미지의 경로가 생성되어 출력된다.
 */
@Controller
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    // 업로드 파일 확장자 제한 (또는 화이트 리스트로 업로드 가능한 파일 확장자만 지정하는 방법도 있음)
    private final String[] BAD_EXTENSION = { "php", "php3", "asp", "aspx", "htm", "html", "phtml", "jsp", "com", "bat", "exe", "inc", "js", "ph", "cgi", "pl", "sh" };

    /**
     * @Method: CKEditor 업로드 (AWS S3 버킷 사용 시, 서비스 클래스 추가)
     * @Memo: MultipartFile (다중 업로드)
     */
    @PostMapping("/image-upload")
    public void imageUpload(@RequestParam MultipartFile upload, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        PrintWriter printWriter = null;

        // CKEditor Callback 이 스크립트 방식에서 JSON 방식으로 변경되었으므로 필요 없을 수도 있음
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        // 디렉토리 생성을 위한 날짜
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1; // 0 ~ 11까지 표현하므로 +1

        String monthStr = "";
        if (month < 10) monthStr = "0" + month;
        else monthStr = "" + month;

//        String resourceRootPath = "C:/Users/amelon"; // 로컬 서버 리소스 경로
        String resourceRootPath = File.separator + "home" + File.separator + "ubuntu"; // 실서버 리소스 경로
        String attachPath = File.separator + "upload" + File.separator + year + "" + monthStr; // 업로드 경로
        String fileUploadPath = resourceRootPath + attachPath; // 최종 경로
        
        logger.info(resourceRootPath);
        logger.info(attachPath);
        logger.info(fileUploadPath);
        
        try {
            String filename = "";
            String filenameExt = "";
            String destFilename = "";
            if (upload != null) {
                filename = upload.getOriginalFilename(); // 파일 확장자를 포함한 전체 이름
                filenameExt = filename.substring(filename.indexOf(".") + 1); // 파일 확장자

                // 업로드 제한 확장자 검사
                for (String ext: BAD_EXTENSION) {
                    if (filenameExt.equalsIgnoreCase(ext)) {
                        throw new IOException("해당 파일은 업로드할 수 없습니다.");
                    }
                }

                if (! "".equals(filename)) {
                    File destDirectory = new File(fileUploadPath);
                    if (! destDirectory.exists()) {
                        destDirectory.mkdirs();
                    }
                    File destFile = File.createTempFile("upload_", "." + filenameExt, destDirectory);
                    upload.transferTo(destFile);
                    destFilename = destFile.getName();
                }
            }

            Map<String, Object> jsonMap = new HashMap<>(); // String 으로 JSON 을 작성할 수도 있음
            logger.info(filename); // 파일 이름
            logger.info(destFilename); // 변환된 파일 이름
            logger.info(attachPath + File.separator + destFilename); // 파일 업로드 경로
            jsonMap.put("uploaded", 1); // 0: 실패, 1: 성공
            jsonMap.put("filename", destFilename);
            jsonMap.put("url", attachPath + File.separator + destFilename); // 예시: /resources/upload/201909/upload_8418122055492541114.png

            ObjectMapper mapper = new ObjectMapper(); // Json 변환 라이브러리
            printWriter = response.getWriter();
            printWriter.println(mapper.writeValueAsString(jsonMap)); // Map -> JSON -> print String
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (out != null) out.close();
                if (printWriter != null) printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}