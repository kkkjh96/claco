package com.academyinfo.member_class.service;

import java.util.List;

import com.academyinfo.member_class.dto.MemberClassRequestDto;
import com.academyinfo.member_class.dto.MemberClassResponseDto;

public interface MemberClassService {
	public List<MemberClassResponseDto> getList(String id);
	public void save(String id, int no, MemberClassRequestDto dto);
}
