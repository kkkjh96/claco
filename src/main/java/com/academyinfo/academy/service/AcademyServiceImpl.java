package com.academyinfo.academy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Files.academy.domain.entity.AcademyFilesEntity;
import com.academyinfo.Files.academy.domain.repository.AcademyFilesRepository;
import com.academyinfo.Files.academy.handler.AcademyFilesHandler;
import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.academy.domain.repository.AcademyRepository;
import com.academyinfo.academy.dto.AcademyRequestDto;
import com.academyinfo.academy.dto.AcademyResponseDto;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member.domain.repository.MemberRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AcademyServiceImpl implements AcademyService {
	private AcademyRepository academyRepository;
	private MemberRepository memberRepository;
	private AcademyFilesHandler academyFilesHandler;
	private AcademyFilesRepository academyFilesRepository;
	
	// builder로 게시된 글 상세보기
	@Transactional
	public AcademyResponseDto getPost(int aindex) {
	    Optional<AcademyEntity> academyEntityWrapper = academyRepository.findById(aindex);
	    // findbyId : Primary key 값을 where 조건으로 데이터를 DB로부터 가져오는 method
	    AcademyEntity academyEntity = academyEntityWrapper.get();
	    // Wrapper.get()로 academyEntity 객체를 가져옴

	    // academyDto academyDTO = convertEntityToDto(academyEntity);
	    AcademyResponseDto academyDTO = convertEntityToDto(academyEntity);
	    
	    return academyDTO;
	}
	
	// insert
	@Transactional
	public int savePost(String id, AcademyRequestDto academyRequestDto, List<MultipartFile> files) throws Exception {
		Optional<MemberEntity> memberEntityWrapper = memberRepository.findById(id);
        MemberEntity member = memberEntityWrapper.get();
        
        List<AcademyFilesEntity> afEntityList = academyFilesHandler.parseFileInfo(null, files);
        
        academyRequestDto.setMindex(member); // member 외래키 설정
        academyRequestDto.setIindex(afEntityList); // academyFiles 외래키 설정
        AcademyEntity academyEntity = academyRequestDto.toEntity();
        
        if (!afEntityList.isEmpty()) {
        	for (AcademyFilesEntity afEntity : afEntityList) {
        		afEntity.setAindex(academyEntity);
        		academyFilesRepository.save(afEntity);
        	}
        }
        
        academyRequestDto.setMindex(member); // member 외래키 설정
		
		return academyRepository.save(academyEntity).getAindex();
	}
	
	// update
		@Transactional
		public int updatePost(String id, AcademyRequestDto academyRequestDto, List<MultipartFile> files) throws Exception {
			Optional<MemberEntity> memberEntityWrapper = memberRepository.findById(id);
	        MemberEntity member = memberEntityWrapper.get();
	        
	        // 기존 데이터베이스에 저장된 파일 목록 구하기
	        Optional<AcademyEntity> academyEntityWrapper = academyRepository.findById(academyRequestDto.getAindex()); 
	        List<AcademyFilesEntity> currentList = academyEntityWrapper.get().getIindex();
	        
	        // 이번에 입력받은 파일 목록
	        // List<MultipartFile> files; ( 매개변수 )
	        
	        // 게시글 수정 후 추가될 파일 목록 저장할 변수 선언
	        List<MultipartFile> addFileList = new ArrayList<>();
	        
	        /*
	         *  데이터베이스(X)  전달파일 (X)  >> 스킵
	         *  데이터베이스(X)  전달파일 (O)  >> 추가
	         *  데이터베이스(O)  전달파일 (X)  >> 삭제
	         *  데이터베이스(O)  전달파일 (O)  >> 스킵
	         */
	        
	        
	        // 변동된 파일만 담기
	        if (CollectionUtils.isEmpty(currentList)) { // 데이터베이스(X)
	        	if (!CollectionUtils.isEmpty(files)) // 전달파일(O)
	        		for (MultipartFile multipartFile : files) {
	        			addFileList.add(multipartFile); // 수정 후 추가될 파일 목록에 추가
	        		}
	        } else { // 기존에 1장 이상의 파일 존재(데이터베이스(O))
	        	if (CollectionUtils.isEmpty(files)) { // 전달파일 전체(X)
	        		for(AcademyFilesEntity file : currentList) {
	        			academyFilesRepository.delete(file);  // 데이터베이스 파일 모두 삭제
	        		}
	        	} else { // 전달된 파일이 1장 이상 존재(전달파일(ㅁ))
	        		/*
	        		 * 기존 파일과 전달된 파일의 원본이름 비교 ( 첨부파일 리스트는 빈 리스트임 )
	        		 * 기존 파일 (O) / 전달된 파일에 동일 파일 (O)   => 스킵
	        		 * 기존 파일 (O) / 전달된 파일에 동일 파일 (X)   => 기존 파일 삭제
	        		 * 기존 파일 (X) / 새로 전달된 파일이 있음 (O)   => 첨부파일 리스트에 추가
	        		 */
	        		// 데이터베이스 내 파일 원본 이름을 담을 리스트 (이미 데이터베이스에 존재하는지 체크)
	        		List<String> originalNameList = new ArrayList<>();
	        		
	        		// 파일 원본명을 데이터베이스에서 추출
	        		for (AcademyFilesEntity file : currentList) {
	        			
	        			// 해당 파일을 검색
	        			Optional<AcademyFilesEntity> afEntityWrapper = academyFilesRepository.findById(file.getIindex());
	        			AcademyFilesEntity afEntity = afEntityWrapper.get();
	        			
	        			// 파일의 원본명
	        			String originalName = afEntity.getOriginalFilename();
	        			
	        			if (!files.contains(originalName)) { // 데이터베이스 (O) / 전달파일 (X)
	        				academyFilesRepository.delete(file); // 파일 삭제
	        			} else { // 데이터베이스 (O) / 전달파일 (O)
	        				originalNameList.add(originalName); // 이름 리스트에 추가
	        			}
	        		}
	        		
	        		// 전달파일 검사
	        		for (MultipartFile file : files) {
	        			String originalName = file.getOriginalFilename(); // 전달파일 원본명
	        			
	        			if (!originalNameList.contains(originalName)) { // 데이터베이스 (X) / 전달파일 (O)?
	        				addFileList.add(file); // 파일 목록에 추가
	        			}
	        		}
	        	}
	        }
	        
	        academyRequestDto.setMindex(member); // member 외래키 설정
	        AcademyEntity academyEntity = academyRequestDto.toEntity();
	        // 새로 데이터베이스에 파일로 저장할 리스트
	        List<AcademyFilesEntity> afEntityList = academyFilesHandler.parseFileInfo(academyEntity, addFileList);
	        
	        if (!afEntityList.isEmpty()) { // 새로 추가된 파일들 데이터베이스에 업로드
	        	for (AcademyFilesEntity afEntity : afEntityList) {
	        		academyFilesRepository.save(afEntity);
	        	}
	        }
	        
			return academyRepository.save(academyEntity).getAindex();
		}
	
	// delete
	@Transactional
    public void deletePost(int aindex) {
        academyRepository.deleteById(aindex);
        // deleteById : Primary key 값을 where 조건으로 데이터를 DB에서 삭제
    }
	
	private AcademyResponseDto convertEntityToDto(AcademyEntity academyEntity) {
		AcademyResponseDto academyResponseDto = new AcademyResponseDto(academyEntity);		
		return academyResponseDto;
	}
}
