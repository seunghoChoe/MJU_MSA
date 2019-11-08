package com.springboot.demo.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String MAPPER_NAME_SPACE = "mapper.BoardMapper.";


	public List<Post> selectAllPost() {
		return sqlSessionTemplate.selectList(MAPPER_NAME_SPACE + "selectAllPost");
	}


	public void createPost(Post newPost) {
		sqlSessionTemplate.insert(MAPPER_NAME_SPACE + "createPost", newPost);
	}


	public Post selectOnePost(Integer id) {
		return sqlSessionTemplate.selectOne(MAPPER_NAME_SPACE + "selectOnePost", id);
	}


	public void postUpdate(Post postUpdate) {
		sqlSessionTemplate.update(MAPPER_NAME_SPACE + "updatePost",postUpdate);
	}


	public void deletePost(Integer id) {
		 sqlSessionTemplate.delete(MAPPER_NAME_SPACE + "deletePost", id) ;
	}


}
