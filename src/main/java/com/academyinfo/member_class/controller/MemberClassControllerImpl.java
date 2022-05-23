package com.academyinfo.member_class.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.academyinfo.member_class.dto.MemberClassRequestDto;
import com.academyinfo.member_class.dto.MemberClassResponseDto;
import com.academyinfo.member_class.service.MemberClassService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("favorite")
public class MemberClassControllerImpl implements MemberClassController {
	private MemberClassService memberClassService;
	
	// 나의 강의 보기(즐겨찾기한 강의 리스트를 출력한다.)
	@GetMapping("/userClassList")
	public String list(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인한 사용자의 정보를 가져온다
		String id = ((UserDetails)principal).getUsername(); // 사용자의 Id
		
		List<MemberClassResponseDto> memberClassList = memberClassService.getList(id);
		
		model.addAttribute("memberClassList", memberClassList);
		
		return "member/userClassList";
	}
	
	// 즐겨찾기 없으면 생성
	@PutMapping("post/{no}")
	public String addFavorite(@PathVariable("no") int no, MemberClassRequestDto dto) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인한 사용자의 정보를 가져온다
		String id = ((UserDetails)principal).getUsername(); // 사용자의 Id
		
		memberClassService.save(id, no, dto);
		
		return "redirect:/claco/post/{no}";
	}
}
