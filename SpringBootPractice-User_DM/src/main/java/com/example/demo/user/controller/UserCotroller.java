package com.example.demo.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.model.MyRestaurantVO;
import com.example.demo.user.model.UserVO;
import com.example.demo.user.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "2. User" })
@RestController
public class UserCotroller {
	private static final Logger logger = LoggerFactory.getLogger(UserCotroller.class);

	@Resource(name = "com.example.demo.user.service.UserService")
	UserServiceImpl mUserService;

	// 로그인
	@ApiOperation(value = "로그인", notes = "아이디, 비밀번호를 입력받아 로그인 한다.")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserVO> login(
			@ApiParam(value = "아이디, 비밀번호", required = true) @RequestBody UserVO user
			) throws Exception {

		
		
		UserVO member = mUserService.login(user);
		ResponseEntity<UserVO> reponseEntity = null;
		if (member != null) {
			reponseEntity = new ResponseEntity<UserVO>(member, HttpStatus.OK);
			System.out.println(reponseEntity);
			return reponseEntity;
		} else {
			reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_ACCEPTABLE);
			System.out.println(reponseEntity);
			return reponseEntity;
		}
	}

	// 회원가입
	@ApiOperation(value = "회원가입", notes = "회원 정보를 입력받아 회원가입을 한다.")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	private ResponseEntity<UserVO> userSignUpProc(
			@ApiParam(value = "아이디, 비밀번호, 이름, 이메일", required = true) @RequestBody UserVO user
			) throws Exception {
		
		//출력
		System.out.println(user.getUser_id());
		System.out.println(user.getUser_password());
		System.out.println(user.getUser_email());
		System.out.println(user.getUser_name());

		if (mUserService.check_id(user.getUser_id()) == 0) {
			mUserService.userSignUpService(user);
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.OK);
			System.out.println(reponseEntity);
			return reponseEntity;
		} else if (mUserService.check_id(user.getUser_id()) == 1) {
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_ACCEPTABLE);
			System.out.println(reponseEntity);
			return reponseEntity;
		} else {
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_IMPLEMENTED);
			System.out.println(reponseEntity);
			return reponseEntity;
		}

	}
	
	// ID 중복 검사 (1105 변경; PathVariable -> RequestParam 수정; AJAX로 GET 방식 호출 시 강제로 쿼리 스트링 형태로 되므로 PathVariable을 사용할 수 없었음)
	@RequestMapping(value = "/check-id", method = RequestMethod.GET)
	public ResponseEntity<Integer> checkId(@RequestParam("user_id") String user_id) throws Exception {
		logger.info("checkId(): {} ", user_id);
		int result = mUserService.check_id(user_id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	   

	// 회원목록
	@ApiOperation(value = "회원목록", notes = "회원목록을 보여준다.")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<UserVO>> selectAllUser() throws Exception {
		logger.info("selectAllUser()");
		List<UserVO> userList = mUserService.userListService();
		ResponseEntity<List<UserVO>> reponseEntity = new ResponseEntity<List<UserVO>>(userList, HttpStatus.OK);
		return reponseEntity;
	}

	// 회원 한명 조회
	@ApiOperation(value = "회원조회", notes = "회원 정보를 보여준다.")
	@RequestMapping(value = "/userinfo/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<UserVO> selectUserById(
			@ApiParam(value = "유저 아이디", required = true) @PathVariable String user_id) throws Exception {
		logger.info("selectUserById()");
		UserVO user = mUserService.userSelectService(user_id);
		ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.OK);
		return reponseEntity;
	}

	// 회원 정보 수정 (1105 변경; 수정된 계정 정보를 세션에 반영하기 위해 응답에 user 전달)
	@ApiOperation(value = "정보수정", notes = "회원정보를 수정한다.")
	@RequestMapping(value = "/userinfo-edit", method = RequestMethod.PUT)
	public ResponseEntity<UserVO> userModify(@ApiParam(value = "아이디, 비밀번호, 이름, 이메일", required = true) @RequestBody UserVO user) throws Exception {
		logger.info("userModify()");
		mUserService.userModifyService(user);
		ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.OK);
		return reponseEntity;
	}
	
	// 회원 정보 수정
		@ApiOperation(value = "회원삭제", notes = "회원을 삭제한다.")
		@RequestMapping(value = "/user-delete/{user_id}", method = RequestMethod.DELETE)
		public ResponseEntity<UserVO> userDelete(
				@ApiParam(value = "아이디", required = true) @PathVariable String user_id) throws Exception {
			logger.info("userDelete()");
			mUserService.userDeleteService(user_id);
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.OK);
			return reponseEntity;
		}
		
	// 가고싶은 식당 조회
		@ApiOperation(value = "내 식당 목록", notes = "내 식당을 보여준다.")
		@RequestMapping(value = "/user/my-restaurants/{user_id}", method = RequestMethod.GET)
		public ResponseEntity<List<MyRestaurantVO>> selectMyRes(
				@ApiParam(value = "유저 아이디", required = true) @PathVariable String user_id) throws Exception {
			logger.info("selectMyRes()");
			List<MyRestaurantVO> myResList = mUserService.myResListService(user_id);
			ResponseEntity<List<MyRestaurantVO>> reponseEntity = new ResponseEntity<List<MyRestaurantVO>>(myResList, HttpStatus.OK);
			return reponseEntity;
		}
		
		// 내 식당 추가
		@ApiOperation(value = "내 식당 추가", notes = "내 식당을 추가한다.")
		@RequestMapping(value = "/user/my-restaurant-insert", method = RequestMethod.POST)
		private void myResInsert(
			@ApiParam(value = "식당번호(res_index)", required = true) @RequestBody MyRestaurantVO MyRes
			) throws Exception {
		
			//출력
			System.out.println(MyRes.getUser_id());
			System.out.println(MyRes.getRes_index());
			
			mUserService.myresInsert(MyRes);
			ResponseEntity<MyRestaurantVO> reponseEntity = new ResponseEntity<MyRestaurantVO>(HttpStatus.OK);
			System.out.println(reponseEntity);
				
		}
	/**
	 * 
	 * Do not Delete For Studying code
	 * 
	 * @throws Exception
	 *
	 */
}
