package com.academyinfo.Files.freeboard.domain.entity;

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

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="freeboardfiles")
public class FreeboardFilesEntity extends TimeEntity {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int iindex;
	
	@Column
	private String originalFilename;
	
	@Column
	private String path;
	
	@Column
	private int size;
	
	@Column
	private boolean isDeleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fbindex")
	private BoardEntity fbindex;

	@Builder
	public FreeboardFilesEntity(int iindex, String originalFilename, String path, int size, boolean isDeleted, BoardEntity fbindex){
		this.iindex = iindex;
		this.originalFilename = originalFilename;
		this.path = path;
		this.size = size;
		this.isDeleted = isDeleted;
		
		this.fbindex = fbindex;
	}
	
	// 게시글에 현재 파일이 있다면 추가
	public void setFbindex(BoardEntity boardEntity) {
		this.fbindex = boardEntity;
	}
}
