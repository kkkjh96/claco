package com.academyinfo.board.alertboard.dto;

import java.time.LocalDateTime;

import com.academyinfo.board.alertboard.domain.entity.AlertBoardEntity;
import com.academyinfo.member.domain.entity.MemberEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AlertBoardRequestDto {
	private int abindex;
	private String title;
	private String contents;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private int count;
	
	private MemberEntity mindex;
	
	
	public AlertBoardEntity toEntity() {
		AlertBoardEntity boardEntity = AlertBoardEntity.builder()
				.abindex(abindex)
				.title(title)
				.contents(contents)
				.count(count)
				.mindex(mindex)
				.build();
		
		return boardEntity;
	}
}
