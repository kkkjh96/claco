package com.academyinfo.member.controller;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;

import com.academyinfo.member.dto.MemberRequestDto;

public interface MemberController {

    // 회원가입 페이지
    public String dispSignup();
    
    // 회원가입 처리
    public String execSignup(@Valid MemberRequestDto memberDto, Errors errors, Model model);
    
    
    // 로그인 페이지
    public String dispLogin(@RequestParam(value = "error", required = false) String error, Model model);
    
    // 접근 거부 페이지
    public String dispDenied();

    // 마이페이지
    public String dispMyInfo(Model model);
    
    // 회원 정보 수정 페이지(일반,기업 공통)
    public String dispUserInfo(Model model);
    
    // 회원 정보 수정하기
    public String updateUserInfo(@Valid MemberRequestDto memberDto, Errors errors, Model model);
    
    // 일반회원이 찜한 강의 목록 페이지
    public String dispUserClassList();
    
    // 학원이 등록한 강의 목록 페이지
    public String dispCpnClassList(Model model);
    
    // 아이디 중복체크
    public boolean checkIdDuplicate(@RequestParam("id") String id);
}