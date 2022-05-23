package com.academyinfo.Class.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Class.domain.repository.ClassRepository;
import com.academyinfo.Class.dto.ClassRequestDto;
import com.academyinfo.Class.dto.ClassResponseDto;
import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;
import com.academyinfo.Files.Class.domain.repository.ClassFilesRepository;
import com.academyinfo.Files.Class.handler.ClassFilesHandler;
import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.academy.domain.repository.AcademyRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClassServiceImpl implements ClassService {
	private ClassRepository classRepository;
	private AcademyRepository academyRepository;
	private ClassFilesHandler classFilesHandler;
	private ClassFilesRepository classFilesRepository;
	
	private static final int RECOMMAND_INT = 4; // 추천페이지 출력
	private static final int BLOCK_PAGE_NUM_COUNT = 5; // 표시되는 페이지 번호
	private static final int PAGE_POST_COUNT = 20; // 한 페이지에 표시되는 게시글 수
	
	//select * from class;
	@Transactional(readOnly = true)
	public List<ClassResponseDto> getClasslist(){
		List<ClassEntity> classEntities = classRepository.findAll();
		List<ClassResponseDto> classDtoList = new ArrayList<>();
		
		if (classEntities.isEmpty()) {
			return classDtoList;
		}
		
		for (ClassEntity classEntity : classEntities) {
			classDtoList.add(this.convertEntityToDto(classEntity));
		}
		
		return classDtoList;
	}
	
	
	
	/*추천강의(c_status가 approval이면서 점수가 제일 높은 4개의 강의를 최근등록순) - 강의내용*/
	@Transactional(readOnly = true)
	public List<ClassResponseDto> getRecommendClass(){
		Sort sort = Sort.by(
				Sort.Order.desc("score"),
				Sort.Order.desc("cindex")
				);
		String status = "approval";
		
		List<ClassEntity> classEntities = classRepository.findByStatus(status, sort);
		List<ClassResponseDto> recommandClassList = new ArrayList<>();
		
		if (classEntities.isEmpty()) {
			return recommandClassList;
		}
		
		for(int i=0; i < RECOMMAND_INT; i++) {
			recommandClassList.add(this.convertEntityToDto(classEntities.get(i)));
		}
		
		return recommandClassList;
	}
	
	/* 키워드 검색 : 체크박스 선택 없을 시 */
	@Transactional(readOnly = true)
	public List<ClassResponseDto> search(Integer pageNum, String keyword){
		String status = "approval";
		Page<ClassEntity> page = classRepository.findByNameContainingAndStatus(keyword, status, PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "startperiod")));
		
		List<ClassEntity> classEntities = page.getContent();
		List<ClassResponseDto> classDtoList = new ArrayList<>();
		
		if (classEntities.isEmpty()) {
			return classDtoList;
		}
		
		for (ClassEntity classEntity : classEntities) {
			classDtoList.add(this.convertEntityToDto(classEntity));
		}
		
		return classDtoList;
	}
	
	/* 키워드 검색 : 체크박스 선택 시 */
	@Transactional(readOnly = true)
	public List<ClassResponseDto> searchFilter(Integer pageNum, String keyword, String[] arr_location) {
		Page<ClassEntity> page = classRepository.selectFilter(keyword, arr_location, PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "startperiod")));
		
		List<ClassEntity> classEntities = page.getContent();
		
		List<ClassResponseDto> classDtoList = new ArrayList<>();
		
		if (classEntities.isEmpty()) {
			return classDtoList;
		}
		
		for (ClassEntity classEntity : classEntities) {
			classDtoList.add(this.convertEntityToDto(classEntity));
		}
		
		return classDtoList;
	}
	
	// 게시글 갯수 카운트
	@Transactional
    public int getClassCount(String keyword, String[] arr_location) {
		if (keyword.equals("") && arr_location.length == 0) { // 검색 중일 시 검색중인 페이지 갯수만 반환하도록
			return (int) classRepository.count();	
		} else if (keyword.equals("")) {
			return (int) classRepository.countLocationFilter(arr_location);
		} else if (arr_location.length == 0) {
			return (int) classRepository.countKeywordFilter(keyword);
		} else {
			return (int) classRepository.countSelectFilter(keyword, arr_location);
		}
    }
	

	public Integer[] getPageList(Integer curPageNum, String keyword, String[] arr_location) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getClassCount(keyword, arr_location));

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산 (올림으로 계산)
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));
        
        // 현재 페이지 기준 블럭의 첫 페이지 번호 계산
        Integer blockStartPageNum =
        		(curPageNum <= BLOCK_PAGE_NUM_COUNT / 2)
        		? 1
        		: curPageNum - BLOCK_PAGE_NUM_COUNT / 2;
        
        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum =
        		(totalLastPageNum > blockStartPageNum + BLOCK_PAGE_NUM_COUNT - 1 )
        		? blockStartPageNum + BLOCK_PAGE_NUM_COUNT - 1
        		: totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= (BLOCK_PAGE_NUM_COUNT / 2) + 1) ? 1 : curPageNum - (BLOCK_PAGE_NUM_COUNT / 2);

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }
	
	
	// builder로 게시된 글 상세보기
	@Transactional
	public ClassResponseDto getPost(int cindex) {
	    Optional<ClassEntity> classEntityWrapper = classRepository.findById(cindex);
	    // findbyId : Primary key 값을 where 조건으로 데이터를 DB로부터 가져오는 method
	    ClassEntity classEntity = classEntityWrapper.get();
	    // Wrapper.get()로 classEntity 객체를 가져옴

	    // classDto classDTO = convertEntityToDto(classEntity);
	    ClassResponseDto classDTO = convertEntityToDto(classEntity);
	    
	    return classDTO;
	}
	
	// insert
	@Transactional
	public int savePost(int aindex, ClassRequestDto classRequestDto, List<MultipartFile> files) throws Exception {
		Optional<AcademyEntity> academyEntityWrapper = academyRepository.findById(aindex);
        AcademyEntity academy = academyEntityWrapper.get();
        
        
        List<ClassFilesEntity> cfEntityList = classFilesHandler.parseFileInfo(null, files);
        
        classRequestDto.setAindex(academy); // academy 외래키 설정
        classRequestDto.setIindex(cfEntityList); // classFiles 외래키 설정
        ClassEntity classEntity = classRequestDto.toEntity();
        
        if (!cfEntityList.isEmpty()) {
        	for (ClassFilesEntity cfEntity : cfEntityList) {
        		cfEntity.setCindex(classEntity);
        		classFilesRepository.save(cfEntity);
        	}
        }
		
		return classRepository.save(classEntity).getCindex();
	}
	
	// update
		@Transactional
		public int updatePost(int aindex, ClassRequestDto classRequestDto, List<MultipartFile> files) throws Exception {
			Optional<AcademyEntity> academyEntityWrapper = academyRepository.findById(aindex);
	        AcademyEntity academy = academyEntityWrapper.get();
	        
	        // 기존 데이터베이스에 저장된 파일 목록 구하기
	        Optional<ClassEntity> classEntityWrapper = classRepository.findById(classRequestDto.getCindex()); 
	        List<ClassFilesEntity> currentList = classEntityWrapper.get().getIindex();
	        
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
	        		for(ClassFilesEntity file : currentList) {
	        			classFilesRepository.delete(file);  // 데이터베이스 파일 모두 삭제
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
	        		for (ClassFilesEntity file : currentList) {
	        			
	        			// 해당 파일을 검색
	        			Optional<ClassFilesEntity> cfEntityWrapper = classFilesRepository.findById(file.getIindex());
	        			ClassFilesEntity cfEntity = cfEntityWrapper.get();
	        			
	        			// 파일의 원본명
	        			String originalName = cfEntity.getOriginalFilename();
	        			
	        			if (!files.contains(originalName)) { // 데이터베이스 (O) / 전달파일 (X)
	        				classFilesRepository.delete(file); // 파일 삭제
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
	        
	        classRequestDto.setAindex(academy); // academy 외래키 설정
	        ClassEntity classEntity = classRequestDto.toEntity();
	        // 새로 데이터베이스에 파일로 저장할 리스트
	        List<ClassFilesEntity> cfEntityList = classFilesHandler.parseFileInfo(classEntity, addFileList);
	        
	        if (!cfEntityList.isEmpty()) { // 새로 추가된 파일들 데이터베이스에 업로드
	        	for (ClassFilesEntity cfEntity : cfEntityList) {
	        		classFilesRepository.save(cfEntity);
	        	}
	        }
	        
			return classRepository.save(classEntity).getCindex();
		}
	
	// delete
	@Transactional
    public void deletePost(int cindex) {
        classRepository.deleteById(cindex);
        // deleteById : Primary key 값을 where 조건으로 데이터를 DB에서 삭제
    }
	
	// 강의 권한 승인
	@Transactional
	public int updateStatus(int cindex) {
		
		return classRepository.updateStatus(cindex);
	}
	

	public int getPrev(Integer pageNum, String keyword, String[] arr_location) {
		int middle = (BLOCK_PAGE_NUM_COUNT / 2) + 1;  // 리스트의 중간값
		
		return (pageNum <= middle) ? -1 : pageNum - middle;
	}
	
	public int getNext(Integer pageNum, String keyword, String[] arr_location) {
		int middle = (BLOCK_PAGE_NUM_COUNT / 2) + 1;  // 리스트의 중간값
		int index = (pageNum <= middle) ? middle - pageNum + 1 : 1;
		boolean checker = true;
		Integer[] lastCheck = this.getPageList(pageNum+index, keyword, arr_location);
		
		for (int i = middle; i < BLOCK_PAGE_NUM_COUNT; i++) {
			if (lastCheck[i] == null) {
				checker = false;
				break;
			}
		}
		if (checker) {
			return lastCheck[BLOCK_PAGE_NUM_COUNT-1];
		}
		
		return -1;
	}
	
	private ClassResponseDto convertEntityToDto(ClassEntity classEntity) {
		ClassResponseDto classResponseDto = new ClassResponseDto(classEntity);
		
		return classResponseDto;
	}
}
