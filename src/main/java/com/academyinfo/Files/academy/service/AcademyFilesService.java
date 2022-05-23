package com.academyinfo.Files.academy.service;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Files.Class.dto.ClassFilesRequestDto;

public interface AcademyFilesService {
	public void save(ClassEntity classFileEntity, ClassFilesRequestDto cfDto);
	public void delete(ClassFilesRequestDto cfDto);
}
