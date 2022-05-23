package com.academyinfo.member_class.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import com.academyinfo.member_class.dto.MemberClassRequestDto;

public interface MemberClassController {
	public String list(Model model);
	public String addFavorite(@PathVariable("no") int no, MemberClassRequestDto dto);
}
