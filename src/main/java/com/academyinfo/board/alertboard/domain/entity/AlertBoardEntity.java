package com.academyinfo.board.alertboard.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.academyinfo.common.entity.TimeEntity;
import com.academyinfo.member.domain.entity.MemberEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "alertboard")
public class AlertBoardEntity extends TimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int abindex;
	
	@Column(length = 20, nullable = false) 
	private String title;
	
	// @Column(length = 200, nullable = false)
	@Column(columnDefinition = "TEXT", nullable = false)
	private String contents;
	
	@Column(columnDefinition = "integer default 0")
	private int count;
	
	// Member와 N:1조인
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "MINDEX")
		private MemberEntity mindex;
		
	/*
	 * @Column
	 * private String writer;
	 */
	
	@Builder
	public AlertBoardEntity(int abindex, String title, String contents, int count, MemberEntity mindex) {
		this.abindex = abindex;
		this.title = title;
		this.contents = contents;
		this.count = count;
		this.mindex = mindex;
	}
}
