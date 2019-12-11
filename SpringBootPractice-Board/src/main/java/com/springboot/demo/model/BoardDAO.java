/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.model;

/**
 * @author KimNawoo
 * @date 2019-12-13
 * @version 0.9
 * @description
 * Controller-Service-DAO로 구성된 계층 관계에서 게시판과 관련된 DAO 클래스
 */
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	private static final String MAPPER_NAME_SPACE = "mapper.BoardMapper.";
	/**
	 * 게시글 목록 불러오기
	 * @return ResponseEntity<List<Post>>
	 */
	public List<Post> selectAllPost() {
		return sqlSessionTemplate.selectList(MAPPER_NAME_SPACE + "selectAllPost");
	}
	/**
	 * 게시글 작성하기
	 * @param newPost(Post 타입의 객체)
	 */
	public void createPost(Post newPost) {
		sqlSessionTemplate.insert(MAPPER_NAME_SPACE + "createPost", newPost);
	}
	/**
	 * 해당 게시글 불러오기(자세히보기)
	 * @param id
	 * @return ResponseEntity<Post>
	 */
	public Post selectOnePost(Integer id) {
		return sqlSessionTemplate.selectOne(MAPPER_NAME_SPACE + "selectOnePost", id);
	}
	/**
	 * 게시글 수정하기
	 * @param postUpdate(Post 타입의 객체)
	 */
	public void postUpdate(Post postUpdate) {
		sqlSessionTemplate.update(MAPPER_NAME_SPACE + "updatePost", postUpdate);
	}
	/**
	 * 게시글 삭제하기
	 * @param postDelete(Post 타입의 객체)
	 */
	public void postDelete(Post postDelete) {
		sqlSessionTemplate.delete(MAPPER_NAME_SPACE + "deletePost", postDelete);
	}

}
