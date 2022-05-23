package com.academyinfo.Files.academy.handler;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Files.academy.domain.entity.AcademyFilesEntity;
import com.academyinfo.academy.domain.entity.AcademyEntity;

public interface AcademyFilesHandler {
	
	public List<AcademyFilesEntity> parseFileInfo (AcademyEntity academyEntity, List<MultipartFile> multipartFiles) throws Exception;
}
