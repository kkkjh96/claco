package com.academyinfo.member_class.dto;

import java.sql.Date;

import com.academyinfo.member_class.domain.entity.MemberClassEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MemberClassResponseDto {
	private int mcindex;
	
	// member >> class
	
	private Long mindex;
	private String mid;
	
	
	// class >> member
	
	private int cindex;
	private String cname;
	private String ccategory;
	private int cprice;
	private double cscore;
	private Date cstartperiod;
	private Date cendperiod;
	
	private int aindex;
	private String aname;
	
	@Builder
	public MemberClassResponseDto(int mcindex, Long mindex, String mid, int cindex, String cname, String ccategory, int cprice, double cscore, Date cstartperiod, Date cendperiod, int aindex, String aname) {
		this.mcindex = mcindex;
		this.mindex = mindex;
		this.mid = mid;
		this.cindex = cindex;
		this.cname = cname;
		this.ccategory = ccategory;
		this.cprice = cprice;
		this.cscore = cscore;
		this.cstartperiod = cstartperiod;
		this.cendperiod = cendperiod;
		this.aindex = aindex;
		this.aname = aname;
	}
	
	@Builder
	public MemberClassResponseDto(MemberClassEntity memberClassEntity) {
		this.mcindex = memberClassEntity.getMcindex();
		this.mindex = memberClassEntity.getMindex().getMindex();
		this.mid = memberClassEntity.getMindex().getId();
		this.cindex = memberClassEntity.getCindex().getCindex();
		this.cname = memberClassEntity.getCindex().getName();
		this.ccategory = memberClassEntity.getCindex().getCategory();
		this.cprice = memberClassEntity.getCindex().getPrice();
		this.cscore = memberClassEntity.getCindex().getScore();
		this.cstartperiod = memberClassEntity.getCindex().getStartperiod();
		this.cendperiod = memberClassEntity.getCindex().getEndperiod();
		this.aindex = memberClassEntity.getCindex().getAindex().getAindex();
		this.aname = memberClassEntity.getCindex().getAindex().getName();
	}
}
