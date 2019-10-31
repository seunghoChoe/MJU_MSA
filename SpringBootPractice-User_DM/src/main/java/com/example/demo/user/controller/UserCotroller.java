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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	   @RequestMapping(value = "/login", method = RequestMethod.POST)
	   public ResponseEntity<UserVO> login(RequestEntity<UserVO> request) throws Exception {

	      UserVO user = (UserVO) request.getBody();
	      UserVO member = mUserService.login(user);
	      ResponseEntity<UserVO> reponseEntity = null;
	      if (member != null) {
	         reponseEntity = new ResponseEntity(member.getUser_id(), HttpStatus.OK);
	         System.out.println(reponseEntity);
	         return reponseEntity;
	      } else {
	         reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_ACCEPTABLE);
	         System.out.println(reponseEntity);
	         return reponseEntity;
	      }
	   }

	   // 회원가입
	   @RequestMapping(value = "/user", method = RequestMethod.POST)
	   private ResponseEntity<UserVO> userSignUpProc(RequestEntity<UserVO> request) throws Exception {

	      UserVO user = (UserVO) request.getBody();
	      System.out.println(user.getUser_id());
	      System.out.println(user.getUser_password());
	      System.out.println(user.getUser_email());
	      System.out.println(user.getUser_name());

	      if (mUserService.check_id(user.getUser_id()) == 0) {
	         mUserService.userSignUpService(user);
	         ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.OK);
	         System.out.println(reponseEntity);
	         return reponseEntity;
	      }else if(mUserService.check_id(user.getUser_id()) == 1) {
	         ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_ACCEPTABLE);
	         System.out.println(reponseEntity);
	         return reponseEntity;
	      }else {
	         ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.NOT_IMPLEMENTED);
	         System.out.println(reponseEntity);
	         return reponseEntity;
	      }
	      
	   }
	
	//회원목록
	@ApiOperation(value = "회원목록", notes = "회원목록을 보여준다.")
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<UserVO>> selectAllUser() throws Exception {
		logger.info("selectAllUser()");
		List<UserVO> userList = mUserService.userListService();
		ResponseEntity<List<UserVO>> reponseEntity = new ResponseEntity<List<UserVO>>(userList, HttpStatus.OK);
		return reponseEntity;
	}
	
	//회원 한명 조회
	@ApiOperation(value = "회원조회", notes = "회원 정보를 보여준다.")
	@RequestMapping(value="/userinfo/{user_id}", method=RequestMethod.GET)
	public ResponseEntity<UserVO> selectUserById(
			@ApiParam(value = "유저 아이디", required = true) @PathVariable String user_id) throws Exception {
		UserVO user = mUserService.userSelectService(user_id);
		ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(user, HttpStatus.OK);
		return reponseEntity;
	}
	
	//회원 정보 수정
	@ApiOperation(value = "정보수정", notes = "회원정보를 수정한다.")
	@RequestMapping(value="/userinfo-edit", method=RequestMethod.PUT)
	public ResponseEntity<UserVO> userModify(RequestEntity<UserVO> request) throws Exception {
		logger.info("userModify()");
		UserVO user = (UserVO)request.getBody();
		mUserService.userModifyService(user);
		ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.OK);
		return reponseEntity;
	}
	
/**
 * 
 * Do not Delete
 * For Studying code
 * 
 * @throws Exception 
 *
 */
}
