package com.springboot.demo.controller;

import com.springboot.demo.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.Restaurant;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;
import static com.springboot.demo.global.Constants.SERVER_MESSAGE;
import static com.springboot.demo.global.Constants.createBaseURI;

/**
 * @Class: 식당 뷰 컨트롤러 클래스
 */
@RestController
public class RestaurantViewController {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantViewController.class);
    private String baseURI = createBaseURI("restaurant-service");

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Method: 식당 등록 폼
     */
    @RequestMapping(value = "/restaurants/new", method = RequestMethod.GET)
    public ModelAndView getRestaurantNew(ModelAndView mv) {
        logger.info("getRestaurantNew()");

        mv.addObject("restaurant", new Restaurant());
        mv.setViewName("restaurants/new");
        return mv;
    }

    /**
     * @Method: 식당 등록
     */
    @RequestMapping(value = "/restaurants/new", method = RequestMethod.POST)
    public ModelAndView postRestaurantNew(@ModelAttribute("restaurant") @Valid Restaurant restaurant, BindingResult result,
                                          ModelAndView mv, RedirectAttributes rttr) {
        logger.info("postRestaurantNew()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            mv.addObject(SERVER_MESSAGE, "식당 등록 정보를 확인해주세요.");
            mv.setViewName("restaurants/new");
        } else {
            String uri = baseURI + "/restaurant";
            ResponseEntity<Restaurant> response = restTemplate.postForEntity(uri, restaurant, Restaurant.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "식당 등록이 완료되었습니다.");
                mv.setViewName("redirect:/restaurants"); // 게시글 목록으로 페이지 이동
            } else {
                mv.addObject(SERVER_MESSAGE, "식당 등록 중, 문제가 발생하였습니다.");
                mv.setViewName("restaurants/new");
            }
        }
        return mv;
    }

    /**
     * @Method: 식당 목록 조회
     */
    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public ModelAndView getRestaurants(ModelAndView mv) {
        logger.info("getRestaurants()");

        String uri = baseURI + "/restaurants";
        ResponseEntity<List<Restaurant>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Restaurant>>() {});
        List<Restaurant> restaurantList = response.getBody();

        mv.addObject("restaurantList", restaurantList);
        mv.setViewName("restaurants/list");
        return mv;
    }

    /**
     * @Method: 식당 상세 조회
     */
    @RequestMapping(value = "/restaurants/{res_index}", method = RequestMethod.GET)
    public ModelAndView getRestaurant(ModelAndView mv, @PathVariable int res_index) {
        logger.info("getRestaurant()");

        String uri = baseURI + "/restaurant/" + res_index;
        ResponseEntity<Restaurant> response = restTemplate.getForEntity(uri, Restaurant.class);
        Restaurant restaurant = response.getBody();

        // 임시 메뉴 등록
        Menu menu1 = new Menu(1, "짜장면", 5000);
        Menu menu2 = new Menu(2, "짬뽕", 5000);
        Menu menu3 = new Menu(3, "보쌈", 8000);
        Menu menu4 = new Menu(4, "제육볶음정식", 7000);
        Menu menu5 = new Menu(5, "쭈꾸미볶음", 7000);
        mv.addObject("menu1", menu1);
        mv.addObject("menu2", menu2);
        mv.addObject("menu3", menu3);
        mv.addObject("menu4", menu4);
        mv.addObject("menu5", menu5);
        // 임시 메뉴 등록

        mv.addObject("restaurant", restaurant);
        mv.setViewName("restaurants/restaurant");
        return mv;
    }

    /**
     * @Method: 식당 수정 폼
     */
    @RequestMapping(value = "/restaurants/{res_index}/edit", method = RequestMethod.GET)
    public ModelAndView getEditRestaurant(@PathVariable("res_index") int res_index, ModelAndView mv) {
        logger.info("getEditRestaurant()");

        String uri = baseURI + "/restaurant/" + res_index;
        ResponseEntity<Restaurant> response = restTemplate.getForEntity(uri, Restaurant.class);
        Restaurant restaurant = response.getBody();

        mv.addObject("restaurant", restaurant);
        mv.setViewName("restaurants/edit");
        return mv;
    }

    /**
     * @Method: 식당 수정
     */
    @RequestMapping(value = "/restaurants/{res_index}/edit", method = RequestMethod.PUT)
    public ModelAndView putEditRestaurant(@PathVariable("res_index") int res_index, @ModelAttribute("post") @Valid Restaurant restaurant, BindingResult result,
                                          ModelAndView mv, RedirectAttributes rttr) {
        logger.info("putEditRestaurant()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            mv.addObject(SERVER_MESSAGE, "식당 등록 정보를 확인해주세요.");
            mv.setViewName("restaurants/edit");
        } else {
            String uri = baseURI + "/restaurant/" + res_index;

            HttpEntity<Restaurant> entity = new HttpEntity<>(restaurant);
            ResponseEntity<Restaurant> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Restaurant.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "식당 수정이 완료되었습니다.");
                mv.setViewName("redirect:/restaurants"); // 식당 목록으로 페이지 이동
            } else {
                mv.addObject(SERVER_MESSAGE, "식당 수정 중, 문제가 발생하였습니다.");
                mv.setViewName("restaurants/edit");
            }
        }
        return mv;
    }

    /**
     * @Method: 식당 삭제
     */
    @RequestMapping(value = "/restaurants/{res_index}", method = RequestMethod.DELETE)
    public ModelAndView deleteRestaurant(@PathVariable("res_index") int res_index, ModelAndView mv) {
        logger.info("deleteRestaurant()");

        String uri = baseURI + "/restaurant/" + res_index;
        ResponseEntity<Restaurant> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, Restaurant.class);

        mv.setViewName("redirect:/restaurants");
        return mv;
    }




//    @RequestMapping(value = "/restaurantModifyView", method = RequestMethod.GET)
//    public ModelAndView showModifyRestaurantView(ModelAndView mv, Restaurant restaurant) {
//        logger.info("showModifyRestaurantView()");
//
//        mv.setViewName("restaurants/putTest");
//        return mv;
//    }
//
//
//    /**
//     * 형님 JSP에서 put으로 받는 방법이 있긴한데 급하게 하느라 우선 post로 했어요
//     */
//    @RequestMapping(value = "/modifyRestaurant", method = RequestMethod.PUT)
//    public ModelAndView modifyRestaurant(ModelAndView mv, Restaurant restaurant) {
//        logger.info("modifyRestaurant()");
//        String uri = baseURI + "/restaurant/" + restaurant.getRes_index();
////    		restTemplate.put(uri, restaurant);
//
//        // put메소드 날려서 ResponseEntity 리턴받으려고 하다보니 조금 복잡하게 코드가 나왔습니다. 추후에 편한 방식으로 수정하셔요..
//        HttpEntity<Restaurant> entity = new HttpEntity<Restaurant>(restaurant);
//        ResponseEntity<Restaurant> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Restaurant.class);
//        Restaurant res = response.getBody();
//        System.out.println(res.getRes_name());
//
//        mv.setViewName("redirect:/");
//        return mv;
//    }
//
//    @RequestMapping(value = "/deleteRestaurant/{res_index}", method = RequestMethod.GET)
//    public ModelAndView deleteRestaurant(ModelAndView mv, @PathVariable int res_index) {
//        logger.info("modifyRestaurant()");
//        String uri = baseURI + "/restaurant/" + res_index;
//        // delete도 마찬가지로 리턴을 받기 위해서 조금 특이한 방식 사용
//        ResponseEntity<Restaurant> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, Restaurant.class);
//        Restaurant res = response.getBody();
//        System.out.println(res.getRes_name());
//
//        mv.setViewName("redirect:/");
//        return mv;
//    }


//	/**
//	 * @Method:
//	 */
//	@RequestMapping(value="/restaurant/restaurantList", method=RequestMethod.GET)
//	public ModelAndView restaurantList(ModelAndView modelAndView) {
//		logger.info("restaurantList()");
//		String url = baseURI + "/restaurant";
//		ResponseEntity<List<Restaurant>> response = restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<Restaurant>>() {});
//		List<Restaurant> restaurantList = response.getBody();
//
//		modelAndView.addObject("restaurantList", restaurantList);
//		modelAndView.setViewName("restaurant/restaurantList");
//		return modelAndView;
//	}
//
//	/**
//	 * @Method:
//	 */
//	@RequestMapping(value="/restaurant/menuList", method=RequestMethod.GET)
//	public ModelAndView menuList(ModelAndView modelAndView) {
//		logger.info("menuList()");
//		String url = baseURI+ "/menu";
//		ResponseEntity<List<Menu>> response = restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<Menu>>() {});
//		List<Menu> menuList = response.getBody();
//
//		modelAndView.addObject("menuList", menuList);
//		modelAndView.setViewName("restaurant/menuList");
//		return modelAndView;
//	}
}
