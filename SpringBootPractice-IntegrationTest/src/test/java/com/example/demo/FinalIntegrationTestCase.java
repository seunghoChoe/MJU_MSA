package com.example.demo;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.xml.serialize.LineSeparator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FinalIntegrationTestCase {
	/**
	 * 
	 * @author DongMin Kim from Major of Applied Software in Myongji Univ.
	 * 
	 * 셀레니움 드라이버 설치 필수 - 본 프로젝트는 ChromeDriver 78.0.3904.70 환경에 맞춰져 있음
	 * 
	 * 
	 */

	private WebDriver driver;
	private WebElement webElement;

	// 로컬환경 : http://localhost:8080/
	// 배포환경 : http://13.125.99.51:8080/
	private String driveurl = "http://13.125.99.51:8080/";

	SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss.SSS초"); // 로그 시간 표시

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
	public void 식당_게시글_쓰기() throws InterruptedException, IOException {
		// 이 메소드는 테스트 코드가 아닌 식당(게시글)의 표본 추가를 위해 실행하는 메소드임

		driver.get(driveurl);// 웹 서비스 접속

		// 로그인
		driver.findElement(By.linkText("로그인")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		webElement = driver.findElement(By.id("user_id"));
		String id = "admin";
		webElement.sendKeys(id);

		webElement = driver.findElement(By.id("user_password"));
		String pw = "admin";
		webElement.sendKeys(pw);

		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(500);
		driver.switchTo().alert().accept();
		Thread.sleep(500);

		// 식당 목록 접속
		driver.findElement(By.linkText("맛집 스토리")).click();
		Thread.sleep(500);
		driver.findElement(By.linkText("식당 목록")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		for (int a = 1; a < 6; a++) {
			driver.findElement(By.id("restaurantItemNew")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			webElement = driver.findElement(By.id("res_name"));
			String title = "베가" + a;
			webElement.sendKeys(title);

			// iframe으로 구성된 곳은 해당 프레임으로 전환
			WebElement iframe = driver.findElement(By.className("cke_wysiwyg_frame"));
			driver.switchTo().frame(iframe);
			WebElement webElement = driver.findElement(By.xpath("/html/body"));
			String description = "가나다라마바사사사";
			webElement.sendKeys(description);
			Thread.sleep(200);

			driver.switchTo().parentFrame();
			webElement = driver.findElement(By.id("newRestaurantButton"));
			webElement.submit();
			Thread.sleep(300);
			driver.switchTo().alert().accept();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
	}

	@Test
	public void 식당삭제_테스트_케이스() throws InterruptedException, IOException {
		//로그파일 생성
		File logFile = new File("./식당삭제_테스트_케이스_logfile.txt");
		FileWriter fw = new FileWriter(logFile);

		driver.get(driveurl);//웹 서비스 접속
		fw.write(format.format(new Date()) + ": 식당 삭제 테스트 케이스 실시.\r\n");

		/*
		 * 로그인 된 사용자의 테스트
		 */
		driver.findElement(By.linkText("로그인")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		webElement = driver.findElement(By.id("user_id"));
		String id = "admin";
		webElement.sendKeys(id);

		webElement = driver.findElement(By.id("user_password"));
		String pw = "admin123!";
		webElement.sendKeys(pw);

		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(500);
		driver.switchTo().alert().accept();
		Thread.sleep(500);
		fw.write(format.format(new Date()) + ": " + id + " 으로 로그인 됨 .\r\n");

		//식당 목록 접속
		driver.findElement(By.linkText("맛집 스토리")).click();
		Thread.sleep(500);
		driver.findElement(By.linkText("식당 목록")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//삭제할 식당 탐색 및 지정
		String restTitle = driver
				.findElement(By.xpath("//*[@id=\"restaurantListContent\"]/div/div[10]/div/div[1]/p[1]")).getText();
		fw.write(format.format(new Date()) + ": 삭제 요청 식당 이름 = '" + restTitle + "'\r\n");

		driver.findElement(By.xpath("//*[@id=\"restaurantListContent\"]/div/div[10]/div")).click();
		fw.write(format.format(new Date()) + ": 10번째 식당의 세부내용 페이지 접근함.\r\n");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//삭제 요청
		driver.findElement(By.id("restaurantItemDelete")).click();
		Thread.sleep(500);

		if (id.equals("admin")) {
			// 사용자가 admin일 때
			if (ExpectedConditions.alertIsPresent().apply(driver) != null) {
				// alert 창이 출력될 때
				fw.write(format.format(new Date()) + ": alert 출력됨, 내용 = '" + driver.switchTo().alert().getText()
						+ "'\r\n");
				driver.switchTo().alert().accept();
				fw.write(format.format(new Date()) + ": 식당 삭제 요청함.\r\n");
				Thread.sleep(500);

				if (driver.switchTo().alert().getText().contains("오류")) {
					//오류 팝업이 출력될 때
					fw.write(format.format(new Date()) + ": alert 출력됨, 내용 = '" + driver.switchTo().alert().getText()
							+ "'\r\n");
					driver.switchTo().alert().accept();
					Thread.sleep(500);
				} else if (driver.switchTo().alert().getText().contains("성공")) {
					//삭제 성공 팝업이 출력될 때
					fw.write(format.format(new Date()) + ": alert 출력됨, 내용 = '" + driver.switchTo().alert().getText()
							+ "'\r\n");
					fw.write(format.format(new Date()) + ": 식당 삭제 요청함.\r\n");
					driver.switchTo().alert().accept();
					Thread.sleep(500);
				}

				// 불안정 코드 - 속도 문제
//					driver.findElement(By.id("restaurantItemList")).click();
//					driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
//	
//					int a = 1;
//					while (true) {
//						try {
//							String restTitle_1 = driver
//									.findElement(By.cssSelector("#restaurantListContent > div > div:nth-child(" + a
//											+ ") > div > div.card-img-text > p.restaurantName"))
//									.getText();
//							System.out.println(restTitle_1);
//							Thread.sleep(500);
//							
//							if (restTitle.equals(restTitle_1)) {
//								fw.write(format.format(new Date()) + ": 식당 삭제 되지 않음.\r\n");
//								break;
//							} else {
//								a++;
//							}
//						} catch (NoSuchElementException e) {
//							fw.write(format.format(new Date()) + ": 식당 삭제 됨.\r\n");
//							fw.write(format.format(new Date()) + ": 케이스 테스트 종료.\r\n");
//							fw.close();
//						}
//					}

			}
		} else {
			// 사용자가 일반 사용자일때
			if (ExpectedConditions.alertIsPresent().apply(driver) != null) {
				fw.write(format.format(new Date()) + ": alert 출력됨, 내용 = '" + driver.switchTo().alert().getText()
						+ "'\r\n");
				Thread.sleep(500);
				driver.switchTo().alert().dismiss();
				fw.write(format.format(new Date()) + ": 데이터 보호를 위해 alert 취소함.\r\n");
				Thread.sleep(500);
			}
		}
		fw.write(format.format(new Date()) + ": 케이스 테스트 종료.\r\n");
		fw.close();
	}

	@Test
	public void 로그인_테스트_성공케이스() throws InterruptedException, IOException {
		File logFile = new File("./로그인_테스트_성공케이스_logfile.txt");
		FileWriter fw = new FileWriter(logFile);

		driver.get(driveurl); //웹 서비스 접속
		fw.write(format.format(new Date()) + " 서버 간 정상 연결된 상태에서 로그인 테스트 실시 .\r\n");

		/*
		 * 로그인 성공케이스
		 */
		fw.write(format.format(new Date()) + " 로그인이 성공하는 케이스 테스트 실시.\r\n");
		driver.findElement(By.linkText("로그인")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// id 필드 탐색
		webElement = driver.findElement(By.id("user_id"));
		String id = "ehdals";
		webElement.sendKeys(id);
		Thread.sleep(500);

		// pw 필드 탐색
		webElement = driver.findElement(By.id("user_password"));
		String pw = "Ehdals1!";
		webElement.sendKeys(pw);
		Thread.sleep(500);

		// 로그인 버튼 클릭
		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(500);

		if (driver.switchTo().alert().getText().contains("로그인")) {
			//'로그인 되었습니다' 팝업이 출력될 시  
			fw.write(format.format(new Date()) + " 아이디: " + id + " / 비밀번호: " + pw + " / 로그인 성공함.\r\n");
			driver.switchTo().alert().accept();
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.linkText("로그아웃")).click();
			driver.switchTo().alert().accept();
			fw.write(format.format(new Date()) + " 로그아웃 됨.\r\n");
		} else {
			//로그인 실패 혹은 예외 발생 시
			fw.write(format.format(new Date()) + " (!!Exception!!) 아이디: " + id + " / 비밀번호: " + pw
					+ " / 로그인 실패 혹은 예외 발생함.\r\n");
		}

		/*
		 * 로그인 실패 - 존재하지 않는 아이디로 로그인 시도
		 */
		Thread.sleep(500);
		fw.write(format.format(new Date()) + " 존재하지 않는 아이디를 입력하여 로그인이 실패하는 케이스 테스트 실시.\r\n");
		driver.findElement(By.linkText("로그인")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// id 필드 탐색
		webElement = driver.findElement(By.id("user_id"));
		id = "ehdalsehdals";
		webElement.sendKeys(id);
		Thread.sleep(500);

		// pw 필드 탐색
		webElement = driver.findElement(By.id("user_password"));
		pw = "Ehdals1!";
		webElement.sendKeys(pw);
		Thread.sleep(500);

		// 로그인 버튼 클릭
		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(500);
		if (driver.switchTo().alert().getText().contains("확인")) {
			//'아이디, 비밀번호를 확인해주세요' 팝업이 출력될 시  
			fw.write(format.format(new Date()) + " 아이디: " + id + " / 비밀번호: " + pw + " / 로그인 실패함.\r\n");
		} else {
			//로그인 성공 혹은 예외 발생 시
			fw.write(format.format(new Date()) + " (!!Exception!!) 아이디: " + id + " / 비밀번호: " + pw
					+ " 로그인 성공 혹은 예외 사항 발생함.\r\n");
		}
		driver.switchTo().alert().accept();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		/*
		 * 로그인 실패_일치하지 않은 비밀번호(비밀번호에 대소문자 구별되는지 확인)
		 */
		fw.write(format.format(new Date()) + " 아이디와 일치하지 않은 비밀번호를 입력하여 로그인이 실패하는 케이스 테스트 실시.\r\n");
		// id 필드 탐색
		webElement = driver.findElement(By.id("user_id"));
		id = "ehdals";
		webElement.sendKeys(id);
		Thread.sleep(500);

		// pw 필드 탐색
		webElement = driver.findElement(By.id("user_password"));
		pw = "ehdals1!";
		webElement.sendKeys(pw);
		Thread.sleep(500);

		// 로그인 버튼 클릭
		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(500);
		if (driver.switchTo().alert().getText().contains("확인")) {
			//'아이디, 비밀번호를 확인해주세요' 팝업이 출력될 시
			fw.write(format.format(new Date()) + " 아이디: " + id + " / 비밀번호: " + pw + " / 로그인 실패함.\r\n");
		} else {
			//로그인 성공 혹은 예외 발생 시
			fw.write(format.format(new Date()) + " (!!Exception!!) 아이디: " + id + " / 비밀번호: " + pw
					+ " 로그인 성공 혹은 예외 사항 발생함.\r\n");
		}

		driver.switchTo().alert().accept();
		fw.write(format.format(new Date()) + " 케이스 테스트 종료.\r\n");
		fw.close();
	}

	@Test
	public void 로그인_테스트_예외케이스() throws InterruptedException, IOException {
		// 로컬 환경 구현 필수
		File logFile = new File("./로그인_테스트_예외케이스_logfile.txt");
		FileWriter fw = new FileWriter(logFile);

		driver.get("http://localhost:8080/");
		fw.write(format.format(new Date()) + " User 서버와 Web 서버가 Disconnet 상태에서 로그인 케이스 테스트 실시.\r\n");
		/*
		 * 로그인 성공
		 */
		driver.findElement(By.linkText("로그인")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// id 필드 탐색
		try {
			//로그인 입력 페이지 정상 출력 검증
			webElement = driver.findElement(By.id("user_id"));
			fw.write(format.format(new Date()) + " 로그인 정보 입력 페이지 정상 출력됨.\r\n");
		} catch (NoSuchElementException e) {
			fw.write(format.format(new Date()) + " (!!Exception!!) 로그인 정보 입력 페이지 출력되지 않음.\r\n");
			fw.write(format.format(new Date()) + " 케이스 테스트 강제종료됨.\r\n");
			fw.close();
		}
		
		String id = "ehdals";
		webElement.sendKeys(id);
		Thread.sleep(500);

		// pw 필드 탐색
		webElement = driver.findElement(By.id("user_password"));
		String pw = "Ehdals1!";
		webElement.sendKeys(pw);
		Thread.sleep(500);

		// 로그인 버튼 클릭
		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		fw.write(format.format(new Date()) + " " + id + " 으로 로그인 시도.\r\n");
		Thread.sleep(500);

		// 점검 안내 페이지 출력 검증
		try {
			if (driver.findElement(By.xpath("//*[@id=\"error\"]/div/span[1]")).getText().contains("oops")) {
				fw.write(format.format(new Date()) + " 점검 안내 페이지 정상 출력됨.\r\n");
			} else {
				fw.write(format.format(new Date()) + " (!!Exception!!) 점검 안내 페이지 출력에 예외발생함.\r\n");
			}
		} catch (NoSuchElementException e) {
			fw.write(format.format(new Date()) + " (!!Exception!!) 점검 안내 페이지 출력되지 않음.\r\n");
		} finally {
			fw.write(format.format(new Date()) + " 케이스 테스트 종료.\r\n");
			fw.close();
		}

	}

	@Test
	public void 게시글수정_테스트_성공케이스() throws InterruptedException, IOException {
		File logFile = new File("./게시글수정_테스트_성공케이스_logfile.txt");
		FileWriter fw = new FileWriter(logFile);

		driver.get(driveurl);
		fw.write(format.format(new Date()) + ": 게시글 수정 테스트 성공 케이스 실시 .\r\n");
		/*
		 * 로그인된 사용자 게시글 수정
		 */
		driver.findElement(By.linkText("로그인")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		webElement = driver.findElement(By.id("user_id"));
		String id = "admin2";
		webElement.sendKeys(id);

		webElement = driver.findElement(By.id("user_password"));
		String pw = "test123!";
		webElement.sendKeys(pw);

		webElement = driver.findElement(By.id("loginButton"));
		webElement.submit();
		Thread.sleep(500);
		driver.switchTo().alert().accept();
		Thread.sleep(500);
		fw.write(format.format(new Date()) + ": '" + id + "' 으로 로그인 됨 .\r\n");
		
		//게시글 목록 접속
		driver.findElement(By.linkText("맛집 매거진")).click();
		Thread.sleep(500);
		driver.findElement(By.linkText("게시글 목록")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		fw.write(format.format(new Date()) + ": 게시글 목록 정상 조회됨.\r\n");
		
		//게시글 작성자와 회원이 일치하는지 검증
		int num = 1;
		while (true) {
			try {
				WebElement element = driver
						.findElement(By.xpath("/html/body/div/div[3]/div/div[3]/div/div[" + num + "]/div/div[1]/p"));
				String writer = element.getText();
				System.out.println(element.getText());
				element.click();
				fw.write(format.format(new Date()) + ": " + num + "번째 게시글의 세부내용 페이지로 접근함.\r\n");

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.findElement(By.id("postItemEdit")).click();
				Thread.sleep(500);

				if (id.equals(writer)) {
					// 작성자와 사용자가 같을 때
					fw.write(format.format(new Date()) + ": 게시글의 작성자와 로그인한 사용자가 같은 것으로 검증됨 .\r\n");
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					if (driver.findElement(By.xpath("//*[@id=\"postItemHeader\"]/p")).getText().equals(": 게시글 수정")) {
						// 수정페이지가 출력됐을 때
						fw.write(format.format(new Date()) + ": 게시글 수정 페이지가 출력됨.\r\n");

						// iframe으로 구성된 곳은 해당 프레임으로 전환
						WebElement iframe = driver.findElement(By.className("cke_wysiwyg_frame"));
						driver.switchTo().frame(iframe);
						WebElement webElement = driver.findElement(By.cssSelector("body"));
						double total = Math.random();
						String description = String.valueOf(total);
						fw.write(format.format(new Date()) + ": 수정될 것으로 예상되는 값 (입력된 값)= '" + description + "'\r\n");
						webElement.clear();
						webElement.sendKeys(description);
						Thread.sleep(500);
						
						driver.switchTo().parentFrame();//부모 프레임으로 복귀
						webElement = driver.findElement(By.id("newPostButton"));
						webElement.submit();
						Thread.sleep(500);
						driver.switchTo().alert().accept();
						Thread.sleep(500);
						
						//게시글 조회 시 수정된 내용이 반영되었는지 검증
						element = driver.findElement(
								By.xpath("/html/body/div/div[3]/div/div[3]/div/div[" + num + "]/div/div[1]/p"));

						element.click();

						String reDescription = driver.findElement(By.xpath("//*[@id=\"postItemContent\"]/p")).getText();
						fw.write(format.format(new Date()) + ": 실제 게시글의 내용 값 = '" + reDescription + "'\r\n");
						break;
					}
				} else {
					// 작성자와 사용자가 다를 때
					fw.write(format.format(new Date()) + ": 로그인 회원과 작성자가 다른 것으로 검증됨.\r\n");
					if (driver.switchTo().alert().getText().contains("작성자만")) {
						// 수정페이지 출력 안 됐을 때
						driver.switchTo().alert().accept();
						fw.write(format.format(new Date()) + ": 게시글 수정 페이지가 출력되지 않음.\r\n");
						driver.findElement(By.id("postItemList")).click();
						Thread.sleep(500);
					}
				}
				num++;
			} catch (NoSuchElementException e) {
				//회원이 작성한 게시글이 없을 시 
				fw.write(format.format(new Date()) + ": 로그인 회원과 작성자가 일치하는 게시글을 찾지 못함.\r\n");
				fw.write(format.format(new Date()) + ": 케이스 테스트 종료.\r\n");
				fw.close();
				break;
			}
		}
		fw.write(format.format(new Date()) + ": 케이스 테스트 종료.\r\n");
		fw.close();
	}

	@Test
	public void 게시글수정_테스트_예외케이스() throws InterruptedException, IOException {
		File logFile = new File("./게시글수정_테스트_예외케이스_logfile.txt");
		FileWriter fw = new FileWriter(logFile);
		/*
		 * 비로그인 사용자 게시글 수정
		 */
		driver.get(driveurl + "board/posts");
		fw.write(format.format(new Date()) + ": 게시글 수정 테스트 예외 케이스 실시.\r\n");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			//첫 게시글 접속
			WebElement element = driver
					.findElement(By.xpath("/html/body/div/div[3]/div/div[3]/div/div[1]/div/div[1]/h4"));
			element.click();
			fw.write(format.format(new Date()) + ": 1번째 게시글 세부내용 페이지 접근함.\r\n");

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			driver.findElement(By.id("postItemEdit")).click();
			if (driver.getTitle().equals("명지리본: 페이지 오류")) {
				//페이지 오류 메시지가 출력될 시
				fw.write(format.format(new Date()) + ": (!!Exception!!) 수정 불가 안내사항이 정상적으로 출력되지 않음.\r\n");
				Thread.sleep(500);
			}
		} catch (NoSuchElementException e) {
			//게시판에 게시글이 없을 시
			fw.write(format.format(new Date()) + ": 게시판 내 게시글이 존재하지 않음.\r\n");
			fw.write(format.format(new Date()) + ": 케이스 테스트 종료.\r\n");
			fw.close();
		}
		fw.write(format.format(new Date()) + ": 케이스 테스트 종료.\r\n");
		fw.close();
	}

	/*
	 * 개발중
	 * 
	 * @Test public void 식당삭제_테스트_예외케이스() throws InterruptedException, IOException {
	 * 
	 * }
	 * 
	 * 
	 */

}
