package com.academyinfo.review.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.academyinfo.review.dto.ReviewRequestDto;
import com.academyinfo.review.dto.ReviewResponseDto;
import com.academyinfo.review.service.ReviewService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("claco")
@Controller
public class ReviewControllerImpl implements ReviewController {
	public ReviewService reviewService;
	
	@GetMapping("review/list")
	public String list(Model model) {
		
		List<ReviewResponseDto> listReview = reviewService.getReview();
		
		model.addAttribute("listReview", listReview);
		
		return "review/list";
	}
	
	// 수강후기 신고 수 증가
    @PutMapping("post/{no}/report/{no2}")
    public String updateReport(@PathVariable("no") int no, @PathVariable("no2") int no2, Model model) {
    	
    	reviewService.updateReport(no2);
    	
    	return "redirect:/claco/post/{no}";
    }
	
	// 댓글 생성
	@PostMapping("post/{no}/review")
	public ResponseEntity commentSave(@PathVariable("no") int no, @RequestBody ReviewRequestDto dto) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인한 사용자의 정보를 가져온다
		String id = ((UserDetails)principal).getUsername(); // 사용자의 Id
		
		return ResponseEntity.ok(reviewService.reviewSave(id, no, dto));
	}
	
	// 댓글 수정
	@PutMapping({"post/{no}/review/{no2}"})
	public ResponseEntity update(@PathVariable("no") int no, @PathVariable("no2") int no2, @RequestBody ReviewRequestDto dto) {
		reviewService.update(no2, dto);
		
		return ResponseEntity.ok(no);
	}
	
	// 댓글 삭제 (no : class.index 유지 / no2 : review.index 조회 및 삭제)
	@DeleteMapping("post/{no}/review/{no2}")
	public ResponseEntity delete(@PathVariable("no") int no, @PathVariable("no2") int no2) {
		reviewService.delete(no2);
		
		return ResponseEntity.ok(no);
	}
}
