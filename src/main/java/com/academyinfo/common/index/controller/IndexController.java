package com.academyinfo.common.index.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.academyinfo.Class.dto.ClassResponseDto;
import com.academyinfo.Class.service.ClassService;
import com.academyinfo.Files.Class.dto.ClassFilesResponseDto;
import com.academyinfo.Files.Class.service.ClassFilesService;
import com.academyinfo.board.alertboard.dto.AlertBoardResponseDto;
import com.academyinfo.board.alertboard.service.AlertBoardService;
import com.academyinfo.board.freeboard.dto.BoardResponseDto;
import com.academyinfo.board.freeboard.service.BoardService;
import com.academyinfo.review.dto.ReviewResponseDto;
import com.academyinfo.review.service.ReviewService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class IndexController {
	private static final int PAGE_NUM = 1;
	
	private BoardService freeBoardService;
	private AlertBoardService alertBoardService;
	private ClassFilesService classFilesService;
	private ClassService classService;
	private ReviewService reviewService;
	
	@GetMapping("/")
	public String index(Model model) {
		
		// 게시판 리스트
		List<BoardResponseDto> freeboardList = freeBoardService.getBoardlist(PAGE_NUM);
		List<AlertBoardResponseDto> alertboardList = alertBoardService.getBoardlist(PAGE_NUM);
		
		/*추천강좌카드*/
		List<ClassResponseDto> listClass = classService.getRecommendClass();
		List<ClassFilesResponseDto> listImage = classFilesService.getImage(listClass);
		
		List<String> imageList = new ArrayList<>();
        
        for (ClassFilesResponseDto dto : listImage) {
        	String [] str = dto.getPath().split(File.separator + File.separator);
        	
        	imageList.add("/" + str[4] + "/" + str[5] + "/" + str[6]);
        }
		
		/*수강후기*/
		// List<ReviewResponseDto> listReview = reviewService.getReview();
        List<ReviewResponseDto> listReview = reviewService.getReviewByCreatedDate();
		
		// 게시판 리스트
		model.addAttribute("freeboardList", freeboardList);
		model.addAttribute("alertboardList", alertboardList);
		
		/*추천강좌카드*/
		model.addAttribute("listClass", listClass);
		model.addAttribute("listImg", imageList);
		
		/*수강후기*/
		model.addAttribute("listReview", listReview);
		
        return "index";
    }
}
