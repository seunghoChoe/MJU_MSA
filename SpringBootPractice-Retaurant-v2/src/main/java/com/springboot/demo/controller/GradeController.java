package com.springboot.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.persistence.Column;

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
import com.springboot.demo.entity.Restaurant;
import com.springboot.demo.model.response.CommonResult;
import com.springboot.demo.repo.GradeJpaRepo;
import com.springboot.demo.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1-2. Grade" })
@RequiredArgsConstructor
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = "/restaurant/")
public class GradeController {
	private final GradeJpaRepo gradeJpaRepo;
	private final ResponseService responseService; // 결과를 처리할 Service

//	@ApiOperation(value = "식당 조회", notes = "모든 식당을 조회한다")
//	@GetMapping(value = "/grade")
//	public ResponseEntity<List<Grade>> findAllRestaurant() {
//		// 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
////		ListResult<Restaurant> sampleList = responseService.getListResult(gradeJpaRepo.findAll());
//		
//		List<Grade> gradeList = gradeJpaRepo.findAll();
//		return new ResponseEntity<List<Grade>>(gradeList, HttpStatus.OK);
////		Gson gson = new Gson(); // Gson 사용
////	    return gson.toJson(sampleList); // json으로 변환 후, 리턴
//	}
	

//	@ApiOperation(value = "한 식당의 평점 조회", notes = "res_index로 식당을 조회한다")
//	@GetMapping(value = "/restaurant/{res_index}/grade")
//	public ResponseEntity<Grade> findUserById(
//			@ApiParam(value = "식당 ID", required = true) @PathVariable int res_index) {
//		// 식당별로 갖고와서 평균 구해야 함 
//		Grade grade = gradeJpaRepo.getOne(res_index);
//		return new ResponseEntity<Grade>(grade, HttpStatus.OK);
//	}
	
	@ApiOperation(value = "평점 입력", notes = "평점을 입력한다.")
	@PostMapping(value = "/{res_index}/grade")
	public ResponseEntity<Grade> save(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index,
			@RequestBody Grade grade
			) throws UnsupportedEncodingException {

		Grade newGrade= gradeJpaRepo.save(grade);
		return new ResponseEntity<Grade>(newGrade, HttpStatus.OK);
	}
	
	@ApiOperation(value = "평점 수정", notes = "식당 평점을 수정한다")
    @PutMapping(value = "/{res_index}/grade/{grade_id}")
    public ResponseEntity<Grade> modify(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index,
    		@ApiParam(value = "평점 아이디", required = true) @PathVariable int grade_id,
    		@RequestBody Grade grade) throws UnsupportedEncodingException {
		
		Grade modifiedGrade = gradeJpaRepo.getOne(grade_id);
		modifiedGrade.setStar(grade.getStar());
		
		return new ResponseEntity<Grade>(modifiedGrade, HttpStatus.OK);
    }
	
	@ApiOperation(value = "평점 삭제", notes = "grade_id로 평 정보를 삭제한다")
	@DeleteMapping(value = "/{res_index}/grade/{grade_id}")
	public CommonResult delete(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index,
			@ApiParam(value = "평점 아이디", required = true) @PathVariable int grade_id) {
		gradeJpaRepo.deleteById(grade_id);
		// 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
		return responseService.getSuccessResult();
	}
}