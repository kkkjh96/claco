package com.academyinfo.Class.dto;

import java.sql.Date;
import java.util.List;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;
import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.member_class.domain.entity.MemberClassEntity;
import com.academyinfo.review.domain.entity.ReviewEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClassRequestDto {
	
	private int cindex;
	private String name;
	private String category;
	private int price;
	private double score;
	private String status;
	private Date startperiod;
	private Date endperiod;
	private String info;
	
	private AcademyEntity aindex;
	/*
	 * 
	 * private String aname;
	 * private int agrade;
	 * private String alocation;
	 * 
	 */
	
	private List<ClassFilesEntity> iindex;
	
	private List<ReviewEntity> rvindex;
	
	private List<MemberClassEntity> mcindex;
	
	public ClassEntity toEntity() {
		ClassEntity classEntity = ClassEntity.builder()
				.cindex(cindex)
				.name(name)
				.category(category)
				.price(price)
				.score(score)
				.status(status)
				.startperiod(startperiod)
				.endperiod(endperiod)
				.info(info)
				.iindex(iindex)
				.aindex(aindex)
				.rvindex(rvindex)
				.mcindex(mcindex)
				.build();
		
		return classEntity;
	}
}
