package com.academyinfo.Files.freeboard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academyinfo.Files.freeboard.domain.entity.FreeboardFilesEntity;
import com.academyinfo.Files.freeboard.domain.repository.FreeboardFilesRepository;
import com.academyinfo.Files.freeboard.dto.FreeboardFilesRequestDto;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FreeboardFilesServiceImpl implements FreeboardFilesService {
	private FreeboardFilesRepository freeboardFilesRepository;
	
	@Transactional
	public void save(BoardEntity freeboard, FreeboardFilesRequestDto fbfDto) {
		fbfDto.setFbindex(freeboard);
		
		FreeboardFilesEntity fbfEntity = fbfDto.toEntity();
		freeboardFilesRepository.save(fbfEntity);
	}
	
	@Transactional
	public void delete(FreeboardFilesRequestDto fbfDto) {
		FreeboardFilesEntity fbfEntity = fbfDto.toEntity();
		freeboardFilesRepository.delete(fbfEntity);
	}
}
