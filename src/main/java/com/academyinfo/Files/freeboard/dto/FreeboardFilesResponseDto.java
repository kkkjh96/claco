package com.academyinfo.Files.freeboard.dto;

import java.time.LocalDateTime;

import com.academyinfo.Files.freeboard.domain.entity.FreeboardFilesEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class FreeboardFilesResponseDto {
	private int iindex;
	private String originalFilename;
	private String path;
	private int size;
	private boolean isDeleted;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	// freeboard 테이블과 조인
	private int fbindex;
	
	@Builder
	public FreeboardFilesResponseDto(int iindex, String originalFilename, String path, int size, boolean isDeleted, LocalDateTime createdDate, LocalDateTime modifiedDate, int fbindex) {
		this.iindex = iindex;
		this.originalFilename = originalFilename;
		this.path = path;
		this.size = size;
		this.isDeleted = isDeleted;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		
		this.fbindex = fbindex;
	}
	
	@Builder
	public FreeboardFilesResponseDto(FreeboardFilesEntity freeboardFilesEntity) {
		this.iindex = freeboardFilesEntity.getIindex();
		this.originalFilename = freeboardFilesEntity.getOriginalFilename();
		this.path = freeboardFilesEntity.getPath();
		this.size = freeboardFilesEntity.getSize();
		this.isDeleted = freeboardFilesEntity.isDeleted();
		this.createdDate = freeboardFilesEntity.getCreatedDate();
		this.modifiedDate = freeboardFilesEntity.getModifiedDate();
		
		this.fbindex = freeboardFilesEntity.getFbindex().getFbindex();
	}
}
