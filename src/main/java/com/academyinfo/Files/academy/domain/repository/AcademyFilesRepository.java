package com.academyinfo.Files.academy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academyinfo.Files.academy.domain.entity.AcademyFilesEntity;

@Repository
public interface AcademyFilesRepository extends JpaRepository<AcademyFilesEntity, Integer> {
	
}
