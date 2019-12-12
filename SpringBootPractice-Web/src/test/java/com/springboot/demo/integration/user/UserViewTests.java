//package com.springboot.demo.integration.user;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import static com.springboot.demo.integration.IntegrationTestConstants.AWS_WEB_URI;
//import static org.hamcrest.Matchers.is;
//
///**
// * @Memo: 셀레니움/웹 드라이버 구동 테스트
// * (https://nesoy.github.io/articles/2017-03/Selenium)
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserViewTests {
//    private static final Logger logger = LoggerFactory.getLogger(UserViewTests.class);
//    private WebDriver driver;
//
//    // *---------------------------------------------------------------------------------------------------------------* [테스트 전/후 작업]
//
//    /**
//     * @Memo: 테스트 실행 전, 테스트 설정 작업
//     */
//    @Before
//    public void setUp() {
//        // 크롬 웹 드라이버 경로 지정 및 인스턴스 생성
//        System.setProperty("webdriver.chrome.driver", "src/test/java/com/springboot/demo/integration/driver/chromedriver.exe");
//        driver = new ChromeDriver();
//    }
//
//    /**
//     * @Memo: 테스트 실행 후, 테스트 내용을 정리한다.
//     */
//    @After
//    public void tearDown() {
//        driver.quit(); // 웹 드라이버 종료
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [웹 페이지 테스트]
//
//    /**
//     * @Memo: 테스트 이름은 테스트 목적이 명확하게 전달되도록 한글로 지정한다.
//     */
//    @Test
//    public void 메인_페이지_타이틀_테스트() throws Exception {
//        driver.get(AWS_WEB_URI);
//
//        // 명지리본: 명지대학교 맛집 검색
//        logger.info(driver.getTitle());
//
//        // 테스트 통과
//        Assert.assertThat(driver.getTitle(), is("명지리본: 명지대학교 맛집 검색"));
//    }
//
//    @Test
//    public void 로그인_성공_후_홈페이지_리다이렉트_테스트() {
//        // 로그인 페이지 URI
//        driver.get(AWS_WEB_URI + "/users/login");
//
//        // 로그인 폼 내의 name 속성으로 ID/비밀번호 입력 필드 탐색
//        WebElement userId = driver.findElement(By.name("user_id"));
//        WebElement userPassword = driver.findElement(By.name("user_password"));
//
//        // 로그인 폼은 class/id/name 속성을 지정하지 않았으므로 태그 이름으로 탐색
//        WebElement loginForm = driver.findElement(By.tagName("form"));
//
//        // ID/비밀번호 입력 후, 폼 제출
//        userId.sendKeys("admin");
//        userPassword.sendKeys("admin");
//        loginForm.submit();
//
//        // 테스트 통과
//        Assert.assertThat(driver.switchTo().alert().getText(), is("admin님, 로그인되었습니다."));
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [??? 테스트]
//}