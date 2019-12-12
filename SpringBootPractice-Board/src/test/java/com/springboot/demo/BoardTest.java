package com.springboot.demo;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 * @author KimNawoo
 * @date 2019-12-13
 * @version 0.9
 * @description
 *   Controller-Service-DAO 테스트클래스
 *   (Web과 DB는 Mock처리)
 */
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.controller.BoardController;
import com.springboot.demo.model.BoardDAO;
import com.springboot.demo.model.Post;
import com.springboot.demo.service.BoardService;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = { BoardController.class, BoardService.class, BoardDAO.class })
public class BoardTest {

	private MockMvc mockMvc;
	@Autowired
	private BoardController boardController;
	@Autowired
	private BoardService boardService;
	@Autowired
	private BoardDAO boardDAO;

	/*웹대신 mockMVC만들기*/ 
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
	}

	@MockBean
	private SqlSessionTemplate sqlSession;
	/**
	 * @category 게시글 목록 불러오기 성공 테스트
	 *  게시글 목록 불러오기 성공시 200(OK)반환
	 * @throws Exception
	 */
	@Test
	public void selectAllPost() throws Exception {
		/* Given */
		
		// request Data & Response Data
		List<Object> boardList = new ArrayList<Object>();
		Post post = new Post();
		post.setPost_id(1);
		post.setPost_user_id("now");
		post.setPost_title("sample1");
		post.setPost_content("sample1");
		post.setPost_image("image");
		boardList.add(post);

		// given Method
		//DAO에서 받아온 값을 Service에 return
		BDDMockito.given(sqlSession.selectList("mapper.BoardMapper.selectAllPost")).willReturn(boardList);
		
		/* When */
		//Web에서 호출하는 메소드
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/posts"));
		
		/* Then */
		action.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].post_user_id").value("now"))
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].post_title").value("sample1"));
	}
	/**
	 * @category 게시글 목록 불러오기 실패 테스트
	 *  게시글 목록이 없는 경우 404(NOT_FOUND)반환
	 * @throws Exception
	 */
	@Test
	public void 리스트가_널일떄_테스트() throws Exception {
		/* Given (없음)*/
		
		BDDMockito.given(sqlSession.selectList("mapper.BoardMapper.selectAllPost")).willReturn(null);
		
		/* When */
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/posts"));
		
		/* Then */
		action.andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 목록 불러오기 실패 테스트
	 *  게시글의 제목이 없는 경우 406(NOT_ACCEPTABLE)반환
	 * @throws Exception
	 */
	@Test
	public void 제목정보가_누락이된채로올때테스트() throws Exception {
		/* Given*/
		// request Data & Response Data
		List<Object> boardList = new ArrayList<Object>();
		Post getPost = new Post();
		getPost.setPost_id(1);
		getPost.setPost_user_id("꺢");
		getPost.setPost_title(null);
		getPost.setPost_content("s내용");
		boardList.add(getPost);

		BDDMockito.given(sqlSession.selectList("mapper.BoardMapper.selectAllPost")).willReturn(boardList);
		
		/* When */
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/posts"));
		
		/* Then */
		action.andExpect(status().isNotAcceptable()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 해당 게시글 불러오기 성공 테스트
	 *  해당 게시글을 불러왔을 경우 200(OK)반환  
	 * @throws Exception
	 */
	@Test
	public void selectOnePost() throws Exception {
		/* Given*/
		// request Data & Response Data
		Post postOne = new Post();
		postOne.setPost_id(1);
		postOne.setPost_title("sample1");
		postOne.setPost_content("sample1");
		
		BDDMockito.given(sqlSession.selectOne("mapper.BoardMapper.selectOnePost", 1)).willReturn(postOne);
		
		/* When */
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/posts/1"));

		/* Then */
		action.andExpect(status().isOk())
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.post_title").value("sample1"))
				.andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 해당 게시글 불러오기 실패 테스트
	 *  선택된 게시글과 불러온 게시글이 다를 경우 204(NO_CONTENT)반환
	 * @throws Exception
	 */
	@Test
	public void 가져온_게시글post_id값이_다를때테스트() throws Exception {
		/* Given*/
		// request Data
		Post postOne = new Post();
		postOne.setPost_id(1);
		
		// response Data
		Post getPostOne = new Post();
		getPostOne.setPost_id(2);
		
		BDDMockito.given(sqlSession.selectOne("mapper.BoardMapper.selectOnePost", 1)).willReturn(getPostOne);
		
		/* When */
		ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/posts/1"));
		
		/* Then */
		action.andExpect(status().isNoContent()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 작성 성공 테스트
	 *  게시글 작성 성공할 경우 200(OK)반환
	 * @throws Exception
	 */
	@Test
	public void createPost() throws Exception {
		/* Given*/
		// request Data & Response Data
		Post newPost = new Post();
		newPost.setPost_user_id("kim");
		newPost.setPost_title("HI");
		newPost.setPost_content("bro");
		newPost.setPost_image("imaaage");

		BDDMockito.given(sqlSession.insert("createPost", newPost)).willReturn(1);
		
		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.post("/post")
				.content(mapper.writeValueAsString(newPost))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		
		/* Then */
		action.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 작성 실패 테스트
	 *  게시글 제목이 없는 경우 204(NO_CONTENT)반환
	 * @throws Exception
	 */
	@Test
	public void 게시글_제목이없을때_테스트() throws Exception {
		/* Given*/
		// request Data & Response Data
		Post newPost = new Post();
		newPost.setPost_user_id("user1");
		newPost.setPost_title(null);

		BDDMockito.given(sqlSession.insert("createPost", newPost)).willReturn(0);
		
		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.post("/post")
				.content(mapper.writeValueAsString(newPost))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		/* Then */
		action.andExpect(status().isNoContent()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 작성 실패 테스트
	 *  식별되지않은 사람이 게시글 작성을 시도할 경우 406(NOT_ACCEPTABLE)반환
	 * @throws Exception
	 */
	@Test
	public void 로그인하지않은사람이게시글쓰는테스트() throws Exception {
		/* Given*/
		// request Data
		Post newPost = new Post();
		newPost.setPost_user_id(null);
		newPost.setPost_title("꺢꺢!");

		BDDMockito.given(sqlSession.insert("createPost", newPost)).willReturn(0);
		
		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.post("/post")
				.content(mapper.writeValueAsString(newPost))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		
		/* Then */
		action.andExpect(status().isNotAcceptable()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 작성 실패 테스트
	 *  게시글 내용이 글자수를 초과했을 경우 400(BAD_REQUEST)반환
	 * @throws Exception
	 */
	@Test
	public void 게시글내용이넘많을때테스트() throws Exception {
		/* Given*/
		// request Data
		Post newPost = new Post();
		newPost.setPost_user_id("꺢꺢");
		newPost.setPost_title("아니그래서말인데요오");
		newPost.setPost_content(
				"1llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
		
		BDDMockito.given(sqlSession.insert("createPost", newPost)).willReturn(0);
		
		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.post("/post")
				.content(mapper.writeValueAsString(newPost))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		
		/* Then */
		action.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 수정 성공 테스트
	 *  게시글 수정을 성공할 경우 200(OK)반환
	 * @throws Exception
	 */
	@Test
	public void postUpdate() throws Exception {
		/* Given*/
		// request Data
		Post postUpdate = new Post();
		postUpdate.setPost_id(1);
		postUpdate.setPost_user_id("user");
		postUpdate.setPost_title("sample3");
		postUpdate.setPost_content("sample3");
		
		//Response Data
		Post getPostUser = new Post();
		getPostUser.setPost_user_id("user");
		
		
		BDDMockito.given(sqlSession.selectOne("mapper.BoardMapper.selectOnePost", postUpdate.getPost_id()))
				.willReturn(getPostUser);
		BDDMockito.given(sqlSession.update("mapper.BoardMapper.updatePost", postUpdate)).willReturn(1);

		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.put("/posts/1")
				.content(mapper.writeValueAsString(postUpdate))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
	
		/* Then */
		action.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 수정 실패 테스트
	 *  수정을 시도한 사람이 해당 게시글 작성자가 아닐때 406(NOT_ACCEPTABLE)반환
	 * @throws Exception
	 */
	@Test
	public void 게시글_주인이아닐때_업데이트테스트() throws Exception {
		/* Given*/
		// request Data
		Post postUpdate = new Post();
		postUpdate.setPost_id(1);
		postUpdate.setPost_user_id("user");
		postUpdate.setPost_title("sample3");
		postUpdate.setPost_content("sample3");
		
		//Response Data
		Post getPostUser = new Post();
		getPostUser.setPost_user_id("김꺢꺢");

		BDDMockito.given(sqlSession.selectOne("mapper.BoardMapper.selectOnePost", postUpdate.getPost_id()))
				.willReturn(getPostUser);
		BDDMockito.given(sqlSession.update("mapper.BoardMapper.updatePost", postUpdate)).willReturn(1);

		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.put("/posts/1")
				.content(mapper.writeValueAsString(postUpdate))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		
		/* Then */
		action.andExpect(status().isNotAcceptable()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 수정 실패 테스트
	 *  수정시 게시글 내용이 없을 때 204(NO_CONTENT)반환
	 * @throws Exception
	 */
	@Test
	public void 게시글_내용이_날아갔을떄_업데이트테스트() throws Exception {
		/* Given*/
		// request Data
		Post getPostUser = new Post();
		getPostUser.setPost_user_id("김꺢꺢");
	
		//Response Data
		Post postUpdate = new Post();
		postUpdate.setPost_id(1);
		postUpdate.setPost_user_id("김꺢꺢");
		postUpdate.setPost_content(null);

		BDDMockito.given(sqlSession.selectOne("mapper.BoardMapper.selectOnePost", postUpdate.getPost_id()))
				.willReturn(getPostUser);
		
		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.put("/posts/1")
				.content(mapper.writeValueAsString(postUpdate))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		
		/* Then */		
		action.andExpect(status().isNoContent()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 삭제 성공 테스트
	 *  게시글 삭제가 성공할 경우 200(OK)반환
	 * @throws Exception
	 */
	@Test
	public void postDelete() throws Exception {
		/* Given*/
		// request Data
		Post postDelete = new Post();
		postDelete.setPost_id(1);
		postDelete.setPost_user_id("user");
		
		//Response Data
		Post getPostUser = new Post();
		getPostUser.setPost_user_id("user");

		// given
		BDDMockito.given(sqlSession.selectOne("mapper.BoardMapper.selectOnePost", postDelete.getPost_id()))
				.willReturn(getPostUser);
		BDDMockito.given(sqlSession.delete("mapper.BoardMapper.deletePost", postDelete)).willReturn(1);

		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.delete("/posts/1")
				.content(mapper.writeValueAsString(postDelete))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		
		/* Then */	
		action.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	/**
	 * @category 게시글 삭제 실패 테스트
	 *  삭제을 시도한 사람이 해당 게시글 작성자가 아닐때 406(NOT_ACCEPTABLE)반환 
	 * @throws Exception
	 */
	@Test
	public void 게시글_주인이아닐때_딜리트테스트() throws Exception {
		/* Given*/
		// request Data
		Post postDelete = new Post();
		postDelete.setPost_id(1);
		postDelete.setPost_user_id("user");

		//Response Data
		Post getPostUser = new Post();
		getPostUser.setPost_user_id("김꺢꺢");
		
		BDDMockito.given(sqlSession.selectOne("mapper.BoardMapper.selectOnePost", postDelete.getPost_id()))
				.willReturn(getPostUser);
		BDDMockito.given(sqlSession.delete("mapper.BoardMapper.deletePost", postDelete)).willReturn(1);

		/* When */
		ObjectMapper mapper = new ObjectMapper();
		ResultActions action = mockMvc
				.perform(MockMvcRequestBuilders.delete("/posts/1")
				.content(mapper.writeValueAsString(postDelete))
				.contentType(MediaType.APPLICATION_JSON_UTF8));
		
		/* Then */
		action.andExpect(status().isNotAcceptable()).andDo(MockMvcResultHandlers.print());
	}
}
