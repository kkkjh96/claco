package com.academyinfo.Files.Class.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.common.entity.TimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="classfiles")
public class ClassFilesEntity extends TimeEntity {
	
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
	@JoinColumn(name = "cindex")
	private ClassEntity cindex;

	@Builder
	public ClassFilesEntity(int iindex, String originalFilename, String path, int size, boolean isDeleted, ClassEntity cindex){
		this.iindex = iindex;
		this.originalFilename = originalFilename;
		this.path = path;
		this.size = size;
		this.isDeleted = isDeleted;
		
		this.cindex = cindex;
	}
	
	// 게시글에 현재 파일이 있다면 추가
	public void setCindex(ClassEntity classEntity) {
		this.cindex = classEntity;
	}
}
