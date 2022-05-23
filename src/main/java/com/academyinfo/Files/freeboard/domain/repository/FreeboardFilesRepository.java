package com.academyinfo.Files.freeboard.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academyinfo.Files.freeboard.domain.entity.FreeboardFilesEntity;

@Repository
public interface FreeboardFilesRepository extends JpaRepository<FreeboardFilesEntity, Integer> {
	
}
