/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.entity.Grade;
import com.springboot.demo.repo.GradeJpaRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * @author Hwamoc Kim
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Grade(평점) Controller는 사용자(Client)의 해당 요청(Request) url에 따른 처리(Handling) 방법을 명시한다
 */

@Api(tags = { "1-2. Grade" })
@RequiredArgsConstructor
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = "/restaurant/")
public class GradeController {
	private final GradeJpaRepo gradeJpaRepo;

	/**
	 * 사용자(Client)의 요청으로부터 한 식당의 아이디를 받아와 해당 식당의 평점을 조회한다 
	 * @param res_index 
	 * 	restaurant의 아이디
	 * @return ResponseEntity<Grade>
	 * 	조회 성공할 경우 - 200(OK)를 반환
	 * 	조회 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "한 식당의 평점 조회", notes = "res_index로 식당을 조회한다")
	@GetMapping(value = "/{res_index}/grade")
	public ResponseEntity<Grade> findUserById(@ApiParam(value = "식당 ID", required = true) @PathVariable int res_index) {
		// 식당별로 갖고와서 평균 구해야 함
		Grade grade = gradeJpaRepo.getOne(res_index);
		return new ResponseEntity<Grade>(grade, HttpStatus.OK);
	}

	/**
	 * 사용자(Client)의 요청으로부터 한 식당의 아이디를 받아와 해당 식당의 평점을 입력한다 
	 * @param res_index 
	 * 	restaurant의 아이디
	 * @return ResponseEntity<Grade>
	 * 	입력 성공할 경우 - 200(OK)를 반환
	 * 	입력 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "평점 입력", notes = "평점을 입력한다.")
	@PostMapping(value = "/{res_index}/grade")
	public ResponseEntity<Grade> save(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index,
			@RequestBody Grade grade) throws UnsupportedEncodingException {

		Grade newGrade = gradeJpaRepo.save(grade);
		return new ResponseEntity<Grade>(newGrade, HttpStatus.OK);
	}

	/**
	 * 사용자(Client)의 요청으로부터 한 식당의 아이디와 한 평점의 아이디를 받아와 해당 식당의 평점을 수정한다  
	 * @param res_index 
	 * 	restaurant의 아이디
	 * @param grade_id 
	 * 	grade의 아이디
	 * @return ResponseEntity<Grade>
	 * 	수정 성공할 경우 - 200(OK)를 반환
	 *  수정 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "평점 수정", notes = "식당 평점을 수정한다")
	@PutMapping(value = "/{res_index}/grade/{grade_id}")
	public ResponseEntity<Grade> modify(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index,
			@ApiParam(value = "평점 아이디", required = true) @PathVariable int grade_id, @RequestBody Grade grade)
			throws UnsupportedEncodingException {

		Grade modifiedGrade = gradeJpaRepo.getOne(grade_id);
		modifiedGrade.setStar(grade.getStar());

		return new ResponseEntity<Grade>(modifiedGrade, HttpStatus.OK);
	}

	/**
	 * 사용자(Client)의 요청으로부터 한 식당의 아이디와 한 평점의 아이디를 받아와 해당 식당의 평점을 삭제한다 
	 * @param res_index 
	 * 	restaurant의 아이디
	 * @param grade_id 
	 * 	grade의 아이디
	 * @return ResponseEntity<Void>
	 * 	삭제 성공할 경우 - 200(OK)를 반환
	 * 	삭제 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "평점 삭제", notes = "grade_id로 평 정보를 삭제한다")
	@DeleteMapping(value = "/{res_index}/grade/{grade_id}")
	public ResponseEntity<Void> delete(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index,
			@ApiParam(value = "평점 아이디", required = true) @PathVariable int grade_id) {
		gradeJpaRepo.deleteById(grade_id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}