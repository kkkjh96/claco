package com.academyinfo.member_class.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.member.domain.entity.MemberEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "memberclass")
public class MemberClassEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mcindex;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mindex")
	private MemberEntity mindex;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cindex")
	private ClassEntity cindex;
	
	
	@Builder
	public MemberClassEntity(int mcindex, MemberEntity mindex, ClassEntity cindex) {
		this.mcindex = mcindex;
		this.mindex = mindex;
		this.cindex = cindex;
	}
}
