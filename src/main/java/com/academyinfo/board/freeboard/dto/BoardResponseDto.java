package com.academyinfo.board.freeboard.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.academyinfo.Files.freeboard.domain.entity.FreeboardFilesEntity;
import com.academyinfo.Files.freeboard.dto.FreeboardFilesResponseDto;
import com.academyinfo.board.comment.domain.entity.CommentEntity;
import com.academyinfo.board.comment.dto.CommentResponseDto;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;
import com.academyinfo.member.domain.entity.MemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BoardResponseDto {
	private int fbindex;
	private String title;
	private String contents;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private int count;
	private int report;
	
	private String mid;
	private List<FreeboardFilesResponseDto> iindex;
	private List<CommentResponseDto> cmtindex;
	
	
	@Builder
	public BoardResponseDto(int fbindex, String title, String contents, int count, int report, LocalDateTime createdDate, LocalDateTime modifiedDate, MemberEntity mindex, List<FreeboardFilesEntity> iindex, List<CommentEntity> cmtindex) {
		this.fbindex = fbindex;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.count = count;
		this.report = report;
		this.mid = mindex.getId();
		
		// List 타입을 DTO 클래스로 하여 엔티티간 무한참조 방지
		this.iindex = iindex.stream().map(FreeboardFilesResponseDto::new).collect(Collectors.toList());
		this.cmtindex = cmtindex.stream().map(CommentResponseDto::new).collect(Collectors.toList());
	}
	
	@Builder
	public BoardResponseDto(BoardEntity boardEntity) {
		this.fbindex = boardEntity.getFbindex();
		this.title = boardEntity.getTitle();
		this.contents = boardEntity.getContents();
		this.createdDate = boardEntity.getCreatedDate();
		this.modifiedDate = boardEntity.getModifiedDate();
		this.count = boardEntity.getCount();
		this.report = boardEntity.getReport();
		this.mid = boardEntity.getMindex().getId();
		
		// List 타입을 DTO 클래스로 하여 엔티티간 무한참조 방지
		this.iindex = boardEntity.getIindex().stream().map(FreeboardFilesResponseDto::new).collect(Collectors.toList());
		this.cmtindex = boardEntity.getCmtindex().stream().map(CommentResponseDto::new).collect(Collectors.toList());
	}
}
