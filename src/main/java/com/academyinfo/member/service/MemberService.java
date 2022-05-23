package com.academyinfo.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.academyinfo.member.dto.MemberRequestDto;
import com.academyinfo.member.dto.MemberResponseDto;

public interface MemberService extends UserDetailsService {
	//관리자 : 회원 정보 조회
	public List<MemberResponseDto> getMemberlist();
	
    // 회원가입 로직
    public Long joinUser(MemberRequestDto memberDto);
    
    // 회원수정 로직
    public Long updateUser(MemberRequestDto memberDto);
    
    // 로그인 로직
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException;
    
    // 회원정보 조회 로직
    public MemberResponseDto getMemberDto(String id);
    
    // 회원가입 시 유효성 체크 로직
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors);
    
    // 관리자 : 회원 삭제
    public void delete(Long mindex);
    
    // 아이디 중복 체크
    public boolean checkIdDuplicate(String id);
}
