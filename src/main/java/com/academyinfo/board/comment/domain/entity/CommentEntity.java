package com.academyinfo.board.comment.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.academyinfo.board.freeboard.domain.entity.BoardEntity;
import com.academyinfo.common.entity.TimeEntity;
import com.academyinfo.member.domain.entity.MemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "comments")
public class CommentEntity extends TimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cmtindex;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String comment; // 댓글 내용
	
	/* 신고 카운트 */
	@Column(columnDefinition = "integer default 0")
	private int report;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fbindex")
	private BoardEntity fbindex;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mindex")
	private MemberEntity mindex;
	
	@Builder
	public CommentEntity(int cmtindex, String comment, int report, LocalDateTime createdDate, LocalDateTime modifiedDate, BoardEntity fbindex, MemberEntity mindex) {
		this.cmtindex = cmtindex;
		this.comment = comment;
		this.report = report;
		this.fbindex = fbindex;
		this.mindex = mindex;
	}
	
	
	
	// 댓글 수정 기능 구현을 위한 comment setter
	public void update(String comment) {
			this.comment = comment;
	}

}
