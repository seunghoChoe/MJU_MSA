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
}
