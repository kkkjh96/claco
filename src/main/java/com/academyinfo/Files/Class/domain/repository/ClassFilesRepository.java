package com.academyinfo.Files.Class.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;

@Repository
public interface ClassFilesRepository extends JpaRepository<ClassFilesEntity, Integer> {
	
}
