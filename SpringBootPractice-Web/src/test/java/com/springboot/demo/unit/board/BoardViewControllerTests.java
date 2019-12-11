//package com.springboot.demo.unit.board;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.springboot.demo.controller.BoardViewController;
//import com.springboot.demo.model.Post;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.client.MockRestServiceServer;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.client.RestTemplate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.springboot.demo.unit.GlobalConstants.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
///**
// * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
// *
// * @author Jaeha Son
// * @date 2019-12-13
// * @version 0.9
// * @description
// * BoardViewController 테스트 클래스
// * (RestTemplate 을 사용한 REST 통신 과정은 Mock API 서버를 사용한다.)
// * (또한, Mock API 서버로부터 받아야 할 JSON 데이터는 테스트하지 않으며 Controller 가 매개변수를 통해 받은 데이터를 모델을 변환하였다는 가정 하에 테스트한다.)
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BoardViewControllerTests {
//    private static final Logger logger = LoggerFactory.getLogger(BoardViewControllerTests.class);
//
//    @Autowired private RestTemplate restTemplate;
//    @Autowired private BoardViewController boardViewController;
//
//    private MockMvc mockMvc;
//    private MockRestServiceServer mockBoardServer;
//    private MockHttpServletRequest mockRequest;
//    private MockHttpSession mockSession;
//    private ObjectMapper mapper;
//
//    // *---------------------------------------------------------------------------------------------------------------* [테스트 전/후 작업]
//
//    @Before
//    public void setUp() {
//        // MockMvc 객체에 BoardViewController 빈을 연결한다.
//        mockMvc = MockMvcBuilders.standaloneSetup(boardViewController).build();
//
//        // Mock API 서버에 RestTemplate 빈을 연결한다.
//        mockBoardServer = MockRestServiceServer.bindTo(restTemplate).build();
//
//        // Mock HttpServletRequest 생성
//        mockRequest = new MockHttpServletRequest();
//
//        // Mock 세션 생성
//        mockSession = new MockHttpSession();
//
//        // Jackson 매퍼 생성
//        // LocalDateTime 파싱 에러 (-> https://stackoverflow.com/questions/45863678/json-parse-error-can-not-construct-instance-of-java-time-localdate-no-string-a)
//        mapper = new ObjectMapper().registerModule(new JavaTimeModule());
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [게시글 등록 테스트]
//
//    /**
//     * 게시글 등록 폼 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void getPostNew_성공_테스트() throws Exception {
//        /* Given (없음) */
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/board/posts/new"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get("post")).isInstanceOf(Post.class);
//
//        Post tempPost = (Post) result.getModelAndView().getModel().get("post");
//        assertThat(tempPost.getPost_user_id()).isNull();
//        assertThat(tempPost.getPost_title()).isNull();
//        assertThat(tempPost.getPost_content()).isNull();
//
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("board/posts/new");
//    }
//
//    /**
//     * 게시글 등록 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void postPostNew_성공_테스트() throws Exception {
//        /* Given */
//        Post requestPost = new Post("admin", "테스트 제목", "테스트 내용", "http://via.placeholder.com/350x150");
//        Post responsePost = new Post("admin", "테스트 제목", "테스트 내용", "http://via.placeholder.com/350x150");
//
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        mockBoardServer.expect(requestTo(BOARD_URI + "/post"))
//                       .andExpect(method(HttpMethod.POST))
//                       .andExpect(content().string(mapper.writeValueAsString(requestPost)))
//                       .andRespond(withStatus(HttpStatus.OK)
//                       .contentType(MediaType.APPLICATION_JSON_UTF8)
//                       .body(mapper.writeValueAsString(responsePost)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(post("/board/posts/new").session(mockSession)
//                                  .param("post_title", "테스트 제목")
//                                  .param("post_content", "테스트 내용")
//                                  .param("post_image", "http://via.placeholder.com/350x150"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(302);
//        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("게시글 등록이 완료되었습니다.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/board/posts");
//    }
//
//    /**
//     * 게시글 등록 실패 테스트
//     * (웹 서비스 내의 유효성 검증 실패로 인한 계정 수정 실패)
//     * @throws Exception
//     */
//    @Test
//    public void postPostNew_유효성_실패_테스트() throws Exception {
//        /* Given */
//        // 웹 서비스에서 테스트가 완료되므로, 리소스 서버 및 요청/응답 객체 없음
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        /* When */
//        MvcResult result = mockMvc.perform(post("/board/posts/new").session(mockSession)
//                                  // 게시글 제목/내용을 빈 값으로 전달하여 유효성 실패 케이스를 유도한다.
//                                  .param("post_title", "")
//                                  .param("post_content", "")
//                                  .param("post_image", "http://via.placeholder.com/350x150"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("게시글 등록 정보를 확인해주세요.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("board/posts/new");
//    }
//
//    /**
//     * 게시글 등록 실패 테스트
//     * (406 응답 코드로 인한 게시글 등록 실패)
//     * @throws Exception
//     */
//    @Test
//    public void postPostNew_응답_코드_실패_테스트() throws Exception {
//        /* Given */
//        Post requestPost = new Post("admin", "테스트 제목", "테스트 내용", "http://via.placeholder.com/350x150");
//        Post responsePost = new Post("admin", "테스트 제목", "테스트 내용", "http://via.placeholder.com/350x150");
//
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        mockBoardServer.expect(requestTo(BOARD_URI + "/post"))
//                       .andExpect(method(HttpMethod.POST))
//                       .andExpect(content().string(mapper.writeValueAsString(requestPost)))
//                       // 406 응답 코드를 지정하여 응답 코드 실패 케이스를 유도한다.
//                       .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE)
//                       .contentType(MediaType.APPLICATION_JSON_UTF8)
//                       .body(mapper.writeValueAsString(responsePost)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(post("/board/posts/new").session(mockSession)
//                                  .param("post_title", "테스트 제목")
//                                  .param("post_content", "테스트 내용")
//                                  .param("post_image", "http://via.placeholder.com/350x150"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("게시글 등록 중, 문제가 발생하였습니다.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("board/posts/new");
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [게시글 조회 테스트]
//
//    /**
//     * 게시글 목록 조회 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void getPosts_성공_테스트() throws Exception {
//        /* Given */
//        List<Post> responsePostList = new ArrayList<>();
//        responsePostList.add(new Post(1, "user1", "제목1", "내용1", "http://via.placeholder.com/350x150", LocalDateTime.now(), LocalDateTime.now()));
//        responsePostList.add(new Post(2, "user2", "제목2", "내용2", "http://via.placeholder.com/350x150", LocalDateTime.now(), LocalDateTime.now()));
//        responsePostList.add(new Post(3, "user3", "제목3", "내용3", "http://via.placeholder.com/350x150", LocalDateTime.now(), LocalDateTime.now()));
//
//        mockBoardServer.expect(requestTo(BOARD_URI + "/posts"))
//                       .andExpect(method(HttpMethod.GET))
//                       .andRespond(withStatus(HttpStatus.OK)
//                       .contentType(MediaType.APPLICATION_JSON_UTF8)
//                       .body(mapper.writeValueAsString(responsePostList)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/board/posts"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//
//        List<Post> checkResponsePostList = (List<Post>) result.getModelAndView().getModel().get("postList");
//        Post post1 = checkResponsePostList.get(0);
//        Post post2 = checkResponsePostList.get(1);
//        Post post3 = checkResponsePostList.get(2);
//
//        assertThat(post1.getPost_user_id()).isEqualTo("user1");
//        assertThat(post2.getPost_user_id()).isEqualTo("user2");
//        assertThat(post3.getPost_user_id()).isEqualTo("user3");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("board/posts/list");
//    }
//
//    /**
//     * 게시글 상세 조회 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void getPost_성공_테스트() throws Exception {
//        /* Given */
//        Post responsePost = new Post(1, "admin", "테스트 제목", "테스트 내용", "http://via.placeholder.com/350x150", LocalDateTime.now(), LocalDateTime.now());
//
//        mockBoardServer.expect(requestTo(BOARD_URI + "/posts/" + 1))
//                       .andExpect(method(HttpMethod.GET))
//                       .andRespond(withStatus(HttpStatus.OK)
//                       .contentType(MediaType.APPLICATION_JSON_UTF8)
//                       .body(mapper.writeValueAsString(responsePost)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/board/posts/" + 1))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get("post")).isInstanceOf(Post.class);
//    }
//}