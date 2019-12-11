//package com.springboot.demo.unit.restaurant;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.springboot.demo.controller.RestaurantViewController;
//import com.springboot.demo.model.Grade;
//import com.springboot.demo.model.Restaurant;
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
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.springboot.demo.unit.GlobalConstants.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
//import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
// *
// * @author Jaeha Son
// * @date 2019-12-13
// * @version 0.9
// * @description
// * RestaurantViewController 테스트 클래스
// * (RestTemplate 을 사용한 REST 통신 과정은 Mock API 서버를 사용한다.)
// * (또한, Mock API 서버로부터 받아야 할 JSON 데이터는 테스트하지 않으며 Controller 가 매개변수를 통해 받은 데이터를 모델을 변환하였다는 가정 하에 테스트한다.)
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RestaurantViewControllerTests {
//    private static final Logger logger = LoggerFactory.getLogger(RestaurantViewControllerTests.class);
//
//    @Autowired private RestTemplate restTemplate;
//    @Autowired private RestaurantViewController restaurantViewController;
//
//    private MockMvc mockMvc;
//    private MockRestServiceServer mockRestaurantServer;
//    private MockHttpServletRequest mockRequest;
//    private MockHttpSession mockSession;
//    private ObjectMapper mapper;
//
//    // *---------------------------------------------------------------------------------------------------------------* [테스트 전/후 작업]
//
//    @Before
//    public void setUp() {
//        // MockMvc 객체에 RestaurantViewController 빈을 연결한다.
//        mockMvc = MockMvcBuilders.standaloneSetup(restaurantViewController).build();
//
//        // Mock API 서버에 RestTemplate 빈을 연결한다.
//        mockRestaurantServer = MockRestServiceServer.bindTo(restTemplate).build();
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
//    // *---------------------------------------------------------------------------------------------------------------* [식당 등록 테스트]
//
//    /**
//     * 식당 등록 폼 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void getRestaurantNew_성공_테스트() throws Exception {
//        /* Given (없음) */
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/restaurants/new"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get("restaurant")).isInstanceOf(Restaurant.class);
//
//        Restaurant tempRestaurant = (Restaurant) result.getModelAndView().getModel().get("restaurant");
//        assertThat(tempRestaurant.getRes_name()).isNull();
//        assertThat(tempRestaurant.getRes_content()).isNull();
//        assertThat(tempRestaurant.getRes_menues()).isNull();
//
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("restaurants/new");
//    }
//
//    /**
//     * 식당 등록 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void postRestaurantNew_성공_테스트() throws Exception {
//        /* Given */
//        // 식당
//        Restaurant requestRestaurant = new Restaurant(1, "테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//        Restaurant responseRestaurant = new Restaurant(1, "테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant"))
//                            .andExpect(method(HttpMethod.POST))
//                            .andExpect(content().string(mapper.writeValueAsString(requestRestaurant)))
//                            .andRespond(withStatus(HttpStatus.OK)
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseRestaurant)));
//
//        /* When */
//        LinkedMultiValueMap<String, String> res_menues = createRestaurantMenusParams();
//
//        MvcResult result = mockMvc.perform(post("/restaurants/new")
//                                  .param("res_index", "1")
//                                  .param("res_name", "테스트 식당")
//                                  .param("res_category", "테스트 카테고리")
//                                  .param("res_grade", "5")
//                                  .param("res_expected_minutes", "10")
//                                  .param("res_content", "테스트 소개")
//                                  .param("res_image", "http://via.placeholder.com/350x150")
//                                  .params(res_menues)).andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(302);
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/restaurants");
//        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("식당 등록이 완료되었습니다.");
//    }
//
//    /**
//     * 식당 등록 실패 테스트
//     * (유효성 검증 실패로 인한 식당 등록 실패)
//     * @throws Exception
//     */
//    @Test
//    public void postRestaurantNew_유효성_실패_테스트() throws Exception {
//        /* Given */
//        // 웹 서비스에서 테스트가 완료되므로, 리소스 서버 및 요청/응답 객체 없음
//
//        /* When */
//        LinkedMultiValueMap<String, String> res_menues = createRestaurantMenusParams();
//
//        MvcResult result = mockMvc.perform(post("/restaurants/new")
//                                  .param("res_index", "1")
//                                  .param("res_name", "테스트 이름")
//
//                                  // 식당 제목/카테고리를 빈 값으로 전달하여 유효성 실패 케이스를 유도한다.
//                                  .param("res_category", "")
//                                  .param("res_grade", "5")
//                                  .param("res_expected_minutes", "10")
//                                  .param("res_content", "테스트 소개")
//                                  .param("res_image", "http://via.placeholder.com/350x150")
//                                  .params(res_menues)).andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("restaurants/new");
//        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("식당 등록 정보를 확인해주세요.");
//    }
//
//    /**
//     * 식당 등록 실패 테스트
//     * (406 응답 코드로 인한 식당 등록 실패)
//     * @throws Exception
//     */
//    @Test
//    public void postRestaurantNew_응답_실패_테스트() throws Exception {
//        /* Given */
//        // 식당
//        Restaurant requestRestaurant = new Restaurant(1, "테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//        Restaurant responseRestaurant = new Restaurant(1, "테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//
//        // 없을 경우, java.lang.AssertionError: No further requests expected: HTTP POST http://localhost:8083/restaurant 0 request(s) executed.
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant"))
//                            .andExpect(method(HttpMethod.POST))
//                            .andExpect(content().string(mapper.writeValueAsString(requestRestaurant)))
//                            .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE) // 406 코드를 지정하여 응답 코드 실패 케이스를 유도한다.
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseRestaurant)));
//
//        /* When */
//        LinkedMultiValueMap<String, String> res_menues = createRestaurantMenusParams();
//
//        MvcResult result = mockMvc.perform(post("/restaurants/new")
//                                  .param("res_index", "1")
//                                  .param("res_name", "테스트 식당")
//                                  .param("res_category", "테스트 카테고리")
//                                  .param("res_grade", "5")
//                                  .param("res_expected_minutes", "10")
//                                  .param("res_content", "테스트 소개")
//                                  .param("res_image", "http://via.placeholder.com/350x150")
//                                  .params(res_menues)).andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("restaurants/new");
//        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("식당 등록 중, 문제가 발생하였습니다.");
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [식당 조회 테스트]
//
//    /**
//     * 식당 목록 조회 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void getRestaurants_성공_테스트() throws Exception {
//        /* Given */
//        // 전체 식당 목록
//        List<Restaurant> responseRestaurantList = new ArrayList<>();
//        responseRestaurantList.add(new Restaurant(1, "테스트 식당 1", "한식", 5, 10, "식당 1 소개", "http://via.placeholder.com/350x150", createMenuList()));
//        responseRestaurantList.add(new Restaurant(2, "테스트 식당 2", "중식", 4, 5, "식당 2 소개", "http://via.placeholder.com/350x150", createMenuList()));
//        responseRestaurantList.add(new Restaurant(3, "테스트 식당 3", "양식", 3, 15, "식당 3 소개", "http://via.placeholder.com/350x150", createMenuList()));
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurants"))
//                            .andExpect(method(HttpMethod.GET))
//                            .andRespond(withStatus(HttpStatus.OK)
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseRestaurantList)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/restaurants"))
//                                  .andExpect(status().isOk())
//                                  .andExpect(view().name("restaurants/list"))
//                                  .andExpect(forwardedUrl("restaurants/list"))
//                                  .andExpect(model().attributeExists("restaurantList"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        List<Restaurant> restaurantList = (List<Restaurant>) result.getModelAndView().getModel().get("restaurantList");
//
//        assertThat(restaurantList.get(0).getRes_name()).isEqualTo("테스트 식당 1");
//        assertThat(restaurantList.get(1).getRes_name()).isEqualTo("테스트 식당 2");
//        assertThat(restaurantList.get(2).getRes_name()).isEqualTo("테스트 식당 3");
//        assertThat(restaurantList.get(0).getRes_menues().get(0).getMenu_name()).isEqualTo("짜장면");
//        assertThat(restaurantList.get(1).getRes_menues().get(0).getMenu_name()).isEqualTo("짜장면");
//        assertThat(restaurantList.get(2).getRes_menues().get(0).getMenu_name()).isEqualTo("짜장면");
//    }
//
//    /**
//     * 식당 상세 조회 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void getRestaurant_성공_테스트() throws Exception {
//        /* Given */
//        // 식당
//        Restaurant responseRestaurant = new Restaurant(1, "테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1))
//                            .andExpect(method(HttpMethod.GET))
//                            .andRespond(withStatus(HttpStatus.OK)
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseRestaurant)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/restaurants/" + 1))
//                                  // 아래의 방법으로도 메뉴 검사 가능
////                                  .andExpect(model().attribute("restaurant", hasProperty("res_menues", hasItem(
////                                          allOf(
////                                               hasProperty("menu_index", is(1)),
////                                               hasProperty("menu_name", is("짜장면")),
////                                               hasProperty("menu_price", is(5000))
////                                          )
////                                   ))))
////                                  .andExpect(model().attribute("restaurant", hasProperty("res_menues", hasItem(
////                                          allOf(
////                                               hasProperty("menu_index", is(2)),
////                                               hasProperty("menu_name", is("짬뽕")),
////                                               hasProperty("menu_price", is(6000))
////                                          )
////                                   ))))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get("grade")).isInstanceOf(Grade.class);
//        assertThat(result.getModelAndView().getModel().get("restaurant")).isInstanceOf(Restaurant.class);
//
//        Restaurant tempRestaurant = (Restaurant) result.getModelAndView().getModel().get("restaurant");
//        assertThat(tempRestaurant.getRes_index()).isEqualTo(1);
//        assertThat(tempRestaurant.getRes_name()).isEqualTo("테스트 식당");
//        assertThat(tempRestaurant.getRes_menues().get(0).getMenu_name()).isEqualTo("짜장면");
//        assertThat(tempRestaurant.getRes_menues().get(1).getMenu_name()).isEqualTo("짬뽕");
//
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("restaurants/restaurant");
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [식당 수정 테스트]
//
//    /**
//     * 식당 수정 폼 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void getRestaurantEdit_성공_테스트() throws Exception {
//        /* Given */
//        // 식당
//        Restaurant responseRestaurant = new Restaurant(1, "테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1))
//                            .andExpect(method(HttpMethod.GET))
//                            .andRespond(withStatus(HttpStatus.OK)
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseRestaurant)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(get("/restaurants/" + 1 + "/edit"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get("restaurant")).isInstanceOf(Restaurant.class);
//
//        Restaurant tempRestaurant = (Restaurant) result.getModelAndView().getModel().get("restaurant");
//        assertThat(tempRestaurant.getRes_index()).isEqualTo(1);
//        assertThat(tempRestaurant.getRes_name()).isEqualTo("테스트 식당");
//        assertThat(tempRestaurant.getRes_menues().get(0).getMenu_name()).isEqualTo("짜장면");
//        assertThat(tempRestaurant.getRes_menues().get(1).getMenu_name()).isEqualTo("짬뽕");
//
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("restaurants/edit");
//    }
//
//    /**
//     * 식당 수정 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void putRestaurantEdit_성공_테스트() throws Exception {
//        /* Given */
//        // 식당
//        Restaurant requestRestaurant = new Restaurant(1, "수정된 테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//        Restaurant responseRestaurant = new Restaurant(1, "수정된 테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1))
//                            .andExpect(method(HttpMethod.PUT))
//                            .andExpect(content().string(mapper.writeValueAsString(requestRestaurant)))
//                            .andRespond(withStatus(HttpStatus.OK)
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseRestaurant)));
//
//        /* When */
//        LinkedMultiValueMap<String, String> res_menues = createRestaurantMenusParams();
//
//        MvcResult result = mockMvc.perform(put("/restaurants/" + 1 + "/edit")
//                                  .param("res_index", "1")
//                                  .param("res_name", "수정된 테스트 식당")
//                                  .param("res_category", "테스트 카테고리")
//                                  .param("res_grade", "5")
//                                  .param("res_expected_minutes", "10")
//                                  .param("res_content", "테스트 소개")
//                                  .param("res_image", "http://via.placeholder.com/350x150")
//                                  .params(res_menues)).andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(302);
//        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("식당 수정이 완료되었습니다.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/restaurants");
//    }
//
//    /**
//     * 식당 수정 실패 테스트
//     * (웹 서비스 내의 유효성 검증 실패로 인한 계정 수정 실패)
//     * @throws Exception
//     */
//    @Test
//    public void putRestaurantEdit_유효성_실패_테스트() throws Exception {
//        /* Given */
//        // 웹 서비스에서 테스트가 완료되므로, 리소스 서버 및 요청/응답 객체 없음
//
//        /* When */
//        LinkedMultiValueMap<String, String> res_menues = createRestaurantMenusParams();
//
//        MvcResult result = mockMvc.perform(put("/restaurants/" + 1 + "/edit")
//                                  .param("res_index", "1")
//
//                                  // 식당 이름/카테고리를 빈 값으로 전달하여 유효성 실패 케이스를 유도한다.
//                                  .param("res_name", "")
//                                  .param("res_category", "")
//                                  .param("res_grade", "5")
//                                  .param("res_expected_minutes", "10")
//                                  .param("res_content", "테스트 소개")
//                                  .param("res_image", "http://via.placeholder.com/350x150")
//                                  .params(res_menues)).andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(302);
//        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("식당 수정 정보를 확인해주세요.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/restaurants/" + 1 + "/edit");
//    }
//
//    /**
//     * 식당 수정 실패 테스트
//     * (406 응답 코드로 인한 식당 수정 실패)
//     * @throws Exception
//     */
//    @Test
//    public void putRestaurantEdit_응답_실패_테스트() throws Exception {
//        /* Given */
//        // 식당
//        Restaurant requestRestaurant = new Restaurant(1, "수정된 테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//        Restaurant responseRestaurant = new Restaurant(1, "테스트 식당", "테스트 카테고리", 5, 10, "테스트 소개", "http://via.placeholder.com/350x150", createMenuList());
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1))
//                            .andExpect(method(HttpMethod.PUT))
//                            .andExpect(content().string(mapper.writeValueAsString(requestRestaurant)))
//                            .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE) // 406 코드를 지정하여 응답 코드 실패 케이스를 유도한다.
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseRestaurant)));
//
//        /* When */
//        LinkedMultiValueMap<String, String> res_menues = createRestaurantMenusParams();
//
//        MvcResult result = mockMvc.perform(put("/restaurants/" + 1 + "/edit")
//                                  .param("res_index", "1")
//                                  .param("res_name", "수정된 테스트 식당")
//                                  .param("res_category", "테스트 카테고리")
//                                  .param("res_grade", "5")
//                                  .param("res_expected_minutes", "10")
//                                  .param("res_content", "테스트 소개")
//                                  .param("res_image", "http://via.placeholder.com/350x150")
//                                  .params(res_menues)).andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("식당 수정 중, 문제가 발생하였습니다.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("restaurants/edit");
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [식당 삭제 테스트]
//
//    /**
//     * 식당 삭제 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void deleteRestaurant_성공_테스트() throws Exception {
//        /* Given */
//        // 리소스 서버에서 식당 번호만 받아서 삭제하므로 요청/응답 객체 없음
//
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1))
//                            .andExpect(method(HttpMethod.DELETE))
//                            .andRespond(withStatus(HttpStatus.OK));
//
//        /* When */
//        MvcResult result = mockMvc.perform(delete("/restaurants/" + 1).session(mockSession)
//                                  .param("res_index", "1"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//
//        // 삭제 성공 시, 1 을 반환하여 웹 단에서 AJAX 호출이 완료된다.
//        assertThat(result.getResponse().getContentAsString()).isEqualTo("1");
//    }
//
//    /**
//     * 식당 삭제 실패 테스트
//     * @throws Exception
//     */
//    @Test
//    public void deleteRestaurant_응답_실패_테스트() throws Exception {
//        /* Given */
//        // 리소스 서버에서 식당 번호만 받아서 삭제하므로 요청/응답 객체 없음
//
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1))
//                            .andExpect(method(HttpMethod.DELETE))
//                            .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE));
//
//        /* When */
//        MvcResult result = mockMvc.perform(delete("/restaurants/" + 1).session(mockSession)
//                                  .param("res_index", "1"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//
//        // 삭제 실패 시, 0 을 반환하여 웹 단에서 AJAX 호출이 완료된다.
//        assertThat(result.getResponse().getContentAsString()).isEqualTo("0");
//    }
//
//    // *---------------------------------------------------------------------------------------------------------------* [평점 등록 테스트]
//
//    /**
//     * 평점 등록 성공 테스트
//     * @throws Exception
//     */
//    @Test
//    public void postGradeNew_성공_테스트() throws Exception {
//        /* Given */
//        // 평점
//        Grade requestGrade = new Grade(1, 5, 1, "admin");
//        Grade responseGrade = new Grade(1, 5, 1, "admin");
//
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1 + "/grade"))
//                            .andExpect(method(HttpMethod.POST))
//                            .andExpect(content().string(mapper.writeValueAsString(requestGrade)))
//                            .andRespond(withStatus(HttpStatus.OK)
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseGrade)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(post("/restaurants/" + 1 + "/grades/new").session(mockSession)
//                                  .param("grade_id", "1")
//                                  .param("star", "5")
//                                  .param("res_index", "1")
//                                  .param("user_id", "admin"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(302);
//        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("평점이 등록되었습니다.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/restaurants/" + 1);
//    }
//
//    /**
//     * 평점 등록 실패 테스트
//     * (웹 서비스 내의 유효성 검증 실패로 인한 계정 수정 실패)
//     * @throws Exception
//     */
//    @Test
//    public void postGradeNew_유효성_실패_테스트() throws Exception {
//        /* Given */
//        // 웹 서비스에서 테스트가 완료되므로, 리소스 서버 및 요청/응답 객체 없음
//
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        /* When */
//        MvcResult result = mockMvc.perform(post("/restaurants/" + 1 + "/grades/new").session(mockSession)
//                                  .param("grade_id", "1")
//                                  // 평점를 빈 값으로 전달하여 유효성 실패 케이스를 유도한다.
//                                  .param("star", "")
//                                  .param("res_index", "1")
//                                  .param("user_id", "admin"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(200);
//        assertThat(result.getModelAndView().getModel().get(SERVER_MESSAGE)).isEqualTo("평점 등록 정보를 확인해주세요.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("restaurants/" + 1);
//    }
//
//    /**
//     * 평점 등록 실패 테스트
//     * (존재하지 않는 식당 번호로 인한 평점 등록 실패)
//     * @throws Exception
//     */
//    @Test
//    public void postGradeNew_존재하지_않는_식당_번호_실패_테스트() throws Exception {
//        /* Given */
//        // 평점
//        Grade requestGrade = new Grade(1, 5, 9999999, "admin");
//        Grade responseGrade = new Grade(1, 5, 9999999, "admin");
//
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        /* When */
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1 + "/grade"))
//                .andExpect(method(HttpMethod.POST))
//                .andExpect(content().string(mapper.writeValueAsString(requestGrade)))
//                .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .body(mapper.writeValueAsString(responseGrade)));
//
//        /* When */
//        MvcResult result = mockMvc.perform(post("/restaurants/" + 1 + "/grades/new").session(mockSession)
//                                  .param("grade_id", "1")
//                                  .param("star", "5")
//                                  // 없는 식당 번호로 전달하여 존재하지 않는 식당 번호 실패 케이스를 유도한다.
//                                  .param("res_index", "9999999")
//                                  .param("user_id", "admin"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(302);
//        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("평점 등록 중, 문제가 발생하였습니다.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/restaurants/" + 1);
//    }
//
//    /**
//     * 평점 등록 실패 테스트
//     * (406 응답 코드로 인한 평점 등록 실패)
//     * @throws Exception
//     */
//    @Test
//    public void postGradeNew_응답_실패_테스트() throws Exception {
//        /* Given */
//        // 평점
//        Grade requestGrade = new Grade(1, 5, 1, "admin");
//        Grade responseGrade = new Grade(1, 5, 1, "admin");
//
//        // 로그인 계정 세션 등록
//        mockSession.setAttribute(LOGGED_USER, createUser());
//
//        /* When */
//        mockRestaurantServer.expect(requestTo(RESTAURANT_URI + "/restaurant/" + 1 + "/grade"))
//                            .andExpect(method(HttpMethod.POST))
//                            .andExpect(content().string(mapper.writeValueAsString(requestGrade)))
//                            .andRespond(withStatus(HttpStatus.NOT_ACCEPTABLE)
//                            .contentType(MediaType.APPLICATION_JSON_UTF8)
//                            .body(mapper.writeValueAsString(responseGrade)));
//
//        MvcResult result = mockMvc.perform(post("/restaurants/" + 1 + "/grades/new").session(mockSession)
//                                  .param("grade_id", "1")
//                                  .param("star", "5")
//                                  .param("res_index", "1")
//                                  .param("user_id", "admin"))
//                                  .andDo(print()).andReturn();
//
//        /* Then */
//        assertThat(result.getResponse().getStatus()).isEqualTo(302);
//        assertThat(result.getFlashMap().get(SERVER_MESSAGE)).isEqualTo("평점 등록 중, 문제가 발생하였습니다.");
//        assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:/restaurants/" + 1);
//    }
//}