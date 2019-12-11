/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.example.demo.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.model.MyRestaurantVO;
import com.example.demo.user.model.UserVO;
import com.example.demo.user.service.UserServiceImpl;
import com.google.common.base.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Lee sojeong
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Controller-Service-DAO로 구성된 계층 관계에서 사용자와 관련된 Controller 클래스
 *
 */

@Api(tags = { "2. User" })
@RestController
public class UserCotroller {
	private static final Logger logger = LoggerFactory.getLogger(UserCotroller.class);

	@Resource(name = "com.example.demo.user.service.UserService")
	UserServiceImpl mUserService;

	/**
	 * Client로 부터 사용자 ID와 비밀번호를 입력받아 로그인 성공여부를 반환한다.
	 * @param user
	 *  UserVO 타입의 객체
	 * @return ResponseEntity<UserVO> <br>
	 *  로그인 성공할 경우 - 200(OK)를 반환 <br>
	 *  로그인 실패할 경우 - 406(Not Acceptable) 반환
	 * @throws Exception
	 */
	@ApiOperation(value = "로그인", notes = "아이디, 비밀번호를 입력받아 로그인 한다.")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserVO> login(
			@ApiParam(value = "아이디, 비밀번호", required = true) @RequestBody UserVO user
			) throws Exception {

		ResponseEntity<UserVO> reponseEntity = null;
		UserVO member = null;
		// login이 실패한 경우 
		if (user.getUser_id() == null) {
			reponseEntity = new ResponseEntity<UserVO>(member, HttpStatus.NOT_ACCEPTABLE);
			return reponseEntity;
		}

		else {
			member = mUserService.login(user);
			// login이 성공한 경우
			if (member != null) {
				reponseEntity = new ResponseEntity<UserVO>(member, HttpStatus.OK);
				System.out.println(reponseEntity);
				return reponseEntity;

				// login이 실패한 경우 	
			} else {
				reponseEntity = new ResponseEntity<UserVO>(member, HttpStatus.NOT_ACCEPTABLE);
				System.out.println(reponseEntity);
				return reponseEntity;
			}
		}
	}

	/**
	 * Client로 부터 사용자 ID, 비밀번호, 이름, 이메일을 입력받아 회원가입 성공여부를 반환한다.
	 * @param user
	 *  UserVO 타입의 객체
	 * @return
	 *  회원가입 성공할 경우 - 200(OK)를 반환 <br>
	 *  회원가입 실패할 경우 - 406(Not Acceptable) 혹은 501(Not Implemented) 반환
	 * @throws Exception
	 */
	@ApiOperation(value = "회원가입", notes = "회원 정보를 입력받아 회원가입을 한다.")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	private ResponseEntity<UserVO> userSignUpProc(
			@ApiParam(value = "아이디, 비밀번호, 이름, 이메일", required = true) @RequestBody UserVO user
			) throws Exception {
		logger.info("userSignUpProc(): {} ", user.getUser_id());

		// 회원가입 성공
		if (mUserService.check_id(user.getUser_id()) == 0) {
			mUserService.userSignUpService(user);
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.OK);
			return reponseEntity;

			// 회원가입 실패
		} else if (mUserService.check_id(user.getUser_id()) == 1) {
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_ACCEPTABLE);
			return reponseEntity;

			// 회원가입 실패	
		} else {
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_IMPLEMENTED);
			return reponseEntity;
		}
	}

	/**
	 * Client로 부터 사용자 ID를 입력받아 아이디 중복 여부를 반환한다.
	 * ID 중복 검사 (1105 변경; PathVariable -> RequestParam 수정; AJAX로 GET 방식 호출 시 강제로 쿼리 스트링 형태로 되므로 PathVariable을 사용할 수 없었음)
	 * @param user_id
	 * @return Integer타입의 result 변수
	 *  중복확인이 정상적으로 작동한 경우 - 200(OK)를 반환
	 * @throws Exception
	 */
	@RequestMapping(value = "/check-id", method = RequestMethod.GET)
	public ResponseEntity<Integer> checkId(@RequestParam("user_id") String user_id) throws Exception  {
		logger.info("checkId(): {} ", user_id);
		int result = mUserService.check_id(user_id);

		// 아이디 중복이 없을 경우
		if(result == 0 ) {
			return new ResponseEntity<>(result, HttpStatus.OK);

			// 아이디 중복이 있을 경우
		}else {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	/**
	 * checkId 예외처리 method<br>
	 * RequestParam이 true일 때  parameter가  null일 경우, Exception catch<br>
	 * TestCaseName : checkId_Null_Test
	 * @param ex
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<UserVO> handleMissingParams(MissingServletRequestParameterException ex) {
		String name = ex.getParameterName();
		logger.info("handleMissingParams(): {} ", name + " parameter is missing");

		ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_ACCEPTABLE);
		return reponseEntity;
	}

	/**
	 * test method<br>
	 * Client 요청이 오면, 회원가입된 회원들의 정보를 반환한다.
	 * @return
	 * UserVo 타입의 List 객체
	 * @throws Exception
	 */
	@ApiOperation(value = "회원목록", notes = "회원목록을 보여준다.")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<UserVO>> selectAllUser() throws Exception {
		logger.info("selectAllUser()");
		List<UserVO> userList = mUserService.userListService();
		ResponseEntity<List<UserVO>> reponseEntity = new ResponseEntity<List<UserVO>>(userList, HttpStatus.OK);
		return reponseEntity;
	}

	/**
	 * 전달받은 사용자 ID의 사용자 정보를 반환한다.
	 * @param user_id
	 * @return 
	 * UserVO 타입의 객체
	 * @throws Exception
	 */
	@ApiOperation(value = "회원조회", notes = "회원 정보를 보여준다.")
	@RequestMapping(value = "/userinfo/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<UserVO> selectUserById(
			@ApiParam(value = "유저 아이디", required = true) @PathVariable String user_id) throws Exception {
		logger.info("selectUserById()");
		UserVO user = mUserService.userSelectService(user_id);

		if(user != null) {
			String userID = user.getUser_id();

			// 회원 조회 성공
			if(userID.equals(user_id) ) {
				ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.OK);
				return reponseEntity;

				// 회원 조회 실패
			}else {
				ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.NOT_ACCEPTABLE);
				return reponseEntity;
			}

			// 회원 조회 실패		
		}else {
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.NOT_ACCEPTABLE);
			return reponseEntity;
		}
	}

	/**
	 * Client로 부터 사용자 ID, 비밀번호, 이름, 이메일을 입력받아 회원정보수정 성공여부를 반환한다.
	 * @param user<br>
	 * UserVO 타입의 객체<br>
	 * 회원 정보 수정 (1105 변경; 수정된 계정 정보를 세션에 반영하기 위해 응답에 user 전달)
	 * @return 
	 *  정보수정 성공할 경우 - 200(OK)를 반환 <br>
	 *  정보수정 실패할 경우 - 406(Not Acceptable) 반환
	 * @throws Exception
	 */
	@ApiOperation(value = "정보수정", notes = "회원정보를 수정한다.")
	@RequestMapping(value = "/userinfo-edit", method = RequestMethod.PUT)
	public ResponseEntity<UserVO> userModify(@ApiParam(value = "아이디, 비밀번호, 이름, 이메일", required = true) @RequestBody UserVO user) throws Exception {
		logger.info("userModify()");

		// 정보수정 실패
		if(user.getUser_id() == null) {
			logger.info("수정하고자 하는 정보를 찾을 수 없습니다.");
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.NOT_ACCEPTABLE);
			return reponseEntity;
		}

		// 정보수정 실패
		if(user.getUser_password() == null) {
			logger.info("수정하고자 하는 비밀번호를 찾을 수 없습니다.");
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.NOT_ACCEPTABLE);
			return reponseEntity;
		}

		// 정보수정 성공
		mUserService.userModifyService(user);
		ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.OK);
		return reponseEntity;
	}

	// 회원 삭제
	/**
	 * 전달 받은 user_id에 해당하는 회원정보를 삭제 성공여부를 반환한다.
	 * @param user_id
	 * @return 
	 *  회원삭제 성공할 경우 - 200(OK)를 반환 <br>
	 *  회원삭제 실패할 경우 - 406(Not Acceptable) 반환
	 * @throws Exception
	 */
	@ApiOperation(value = "회원삭제", notes = "회원을 삭제한다.")
	@RequestMapping(value = "/user-delete/{user_id}", method = RequestMethod.DELETE)
	public ResponseEntity<UserVO> userDelete(
			@ApiParam(value = "아이디", required = true) @PathVariable String user_id) throws Exception {
		logger.info("userDelete()");

		// 회원삭제 실패
		if(mUserService.userDeleteService(user_id) == 0) {
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_ACCEPTABLE);
			return reponseEntity;

			// 회원삭제 성공
		}else {
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.OK);
			return reponseEntity;
		}

	}

	
	
	
	
	/**
	 * 전달 받은 사용자 아이디를 가지는 식당목록을 반환한다.
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "내 식당 목록", notes = "내 식당을 보여준다.")
	@RequestMapping(value = "/user/my-restaurants/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<List<MyRestaurantVO>> selectMyRes(
			@ApiParam(value = "유저 아이디", required = true) @PathVariable String user_id) throws Exception {
		logger.info("selectMyRes()");
		List<MyRestaurantVO> myResList = mUserService.myResListService(user_id);
		ResponseEntity<List<MyRestaurantVO>> reponseEntity = new ResponseEntity<List<MyRestaurantVO>>(myResList, HttpStatus.OK);
		return reponseEntity;
	}

	/**
	 * 전달받은 사용자 아이디와 식당 번호를 내식당DB에 추가한다.
	 * @param MyRes
	 * @throws Exception
	 */
	@ApiOperation(value = "내 식당 추가", notes = "내 식당을 추가한다.")
	@RequestMapping(value = "/user/my-restaurant-insert", method = RequestMethod.POST)
	private void myResInsert(
			@ApiParam(value = "식당번호(res_index)", required = true) @RequestBody MyRestaurantVO MyRes
			) throws Exception {

		logger.info("myResInsert()");

		mUserService.myresInsert(MyRes);
		ResponseEntity<MyRestaurantVO> reponseEntity = new ResponseEntity<MyRestaurantVO>(HttpStatus.OK);
	}
}
