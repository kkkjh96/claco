package com.academyinfo.member_class.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Class.domain.repository.ClassRepository;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member.domain.repository.MemberRepository;
import com.academyinfo.member_class.domain.entity.MemberClassEntity;
import com.academyinfo.member_class.domain.repository.MemberClassRepository;
import com.academyinfo.member_class.dto.MemberClassRequestDto;
import com.academyinfo.member_class.dto.MemberClassResponseDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberClassServiceImpl implements MemberClassService {
	private MemberClassRepository memberClassRepository;
	private MemberRepository memberRepository;
	private ClassRepository classRepository;
	
	@Transactional(readOnly = true)
	public List<MemberClassResponseDto> getList(String id) {
		Optional<MemberEntity> memberEntityWrapper = memberRepository.findById(id);
		MemberEntity memberEntity = memberEntityWrapper.get();
		
		List<MemberClassEntity> memberClassEntities = memberClassRepository.findByMindex(memberEntity);
		List<MemberClassResponseDto> memberClassDtoList = new ArrayList<>();
		
		for ( MemberClassEntity memberClassEntity : memberClassEntities ) {  
			// 정규식 for
			// 반복문으로 게시판 구성요소들을 불러와 리스트에 추가
			memberClassDtoList.add(this.convertEntityToDto(memberClassEntity));
		}
		
		return memberClassDtoList;
	}
	
	// 즐겨찾기 버튼 생성/삭제
	@Transactional
	public void save(String id, int no, MemberClassRequestDto dto) {
		Optional<MemberEntity> memberEntityWrapper = memberRepository.findById(id);
		MemberEntity memberEntity = memberEntityWrapper.get();
		
		Optional<ClassEntity> classEntityWrapper = classRepository.findById(no);
		ClassEntity classEntity = classEntityWrapper.get();
		
		MemberClassEntity entity = memberClassRepository.findByMindexAndCindex(memberEntity, classEntity);
		
		if (entity != null) {
			memberClassRepository.delete(entity);
			return ;
		}
		
		dto.setMindex(memberEntity);
		dto.setCindex(classEntity);
		
		memberClassRepository.save(dto.toEntity());
	}
	
	private MemberClassResponseDto convertEntityToDto(MemberClassEntity memberClassEntity) {
	    return new MemberClassResponseDto(memberClassEntity);
	}
}
