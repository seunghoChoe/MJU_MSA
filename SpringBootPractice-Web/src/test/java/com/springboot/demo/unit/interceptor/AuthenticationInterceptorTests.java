package com.springboot.demo.unit.interceptor;

import com.google.common.collect.FluentIterable;
import com.springboot.demo.controller.UserViewController;
import com.springboot.demo.interceptor.AuthenticationInterceptor;
import com.springboot.demo.interceptor.LoginCheck;
import com.springboot.demo.unit.board.BoardViewControllerTests;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.springboot.demo.unit.GlobalConstants.LOGGED_USER;
import static com.springboot.demo.unit.GlobalConstants.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationInterceptorTests {
    private static final Logger logger = LoggerFactory.getLogger(BoardViewControllerTests.class);

    @Autowired ApplicationContext applicationContext;
    @Autowired private RequestMappingHandlerAdapter handlerAdapter;
    @Autowired private RequestMappingHandlerMapping handlerMapping;
    @Autowired private UserViewController userViewController;

    private MockMvc mockMvc;
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private MockHttpSession mockSession;

    // *---------------------------------------------------------------------------------------------------------------* [테스트 전/후 작업]

    @Before
    public void setUp() {
        // MockMvc 객체에 UserViewController 빈을 연결한다.
        mockMvc = MockMvcBuilders.standaloneSetup(userViewController).build();

        // Mock HttpServletRequest 생성
        mockRequest = new MockHttpServletRequest();

        // Mock HttpServletResponse 생성
        mockResponse = new MockHttpServletResponse();

        // Mock 세션 생성
        mockSession = new MockHttpSession();
    }

    // *---------------------------------------------------------------------------------------------------------------* [인증 인터셉터 테스트]

    /**
     *
     * @throws Exception
     */
    @Test
    public void preHandle_인증_검사_없음_테스트() throws Exception {

    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void preHandle_인증_됨_테스트() throws Exception {
        // 로그인 계정 세션 등록
        mockSession.setAttribute(LOGGED_USER, createUser());

        MvcResult result = mockMvc.perform(get("/users/logout").session(mockSession))
                                  .andDo(print()).andReturn();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void preHandle_인증_안됨_테스트() throws Exception {
        // null 세션 등록
        mockSession.setAttribute(LOGGED_USER, null);

        MvcResult result = mockMvc.perform(get("/users/logout").session(mockSession))
                                  .andDo(print()).andReturn();
    }

//    /**
//     *
//     * @throws Exception
//     */
//    @Test
//    public void checkType_LoginCheck_타입_맞음_테스트() throws Exception {
//        AuthenticationInterceptor interceptorClass = new AuthenticationInterceptor();
//
//        // 인터셉터 클래스의 checkType() 메소드
//        Method checkTypeMethod = interceptorClass.getClass().getDeclaredMethod("checkType", Object.class);
//
//        // private 메소드 접근 허용
//        checkTypeMethod.setAccessible(true);
//
//        // Object value = checkTypeMethod.invoke(interceptorClass, HandlerMethod.class);
//        assertThat(checkTypeMethod.invoke(interceptorClass, HandlerMethod.class)).isInstanceOf(LoginCheck.class);
//    }
//
//    /**
//     *
//     * @throws Exception
//     */
//    @Test
//    public void checkType_LoginCheck_타입_아님_테스트() throws Exception {
//        AuthenticationInterceptor interceptorClass = new AuthenticationInterceptor();
//
//        // 인터셉터 클래스의 checkType() 메소드
//        Method checkTypeMethod = interceptorClass.getClass().getDeclaredMethod("checkType", Object.class);
//
//        // private 메소드 접근 허용
//        checkTypeMethod.setAccessible(true);
//
//        assertThat(checkTypeMethod.invoke(interceptorClass, String.class)).isNull();
//    }
}