package com.academyinfo.Files.academy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;
import com.academyinfo.Files.Class.domain.repository.ClassFilesRepository;
import com.academyinfo.Files.Class.dto.ClassFilesRequestDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AcademyFilesServiceImpl implements AcademyFilesService {
	private ClassFilesRepository classFilesRepository;
	
	@Transactional
	public void save(ClassEntity classEntity, ClassFilesRequestDto cfDto) {
		cfDto.setCindex(classEntity);
		
		ClassFilesEntity cfEntity = cfDto.toEntity();
		classFilesRepository.save(cfEntity);
	}
	
	@Transactional
	public void delete(ClassFilesRequestDto cfDto) {
		ClassFilesEntity cfEntity = cfDto.toEntity();
		classFilesRepository.delete(cfEntity);
	}
}
