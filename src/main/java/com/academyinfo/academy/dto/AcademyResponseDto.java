package com.academyinfo.academy.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Class.dto.ClassResponseDto;
import com.academyinfo.Files.academy.domain.entity.AcademyFilesEntity;
import com.academyinfo.Files.academy.dto.AcademyFilesResponseDto;
import com.academyinfo.academy.domain.entity.AcademyEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AcademyResponseDto {
	private int aindex;
	private String name;
	private int grade;
	private String location;
	private String address;
	private String phone;
	private String homepage;
	private String email;
	private String companynum;
	private String info;
	
	// member와 1:N 조인
	private Long mindex;
	private String mid;
	
	// class와 1:N 조인
	private List<ClassResponseDto> cindex;
	
	// image와 1:N 조인
	private List<AcademyFilesResponseDto> iindex;
	
	@Builder
	public AcademyResponseDto (int aindex, String name, int grade, String location, String address, String companynum, String phone, String homepage, String email, String info, Long mindex, String mid, List<ClassEntity> cindex, List<AcademyFilesEntity> iindex) {
		this.aindex = aindex;
		this.name = name;
		this.grade = grade;
		this.location = location;
		this.address = address;
		this.phone = phone;
		this.homepage = homepage;
		this.email = email;
		this.companynum = companynum;
		this.info = info;
		
		this.mindex = mindex;
		this.mid = mid;
		// 코멘트 List 타입을 DTO 클래스로 하여 엔티티간 무한참조 방지
		this.cindex = cindex.stream().map(ClassResponseDto::new).collect(Collectors.toList());
		this.iindex = iindex.stream().map(AcademyFilesResponseDto::new).collect(Collectors.toList());
	}
	
	@Builder
	public AcademyResponseDto(AcademyEntity academy) {
		this.aindex = academy.getAindex();
		this.name = academy.getName();
		this.grade = academy.getGrade();
		this.location = academy.getLocation();
		this.address = academy.getAddress();
		this.phone = academy.getPhone();
		this.homepage = academy.getHomepage();
		this.email = academy.getEmail();
		this.companynum = academy.getCompanynum();
		this.info = academy.getInfo();
		
		this.mindex = academy.getMindex().getMindex();
		this.mid = academy.getMindex().getId();
		
		// List 타입을 DTO 클래스로 하여 엔티티간 무한참조 방지
		this.cindex = academy.getCindex().stream().map(ClassResponseDto::new).collect(Collectors.toList());
		this.iindex = academy.getIindex().stream().map(AcademyFilesResponseDto::new).collect(Collectors.toList());
	}
}
