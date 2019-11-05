package com.springboot.demo.controller;

import com.springboot.demo.model.MyRestaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import static com.springboot.demo.global.Constants.SERVER_MESSAGE;
import static com.springboot.demo.global.Constants.createBaseURI;
import static com.springboot.demo.global.UserSession.getUserSession;

/**
 * @Class: 즐겨찾기 뷰 컨트롤러 클래스
 */
@RestController
public class FavoriteViewController {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteViewController.class);
    private String baseURI = createBaseURI("user-service");

    @Autowired
    private RestTemplate restTemplate;

    /**
     * API
     * /user/my-restaurant-insert (내 식당 추가)
     * /user/my-restaurants/{user_id} (내 식당 목록)
     */

    /**
     * @Method: 즐겨찾기 목록 조회
     */
    @RequestMapping(value = "/favorites", method = RequestMethod.GET)
    public ModelAndView getFavorites(ModelAndView mv, HttpServletRequest request) {
        logger.info("getFavorites()");

        String userId = getUserSession(request).getUser_id();
        String uri = baseURI + "/user/my-restaurants/" + userId;
        ResponseEntity<MyRestaurant[]> response = restTemplate.getForEntity(uri, MyRestaurant[].class); // 요청 URI 에 userId 이미 있음.
        List<MyRestaurant> favoriteList = Arrays.asList(response.getBody());

        mv.addObject("favoriteList", favoriteList);
        mv.setViewName("fragments/favorite");
        return mv;
    }

    /**
     * @Method: 즐겨찾기 추가 (식당 상세 조회 시, 버튼을 구현하여 즐겨찾기 등록할 수 있도록 한다.)
     */
    @RequestMapping(value = "/favorites/new", method = RequestMethod.POST)
    public ModelAndView postFavorite(MyRestaurant myRestaurant, ModelAndView mv, HttpServletRequest request, RedirectAttributes rttr) {
        logger.info("postFavorite()");

        myRestaurant.setUser_id(getUserSession(request).getUser_id());
        String uri = baseURI + "/user/my-restaurant-insert";

        ResponseEntity<MyRestaurant> response = restTemplate.postForEntity(uri, myRestaurant, MyRestaurant.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            rttr.addFlashAttribute(SERVER_MESSAGE, "내 식당 목록에 추가되었습니다.");
            mv.setViewName("redirect:/restaurants");
        } else {
            rttr.addFlashAttribute(SERVER_MESSAGE, "내 식당 목록에 추가 중, 오류가 발생하였습니다.");
            mv.setViewName("redirect:/restaurants"); // 조회 중이던 식당 페이지의 인덱스로 돌려야 함
        }
        return mv;
    }
}