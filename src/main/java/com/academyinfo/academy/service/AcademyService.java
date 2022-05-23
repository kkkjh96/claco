package com.academyinfo.academy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.academy.dto.AcademyRequestDto;
import com.academyinfo.academy.dto.AcademyResponseDto;

public interface AcademyService {
	public AcademyResponseDto getPost(int aindex);
	public int savePost(String id, AcademyRequestDto academyRequestDto, List<MultipartFile> files) throws Exception;
	public int updatePost(String id, AcademyRequestDto academyRequestDto, List<MultipartFile> files) throws Exception;
    public void deletePost(int aindex);
}
