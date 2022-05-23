package com.academyinfo.Files.academy.dto;

import java.time.LocalDateTime;

import com.academyinfo.Files.academy.domain.entity.AcademyFilesEntity;
import com.academyinfo.academy.domain.entity.AcademyEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AcademyFilesRequestDto {
	private int iindex;
	private String originalFilename;
	private String path;
	private int size;
	private boolean isDeleted;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	private AcademyEntity aindex;
	
	public AcademyFilesEntity toEntity() {
		AcademyFilesEntity academyFilesEntity = AcademyFilesEntity.builder()
				.iindex(iindex)
				.originalFilename(originalFilename)
				.path(path)
				.size(size)
				.isDeleted(isDeleted)
				.aindex(aindex)
				.build();
		
		return academyFilesEntity;
	}
}
