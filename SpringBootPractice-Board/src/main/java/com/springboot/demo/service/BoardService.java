package com.springboot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.BoardDAO;
import com.springboot.demo.model.Post;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;

	public List<Post> selectAllPost() {
		return boardDAO.selectAllPost();
	}

	public void createPost(Post newPost) {
		boardDAO.createPost(newPost);
	}

	public Post selectOneboard(Integer id) {
		return boardDAO.selectOnePost(id);
	}

	public void postUpdate(Post postUpdate) {
		boardDAO.postUpdate(postUpdate);
		
	}

	public void deletePost(Integer id) {
		boardDAO.deletePost(id);
	}

}
