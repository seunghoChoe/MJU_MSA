package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPracticeIntegrationTestApplicationTests {
	
	/**
	 * @author Dongmin Kim 
	 * 		   from Major of Applied Software in Myongji Univ.
	 * 
	 * 이 클래스 코드들은 초기 버전 통합테스트이므로 실제 배포 환경 및 시나리오와 차이가 있을 수 있음. 
	 */

	private WebDriver driver;
	private WebElement webElement;
	private String driveurl = "http://13.125.99.51:8080/";

	SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver.exe"); // ChromeDriver 위치
		driver = new ChromeDriver(); // Driver 생성
	}

	@After
	public void tearDown() {
		driver.quit(); // Driver 종료
	}

	@Test
	public void 계정등록_테스트() throws InterruptedException {
		driver.get(driveurl);
		driver.findElement(By.linkText("계정 등록")).click();

		webElement = driver.findElement(By.id("user_id"));
		String id = "admin";
		webElement.sendKeys(id);

		webElement = driver.findElement(By.id("user_password"));
		String pwd = "admin";
		webElement.sendKeys(pwd);

		webElement = driver.findElement(By.id("user_name"));
		String name = "admin";
		webElement.sendKeys(name);

		webElement = driver.findElement(By.id("user_email"));
		String mail = "admin";
		webElement.sendKeys(mail);

		webElement = driver.findElement(By.id("joinButton"));
		webElement.submit();

		Thread.sleep(7000);
	}

	@Test
	public void 로그인_테스트() throws InterruptedException {
		// get page (= 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다)
		driver.get(driveurl);
		driver.findElement(By.linkText("로그인")).click();
		Thread.sleep(2000);

		// id 필드 탐색
		webElement = driver.findElement(By.id("user_id"));
		String id = "admin12345";
		webElement.sendKeys(id);
		Thread.sleep(2000);

		// pw 필드 탐색
		webElement = driver.findElement(By.id("user_password"));
		String pw = "admin";
		webElement.sendKeys(pw);
		Thread.sleep(2000);

		// 로그인 버튼 클릭
		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();

		Thread.sleep(2000);

	}

	@Test
	public void 네비게이션바_클릭_테스트() throws InterruptedException {
		driver.get(driveurl);

		driver.findElement(By.linkText("명지리본")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("로그인")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("계정 등록")).click();
		driver.findElement(By.id("joinCancelButton")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("전체 계정 조회")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("게시글 목록")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("게시글 등록")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("문의사항 조회")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("문의사항 작성")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 스토리")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("내 식당 목록")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 스토리")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("식당 목록")).click();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 스토리")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("식당 등록")).click();
		Thread.sleep(1000);

	}

	@Test
	public void 게시글작성_비로그인_테스트() throws InterruptedException {

		driver.get(driveurl + "board/posts");

		driver.findElement(By.id("postItemNew")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		webElement = driver.findElement(By.id("post_title"));
		String title = "admin";
		webElement.sendKeys(title);
		Thread.sleep(1000);

		// iframe으로 구성된 곳은 해당 프레임으로 전환
		WebElement iframe = driver.findElement(By.className("cke_wysiwyg_frame"));
		driver.switchTo().frame(iframe);
		WebElement webElement = driver.findElement(By.cssSelector("body"));
		String description = "admin";
		webElement.sendKeys(description);
		Thread.sleep(1000);

		driver.switchTo().parentFrame();
		webElement = driver.findElement(By.id("newPostButton"));
		webElement.submit();
		Thread.sleep(2000);

		if (driver.getTitle().equals("명지리본: 페이지 오류")) {
			System.out.println(format.format(new Date()) + ": **게시글 작성 페이지에서 예외가 발생하였습니다. 확인 바랍니다.");
		} else {
			driver.switchTo().alert().accept();
		}

	}

	@Test
	public void 게시글조회_비로그인_테스트() throws InterruptedException {

		driver.get(driveurl);

		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("게시글 목록")).click();
		Thread.sleep(1000);

		int a = 1;
		while (true) {
			try {
				Thread.sleep(1000);
				WebElement element = driver
						.findElement(By.xpath("/html/body/div/div[3]/div/div[3]/div/div[" + a + "]/div/div[1]/h4"));
				a++;
			} catch (NoSuchElementException e) {
				System.out.println(format.format(new Date()) + ": 게시글 조회 페이지에 출력된 총 게시글 갯수: " + (a - 1));
				break;
			}
		}
	}

	@Test
	public void 게시글수정_비로그인_테스트() throws InterruptedException {
		driver.get(driveurl + "board/posts");
		int a = 1;
		while (true) {
			try {
				Thread.sleep(1000);
				WebElement element = driver
						.findElement(By.xpath("/html/body/div/div[3]/div/div[3]/div/div[" + a + "]/div/div[1]/h4"));
				System.out.println(format.format(new Date()) + ": " + a + "번째 게시물: " + element.getText());
				element.click();
				String url = driver.getCurrentUrl();
				Thread.sleep(1000);

				driver.findElement(By.id("postItemEdit")).click();
				if (driver.getTitle().equals("명지리본: 페이지 오류")) {
					System.out.println(format.format(new Date()) + ": **게시글 수정 페이지에서 예외가 발생하였습니다. 확인 바랍니다.");
					Thread.sleep(1000);
				}
				driver.get(url);
				Thread.sleep(1000);

				driver.findElement(By.id("postItemList")).click();
				a++;
			} catch (NoSuchElementException e) {
				break;
			}
		}
	}

	@Test
	public void 게시글삭제_비로그인_테스트() throws InterruptedException {
		driver.get(driveurl + "board/posts");
		int a = 1;
		while (true) {
			try {
				Thread.sleep(1000);
				WebElement element = driver
						.findElement(By.xpath("/html/body/div/div[3]/div/div[3]/div/div[" + a + "]/div/div[1]/h4"));
				System.out.println(format.format(new Date()) + ": " + element.getText());
				element.click();
				String url = driver.getCurrentUrl();
				Thread.sleep(1000);

				driver.findElement(By.id("postItemDelete")).click();
				driver.switchTo().alert().accept();
				Thread.sleep(2000);
				if (ExpectedConditions.alertIsPresent().apply(driver) != null) {
					driver.switchTo().alert().accept();
					Thread.sleep(2000);
				}

				if (driver.getTitle().equals("명지리본: 페이지 오류")) {
					System.out.println(format.format(new Date()) + ": **게시글 삭제 페이지에서 예외가 발생하였습니다. 확인 바랍니다.");
					Thread.sleep(1000);
				}
				driver.get(url);
				Thread.sleep(2000);

				driver.findElement(By.id("postItemList")).click();
				a++;
			} catch (NoSuchElementException e) {
				break;
			}
		}
	}

	// 게시글을 작성한 후 목록의 제목과 작성자가 일치하도록 출력되는지 확인한다.
	@Test
	public void 게시글CR_로그인_테스트() throws InterruptedException {

		driver.get(driveurl);

		driver.findElement(By.linkText("로그인")).click();
		Thread.sleep(2000);

		webElement = driver.findElement(By.id("user_id"));
		String id = "ehdals";
		webElement.sendKeys(id);
		Thread.sleep(2000);

		webElement = driver.findElement(By.id("user_password"));
		String pw = "Ehdals1!";
		webElement.sendKeys(pw);
		Thread.sleep(2000);

		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(2000);
		driver.switchTo().alert().accept();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("게시글 목록")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("postItemNew")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		webElement = driver.findElement(By.id("post_title"));
		String title = "짜우짜우짜우짜우반점";
		webElement.sendKeys(title);
		Thread.sleep(500);

		// iframe으로 구성된 곳은 해당 프레임으로 전환
		WebElement iframe = driver.findElement(By.className("cke_wysiwyg_frame"));
		driver.switchTo().frame(iframe);
		WebElement webElement = driver.findElement(By.cssSelector("body"));
		String description = "admin";
		webElement.sendKeys(description);
		Thread.sleep(500);

		driver.switchTo().parentFrame();
		webElement = driver.findElement(By.id("newPostButton"));
		webElement.submit();
		Thread.sleep(500);
		driver.switchTo().alert().accept();

		int a = 1;
		while (true) {
			try {
				Thread.sleep(100);
				WebElement element = driver
						.findElement(By.xpath("/html/body/div/div[3]/div/div[3]/div/div[" + a + "]/div/div[1]/h4"));
				System.out.println(a + "번째 게시물 제목: " + element.getText());
				a++;
			} catch (NoSuchElementException e) {
				System.out.println(format.format(new Date()) + ": 게시글 조회 페이지에 출력된 총 게시글 갯수: " + (a - 1));
				break;
			}
		}
	}

	// 작성자와 다른 아이디로 게시글 수정을 시도한다.
	@Test
	public void 게시글수정_로그인_테스트() throws InterruptedException, IOException {
		File logFile = new File("./게시글삭제_logfile.txt");
		FileWriter fw = new FileWriter(logFile);
		
		driver.get(driveurl);

		driver.findElement(By.linkText("로그인")).click();
		Thread.sleep(500);

		webElement = driver.findElement(By.id("user_id"));
		String id = "ehdals";
		webElement.sendKeys(id);
		Thread.sleep(500);

		webElement = driver.findElement(By.id("user_password"));
		String pw = "Ehdals1!";
		webElement.sendKeys(pw);
		Thread.sleep(500);

		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		Thread.sleep(1000);

		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(1000);
		driver.findElement(By.linkText("게시글 목록")).click();
		Thread.sleep(1000);

		WebElement element = driver.findElement(By.xpath("/html/body/div/div[3]/div/div[3]/div/div[4]/div/div[1]/p"));
		String writer = element.getText();
		element.click();
		Thread.sleep(1000);

		driver.findElement(By.id("postItemEdit")).click();
		Thread.sleep(1000);
		try {
			if (id.equals(writer)) {
				Thread.sleep(1000);
				if (driver.findElement(By.xpath("//*[@id=\"postItemHeader\"]/p")).getText().equals(": 게시글 수정")) {
					System.out.println(format.format(new Date()) + ": 로그인 회원과 작성자가 같은 상태에서 게시글 수정 페이지가 출력되었습니다.");
					Thread.sleep(1000);
				} else {
					System.out.println(
							format.format(new Date()) + ": 로그인 회원과 작성자가 같은 상태이지만 게시글 수정 페이지가 정상적으로 출력되지 않았습니다.");
					Thread.sleep(1000);
				}
			} else {
				Thread.sleep(1000);
				try {
					Thread.sleep(1000);
					assertFalse(
							driver.findElement(By.xpath("//*[@id=\"postItemHeader\"]/p")).getText().equals(": 게시글 수정"));
				} catch (AssertionError e) {
					System.out.println(format.format(new Date()) + ": 로그인 회원과 작성자가 다른 상태에서 게시글 수정 페이지가 출력되었습니다.");
					Thread.sleep(1000);
				} catch (NoSuchElementException e) {
					System.out.println(format.format(new Date()) + ": 로그인 회원과 작성자가 다른 상태에서 게시글 수정 페이지가 출력되지 않았습니다.");
					Thread.sleep(1000);
				}
			}
		} catch (UnhandledAlertException e) {
			System.out.println(format.format(new Date()) + ": 로그인 회원과 작성자가 다른 상태에서 게시글 수정 페이지가 출력되지 않았습니다.");
		}
	}

	// 작성자와 다른 아이디로 게시글 삭제 시도한다.
	@Test
	public void 게시글삭제_로그인_테스트() throws InterruptedException, IOException {
		File logFile = new File("./게시글삭제_logfile.txt");
		FileWriter fw = new FileWriter(logFile);

		driver.get(driveurl);

		driver.findElement(By.linkText("로그인")).click();
		Thread.sleep(500);

		webElement = driver.findElement(By.id("user_id"));
		String id = "ehdals";
		webElement.sendKeys(id);
		Thread.sleep(500);

		webElement = driver.findElement(By.id("user_password"));
		String pw = "Ehdals1!";
		webElement.sendKeys(pw);
		Thread.sleep(500);

		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(500);
		driver.switchTo().alert().accept();
		Thread.sleep(500);

		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(500);
		driver.findElement(By.linkText("게시글 목록")).click();
		Thread.sleep(1000);

		WebElement element = driver.findElement(By.xpath("/html/body/div/div[3]/div/div[3]/div/div[5]/div/div[1]/p"));
		String writer = element.getText();
		element.click();
		Thread.sleep(500);

		driver.findElement(By.id("postItemDelete")).click();
		Thread.sleep(500);
		driver.switchTo().alert().accept();
		Thread.sleep(500);
		if (id.equals(writer)) {
			if (ExpectedConditions.alertIsPresent().apply(driver) != null) {
				driver.switchTo().alert().accept();
				System.out.println(format.format(new Date()) + ": 로그인 회원과 작성자가 같은 상태에서 게시글이 삭제되었습니다.");
				Thread.sleep(500);
			}
		} else {
			if (driver.switchTo().alert().getText().contains("성공")) {
				fw.write(format.format(new Date()) + ": 로그인 회원과 작성자가 다른 상태에서 게시글이 삭제되었습니다.");
			}
		}
		Thread.sleep(1000);
		fw.close();
		
		if (driver.getTitle().equals("명지리본: 페이지 오류")) {
			System.out.println(format.format(new Date()) + ": **게시글 삭제 페이지에서 예외가 발생하였습니다. 확인 바랍니다.");
			Thread.sleep(500);
		}

	}

	// 개발중인 코드
	/*
	 * try { Assert.assertThat(driver.getTitle(),is("URL의 Title"));
	 * System.out.println("타이틀 같음"); } catch (AssertionError e) {
	 * System.out.println("타이틀 다름"); }
	 * 
	 * @Test public void 게시판_로그인_테스트() throws InterruptedException {
	 * driver.get("http://13.125.99.51:8080/board/posts");
	 * 
	 * driver.findElement(By.id("postItemNew")).click();
	 * 
	 * webElement = driver.findElement(By.id("post_title")); String title = "admin";
	 * webElement.sendKeys(title);
	 * 
	 * webElement = driver.findElement(By.id("post_title")); String title = "admin";
	 * webElement.sendKeys(title); }
	 * 
	 * @Test public void 게시판_스마트에디터_테스트() throws InterruptedException {
	 * driver.get("http://13.125.99.51:8080/board/posts");
	 * 
	 * driver.findElement(By.id("postItemNew")).click(); }
	 */
}
