package com.academyinfo.Files.Class.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Class.domain.repository.ClassRepository;
import com.academyinfo.Class.dto.ClassResponseDto;
import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;
import com.academyinfo.Files.Class.domain.repository.ClassFilesRepository;
import com.academyinfo.Files.Class.dto.ClassFilesRequestDto;
import com.academyinfo.Files.Class.dto.ClassFilesResponseDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClassFilesServiceImpl implements ClassFilesService {
	private ClassFilesRepository classFilesRepository;
	private ClassRepository classRepository;
	
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
	
	// 메인페이지 추천강의 이미지 N개 출력
	@Transactional(readOnly = true)
	public List<ClassFilesResponseDto> getImage(List<ClassResponseDto> listClass){
		List<ClassFilesResponseDto> classFilesDtoList = new ArrayList<>();
		
		if (listClass == null || listClass.isEmpty()) {
			return classFilesDtoList;
		}

	    for (ClassResponseDto classResponseDto : listClass) {
	    	if (classResponseDto.getIindex() != null && !classResponseDto.getIindex().isEmpty()) {
	    		classFilesDtoList.add(classResponseDto.getIindex().get(0));
	    	} else {
	    		Optional<ClassEntity> classEntityWrapper = classRepository.findById(classResponseDto.getCindex());
	    		ClassEntity classEntity = classEntityWrapper.get();
	    		
	    		ClassFilesResponseDto cfResponseDto = new ClassFilesResponseDto(new ClassFilesEntity().builder()
	    				.originalFilename("사진1.jpg")
	    				.path("src\\main\\resources\\static\\img\\temp\\사진1.jpg")
	    				.size(99391)
	    				.isDeleted(false)
	    				.cindex(classEntity)
	    				.build());
	    		classFilesDtoList.add(cfResponseDto);
	    	}
	    }
		
		return classFilesDtoList;
	}
	
	private ClassFilesResponseDto convertEntityToDto(ClassFilesEntity classFilesEntity) {
	    return new ClassFilesResponseDto(classFilesEntity);
	}
}
