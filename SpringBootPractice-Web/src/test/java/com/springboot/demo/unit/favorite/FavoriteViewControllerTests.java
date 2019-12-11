//package com.springboot.demo.unit.favorite;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.springboot.demo.controller.FavoriteViewController;
//import com.springboot.demo.model.Menu;
//import com.springboot.demo.model.MyRestaurant;
//import com.springboot.demo.model.Restaurant;
//import com.springboot.demo.model.User;
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
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.springboot.demo.unit.GlobalConstants.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//
///**
// * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
// *
// * @author Jaeha Son
// * @date 2019-12-13
// * @version 0.9
// * @description
// * FavoriteViewController 테스트 클래스
// * (RestTemplate 을 사용한 REST 통신 과정은 Mock API 서버를 사용한다.)
// * (또한, Mock API 서버로부터 받아야 할 JSON 데이터는 테스트하지 않으며 Controller 가 매개변수를 통해 받은 데이터를 모델을 변환하였다는 가정 하에 테스트한다.)
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class FavoriteViewControllerTests {
//    private static final Logger logger = LoggerFactory.getLogger(FavoriteViewControllerTests.class);
//
//    @Autowired private RestTemplate restTemplate;
//    @Autowired private FavoriteViewController favoriteViewController;
//
//    private MockMvc mockMvc;
//    private MockRestServiceServer mockMultiServer;
//    private MockHttpServletRequest mockRequest;
//    private MockHttpSession mockSession;
//    private ObjectMapper mapper;
//
//    // *---------------------------------------------------------------------------------------------------------------* [테스트 전/후 작업]
//
//    @Before
//    public void setUp() {
//        // MockMvc 객체에 FavoriteViewController 빈을 연결한다.
//        mockMvc = MockMvcBuilders.standaloneSetup(favoriteViewController).build();
//
//        // Mock API 서버에 RestTemplate 빈을 연결한다. (사용자가 정의한 순서대로 예측하지 않도록 지정)
//        mockMultiServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
//
//        // Mock HttpServletRequest 생성
//        mockRequest = new MockHttpServletRequest();
//
//        // Mock 세션 생성
//        mockSession = new MockHttpSession();
//
//        // Jackson 매퍼 생성
//        mapper = new ObjectMapper();
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [즐겨찾기 목록 조회 테스트]
//
//    /**
//     * 즐겨찾기 목록 조회 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void getFavorites_성공_테스트() throws Exception {
//        /* Given */
//        // 세션에 등록될 계정 정보
//        User loggedUser = new User("user1", "test123!", "테스트", "user1@test.com");
//        mockSession.setAttribute(LOGGED_USER, loggedUser);
//
//        // 즐겨찾기 된 식당 목록
//        List<MyRestaurant> responseMyRestaurantList = new ArrayList<>();
//        responseMyRestaurantList.add(new MyRestaurant(1, "user1"));
//        responseMyRestaurantList.add(new MyRestaurant(2, "user1"));
//        responseMyRestaurantList.add(new MyRestaurant(3, "user1"));
//
//        // 식당별 메뉴 목록
//        List<Menu> menuListA = new ArrayList<>();
//        menuListA.add(new Menu(1, "공기밥A1", 500));
//        menuListA.add(new Menu(2, "공기밥A2", 1000));
//        List<Menu> menuListB = new ArrayList<>();
//        menuListB.add(new Menu(1, "공기밥B1", 1000));
//        menuListB.add(new Menu(2, "공기밥B2", 1500));
//        List<Menu> menuListC = new ArrayList<>();
//        menuListC.add(new Menu(1, "공기밥C1", 1500));
//        menuListC.add(new Menu(2, "공기밥C2", 2000));
//
//        // 전체 식당 목록
//        List<Restaurant> responseRestaurantList = new ArrayList<>();
//        responseRestaurantList.add(new Restaurant(1, "식당A", "한식", 5, 10, "식당A 소개", "식당A 이미지", menuListA));
//        responseRestaurantList.add(new Restaurant(2, "식당B", "중식", 4, 5, "식당B 소개", "식당B 이미지", menuListB));
//        responseRestaurantList.add(new Restaurant(3, "식당C", "양식", 3, 15, "식당C 소개", "식당C 이미지", menuListC));
//
//        // 계정 ID - 식당 번호 매핑 목록
//        mockMultiServer.expect(requestTo(USER_URI + "/user/my-restaurants/" + "user1"))
//                       .andExpect(method(HttpMethod.GET))
//                       .andRespond(withStatus(HttpStatus.OK)
//                       .contentType(MediaType.APPLICATION_JSON_UTF8)
//                       .body(mapper.writeValueAsString(responseMyRestaurantList)));
//
//        // 전체 식당 목록
//        mockMultiServer.expect(requestTo(RESTAURANT_URI + "/restaurants"))
//                       .andExpect(method(HttpMethod.GET))
//                       .andRespond(withStatus(HttpStatus.OK)
//                       .contentType(MediaType.APPLICATION_JSON_UTF8)
//                       .body(mapper.writeValueAsString(responseRestaurantList)));
//
//        // 매핑 목록과 전체 식당 목록을 비교하여 양쪽의 식당 번호가 같은 경우,
//        // 나의 식당 목록에 추가하도록 구현되어 있음.
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/favorites").session(mockSession))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//
//        List<Restaurant> myRestaurantList = (List<Restaurant>) result.getModelAndView().getModel().get("favoriteList");
//        assertThat(myRestaurantList.get(0).getRes_name()).isEqualTo("식당A");
//        assertThat(myRestaurantList.get(1).getRes_name()).isEqualTo("식당B");
//        assertThat(myRestaurantList.get(2).getRes_name()).isEqualTo("식당C");
//
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("fragments/favorite");
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [즐겨찾기 등록 테스트]
//
//    /**
//     * 즐겨찾기 등록 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void postFavorite_성공_테스트() throws Exception {
//        /* Given */
//        // 세션에 등록될 계정 정보
//        User loggedUser = new User("admin", "test123!", "테스트", "admin@test.com");
//        mockSession.setAttribute(LOGGED_USER, loggedUser);
//
//        // API 호출에 사용될 즐겨찾기 정보
//        MyRestaurant requestMyRestaurant = new MyRestaurant(1, "admin");
//
//        // 즐겨찾기 식당 등록 시, API 서버는 응답 코드만 반환하도록 구현되어 있음
//        mockMultiServer.expect(requestTo(USER_URI + "/user/my-restaurant-insert"))
//                       .andExpect(method(HttpMethod.POST))
//                       .andExpect(content().string(mapper.writeValueAsString(requestMyRestaurant)))
//                       .andRespond(withStatus(HttpStatus.OK));
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/favorites/new/" + 1).session(mockSession))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(302);
//        assertThat(result.getFlashMap().get("serverMessage")).isEqualTo("내 식당 목록에 추가되었습니다.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/favorites");
//    }
//}