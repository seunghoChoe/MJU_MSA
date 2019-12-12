/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.service;
/**
 * @author KimNawoo
 * @date 2019-12-13
 * @version 0.9
 * @description
 * Controller-Service-DAO로 구성된 계층 관계에서 게시판과 관련된 Service 클래스
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.BoardDAO;
import com.springboot.demo.model.Post;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;

	/**
	 * 게시글 목록 불러오기
	 * @return ResponseEntity<List<Post>>
	 */
	public List<Post> selectAllPost() {
		return boardDAO.selectAllPost();
	}

	/**
	 * 게시글 작성하기
	 * @param newPost(Post 타입의 객체)
	 */
	public void createPost(Post newPost) {
		boardDAO.createPost(newPost);
	}

	/**
	 * 해당 게시글 불러오기(자세히보기)
	 * @param id
	 * @return ResponseEntity<Post>
	 */
	public Post selectOneboard(Integer id) {
		return boardDAO.selectOnePost(id);
	}

	/**
	 * 게시글 수정하기
	 * @param postUpdate(Post 타입의 객체)
	 */
	public void postUpdate(Post postUpdate) {
		boardDAO.postUpdate(postUpdate);

	}

	/**
	 * 게시글 삭제하기
	 * @param postDelete(Post 타입의 객체)
	 */
	public void postDelete(Post postDelete) {
		boardDAO.postDelete(postDelete);
	}

}
