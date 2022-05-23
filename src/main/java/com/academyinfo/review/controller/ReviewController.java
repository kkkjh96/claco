package com.academyinfo.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.academyinfo.review.dto.ReviewRequestDto;

public interface ReviewController {
	
	public String list(Model model);
	public ResponseEntity commentSave(@PathVariable("no") int no, @RequestBody ReviewRequestDto dto);
	public ResponseEntity update(@PathVariable("no") int no, @PathVariable("no2") int no2, @RequestBody ReviewRequestDto dto);
	public ResponseEntity delete(@PathVariable("no") int no, @PathVariable("no2") int no2);
	public String updateReport(@PathVariable("no") int no, @PathVariable("no2") int no2, Model model);
}
