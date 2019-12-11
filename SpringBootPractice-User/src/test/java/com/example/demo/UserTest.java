/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.example.demo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.user.controller.UserCotroller;
import com.example.demo.user.model.UserVO;
import com.example.demo.user.service.ResDAO;
import com.example.demo.user.service.UserDAO;
import com.example.demo.user.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Lee sojeong
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * UserController test클래스
 * 웹은 mockMVC, DB는 BDDMockito를 사용하여 mock처리하였다.
 * UserController의 selectMyRes, myResInsert method는 test하지 않는다.
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserCotroller.class, UserServiceImpl.class, UserDAO.class, ResDAO.class})


public class UserTest {

	private MockMvc mockMVC;

	@Autowired
	private UserCotroller userCotroller;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ResDAO resDAO;

	@MockBean
	private SqlSessionTemplate sqlSession;

	@Before
	public void serUp() {
		mockMVC = MockMvcBuilders.standaloneSetup(userCotroller).build();
	}

	/**
	 * connection_test method test case
	 * @throws Exception
	 */
	@Test
	public void selectAllUserTest() throws Exception{
		//given
		//request Data
		//Response Data
		List<Object> userList = new ArrayList<Object>();
		UserVO user = new UserVO();
		user.setUser_id("admin123");
		user.setUser_password("admin123!");
		userList.add(user);

		//given Method
		BDDMockito.given(sqlSession.selectList("mapper.UserMapper.userList")).willReturn(userList);

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.get("/users"));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isOk())
		.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].user_id").value("admin123"))
		.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].user_password").value("admin123!"));
		//		fail("Not yet implemented");
	}

	/**
	 * checkId 중복이 없을 경우 test case
	 * @throws Exception
	 */
	@Test
	public void checkId_중복없음_Test() throws Exception{
		//given
		//request Data
		//Response Data
		String user_id = "dongmin123";
		//		UserVO requestUser = new UserVO();
		//		requestUser.setUser_id("dongmin123");

		int responseUser;

		//given Method
		BDDMockito.given(sqlSession.selectOne("check_id", user_id)).willReturn(0);

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.get("/check-id?user_id=dongmin123"));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isOk());
	}

	/**
	 * checkId 중복이 있을 경우 test case
	 * @throws Exception
	 */
	@Test
	public void checkId_중복있음_Test() throws Exception{
		//Response Data
		String user_id = "dongmin123";

		//given Method
		BDDMockito.given(sqlSession.selectOne("check_id", user_id)).willReturn(1);

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.get("/check-id?user_id=dongmin123"));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isOk());
	}

	/**
	 * checkId 예외처리 test case<br>
	 * Client에서 parameter를 null로 줄 경우<br>
	 * UserController의 handleMissingParams method 수행 
	 * @throws Exception
	 */
	@Test
	public void checkId_Null_Test() throws Exception{

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.get("/check-id?"));
		action.andDo(MockMvcResultHandlers.print());
		action.andExpect(status().isNotAcceptable());

	}

	/**
	 * login 성공 test case
	 * @throws Exception
	 */
	@Test
	public void loginTest() throws Exception{
		//given
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");
		requestUser.setUser_password("dongmin123!");

		//request Data (Expected)
		UserVO responseUser = new UserVO();
		responseUser.setUser_id("dongmin123");
		responseUser.setUser_password("dongmin123!");

		BDDMockito.given(sqlSession.selectOne("check_id", requestUser.getUser_id())).willReturn(1);
		BDDMockito.given(sqlSession.selectOne("userLogin", requestUser.getUser_id())).willReturn(responseUser);

		//when
		ObjectMapper mapper = new ObjectMapper();

		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.post("/login")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isOk());
	}

	/**
	 * login 실패 test case<br>
	 * Parameter인 UserVo user가  null인 경우<br>
	 * UserController에서 아이디 값이 null일 경우로 test case 수행
	 * @throws Exception
	 */
	@Test
	public void login_RequestBody_Null_Test() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();

		//request Data (Expected)

		//when
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.post("/login")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		action.andExpect(status().isNotAcceptable())
		.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * login 실패 test case<br>
	 * 가입되지 않은 아이디일 경우<br>
	 * UserController에서 member가 null일 경우로 test case 수행
	 * @throws Exception
	 */
	@Test
	public void login_ServiceReturn_Null_Test() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dfg");
		requestUser.setUser_password("dfgh!");

		//request Data (Expected)
		UserVO responseUser = new UserVO();

		BDDMockito.given(sqlSession.selectOne("check_id", requestUser.getUser_id())).willReturn(0);

		//when
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(requestUser.getUser_id());
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.post("/login")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8))	
				.andExpect(status().isNotAcceptable())
				.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * userSignUpProc(회원가입) 성공 test case
	 * @throws Exception
	 */
	@Test
	public void userSignUpProcTest() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");
		requestUser.setUser_password("dongmin123!");
		requestUser.setUser_name("동민오라버니");
		requestUser.setUser_email("dong@naver.com");

		//request Data (Expected)
		UserVO responseUser = new UserVO();
		responseUser.setUser_id("dongmin123");
		responseUser.setUser_password("dongmin123!");

		BDDMockito.given(sqlSession.selectOne("check_id", requestUser.getUser_id())).willReturn(0);
		BDDMockito.given(sqlSession.insert("userInsert", requestUser.getUser_id())).willReturn(1);

		//when
		ObjectMapper mapper = new ObjectMapper();

		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.post("/user")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		//then
		action.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk());
	}

	/**
	 * userSignUpProc(회원가입) 실패 test case
	 * 이미 가입된 아이디일 경우<br>
	 * UserController에서 check_id가 1일 경우 test case 수행
	 * @throws Exception
	 */
	@Test
	public void userSignUpProc_아이디중복_Test() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");
		requestUser.setUser_password("dongmin123!");
		requestUser.setUser_name("동민오라버니");
		requestUser.setUser_email("dong@naver.com");

		//request Data (Expected)
		UserVO responseUser = new UserVO();

		BDDMockito.given(sqlSession.selectOne("check_id", requestUser.getUser_id())).willReturn(1);

		//when
		ObjectMapper mapper = new ObjectMapper();

		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.post("/user")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		//then
		action.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isNotAcceptable());
	}

	/**
	 * userSignUpProc(회원가입) 실패 test case
	 * 이미 가입된 아이디일 경우<br>
	 * UserController에서 check_id가 0과 1이 아닐 경우 test case 수행
	 * @throws Exception
	 */
	@Test
	public void userSignUpProc_Else_Test() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");
		requestUser.setUser_password("dongmin123!");
		requestUser.setUser_name("동민오라버니");
		requestUser.setUser_email("dong@naver.com");

		//request Data (Expected)
		UserVO responseUser = new UserVO();

		BDDMockito.given(sqlSession.selectOne("check_id", requestUser.getUser_id())).willReturn(5);

		//when
		ObjectMapper mapper = new ObjectMapper();

		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.post("/user")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		//then
		action.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isNotImplemented());
	}

	/**
	 * selectUserById(회원조회) 성공 test case
	 * @throws Exception
	 */
	@Test
	public void selectUserByIdTest() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("real");
		requestUser.setUser_password("dongmin123!");

		//request Data (Expected)
		UserVO responseUser = new UserVO();
		responseUser.setUser_id("real");
		responseUser.setUser_password("dongmin123!");

		//given Method
		BDDMockito.given(sqlSession.selectOne("mapper.UserMapper.selectUserById", requestUser.getUser_id() ))
		.willReturn(responseUser);

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.get("/userinfo/real"));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isOk())
		.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.user_id").value("real"))
		.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.user_password").value("dongmin123!"));
	}

	/**
	 * selectUserById(회원조회) 실패 test case
	 * service의 parameter값인 user_id가 null인 경우
	 * @throws Exception
	 */
	@Test
	public void selectUserById_ServiceReturn_Null_Test() throws Exception{
		//given
		//request Data
		UserVO requestUser = new UserVO();

		//request Data (Expected)
		UserVO responseUser = new UserVO();

		//given Method
		BDDMockito.given(sqlSession.selectOne("mapper.UserMapper.selectUserById", requestUser.getUser_id() ))
		.willReturn(responseUser);

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.get("/userinfo/dongmin123"));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isNotAcceptable());
	}

	/**
	 * selectUserById(회원조회) 실패 test case
	 * 전달 받은 사용자 ID와 조회해온 사용자ID값이 일치하지 않는 경우
	 * @throws Exception
	 */
	@Test
	public void selectUserById_NotMatch_Test() throws Exception{
		//given
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");
		requestUser.setUser_password("dongmin123!");

		//request Data (Expected)
		UserVO responseUser = new UserVO();
		responseUser.setUser_id("not_dongmin");

		//given Method
		BDDMockito.given(sqlSession.selectOne("mapper.UserMapper.selectUserById", requestUser.getUser_id() ))
		.willReturn(responseUser);

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.get("/userinfo/dongmin123"));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isNotAcceptable())
		.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.user_id").value("not_dongmin"));
	}

	/**
	 * userModify 성공 test case
	 * @throws Exception
	 */
	@Test
	public void userModifyTest() throws Exception{
		//given
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");
		requestUser.setUser_password("dongmin123!");
		requestUser.setUser_name("김동민");
		requestUser.setUser_email("dongmin@naver.com");

		BDDMockito.given(sqlSession.update("mapper.UserMapper.userModify", requestUser)).willReturn(new Integer(1));

		//when
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.put("/userinfo-edit")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isOk());
	}

	/**
	 * userModify 실패 test case
	 * 입력받은 사용자 아이디가 null인 경우
	 * @throws Exception
	 */
	@Test
	public void userModify_ID_NUll_Test() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_password("dongmin123!");
		requestUser.setUser_name("김동민");
		requestUser.setUser_email("dongmin@naver.com");

		//request Data (Expected)
		UserVO responseUser = new UserVO();

		//when
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.put("/userinfo-edit")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		//then
		action.andExpect(status().isNotAcceptable());
		action.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * userModify 실패 test case
	 * 입력받은 사용자 비밀번호가 null인 경우
	 * @throws Exception
	 */
	@Test
	public void userModify_PW_NUll_Test() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");
		requestUser.setUser_name("김동민");
		requestUser.setUser_email("dongmin@naver.com");

		//request Data (Expected)
		UserVO responseUser = new UserVO();

		//when
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.put("/userinfo-edit")
				.content(mapper.writeValueAsString(requestUser))
				.contentType(MediaType.APPLICATION_JSON_UTF8));

		//then
		action.andExpect(status().isNotAcceptable());
		action.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * userDelete 성공 test case
	 * @throws Exception
	 */
	@Test
	public void userDeleteTest() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");
		requestUser.setUser_password("dongmin123!");

		//given Method
		BDDMockito.given(sqlSession.selectOne("check_id", requestUser.getUser_id())).willReturn(1);
		BDDMockito.given(sqlSession.delete("mapper.UserMapper.userDelete", requestUser.getUser_id() )).willReturn(1);

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.delete("/user-delete/dongmin123"));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isOk());
	}

	/**
	 * userDelete 실패 test case
	 * 입력받은 아이디가 회원목록에 없는경우
	 * @throws Exception
	 */
	@Test
	public void userDelete_ID_NUll_Test() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();
		requestUser.setUser_id("dongmin123");

		//given Method
		BDDMockito.given(sqlSession.selectOne("check_id", requestUser.getUser_id())).willReturn(0);
		BDDMockito.given(sqlSession.delete("mapper.UserMapper.userDelete", requestUser.getUser_id() )).willReturn(0);

		//when
		ResultActions action = mockMVC.perform(MockMvcRequestBuilders.delete("/user-delete/dongmin123"));
		action.andDo(MockMvcResultHandlers.print());

		//then
		action.andExpect(status().isNotAcceptable());
	}

	/**
	 * userDelete 실패 test case
	 * 전달받은 user_id가 null인 경우
	 * @throws Exception
	 */
	@Test
	public void userDelete_URL_Null_Test() throws Exception{
		//request Data
		UserVO requestUser = new UserVO();

		//given Method
		BDDMockito.given(sqlSession.selectOne("check_id", requestUser.getUser_id())).willReturn(0);
		BDDMockito.given(sqlSession.delete("mapper.UserMapper.userDelete", requestUser.getUser_id() )).willReturn(0);

		//when
		ResponseEntity<UserVO> reponseEntity = userCotroller.userDelete(requestUser.getUser_id());

		//then
		assertThat(reponseEntity.getStatusCode(), equalTo(HttpStatus.NOT_ACCEPTABLE));
	}
}
