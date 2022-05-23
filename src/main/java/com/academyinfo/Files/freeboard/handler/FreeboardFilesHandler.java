package com.academyinfo.Files.freeboard.handler;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Files.freeboard.domain.entity.FreeboardFilesEntity;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;

public interface FreeboardFilesHandler {
	// public List<FreeboardFilesEntity> parseFileInfo (List<MultipartFile> multipartFiles) throws Exception;
	
	public List<FreeboardFilesEntity> parseFileInfo (BoardEntity boardEntity, List<MultipartFile> multipartFiles) throws Exception;
}
