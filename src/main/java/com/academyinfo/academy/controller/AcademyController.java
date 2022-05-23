package com.academyinfo.academy.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.academy.dto.AcademyRequestDto;

public interface AcademyController {
	@GetMapping("add")
	public String add(Model model);
	public String add(AcademyRequestDto academyRequestDto, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception;
	public String detail(@PathVariable("no") int no, Model model);
	public String edit(@PathVariable("no") int no, Model model);
	public String update(AcademyRequestDto academyDTO, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception;
	public String delete(@PathVariable("no") int no);
}
