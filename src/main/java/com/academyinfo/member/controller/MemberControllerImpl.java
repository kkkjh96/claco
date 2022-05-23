package com.academyinfo.member.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.academyinfo.Class.dto.ClassResponseDto;
import com.academyinfo.academy.dto.AcademyResponseDto;
import com.academyinfo.board.alertboard.dto.AlertBoardResponseDto;
import com.academyinfo.board.comment.dto.CommentResponseDto;
import com.academyinfo.board.freeboard.dto.BoardResponseDto;
import com.academyinfo.member.dto.MemberRequestDto;
import com.academyinfo.member.dto.MemberResponseDto;
import com.academyinfo.member.service.MemberService;
import com.academyinfo.review.dto.ReviewResponseDto;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("user")
public class MemberControllerImpl implements MemberController {
    private MemberService memberService;
    
    // 회원가입 페이지
    @GetMapping("signup")
    public String dispSignup() {
        return "member/signUp";
    }
    
    // 회원가입 처리
    @PostMapping("signup")
    public String execSignup(@Valid MemberRequestDto memberDto, Errors errors, Model model) {
    	if(errors.hasErrors()) {
    		/* 회원가입 실패시 입력 데이터 값을 유지 */
    		model.addAttribute("memberDto", memberDto);
    		
    		/* 유효성 통과 못한 필드와 메시지를 핸들링 */
    		Map<String, String> validatorResult = memberService.validateHandling(errors);
    		for (String key : validatorResult.keySet()) {
    				model.addAttribute(key, validatorResult.get(key));
    		}
	    		
    		/* 회원가입 페이지로 다시 리턴 */
    		return "member/SignUp";
    	}
    	
        memberService.joinUser(memberDto);

        return "redirect:/user/login";
    }
        
    // 로그인 페이지
    @GetMapping("login")
    public String dispLogin(@RequestParam(value = "error", required = false) String error, Model model) {
    	model.addAttribute("error", error);
    	return "member/login";
    }

    // 접근 거부 페이지
    @GetMapping("denied")
    public String dispDenied() {
        return "denied";
    }

    // 마이페이지
    @GetMapping("mypage")
    public String dispMyInfo(Model model) {
        return "member/mypage";
        
    }
    
    
    // 회원 정보 수정 페이지(일반,기업 공통)
    @GetMapping("info")
    public String dispUserInfo(Model model) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인한 사용자의 정보를 가져온다
		String id = ((UserDetails)principal).getUsername(); // 사용자의 Id
		
		MemberResponseDto memberDto = memberService.getMemberDto(id);
        // String name = memberDto.getName();
        
    	model.addAttribute("memberDto", memberDto);
    	
        return "member/myinfo";
    }
    
    // 회원 정보 수정하기    
    @PutMapping("update")
    public String updateUserInfo(@Valid MemberRequestDto memberDto, Errors errors, Model model) {
    	if(errors.hasErrors()) {
    		/* 회원가입 실패시 입력 데이터 값을 유지 */
    		model.addAttribute("memberDto", memberDto);
    		/* 유효성 통과 못한 필드와 메시지를 핸들링 */
    		Map<String, String> validatorResult = memberService.validateHandling(errors);
	    		for (String key : validatorResult.keySet()) {
	    		model.addAttribute(key, validatorResult.get(key));
    		}
    		/* 회원수정 페이지로 다시 리턴 */
    		return "member/myinfo";
    	}
    	
    	memberService.updateUser(memberDto);
    	
    	return "redirect:/user/mypage";
    }
    
    // 일반회원이 찜한 강의 목록 페이지
    @GetMapping("userClassList")
    public String dispUserClassList() {
        return "member/userClassList";
    }
    
    // 일반회원이 쓴 자유게시판 글 목록 페이지
    @GetMapping("userArticleList")
    public String dispUserArticleList(Model model) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인한 사용자의 정보를 가져온다
		String id = ((UserDetails)principal).getUsername(); // 사용자의 Id
		
		MemberResponseDto memberResponseDto = memberService.getMemberDto(id);
		
		List<BoardResponseDto> freeboardList = memberResponseDto.getFbindex();
		List<AlertBoardResponseDto> alertboardList = memberResponseDto.getAbindex();
		List<CommentResponseDto> commentList = memberResponseDto.getCmtindex();
		List<ReviewResponseDto> reviewList = memberResponseDto.getRvindex();
		
    	model.addAttribute("freeboardList", freeboardList);
    	model.addAttribute("alertboardList", alertboardList);
    	model.addAttribute("commentList", commentList);
    	model.addAttribute("reviewList", reviewList);
		
        return "member/userArticleList";
    }
    
    // 학원이 등록한 강의 목록 페이지
    @GetMapping("cpnClassList")
    public String dispCpnClassList(Model model) {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인한 사용자의 정보를 가져온다
		String id = ((UserDetails)principal).getUsername(); //
		
		MemberResponseDto memberResponseDto = memberService.getMemberDto(id);
		List<AcademyResponseDto> academyList = memberResponseDto.getAindex();	
		List<ClassResponseDto> classList = new ArrayList<>();
		
		for (int i=0; i < academyList.size(); i++) {
			for (int j=0; j < academyList.get(i).getCindex().size(); j++) {
				classList.add(academyList.get(i).getCindex().get(j));
			}
		}
		
		model.addAttribute("academyList", academyList);
		model.addAttribute("classList", classList);
    	
        return "member/cpnClassList";
    }
    
    // 아이디 중복 체크  (아이디가 이미 존재하면 true를 반환한다)
    @GetMapping("idCheck")
    @ResponseBody
    public boolean checkIdDuplicate(@RequestParam("id") String id)	{
		boolean check = memberService.checkIdDuplicate(id);
		
		System.out.println("Id Check:" + check);
		return check;
	}
    
}