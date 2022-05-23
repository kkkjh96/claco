package com.academyinfo.Class.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Class.dto.ClassRequestDto;
import com.academyinfo.Class.dto.ClassResponseDto;

public interface ClassService {
	public List<ClassResponseDto> getClasslist();
	public List<ClassResponseDto> getRecommendClass();
	
	// public List<ClassResponseDto> search(String keyword);
	public List<ClassResponseDto> search(Integer pageNum, String keyword);
	
	// public List<ClassResponseDto> searchFilter(String keyword, String[] arr_location);
	public List<ClassResponseDto> searchFilter(Integer pageNum, String keyword, String[] arr_location);
	/*
	public List<ClassResponseDto> testFilter_S(String keyword, String[] arr_location_S);
	public List<ClassResponseDto> testFilter_B(String keyword, String[] arr_location_B);
	*/
	public int updateStatus(int cindex);
	public ClassResponseDto getPost(int cindex);
	public int savePost(int aindex, ClassRequestDto classRequestDto, List<MultipartFile> files) throws Exception;
	public int updatePost(int aindex, ClassRequestDto classRequestDto, List<MultipartFile> files) throws Exception;
	public void deletePost(int cindex);
	
	public int getClassCount(String keyword, String[] arr_location);
	
	
	public Integer[] getPageList(Integer curPageNum, String keyword, String[] arr_location);
	public int getPrev(Integer pageNum, String keyword, String[] arr_location);
	public int getNext(Integer pageNum, String keyword, String[] arr_location);
}
