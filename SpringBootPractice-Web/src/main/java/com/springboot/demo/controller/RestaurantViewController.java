package com.springboot.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.springboot.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.Menu;
import com.springboot.demo.model.Restaurant;

import static com.springboot.demo.global.Constants.createBaseURI;

/**
 * @Class: 맛집 뷰 컨트롤러 클래스
 */
@RestController
public class RestaurantViewController {
    private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);
    private String baseURI = createBaseURI("restaurant-service");
    
    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Method: 맛집 목록 조회
     */
    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public ModelAndView getRestaurants(ModelAndView mv) {
        logger.info("getRestaurants()");

        String uri = baseURI + "/restaurants";
        ResponseEntity<Restaurant[]> response = restTemplate.getForEntity(uri, Restaurant[].class);
        response.getBody();
        List<Restaurant> restaurantList = Arrays.asList(response.getBody());
        
//      ResponseEntity<Restaurant[]> response = restTemplate.getForEntity(uri, Restaurant[].class);
//      System.out.println(response);
//      List<Restaurant> restaurantList = Arrays.asList(Objects.requireNonNull(response.getBody()));
//		List<Restaurant> restaurantList = new ArrayList<>();
//		restaurantList.add(new Restaurant(1, "A식당", "한식", 1, 10));
//		restaurantList.add(new Restaurant(2, "B식당", "중식", 1, 10));
//		restaurantList.add(new Restaurant(3, "C식당", "양식", 1, 10));
//		restaurantList.add(new Restaurant(4, "D식당", "분식", 1, 10));
//		logger.info(restaurantList.get(1).getRes_name());
//		logger.info(restaurantList.get(1).getRes_category());
        mv.addObject("restaurantList", restaurantList);
        mv.setViewName("restaurants/list");
        return mv;
    }
    
    /**
     * @Method: 맛집 1개 조회
     */
    @RequestMapping(value = "/restaurants/{res_index}", method = RequestMethod.GET)
    public ModelAndView getRestaurant(ModelAndView mv, @PathVariable int res_index) {
    		logger.info("getRestaurant()");
    		String uri = baseURI + "/restaurants/" + res_index;
    		System.out.println(uri);
        ResponseEntity<Restaurant> response = restTemplate.getForEntity(uri, Restaurant.class);
        List<Restaurant> restaurantList = Arrays.asList(response.getBody());
    	
    		mv.addObject("restaurantList", restaurantList);
        mv.setViewName("restaurants/list");
    		return mv;
    }
    
    /**
     * @Method: 식당 등록 페이지
     */
    @RequestMapping(value = "/restaurantAddView", method = RequestMethod.GET)
    public ModelAndView showAddRestaurantView(ModelAndView mv, Restaurant restaurant) {
    		logger.info("showAddRestaurantView()");
        mv.setViewName("restaurants/post");
    		return mv;
    }
    
    /**
     * @Method: 식당 등록 
     */
    @RequestMapping(value = "/restaurant", method = RequestMethod.POST)
    public ModelAndView addRestaurant(ModelAndView mv, Restaurant restaurant) {
    		logger.info("addRestaurant()");
    		String uri = baseURI + "/restaurant";
    		ResponseEntity<Restaurant> response = restTemplate.postForEntity(uri, restaurant, Restaurant.class);
    		
        mv.setViewName("redirect:/restaurants");
    		return mv;
    }
    
    @RequestMapping(value = "/restaurantModifyView", method = RequestMethod.GET)
    public ModelAndView showModifyRestaurantView(ModelAndView mv, Restaurant restaurant) {
    		logger.info("showModifyRestaurantView()");

    		mv.setViewName("restaurants/putTest");
    		return mv;
    }
    
    
    /**
     * 형님 JSP에서 put으로 받는 방법이 있긴한데 급하게 하느라 우선 post로 했어요 
     */
    @RequestMapping(value = "/modifyRestaurant", method = RequestMethod.PUT)
    public ModelAndView modifyRestaurant(ModelAndView mv, Restaurant restaurant) {
    		logger.info("modifyRestaurant()");
    		String uri = baseURI + "/restaurant/" + restaurant.getRes_index();
//    		restTemplate.put(uri, restaurant);
    		
    		// put메소드 날려서 ResponseEntity 리턴받으려고 하다보니 조금 복잡하게 코드가 나왔습니다. 추후에 편한 방식으로 수정하셔요..  
    		HttpEntity<Restaurant> entity = new HttpEntity<Restaurant>(restaurant);
    		ResponseEntity<Restaurant> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Restaurant.class);
    		Restaurant res = response.getBody();
    		System.out.println(res.getRes_name());
    		
        mv.setViewName("redirect:/");
    		return mv;
    }
    
    @RequestMapping(value = "/deleteRestaurant/{res_index}", method = RequestMethod.GET)
    public ModelAndView deleteRestaurant(ModelAndView mv, @PathVariable int res_index) {
    		logger.info("modifyRestaurant()");
		String uri = baseURI + "/restaurant/" + res_index;
		// delete도 마찬가지로 리턴을 받기 위해서 조금 특이한 방식 사용 
		ResponseEntity<Restaurant> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, Restaurant.class);
		Restaurant res = response.getBody();
		System.out.println(res.getRes_name());
		
    		mv.setViewName("redirect:/");
		return mv;
    }


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
