package com.academyinfo.board.comment.dto;

import java.time.LocalDateTime;

import com.academyinfo.board.comment.domain.entity.CommentEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentResponseDto {
	private int cmtindex;
	private String comment;
	private int report;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private int fbindex;
	private String fbtitle;
	private String mid;
	
	@Builder
	public CommentResponseDto(CommentEntity comment) {
			this.cmtindex = comment.getCmtindex();
			this.comment = comment.getComment();
			this.report = comment.getReport();
			this.createdDate = comment.getCreatedDate();
			this.modifiedDate = comment.getModifiedDate();
			this.fbindex = comment.getFbindex().getFbindex();
			this.fbtitle = comment.getFbindex().getTitle();
			this.mid = comment.getMindex().getId();
	}
}
