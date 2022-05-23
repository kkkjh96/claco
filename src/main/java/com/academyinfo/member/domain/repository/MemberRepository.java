package com.academyinfo.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.academyinfo.member.domain.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findById(@Param("id") String id);
	Optional<MemberEntity> findByMindex(Long mindex);
	
	// 아이디 중복 체크
	boolean existsById(String id);
}
