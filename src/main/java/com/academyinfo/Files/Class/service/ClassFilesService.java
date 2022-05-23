package com.academyinfo.Files.Class.service;

import java.util.List;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Class.dto.ClassResponseDto;
import com.academyinfo.Files.Class.dto.ClassFilesRequestDto;
import com.academyinfo.Files.Class.dto.ClassFilesResponseDto;

public interface ClassFilesService {
	public void save(ClassEntity classFileEntity, ClassFilesRequestDto cfDto);
	public List<ClassFilesResponseDto> getImage(List<ClassResponseDto> listClass);
	public void delete(ClassFilesRequestDto cfDto);
	// public List<ClassFilesResponseDto> getImage();
}
