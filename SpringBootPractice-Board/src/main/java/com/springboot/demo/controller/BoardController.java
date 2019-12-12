/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.controller;

/**
 * @author KimNawoo
 * @date 2019-12-13
 * @version 0.9
 * @description
 * Controller-Service-DAO로 구성된 계층 관계에서 게시판과 관련된 Controller 클래스
 */
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.model.Post;
import com.springboot.demo.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "1. boardservice" })
@RestController
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;

	/**
	 * 모든 게시글 목록 불러오기 성공여부를 반환한다
	 * 
	 * @return ResponseEntity<List<Post>> 
	 *  게시글 목록 불러오기를 성공할 경우 - 200(OK)을 반환 
	 *  게시글 목록 불러오기를 실패할 경우 - 404(NOT_FOUND)을 반환 
	 *  게시글 제목을 불러오지 못한 경우 - 406(NOT_ACCEPTABLE)을 반환
	 */
	@ApiOperation(value = "게시글 조회", notes = "모든 게시글을 조회한다")
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> selectAllPost() {

		logger.info("selectAllPost()");
		List<Post> postList = boardService.selectAllPost();

		if (postList == null) {
			ResponseEntity<List<Post>> responseEntity = new ResponseEntity<List<Post>>(postList, HttpStatus.NOT_FOUND);
			return responseEntity;
		} else {
			if (postList.get(0).getPost_title() == null) {
				ResponseEntity<List<Post>> responseEntity = new ResponseEntity<List<Post>>(postList,HttpStatus.NOT_ACCEPTABLE);
				return responseEntity;
			}
			ResponseEntity<List<Post>> responseEntity = new ResponseEntity<List<Post>>(postList, HttpStatus.OK);
			return responseEntity;
		}
	}

	/**
	 * Client로부터 게시글 정보를 입력받아 게시글 작성 성공 여부를 반환한다.
	 * 
	 * @param postNew(Post 타입의 객체)
	 * @return ResponseEntity<Post> 
	 *  게시글 작성이 성공할 경우 - 200(OK)을 반환 
	 *  게시글 작성인이 없는 경우 - 406(NOT_ACCEPTABLE)을 반환 
	 *  게시글 제목이 없는 경우 - 204(NO_CONTENT)을 반환 
	 *  게시글 내용이 글자수를 초과한 경우 - 400(BAD_REQUEST)을 반환
	 */
	@ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다")
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ResponseEntity<Post> postNew(@RequestBody Post postNew) {

		logger.info("postNew()");
		if (postNew.getPost_user_id() != null) {
			if (postNew.getPost_title() == null) {
				ResponseEntity<Post> response = new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
				return response;
			}
			if (postNew.getPost_content().length() > 200) {
				ResponseEntity<Post> response = new ResponseEntity<Post>(HttpStatus.BAD_REQUEST);
				return response;
			}
			boardService.createPost(postNew);
			ResponseEntity<Post> response = new ResponseEntity<Post>(HttpStatus.OK);
			return response;
		} else {
			ResponseEntity<Post> response = new ResponseEntity<Post>(HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
	}

	/**
	 * Client로부터 게시글 id를 받아 해당 게시글 불러오기 성공여부를 반환한다
	 * 
	 * @param id
	 * @return ResponseEntity<Post> 
	 *  해당 게시글 불러오기를 성공한 경우 - 200(OK)을 반환
	 *  받은 id와 해당 불러온 게시글의 id가 맞지 않는 경우(잘못 불러온 경우) - 204(NO_CONTENT)를 반환
	 */
	@ApiOperation(value = "게시글 상세조회", notes = "선택한 게시글을 조회한다")
	@RequestMapping(value = "/posts/{post_id}", method = RequestMethod.GET)
	public ResponseEntity<Post> selectOnePost(@PathVariable("post_id") Integer id) {
		logger.info("selectOnePost()");

		Post postOne = boardService.selectOneboard(id);
		// 보낸 postID와 게시글의 postID가 맞지 않을 때
		if (id.equals(postOne.getPost_id())) {
			ResponseEntity<Post> responseEntity = new ResponseEntity<Post>(postOne, HttpStatus.OK);
			return responseEntity;
		} else {
			ResponseEntity<Post> responseEntity = new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
			return responseEntity;
		}
	}

	/**
	 * Client로부터 게시글 수정정보를 받아 성공여부를 반환한다.
	 * 
	 * @param id
	 * @param postUpdate(Post 타입의 객체)
	 * @return  ResponseEntity<Post>
	 *  게시글 수정이 성공한 경우 - 200(OK)을 반환 
	 *  게시글 작성자와 수정을 시도한 작성자가 맞지 않는 경우 - 406(NOT_ACCEPTABLE)을 반환 
	 *  수정한 게시글 정보가 없어진 경우 - 204(NO_CONTENT)를 반환
	 */
	@ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다")
	@RequestMapping(value = "/posts/{post_id}", method = RequestMethod.PUT)
	public ResponseEntity<Post> postUpdate(@PathVariable("post_id") Integer id, @RequestBody Post postUpdate) {
		logger.info("postUpdate()");
		// 웹에서 id 받아오기
		postUpdate.setPost_id(id);
		// 받아온 id로 해당하는 게시글정보 가져오기
		Post postUserID = boardService.selectOneboard(id);
		
		// 받아온userid와 기존 게시글을 쓴 user가 같은 경우
		if (postUpdate.getPost_user_id().equals(postUserID.getPost_user_id())) {
			if (postUpdate.getPost_content() == null) {
				ResponseEntity<Post> null_response = new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
				return null_response;
			} 
			boardService.postUpdate(postUpdate);
			ResponseEntity<Post> ok_response = new ResponseEntity<Post>(HttpStatus.OK);
			return ok_response;
		} else {
			ResponseEntity<Post> notAccept_response = new ResponseEntity<Post>(HttpStatus.NOT_ACCEPTABLE);
			return notAccept_response;
		}
	}

	/**
	 * Client로부터 게시글 id를 받아 해당 게시글 삭제 성공 여부를 반환한다.
	 * 
	 * @param id
	 * @param postDelete(Post 타입의 객체)
	 * @return ResponseEntity<Post>
	 * 게시글 삭제가 성공한 경우 -200(OK)을 반환 
	 * 게시글 작성자와 삭제를 시도한 자가 맞지 않는 경우 - 406(NOT_ACCEPTABLE)을 반환
	 */
	@ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다")
	@RequestMapping(value = "/posts/{post_id}", method = RequestMethod.DELETE)
	public ResponseEntity<Post> postDelete(@PathVariable("post_id") Integer id, @RequestBody Post postDelete) {
		logger.info("postDelete()");
		// 웹에서 id 받아오기
		postDelete.setPost_id(id);
		// 받아온 id로 해당하는 게시글정보 가져오기
		Post postUserID = boardService.selectOneboard(id);

		if (postDelete.getPost_user_id().equals(postUserID.getPost_user_id())) {
			logger.info("userInfo" + postDelete.getPost_user_id());
			boardService.postDelete(postDelete);
			ResponseEntity<Post> response = new ResponseEntity<Post>(HttpStatus.OK);
			return response;
		} else {
			ResponseEntity<Post> response = new ResponseEntity<Post>(HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
	}
}