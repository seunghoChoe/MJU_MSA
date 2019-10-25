package com.springboot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import static com.springboot.demo.global.Constants.SERVER_MESSAGE;
import static com.springboot.demo.global.Constants.createBaseURI;

import java.util.Arrays;
import java.util.List;

/**
 * @Class: 계정 뷰 컨트롤러 클래스
 */
@RestController
public class UserViewController {
    private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);
    private String baseURI = createBaseURI("user-service");

    @Autowired
    private RestTemplate restTemplate;

    
   
    
    /**
     * @Method: 계정 등록 폼
     */
	@RequestMapping(value = "/users/join", method = RequestMethod.GET)
	public ModelAndView getJoin(ModelAndView mv) {
		logger.info("getJoin()");

		mv.addObject("user", new User());
		mv.setViewName("users/join"); // /users/join 지정 시, 타일즈 적용 안되므로 슬래시 삭제
		return mv;
	}

	/**
	 * @Method: 계정 등록
	 */
	@RequestMapping(value = "/users/join", method = RequestMethod.POST)
	public ModelAndView postJoin(@ModelAttribute("user") @Valid User user, BindingResult result, ModelAndView mv, RedirectAttributes rttr) {
		logger.info("postJoin()");

		if (result.hasErrors()) {
			logger.info(result.toString());
			mv.addObject(SERVER_MESSAGE, "계정 등록 정보를 확인해주세요.");
			mv.setViewName("users/join");
		} else {
			String uri = baseURI + "/user";
			System.out.println(uri);
			ResponseEntity<User> response = restTemplate.postForEntity(uri, user, User.class);
			if (response.getStatusCode().is2xxSuccessful()) {
				rttr.addFlashAttribute(SERVER_MESSAGE, "계정 등록이 완료되었습니다.");
				mv.setViewName("redirect:/");
			} else {
				mv.addObject(SERVER_MESSAGE, "계정 등록 중, 에러가 발생하였습니다.");
				mv.setViewName("users/join");
			}
		}

		return mv;
	}

    /**
     * @Method: 계정 로그인 폼
     */
	@RequestMapping(value = "/users/login", method = RequestMethod.GET)
	public ModelAndView getLogin(ModelAndView mv) {
		logger.info("getLogin()");

		mv.setViewName("users/login");
		return mv;
	}

	/**
	 * @Method: 계정 로그인
	 */
	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public ModelAndView postLogin(User user, HttpServletRequest request, ModelAndView mv) {
		logger.info("postLogin()");
		String uri = baseURI + "/login";
		ResponseEntity<User> response = restTemplate.postForEntity(uri, user, User.class);
		 if(response.getStatusCode().is2xxSuccessful()) {
			 request.getSession().setAttribute("loggedUser", response.getBody());
			 mv.addObject(SERVER_MESSAGE, "success");
			 mv.setViewName("redirect:/");
		 }else {
			 mv.addObject(SERVER_MESSAGE, "failed");
			 mv.setViewName("redirect:/");
		 }
		return mv;
	}
	
	 /**
     * @Method: 사용자 목록 조회 (For Test) 
     */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ModelAndView getUsers(ModelAndView mv) {
		logger.info("getJoin()");
		
		User[] users = restTemplate.getForObject(baseURI + "/users", User[].class);
		List<User> userList = (List<User>) Arrays.asList(users);
		
		mv.addObject("userList", userList);
		mv.setViewName("users/users_test"); // /users/join 지정 시, 타일즈 적용 안되므로 슬래시 삭제
		return mv;
	}
}