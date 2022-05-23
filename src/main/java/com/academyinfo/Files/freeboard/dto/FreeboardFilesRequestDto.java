package com.academyinfo.Files.freeboard.dto;

import java.time.LocalDateTime;

import com.academyinfo.Files.freeboard.domain.entity.FreeboardFilesEntity;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FreeboardFilesRequestDto {
	private int iindex;
	private String originalFilename;
	private String path;
	private int size;
	private boolean isDeleted;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	
	private BoardEntity fbindex;
	
	public FreeboardFilesEntity toEntity() {
		FreeboardFilesEntity imageEntity = FreeboardFilesEntity.builder()
				.iindex(iindex)
				.originalFilename(originalFilename)
				.path(path)
				.size(size)
				.isDeleted(isDeleted)
				.fbindex(fbindex)
				.build();
		
		return imageEntity;
	}
}
