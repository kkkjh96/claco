package com.academyinfo.Files.Class.dto;

import java.time.LocalDateTime;

import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ClassFilesResponseDto {
	private int iindex;
	private String originalFilename;
	private String path;
	private int size;
	private boolean isDeleted;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	// Class 테이블과 조인
	private int cindex;
	
	@Builder
	public ClassFilesResponseDto(int iindex, String originalFilename, String path, int size, boolean isDeleted, LocalDateTime createdDate, LocalDateTime modifiedDate, int cindex) {
		this.iindex = iindex;
		this.originalFilename = originalFilename;
		this.path = path;
		this.size = size;
		this.isDeleted = isDeleted;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		
		this.cindex = cindex;
	}
	
	@Builder
	public ClassFilesResponseDto(ClassFilesEntity classFilesEntity) {
		this.iindex = classFilesEntity.getIindex();
		this.originalFilename = classFilesEntity.getOriginalFilename();
		this.path = classFilesEntity.getPath();
		this.size = classFilesEntity.getSize();
		this.isDeleted = classFilesEntity.isDeleted();
		this.createdDate = classFilesEntity.getCreatedDate();
		this.modifiedDate = classFilesEntity.getModifiedDate();
		
		this.cindex = classFilesEntity.getCindex().getCindex();
	}
}
