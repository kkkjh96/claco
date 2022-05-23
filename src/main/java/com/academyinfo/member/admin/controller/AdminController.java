package com.academyinfo.member.admin.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import com.academyinfo.Class.dto.ClassRequestDto;
import com.academyinfo.review.dto.ReviewRequestDto;

public interface AdminController {
    public String dispAdmin();
	public String dispUserManagePage(Model model);
	public String dispArticleManagePage(Model model);
	public String dispAuthPage(Model model);
	public String update(ClassRequestDto classDTO);
	public String update(ReviewRequestDto reviewDTO);
	public String deleteBoard(@PathVariable("no") int no);
	public String deleteComment(@PathVariable("no") int no, @PathVariable("no2") int no2);
	public String deleteReview(@PathVariable("no") int no, @PathVariable("no2") int no2);
	public String deleteMember(@PathVariable("no") Long no);
}
