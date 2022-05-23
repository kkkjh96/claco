package com.academyinfo.Files.Class.dto;

import java.time.LocalDateTime;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClassFilesRequestDto {
	private int iindex;
	private String originalFilename;
	private String path;
	private int size;
	private boolean isDeleted;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	private ClassEntity cindex;
	
	public ClassFilesEntity toEntity() {
		ClassFilesEntity classFilesEntity = ClassFilesEntity.builder()
				.iindex(iindex)
				.originalFilename(originalFilename)
				.path(path)
				.size(size)
				.isDeleted(isDeleted)
				.cindex(cindex)
				.build();
		
		return classFilesEntity;
	}
}
