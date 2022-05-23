package com.academyinfo.member.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.academyinfo.common.entity.Role;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member.domain.repository.MemberRepository;
import com.academyinfo.member.dto.MemberRequestDto;
import com.academyinfo.member.dto.MemberResponseDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private MemberRepository memberRepository;

    @Transactional(readOnly = true)
	public List<MemberResponseDto> getMemberlist() {
		/* list를 읽어와서 표시
		 */
		List<MemberEntity> memberEntities = memberRepository.findAll(); 
		List<MemberResponseDto> memberDtoList = new ArrayList<>();
		
		for ( MemberEntity memberEntity : memberEntities ) {  
			// 정규식 for
			// 반복문으로 게시판 구성요소들을 불러와 리스트에 추가
			memberDtoList.add(this.convertEntityToDto(memberEntity));
		}
		
		return memberDtoList;
	}
    
    // 회원가입 로직
    @Transactional
    public Long joinUser(MemberRequestDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPwd(passwordEncoder.encode(memberDto.getPwd()));

        return memberRepository.save(memberDto.toEntity()).getMindex();
    }
    
    // 회원수정 로직
    @Transactional
    public Long updateUser(MemberRequestDto memberDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPwd(passwordEncoder.encode(memberDto.getPwd()));
        
        return memberRepository.save(memberDto.toEntity()).getMindex();
    }
    
    // 로그인 로직
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<MemberEntity> userEntityWrapper = memberRepository.findById(id);
        MemberEntity userEntity = userEntityWrapper.get();
        String role = userEntity.getRole();

        List<GrantedAuthority> authorities = new ArrayList<>();		// 권한 인스턴스 생성
        
        // 회원가입 할때 추가된 권한 테이블을 조회하여 각각의 조건에 맞게 권한 부여
        if (("admin").equals(id)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else if (("ROLE_MEMBER").equals(role)) {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        } else if (("ROLE_COMPANY").equals(role)) {
        	authorities.add(new SimpleGrantedAuthority(Role.COMPANY.getValue()));
        }

        return new User(userEntity.getId(), userEntity.getPwd(), authorities);
    }
    
    // 회원정보 조회 로직
    @Transactional(readOnly=true)
    public MemberResponseDto getMemberDto(String id) {
    	Optional<MemberEntity> userEntityWrapper = memberRepository.findById(id);
        MemberEntity userEntity = userEntityWrapper.get();
        
    	return this.convertEntityToDto(userEntity);
    }
    
    // 회원가입 시 유효성 체크 로직
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
	    Map<String, String> validatorResult = new HashMap<>();
	    
	    /* 유효성 검사에 실패한 필드 목록을 받음 */
	    for (FieldError error : errors.getFieldErrors()) {
		    String validKeyName = String.format("valid_%s", error.getField());
		    validatorResult.put(validKeyName, error.getDefaultMessage());
	    }
	    
	    return validatorResult;
    }
    
    // 관리자 : 회원 삭제
	@Transactional
	public void delete(Long mindex) {
        memberRepository.deleteById(mindex);
        // deleteById : Primary key 값을 where 조건으로 데이터를 DB에서 삭제
    }
	
	// 아이디 중복 체크
 	public boolean checkIdDuplicate(String id) {
 		return memberRepository.existsById(id);
 	}
    
    private MemberResponseDto convertEntityToDto(MemberEntity memberEntity) {
	    return new MemberResponseDto(memberEntity);
	}
}