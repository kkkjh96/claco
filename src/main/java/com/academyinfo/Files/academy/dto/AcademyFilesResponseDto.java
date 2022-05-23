package com.academyinfo.Files.academy.dto;

import java.time.LocalDateTime;

import com.academyinfo.Files.academy.domain.entity.AcademyFilesEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AcademyFilesResponseDto {
	private int iindex;
	private String originalFilename;
	private String path;
	private int size;
	private boolean isDeleted;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	// Academy 테이블과 조인
	private int aindex;
	
	@Builder
	public AcademyFilesResponseDto(int iindex, String originalFilename, String path, int size, boolean isDeleted, LocalDateTime createdDate, LocalDateTime modifiedDate, int aindex) {
		this.iindex = iindex;
		this.originalFilename = originalFilename;
		this.path = path;
		this.size = size;
		this.isDeleted = isDeleted;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		
		this.aindex = aindex;
	}
	
	@Builder
	public AcademyFilesResponseDto(AcademyFilesEntity academyFilesEntity) {
		this.iindex = academyFilesEntity.getIindex();
		this.originalFilename = academyFilesEntity.getOriginalFilename();
		this.path = academyFilesEntity.getPath();
		this.size = academyFilesEntity.getSize();
		this.isDeleted = academyFilesEntity.isDeleted();
		this.createdDate = academyFilesEntity.getCreatedDate();
		this.modifiedDate = academyFilesEntity.getModifiedDate();
		
		this.aindex = academyFilesEntity.getAindex().getAindex();
	}
}
