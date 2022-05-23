package com.academyinfo.Class.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Class.dto.ClassRequestDto;

public interface ClassController {
	
	public String list();
	// public String search(@RequestParam(value="keyword", required=false) String keyword, Model model);
	// public String search(@RequestParam(value="keyword", required=false) String keyword, @RequestParam(value="arr_location", required=false) String[] arr_location, Model model);
	public String search(@RequestParam(value="page", defaultValue = "1") Integer pageNum, @RequestParam(value="keyword", required=false) String keyword, @RequestParam(value="arr_location", required=false) String[] arr_location, Model model);
	
	public String add(@PathVariable("no") int aindex, Model model);
	
	// 강의 등록화면에서 데이터 넘어오면 DB 저장
	public String write(@PathVariable("no") int aindex, ClassRequestDto classRequestDto, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception;
	
	// 게시글 상세보기
	public String detail(@PathVariable("no") int no, Model model);

	// 게시글 수정
	public String edit(@PathVariable("no") int no, Model model);
	// 게시글 수정 값 DB 저장
	public String update(ClassRequestDto classDTO, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception;
	    
	// 게시글 삭제 실행
	public String delete(@PathVariable("no") int no);	
	public void addPrevNext(Integer [] pageList, Integer pageNum, String keyword, String [] arr_location, Model model);
}
