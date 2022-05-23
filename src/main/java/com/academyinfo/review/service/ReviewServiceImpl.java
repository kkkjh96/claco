package com.academyinfo.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.Class.domain.repository.ClassRepository;
import com.academyinfo.Class.dto.ClassRequestDto;
import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.academy.domain.repository.AcademyRepository;
import com.academyinfo.academy.dto.AcademyRequestDto;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member.domain.repository.MemberRepository;
import com.academyinfo.review.domain.entity.ReviewEntity;
import com.academyinfo.review.domain.repository.ReviewRepository;
import com.academyinfo.review.dto.ReviewRequestDto;
import com.academyinfo.review.dto.ReviewResponseDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
	private ReviewRepository reviewRepository;
	private ClassRepository classRepository;
	private MemberRepository memberRepository;
	private AcademyRepository academyRepository;
	
	private static final int MAX_REPORT_COUNT = 5; // 허용 report 수
	private static final double MIDDLE = 2.5; // 중간값
	
	//select * from review;
	@Transactional(readOnly = true)
	public List<ReviewResponseDto> getReview(){
		List<ReviewEntity> reviewEntities = reviewRepository.findAll();
		List<ReviewResponseDto> reviewDtoList = new ArrayList<>();
		
		if (reviewEntities.isEmpty()) {
	    	return reviewDtoList;
	    }

	    for (ReviewEntity reviewEntity : reviewEntities) {
	        reviewDtoList.add(this.convertEntityToDto(reviewEntity));
	    }
	    
		return reviewDtoList;
	}
	
	/* 최신 리뷰 순서로 정렬 검색 */
	@Transactional(readOnly = true)
	public List<ReviewResponseDto> getReviewByCreatedDate(){
		// reviewRepository.findOrderByCreatedDateDesc();
		/*
		Sort sort = Sort.by(
				Sort.Order.desc("createdDate")
				);
		*/
		
		// List<ReviewEntity> reviewEntities = reviewRepository.findAll(sort);
		List<ReviewEntity> reviewEntities = reviewRepository.selectTop6();
		
		List<ReviewResponseDto> reviewDtoList = new ArrayList<>();
		
		if (reviewEntities.isEmpty()) {
	    	return reviewDtoList;
	    }

	    for (ReviewEntity reviewEntity : reviewEntities) {
	        reviewDtoList.add(this.convertEntityToDto(reviewEntity));
	    }
	    
		return reviewDtoList;
	}
	
	// 수강후기 생성
	@Transactional
	public int reviewSave(String id, int cindex, ReviewRequestDto dto) {
		Optional<MemberEntity> memberEntityWrapper = memberRepository.findById(id);
        MemberEntity member = memberEntityWrapper.get();
        
		ClassEntity classes = classRepository.findById(cindex).orElseThrow(() ->
		new IllegalArgumentException("댓글 쓰기 실패: 해당 강의가 존재하지 않습니다." + cindex));
		
		dto.setCindex(classes);
		dto.setMindex(member);
		ReviewEntity review = dto.toEntity();
		reviewRepository.save(review);
		
		return dto.getRvindex();
	}
	
	// 수강후기 수정
	@Transactional
	public void update(int rvindex, ReviewRequestDto dto) {
		ReviewEntity review = reviewRepository.findById(rvindex).orElseThrow(() ->
		new IllegalArgumentException("해당 수강후기가 존재하지 않습니다. " + rvindex));
		
		review.update(dto.getContents());
	}
	
	// 수강후기 권한 승인
	@Transactional
	public int updateStatus(int rvindex) {
		Optional<ReviewEntity> rvEntityWrapper = reviewRepository.findById(rvindex);
		ReviewEntity rvEntity = rvEntityWrapper.get();
		Optional<ClassEntity> classWrapper = classRepository.findById(rvEntity.getCindex().getCindex());
		ClassEntity classes = classWrapper.get();
		
		calculateScore(rvEntity.getScore(), classes, 1);
		
		return reviewRepository.updateStatus(rvindex);
	}
	
	// 수강후기 삭제
	@Transactional
	public void delete(int rvindex) {
		ReviewEntity review = reviewRepository.findById(rvindex).orElseThrow(() ->
		new IllegalArgumentException("해당 수강후기가 존재하지 않습니다. id=" + rvindex));
		
		calculateScore(review.getScore(), review.getCindex(), -1);
		
		reviewRepository.delete(review);
	}
	
	// 신고 조건부 조회
	@Transactional(readOnly = true)
	public List<ReviewResponseDto> getReportReviewlist() {
		/* list를 읽어와서 표시(report용)
		 */
		
		List<ReviewEntity> reviewEntities = reviewRepository.findByReportGreaterThan(MAX_REPORT_COUNT); 
		List<ReviewResponseDto> reviewDtoList = new ArrayList<>();
		
		for ( ReviewEntity reviewEntity : reviewEntities ) {  
			// 정규식 for
			// 반복문으로 게시판 구성요소들을 불러와 리스트에 추가
			reviewDtoList.add(this.convertEntityToDto(reviewEntity));
		}
		
		return reviewDtoList;
	}
	
	// 수강후기 신고 수 증가
	@Transactional
	public int updateReport(int no) {
		return reviewRepository.updateReport(no);
	}
	
	// 수강후기 등록 시 강의평점, 학원 등급점수 변동
	@Transactional
	public void calculateScore(double score, ClassEntity classes, int sign) { 
		// score : 수강후기 별점, classes : 해당 강의 정보, sign : 생성 시 1, 삭제 시 -1 입력으로 부호 변경
		AcademyRequestDto academyDto = new AcademyRequestDto();
		Optional<AcademyEntity> academyEntityWrapper = academyRepository.findById(classes.getAindex().getAindex());
		AcademyEntity academyEntity = academyEntityWrapper.get();
		
		double diff = score - MIDDLE; // 변환값
		double cScore = classes.getScore(); // 강의의 기존 평균 점수
		double totalScore, updatedScore = 0; // 학원에 사용할 총 점수
		
		List<ReviewEntity> rvEntity = classes.getRvindex();
		List<ReviewEntity> approvalRvEntity = new ArrayList<>();
		
		
		// 승인된 수강후기만 점수에 변동을 준다. (생성 시 기본 상태 : limited)
		for (ReviewEntity review : rvEntity) {
			if (review.getStatus().equals("approval")) {
				approvalRvEntity.add(review);
			}
		}
		
		int rvcount = approvalRvEntity.size(); // 수강후기 갯수
		

		if (rvcount == 0) { // 기존 수강후기가 0개일 때
			totalScore = 0;
		} else {
			totalScore = cScore * rvcount;
		}
		
		updatedScore = totalScore + (sign * score); // 수정된 총 점수

		if (sign == -1 && rvcount == 1) { // 삭제 시 남은 review가 0이 될 경우
				cScore = 0;
				updatedScore = 0;
		} else {
			cScore = updatedScore / (rvcount + (sign * 1)); // 강의 평점
		}
		
		ClassRequestDto classDto = new ClassRequestDto();
		
		// 강의 평점 수정 반영 / 나머지 변수 동일하게 작성
		classDto.setCindex(classes.getCindex());
		classDto.setName(classes.getName());
		classDto.setCategory(classes.getCategory());
		classDto.setPrice(classes.getPrice());
		classDto.setStatus(classes.getStatus());
		classDto.setStartperiod(classes.getStartperiod());
		classDto.setEndperiod(classes.getEndperiod());
		classDto.setInfo(classes.getInfo());
		classDto.setAindex(classes.getAindex());
		classDto.setIindex(classes.getIindex());
		classDto.setRvindex(classes.getRvindex());
		classDto.setMcindex(classes.getMcindex());
		classDto.setScore(cScore);
		classRepository.save(classDto.toEntity());
		
		// academy grade 수정 반영 / 나머지 변수 동일하게 작성
		academyDto.setAindex(academyEntity.getAindex());
		academyDto.setName(academyEntity.getName());
		academyDto.setLocation(academyEntity.getLocation());
		academyDto.setAddress(academyEntity.getAddress());
		academyDto.setCompanynum(academyEntity.getCompanynum());
		academyDto.setInfo(academyEntity.getInfo());
		academyDto.setMindex(academyEntity.getMindex());
		academyDto.setCindex(academyEntity.getCindex());
		academyDto.setIindex(academyEntity.getIindex());
		
		int updatedGrade = academyEntity.getGrade() + (sign * ((int)( diff * 10)));
		academyDto.setGrade(updatedGrade);
		academyRepository.save(academyDto.toEntity());
	}
	
	private ReviewResponseDto convertEntityToDto(ReviewEntity reviewEntity) {
	    return new ReviewResponseDto(reviewEntity);
	}
	
	
}
