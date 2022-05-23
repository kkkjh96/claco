package com.academyinfo.academy.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Files.academy.domain.entity.AcademyFilesEntity;
import com.academyinfo.member.domain.entity.MemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="academy")
public class AcademyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int aindex;
	
	@Column
	private String name;
	
	@Column
	@ColumnDefault("1000")
	private int grade;
	
	@Column
	private String address;
	
	@Column
	private String location;
	
	@Column
	private String phone;
	
	@Column
	private String homepage;
	
	@Column
	private String email;
	
	@Column
	private String companynum;
	
	@Column
	private String info;
	
	@ManyToOne
	@JoinColumn(name = "mindex")
	private MemberEntity mindex;
	
	@Column
	@OneToMany(mappedBy = "aindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<ClassEntity> cindex;
	
	@Column
	@OneToMany(mappedBy = "aindex", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AcademyFilesEntity> iindex;
	
	@Builder
	public AcademyEntity (int aindex, String name, int grade, String location, String address, String companynum, String phone, String homepage, String email, String info, MemberEntity mindex, List<ClassEntity> cindex, List<AcademyFilesEntity> iindex) {
		this.aindex = aindex;
		this.name = name;
		this.grade = grade;
		this.location = location;
		this.address = address;
		this.phone = phone;
		this.homepage = homepage;
		this.companynum = companynum;
		this.email = email;
		this.info = info;
		this.mindex = mindex;
		this.cindex = cindex;
		this.iindex = iindex;
	}
	
	public void addAcademyFilesEntity(AcademyFilesEntity afEntity) {
		this.iindex.add(afEntity);
		
		if (afEntity.getAindex() != this) {
			afEntity.setAindex(this);
		}
	}
}
