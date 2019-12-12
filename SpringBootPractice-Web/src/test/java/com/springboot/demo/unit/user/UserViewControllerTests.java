package com.springboot.demo.unit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.controller.UserViewController;
import com.springboot.demo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static com.springboot.demo.unit.GlobalConstants.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * UserViewController 테스트 클래스
 * (RestTemplate 을 사용한 REST 통신 과정은 Mock API 서버를 사용한다.)
 * (또한, Mock API 서버로부터 받아야 할 JSON 데이터는 테스트하지 않으며 Controller 가 매개변수를 통해 받은 데이터를 모델을 변환하였다는 가정 하에 테스트한다.)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserViewControllerTests {
    private static final Logger logger = LoggerFactory.getLogger(UserViewControllerTests.class);

    @Autowired private RestTemplate restTemplate;
    @Autowired private UserViewController userViewController;

    private MockMvc mockMvc;
    private MockRestServiceServer mockUserServer;
    private MockHttpServletRequest mockRequest;
    private MockHttpSession mockSession;
    private ObjectMapper mapper;

    // *---------------------------------------------------------------------------------------------------------------* [테스트 전/후 작업]

    @Before
    public void setUp() {
        // MockMvc 객체에 UserViewController 빈을 연결한다.
        mockMvc = MockMvcBuilders.standaloneSetup(userViewController).build();

        // Mock API 서버에 RestTemplate 빈을 연결한다. (사용자가 정의한 순서대로 예측하지 않도록 지정)
        mockUserServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();

        // Mock HttpServletRequest 생성
        mockRequest = new MockHttpServletRequest();

        // Mock 세션 생성
        mockSession = new MockHttpSession();

        // Jackson 매퍼 생성
        mapper = new ObjectMapper();
    }

    // *---------------------------------------------------------------------------------------------------------------* [계정 등록 테스트]

    /**
     * 계정 등록 폼 성공 테스트
     * @throws Exception
     */
    @Test
    public void getJoin_성공_테스트() throws Exception {
        /* Given (없음) */

        /* When */
        MvcResult result = mockMvc.perform(get("/users/join"))
                                  .andDo(print()).andReturn();

        /* Then */
        // Controller 의 응답 코드, 모델 타입, 모델에 저장된 객체, 뷰 이름 검사
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getModelAndView().getModel().get("user")).isInstanceOf(User.class);
        assertThat(((User) result.getModelAndView().getModel().get("user")).getUser_id()).isNull();
        assertThat(((User) result.getModelAndView().getModel().get("user")).getUser_password()).isNull();
        assertThat(((User) result.getModelAndView().getModel().get("user")).getUser_name()).isNull();
        assertThat(((User) result.getModelAndView().getModel().get("user")).getUser_email()).isNull();
        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/join");
    }

    /**
     * 계정 등록 성공 테스트
     * (참고: 계정 등록 시, 리소스 서버에서 등록된 계정 객체를 반환하도록 구현되어 있다.)
     * @throws Exception
     */
    @Test
    public void postJoin_성공_테스트() throws Exception {
        /* Given */
        User requestUser = new User("admin", "test123!", "테스트", "admin@test.com");
        User responseUser = new User("admin", "test123!", "테스트", "admin@test.com");

        // 리소스 서버는 localhost:8080/user, POST, JSON(requestUser) 로 요청이 올 때,
        // 200 OK, JSON(responseUser)를 반환하여 응답한다.
        mockUserServer.expect(requestTo(USER_URI + "/user"))
                      .andExpect(method(HttpMethod.POST))
                      .andExpect(content().string(mapper.writeValueAsString(requestUser)))
                      .andRespond(withStatus(HttpStatus.OK)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .body(mapper.writeValueAsString(responseUser)));

        /* When */
        MvcResult result = mockMvc.perform(post("/users/join")
                                  .param("user_id", "admin")
                                  .param("user_password", "test123!")
                                  .param("user_name", "테스트")
                                  .param("user_email", "admin@test.com"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("계정 등록이 완료되었습니다.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/");
    }

    /**
     * 계정 등록 실패 테스트
     * (웹 서비스 내의 유효성 검증 실패로 인한 계정 등록 실패)
     * @throws Exception
     */
    @Test
    public void postJoin_유효성_실패_테스트() throws Exception {
        /* Given */
        // 웹 서비스에서 테스트가 완료되므로, 리소스 서버 및 요청/응답 객체 없음

        /* When */
        MvcResult result = mockMvc.perform(post("/users/join")
                                  .param("user_id", "admin")
                                  .param("user_password", "test123!")

                                  // 사용자 이름/이메일을 빈 값으로 전달하여 유효성 검증 실패 케이스를 유도한다.
                                  .param("user_name", "")
                                  .param("user_email", ""))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("계정 등록 정보를 확인해주세요.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/join");
    }

    /**
     * 계정 등록 실패 테스트
     * (계정 서비스의 406 응답 코드로 인한 계정 등록 실패)
     * @throws Exception
     */
    @Test
    public void postJoin_응답_실패_테스트() throws Exception {
        /* Given */
        User requestUser = new User("admin", "test123!", "테스트", "admin@test.com");
        User responseUser = new User("admin", "test123!", "테스트", "admin@test.com");

        mockUserServer.expect(requestTo(USER_URI + "/user"))
                      .andExpect(method(HttpMethod.POST))
                      .andExpect(content().string(mapper.writeValueAsString(requestUser)))

                      // 406 응답 코드를 지정하여 응답 코드 실패 케이스를 유도한다.
                      .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .body(mapper.writeValueAsString(responseUser)));

        /* When */
        MvcResult result = mockMvc.perform(post("/users/join")
                                  .param("user_id", "admin")
                                  .param("user_password", "test123!")
                                  .param("user_name", "테스트")
                                  .param("user_email", "admin@test.com"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("계정 등록 중, 문제가 발생하였습니다.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/join");
    }

    // *---------------------------------------------------------------------------------------------------------------* [계정 로그인 테스트]

    /**
     * 계정 로그인 폼 성공 테스트
     * @throws Exception
     */
    @Test
    public void getLogin_성공_테스트() throws Exception {
        /* Given (없음) */

        /* When */
        MvcResult result = mockMvc.perform(get("/users/login"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/login");
    }

    /**
     * 계정 로그인 성공 테스트
     * @throws Exception
     */
    @Test
    public void postLogin_성공_테스트() throws Exception {
        /* Given */
        // 로그인 시, ID/비밀번호만 입력하므로 이름/이메일은 null (null 이므로 Controller 호출 시, 매개변수를 전달하지 않는다.)
        User requestUser = new User("admin", "test123!", null, null);
        User responseUser = new User("admin", "test123!", "테스트", "admin@test.com");

        mockUserServer.expect(requestTo(USER_URI + "/login"))
                      .andExpect(method(HttpMethod.POST))
                      .andExpect(content().string(mapper.writeValueAsString(requestUser)))
                      .andRespond(withStatus(HttpStatus.OK)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .body(mapper.writeValueAsString(responseUser)));

        /* When */
        // 세션 검사 (로그인 전, 세션에 계정 정보가 할당되지 않은 상태이므로 null)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isNull();

        MvcResult result = mockMvc.perform(post("/users/login").session(mockSession)
                                  .param("user_id", "admin")
                                  .param("user_password", "test123!"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("admin님, 로그인되었습니다.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/");

        // 세션 검사 (로그인 후, 세션에 계정 정보가 할당된 상태)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isInstanceOf(User.class);
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_id()).isEqualTo("admin");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_password()).isEqualTo("test123!");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_name()).isEqualTo("테스트");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_email()).isEqualTo("admin@test.com");
    }

    /**
     * 계정 로그인 실패 테스트
     * (ID 또는 비밀번호 불일치로 인한 계정 로그인 실패)
     * @throws Exception
     */
    @Test
    public void postLogin_실패_테스트() throws Exception {
        /* Given */
        // 비밀번호 불일치 테스트이므로 계정 비밀번호를 빈 값으로 지정한다.
        User requestUser = new User("admin", "", null, null);

        mockUserServer.expect(requestTo(USER_URI + "/login"))
                      .andExpect(method(HttpMethod.POST))
                      .andExpect(content().string(mapper.writeValueAsString(requestUser)))
                      .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE)
                      .contentType(MediaType.APPLICATION_JSON_UTF8));

        /* When */
        // 세션 검사 (로그인 전, 세션에 계정 정보가 할당되지 않은 상태이므로 null)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isNull();

        MvcResult result = mockMvc.perform(post("/users/login")
                                  .param("user_id", "admin")

                                  // 계정 비밀번호를 빈 값으로 전달하여 로그인 실패 케이스를 유도한다.
                                  .param("user_password", ""))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("ID 또는 비밀번호를 확인해주세요.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/login");

        // 세션 검사 (로그인 실패 후, 세션에 계정 정보가 할당되지 않은 상태이므로 null)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isNull();
    }

    // *---------------------------------------------------------------------------------------------------------------* [계정 로그아웃 테스트]

    /**
     * 계정 로그아웃 성공 테스트
     * @throws Exception
     */
    @Test
    public void requestLogout_성공_테스트() throws Exception {
        /* Given */
        User requestUser = new User("admin", "test123!", "테스트", "admin@test.com");

        // 세션에 계정 정보를 등록하여 로그인 상태를 가정
        mockSession.setAttribute(LOGGED_USER, requestUser);

        /* When */
        // 세션 검사 (로그아웃 전, 세션에 계정 정보가 할당된 상태)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isInstanceOf(User.class);
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_id()).isEqualTo("admin");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_password()).isEqualTo("test123!");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_name()).isEqualTo("테스트");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_email()).isEqualTo("admin@test.com");

        MvcResult result = mockMvc.perform(get("/users/logout").session(mockSession))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("admin님, 로그아웃 되었습니다.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/");

        // 세션 검사 (로그아웃 후, 세션에 계정 정보가 할당되지 않은 상태이므로 null)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isNull();
    }

    // *---------------------------------------------------------------------------------------------------------------* [계정 수정 테스트]

    /**
     * 계정 수정 폼 성공 테스트
     * @throws Exception
     */
    @Test
    public void getEdit_성공_테스트() throws Exception {
        /* Given */
        User requestUser = new User("admin", "test123!", "테스트", "admin@test.com");

        // 세션에 계정 정보를 등록하여 로그인 상태를 가정
        mockSession.setAttribute(LOGGED_USER, requestUser);

        // 세션 검사 (계정 수정 전, 세션에 계정 정보가 할당된 상태)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isInstanceOf(User.class);
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_id()).isEqualTo("admin");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_password()).isEqualTo("test123!");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_name()).isEqualTo("테스트");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_email()).isEqualTo("admin@test.com");

        /* When */
        MvcResult result = mockMvc.perform(get("/users/edit").session(mockSession))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getModelAndView().getModel().get("user")).isInstanceOf(User.class);
        assertThat(((User) result.getModelAndView().getModel().get("user")).getUser_id()).isEqualTo("admin");
        assertThat(((User) result.getModelAndView().getModel().get("user")).getUser_password()).isEqualTo("test123!");
        assertThat(((User) result.getModelAndView().getModel().get("user")).getUser_name()).isEqualTo("테스트");
        assertThat(((User) result.getModelAndView().getModel().get("user")).getUser_email()).isEqualTo("admin@test.com");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/edit");
    }

    /**
     * 계정 수정 폼 실패 테스트
     * (계정 수정 폼 실패) -> 비로그인 상태에서 계정 수정 폼을 요청하였을 때
     */

    /**
     * 계정 수정 성공 테스트
     * (참고: 계정 수정 시, 리소스 서버에서 수정된 계정 객체를 반환하도록 구현되어 있다.)
     * @throws Exception
     */
    @Test
    public void putEdit_성공_테스트() throws Exception {
        /* Given */
        User loggedUser = new User("admin", "test123!", "테스트", "admin@test.com");
        User requestUser = new User("admin", "admin123!", "테스트", "admin@test.com");
        User responseUser = new User("admin", "admin123!", "테스트", "admin@test.com");

        // 로그인 된 상태에서 계정 정보를 수정할 수 있으므로 세션 등록
        mockSession.setAttribute(LOGGED_USER, loggedUser);

        mockUserServer.expect(requestTo(USER_URI + "/userinfo-edit"))
                      .andExpect(method(HttpMethod.PUT))
                      .andExpect(content().string(mapper.writeValueAsString(requestUser)))
                      .andRespond(withStatus(HttpStatus.OK)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .body(mapper.writeValueAsString(responseUser)));

        /* When (ID 수정은 없으므로 제외) */
        MvcResult result = mockMvc.perform(put("/users/edit").session(mockSession)
                                  .param("user_password", "admin123!")
                                  .param("user_name", "테스트")
                                  .param("user_email", "admin@test.com"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("계정 수정이 완료되었습니다.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/");

        // 세션 검사 (계정 수정 후, 세션에 수정된 계정 정보가 할당된 상태)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isInstanceOf(User.class);
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_id()).isEqualTo("admin");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_password()).isEqualTo("admin123!");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_name()).isEqualTo("테스트");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_email()).isEqualTo("admin@test.com");
    }

    /**
     * 계정 수정 실패 테스트
     * (웹 서비스 내의 유효성 검증 실패로 인한 계정 수정 실패)
     * @throws Exception
     */
    @Test
    public void putEdit_유효성_실패_테스트() throws Exception {
        /* Given */
        // 웹 서비스에서 테스트가 완료되므로, 리소스 서버 및 요청/응답 객체 없음
        User loggedUser = new User("admin", "test123!", "테스트", "admin@test.com");

        // 로그인 된 상태에서 계정 정보를 수정할 수 있으므로 세션 등록
        mockSession.setAttribute(LOGGED_USER, loggedUser);

        /* When (ID 수정은 없으므로 제외) */
        MvcResult result = mockMvc.perform(put("/users/edit").session(mockSession)
                                  // 계정 비밀번호를 빈 값으로 전달하여 유효성 검증 실패 케이스를 유도한다.
                                  .param("user_password", "")
                                  .param("user_name", "테스트")
                                  .param("user_email", "admin@test.com"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("계정 수정 정보를 확인해주세요.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/users/edit");

        // 세션 검사 (계정 수정 실패 후, 세션에 기존 계정 정보가 있는 상태)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isInstanceOf(User.class);
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_id()).isEqualTo("admin");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_password()).isEqualTo("test123!");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_name()).isEqualTo("테스트");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_email()).isEqualTo("admin@test.com");
    }

    /**
     * 계정 수정 실패 테스트
     * (계정 서비스의 406 응답 코드로 인한 계정 수정 실패)
     * @throws Exception
     */
    @Test
    public void putEdit_응답_실패_테스트() throws Exception {
        /* Given */
        User loggedUser = new User("admin", "test123!", "테스트", "admin@test.com");
        User requestUser = new User("admin", "admin123!", "테스트", "admin@test.com");
        User responseUser = new User("admin", "admin123!", "테스트", "admin@test.com");

        // 로그인 된 상태에서 계정 정보를 수정할 수 있으므로 세션 등록
        mockSession.setAttribute(LOGGED_USER, loggedUser);

        mockUserServer.expect(requestTo(USER_URI + "/userinfo-edit"))
                      .andExpect(method(HttpMethod.PUT))
                      .andExpect(content().string(mapper.writeValueAsString(requestUser)))
                      // 406 응답 코드를 지정하여 응답 실패 케이스를 유도한다.
                      .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .body(mapper.writeValueAsString(responseUser)));

        /* When (ID 수정은 없으므로 제외) */
        MvcResult result = mockMvc.perform(put("/users/edit").session(mockSession)
                                  .param("user_password", "admin123!")
                                  .param("user_name", "테스트")
                                  .param("user_email", "admin@test.com"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("계정 수정 중, 문제가 발생하였습니다.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/users/edit");

        // 세션 검사 (계정 수정 실패 후, 세션에 기존 계정 정보가 있는 상태)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isInstanceOf(User.class);
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_id()).isEqualTo("admin");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_password()).isEqualTo("test123!");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_name()).isEqualTo("테스트");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_email()).isEqualTo("admin@test.com");
    }

    // *---------------------------------------------------------------------------------------------------------------* [계정 탈퇴 테스트]

    /**
     * 계정 탈퇴 폼 성공 테스트
     * @throws Exception
     */
    @Test
    public void getDelete_성공_테스트() throws Exception {
        /* Given (없음) */

        /* When */
        MvcResult result = mockMvc.perform(get("/users/delete"))
                                  .andDo(print()).andReturn();

        /* Then */
        // View 검사 (Model 이 없으므로 View 를 검사한다.)
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/delete");
    }

    /**
     * 계정 탈퇴 성공 테스트
     * @throws Exception
     */
    @Test
    public void deleteUser_성공_테스트() throws Exception {
        /* Given */
        User loggedUser = new User("admin", "test123!", "테스트", "admin@test.com");
        User requestUser = new User("admin", "test123!", null, null);
        User responseUser = new User(null, null, null, null);

        // 로그인 된 상태에서 계정 탈퇴할 수 있으므로 세션 등록
        mockSession.setAttribute(LOGGED_USER, loggedUser);

        mockUserServer.expect(requestTo(USER_URI + "/login"))
                      .andExpect(method(HttpMethod.POST))
                      .andExpect(content().string(mapper.writeValueAsString(requestUser)))
                      .andRespond(withStatus(HttpStatus.OK)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .body(mapper.writeValueAsString(responseUser)));

        mockUserServer.expect(requestTo(USER_URI + "/user-delete/" + "admin"))
                      .andExpect(method(HttpMethod.DELETE))
                      .andRespond(withStatus(HttpStatus.OK));

        /* When */
        MvcResult result = mockMvc.perform(delete("/users/delete").session(mockSession)
                                  .param("user_id", "admin")
                                  .param("user_password", "test123!"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("계정 탈퇴가 완료되었습니다.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/");

        // 세션 검사 (계정 탈퇴 후, 세션에 계정 정보가 없는 상태)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isNull();
    }

    /**
     * 계정 탈퇴 실패 테스트
     * (비밀번호 불일치로 인한 실패)
     * @throws Exception
     */
    @Test
    public void deleteUser_비밀번호_검사_실패_테스트() throws Exception {
        /* Given */
        User loggedUser = new User("admin", "test123!", "테스트", "admin@test.com");
        User requestUser = new User("admin", "", null, null);
        User responseUser = new User("admin", "test123!", "테스트", "admin@test.com");

        // 로그인 된 상태에서 계정 탈퇴할 수 있으므로 세션 등록
        mockSession.setAttribute(LOGGED_USER, loggedUser);

        mockUserServer.expect(requestTo(USER_URI + "/login"))
                      .andExpect(method(HttpMethod.POST))
                      .andExpect(content().string(mapper.writeValueAsString(requestUser)))
                      // 406 응답 코드를 지정하여 응답 실패 케이스를 유도한다.
                      .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .body(mapper.writeValueAsString(responseUser)));

        /* When */
        MvcResult result = mockMvc.perform(delete("/users/delete").session(mockSession)
                                  .param("user_id", "admin")
                                  // 계정 비밀번호를 빈 값으로 전달하여 비밀번호 검사 실패 케이스를 유도한다.
                                  .param("user_password", ""))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(302);
        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("비밀번호가 일치하지 않습니다.");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/users/delete");

        // 세션 검사 (계정 탈퇴 실패 후, 세션에 기존 계정 정보가 있는 상태)
        assertThat(mockSession.getAttribute(LOGGED_USER)).isInstanceOf(User.class);
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_id()).isEqualTo("admin");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_password()).isEqualTo("test123!");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_name()).isEqualTo("테스트");
        assertThat(((User) mockSession.getAttribute(LOGGED_USER)).getUser_email()).isEqualTo("admin@test.com");
    }

    // *---------------------------------------------------------------------------------------------------------------* [계정 조회 테스트]

    /**
     * 계정 목록 조회 성공 테스트
     * @throws Exception
     */
    @Test
    public void getUsers_성공_테스트() throws Exception {
        /* Given */
        List<User> responseUserList = new ArrayList<>();
        responseUserList.add(new User("user1", "test123!", "테스트1", "user1@test.com"));
        responseUserList.add(new User("user2", "test123!", "테스트2", "user2@test.com"));
        responseUserList.add(new User("user3", "test123!", "테스트3", "user3@test.com"));

        mockUserServer.expect(requestTo(USER_URI + "/users"))
                      .andExpect(method(HttpMethod.GET))
                      .andRespond(withStatus(HttpStatus.OK)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .body(mapper.writeValueAsString(responseUserList)));

        /* When */
        MvcResult result = mockMvc.perform(get("/users"))
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        List<User> checkResponseUserList = (List<User>) result.getModelAndView().getModel().get("userList");
        User user1 = checkResponseUserList.get(0);
        User user2 = checkResponseUserList.get(1);
        User user3 = checkResponseUserList.get(2);

        assertThat(user1.getUser_id()).isEqualTo("user1");
        assertThat(user2.getUser_id()).isEqualTo("user2");
        assertThat(user3.getUser_id()).isEqualTo("user3");
        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/users_test");
    }

    /**
     * 계정 목록 조회 실패 테스트
     * @throws Exception
     */
    @Test
    public void getUsers_실패_테스트() throws Exception {
        /* Given (조회된 계정 목록이 없음을 가정) */
        mockUserServer.expect(requestTo(USER_URI + "/users"))
                      .andExpect(method(HttpMethod.GET))
                      .andRespond(withStatus(HttpStatus.OK)
                      .contentType(MediaType.APPLICATION_JSON_UTF8)

                      // API 서버로부터 받은 계정 목록을 빈 값으로 지정하여 실패 케이스를 유도한다.
                      // (실제 API 서버에서도 null 이 아니라 빈 리스트를 반환한다.)
                      .body(mapper.writeValueAsString(new ArrayList<User>())));

        /* When */
        MvcResult result = mockMvc.perform(get("/users"))
                                  .andExpect(model().attributeExists("userList")) // 응답 값이 빈 리스트이더라도 모델에 빈 리스트를 저장하도록 구현됨.
                                  .andDo(print()).andReturn();

        /* Then */
        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        List<User> checkResponseUserList = (List<User>) result.getModelAndView().getModel().get("userList");
        assertThat(checkResponseUserList.size()).isEqualTo(0);

        assertThat(result.getModelAndView().getViewName()).isEqualTo("users/users_test");
    }

    // *---------------------------------------------------------------------------------------------------------------* [??? 테스트]
}