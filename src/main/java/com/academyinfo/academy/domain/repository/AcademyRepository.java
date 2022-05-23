package com.academyinfo.academy.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.academyinfo.academy.domain.entity.AcademyEntity;

public interface AcademyRepository extends JpaRepository<AcademyEntity, Integer> {

}
