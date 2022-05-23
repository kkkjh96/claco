package com.academyinfo.Files.freeboard.service;

import com.academyinfo.Files.freeboard.dto.FreeboardFilesRequestDto;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;

public interface FreeboardFilesService {
	public void save(BoardEntity freeboard, FreeboardFilesRequestDto fbfDto);
	public void delete(FreeboardFilesRequestDto fbfDto);
}
