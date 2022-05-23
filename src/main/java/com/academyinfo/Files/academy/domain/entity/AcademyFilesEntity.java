package com.academyinfo.Files.academy.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.common.entity.TimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="academyfiles")
public class AcademyFilesEntity extends TimeEntity {
	
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
	@JoinColumn(name = "aindex")
	private AcademyEntity aindex;

	@Builder
	public AcademyFilesEntity(int iindex, String originalFilename, String path, int size, boolean isDeleted, AcademyEntity aindex){
		this.iindex = iindex;
		this.originalFilename = originalFilename;
		this.path = path;
		this.size = size;
		this.isDeleted = isDeleted;
		
		this.aindex = aindex;
	}
	
	// 게시글에 현재 파일이 있다면 추가
	public void setAindex(AcademyEntity academyEntity) {
		this.aindex = academyEntity;
	}
}
