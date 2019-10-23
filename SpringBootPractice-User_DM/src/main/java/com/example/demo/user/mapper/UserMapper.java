package com.example.demo.user.mapper;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.user.model.UserVO;

@Repository("com.example.demo.user.mapper.UserMapper")
public interface UserMapper {

	// 회원 목록
	public List<UserVO> userList() throws Exception;

	// 회원 내정보
	public UserVO boardDetail(int bno) throws Exception;

	// 회원 등록
	public int userInsert(UserVO board) throws Exception;

	// 게시글 수정
	public int boardUpdate(UserVO board) throws Exception;

	// 게시글 삭제
	public int boardDelete(int bno) throws Exception;

}
