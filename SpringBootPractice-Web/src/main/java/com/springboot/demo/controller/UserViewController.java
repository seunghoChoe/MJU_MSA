package com.springboot.demo.controller;

import com.springboot.demo.interceptor.LoginCheck;
import com.springboot.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import static com.springboot.demo.global.Constants.*;
import static com.springboot.demo.global.UserSession.*;
import static com.springboot.demo.global.UserSession.getUserSession;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 계정 뷰와 관련된 Controller 클래스
 */
@RestController
@RequiredArgsConstructor
public class UserViewController {
    private static final Logger logger = LoggerFactory.getLogger(UserViewController.class);
    private final String userServiceUri = createApiUri("user-service");
    private final UserService userService;

    /**
     * 계정 등록 폼
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     * 빈 계정 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/users/join", method = RequestMethod.GET)
    public ModelAndView getJoin(ModelAndView mv) {
        logger.info("getJoin()");

        mv.addObject("user", new User());

        // /users/join 지정 시, 타일즈 적용이 안되므로 슬래시 삭제
        mv.setViewName("users/join");

        return mv;
    }

    /**
     * 계정 등록
     * @param user
     *  뷰에서 전달된 계정 모델
     * @param result
     *  유효성 검증 결과(BindingResult) 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  유효성 검증 실패 - 실패 메세지와 JSP 페이지 반환
     *  계정 등록 성공 - 성공 메세지와 JSP 페이지 반환
     *  리소스 서버 응답 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @RequestMapping(value = "/users/join", method = RequestMethod.POST)
    public ModelAndView postJoin(@ModelAttribute("user") @Valid User user, BindingResult result, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("postJoin()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            mv.addObject(SERVER_MESSAGE, "계정 등록 정보를 확인해주세요.");
            mv.setViewName("users/join");

        } else {
            ResponseEntity<User> response = userService.postJoin(userServiceUri + "/user", user);

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "계정 등록이 완료되었습니다.");
                mv.setViewName("redirect:/");

            } else {
                mv.addObject(SERVER_MESSAGE, "계정 등록 중, 문제가 발생하였습니다.");
                mv.setViewName("users/join");
            }
        }

        return mv;
    }

    /**
     * 계정 로그인 폼
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  JSP 페이지 반환
     */
    @RequestMapping(value = "/users/login", method = RequestMethod.GET)
    public ModelAndView getLogin(ModelAndView mv) {
        logger.info("getLogin()");

        mv.setViewName("users/login");

        return mv;
    }

    /**
     * 계정 로그인
     * @param user
     *  뷰에서 전달된 계정 모델
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  로그인 성공 - 성공 메세지와 JSP 페이지 반환
     *  로그인 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView postLogin(User user, HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("postLogin()");

        ResponseEntity<User> response = userService.postLogin(userServiceUri + "/login", user);

        if (response.getStatusCode().is2xxSuccessful()) {
			setUserSession(request, response.getBody()); // 로그인된 계정 정보를 세션에 등록
			rttr.addFlashAttribute(SERVER_MESSAGE, user.getUser_id() + "님, 로그인되었습니다.");
            mv.setViewName("redirect:/");

        } else {
			mv.addObject(SERVER_MESSAGE, "ID 또는 비밀번호를 확인해주세요.");
            mv.setViewName("users/login");
        }

        return mv;
    }

    /**
     * 계정 로그아웃
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  로그아웃 성공 - 성공 메세지와 JSP 페이지 반환
     *  로그아웃 실패 - 미구현 (loggedUser 로 등록된 세션이 삭제되었는지 검사하여 구현해야 함)
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
	@RequestMapping(value = "/users/logout", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView requestLogout(HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
		logger.info("requestLogout()");

		rttr.addFlashAttribute(SERVER_MESSAGE, getUserSession(request).getUser_id() + "님, 로그아웃 되었습니다.");
		deleteUserSession(request);

		mv.setViewName("redirect:/");
		return mv;
	}

    /**
     * 계정 수정 폼
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  세션에 등록된 계정 모델과 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/users/edit", method = RequestMethod.GET)
    public ModelAndView getEdit(HttpServletRequest request, ModelAndView mv) {
        logger.info("getEdit()");

        mv.addObject("user", getUserSession(request));
        mv.setViewName("users/edit");

        return mv;
    }

    /**
     * 계정 수정
     * @param user
     *  뷰에서 전달된 계정 모델
     * @param result
     *  유효성 검증 결과(BindingResult) 객체
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  유효성 검증 실패 - 실패 메세지와 JSP 페이지 반환
     *  계정 수정 성공 - 성공 메세지와 JSP 페이지 반환
     *  리소스 서버 응답 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/users/edit", method = RequestMethod.PUT)
    public ModelAndView putEdit(@ModelAttribute("user") @Valid User user, BindingResult result, HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("putEdit()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            rttr.addFlashAttribute(SERVER_MESSAGE, "계정 수정 정보를 확인해주세요.");
            mv.setViewName("redirect:/users/edit");

        } else {
            user.setUser_id(getUserSession(request).getUser_id());
            ResponseEntity<User> response = userService.putEdit(userServiceUri + "/userinfo-edit", new HttpEntity<>(user));

            if (response.getStatusCode().is2xxSuccessful()) {
                setUserSession(request, response.getBody()); // 수정된 계정 정보를 세션에 등록
                rttr.addFlashAttribute(SERVER_MESSAGE, "계정 수정이 완료되었습니다.");
                mv.setViewName("redirect:/");

            } else {
                rttr.addFlashAttribute(SERVER_MESSAGE, "계정 수정 중, 문제가 발생하였습니다.");
                mv.setViewName("redirect:/users/edit");
            }
        }

        return mv;
    }

    /**
     * 계정 탈퇴 폼
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/users/delete", method = RequestMethod.GET)
    public ModelAndView getDelete(ModelAndView mv) {
        logger.info("getDelete()");

        mv.setViewName("users/delete");

        return mv;
    }

    /**
     * 계정 탈퇴 (뷰에서 비밀번호를 전달하면 로그인 로직 및 세션에 등록된 계정 ID를 통해 본인 확인 후, 탈퇴 처리)
     * @param user
     *  뷰에서 전달된 계정 모델
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  로그인 실패 - 실패 메세지와 JSP 페이지 반환
     *  로그인 성공 및 계정 탈퇴 성공 - 성공 메세지와 JSP 페이지 반환
     *  로그인 성공 및 계정 탈퇴 실패 - 보완 필요 (올바른 비밀번호 입력 후에도 리소스 서버의 실패 응답 시, 비밀번호 불일치 메세지가 출력될 수 있음)
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/users/delete", method = RequestMethod.DELETE)
    public ModelAndView deleteUser(User user, HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("deleteUser()");

        user.setUser_id(getUserSession(request).getUser_id());
        ResponseEntity<User> loginResponse = userService.postLogin(userServiceUri + "/login", user);

        if (loginResponse.getStatusCode().is2xxSuccessful()) {
            userService.deleteUser(userServiceUri + "/user-delete/" + user.getUser_id());
            deleteUserSession(request);
            rttr.addFlashAttribute(SERVER_MESSAGE, "계정 탈퇴가 완료되었습니다.");
            mv.setViewName("redirect:/");

        } else {
            rttr.addFlashAttribute(SERVER_MESSAGE, "비밀번호가 일치하지 않습니다.");
            mv.setViewName("redirect:/users/delete");
        }

        return mv;
    }

    /**
     * 계정 목록 조회
     * @param mv
     *  ModelAndView 객체
     * @return
     *  계정 목록 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsers(ModelAndView mv) {
        logger.info("getUsers()");

        List<User> userList = userService.getUsers(userServiceUri + "/users").getBody();
        mv.addObject("userList", userList);
        mv.setViewName("users/users_test");

        return mv;
    }
}