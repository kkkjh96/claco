package com.academyinfo.Files.Class.handler;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;

public interface ClassFilesHandler {
	
	public List<ClassFilesEntity> parseFileInfo (ClassEntity classEntity, List<MultipartFile> multipartFiles) throws Exception;
}
