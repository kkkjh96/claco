package com.academyinfo.member_class.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member_class.domain.entity.MemberClassEntity;

public interface MemberClassRepository extends JpaRepository<MemberClassEntity, Integer> {
	List<MemberClassEntity> findByMindex(MemberEntity memberEntity);
	
	MemberClassEntity findByMindexAndCindex(MemberEntity memberEntity, ClassEntity classEntity);
}
