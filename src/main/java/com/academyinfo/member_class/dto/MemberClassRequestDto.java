package com.academyinfo.member_class.dto;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member_class.domain.entity.MemberClassEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberClassRequestDto {
	private int mcindex;
	
	private MemberEntity mindex;
	private ClassEntity cindex;
	
	
	public MemberClassEntity toEntity() {
		MemberClassEntity memberClassEntity = MemberClassEntity.builder()
				.mcindex(mcindex)
				.mindex(mindex)
				.cindex(cindex)
				.build();
		
		return memberClassEntity;
	}
}
