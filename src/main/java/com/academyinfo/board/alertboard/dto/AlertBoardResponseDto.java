package com.academyinfo.board.alertboard.dto;

import java.time.LocalDateTime;

import com.academyinfo.board.alertboard.domain.entity.AlertBoardEntity;
import com.academyinfo.member.domain.entity.MemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AlertBoardResponseDto {
	private int abindex;
	private String title;
	private String contents;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private int count;
	
	private String mid;
	
	@Builder
	public AlertBoardResponseDto(int abindex, String title, String contents, int count, LocalDateTime createdDate, LocalDateTime modifiedDate, MemberEntity mindex) {
		this.abindex = abindex;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.count = count;
		this.mid = mindex.getId();
	}
	
	@Builder
	public AlertBoardResponseDto(AlertBoardEntity alertBoardEntity) {
		this.abindex = alertBoardEntity.getAbindex();
		this.title = alertBoardEntity.getTitle();
		this.contents = alertBoardEntity.getContents();
		this.createdDate = alertBoardEntity.getCreatedDate();
		this.modifiedDate = alertBoardEntity.getModifiedDate();
		this.count = alertBoardEntity.getCount();
		this.mid = alertBoardEntity.getMindex().getId();
	}
}
