package com.academyinfo.board.alertboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.academyinfo.board.alertboard.domain.entity.AlertBoardEntity;
import com.academyinfo.board.alertboard.domain.repository.AlertBoardRepository;
import com.academyinfo.board.alertboard.dto.AlertBoardRequestDto;
import com.academyinfo.board.alertboard.dto.AlertBoardResponseDto;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member.domain.repository.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlertBoardServiceImpl implements AlertBoardService {
	private AlertBoardRepository AlertBoardRepository;
	private MemberRepository memberRepository;
	
	private static final int BLOCK_PAGE_NUM_COUNT = 5; // 표시되는 페이지 번호
	private static final int PAGE_POST_COUNT = 7; // 한 페이지에 표시되는 게시글 수
	
	
	@Transactional
	// public List<BoardDto> getBoardlist(Integer pageNum) {
	public List<AlertBoardResponseDto> getBoardlist(Integer pageNum) {
		/* list를 읽어와서 표시
		 * repository.find() 메서드 호출 시 Pageable 인터페이스를 구현한 클래스(PageRequest.of()) 전달하여 페이징
		 * PageRequest.of(limit, offset, sort)
		 */
		// 전체 목록 출력
		Page<AlertBoardEntity> page = AlertBoardRepository.findAll(PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "abindex")));
		
		List<AlertBoardEntity> boardEntities = page.getContent(); 
		// List<BoardDto> boardDtoList = new ArrayList<>();
		List<AlertBoardResponseDto> boardDtoList = new ArrayList<>();
		
		for ( AlertBoardEntity AlertBoardEntity : boardEntities ) {  
			// 정규식 for
			// 반복문으로 게시판 구성요소들을 불러와 리스트에 추가
			boardDtoList.add(this.convertEntityToDto(AlertBoardEntity));
		}
		
		return boardDtoList;
	}
	
	// 게시글 갯수 카운트
	@Transactional
    public int getBoardCount(String keyword, String filter) {
		if (keyword.equals("")) { // 검색 중일 시 검색중인 페이지 갯수만 반환하도록
			return (int) AlertBoardRepository.count();	
		} else {
			if (filter.equals("title")){
				return (int) AlertBoardRepository.countBykeywordtitleContaining(keyword);
			} else if (filter.equals("contents")) {
				return (int) AlertBoardRepository.countBykeywordcontentsContaining(keyword);
			} else {
				return (int) AlertBoardRepository.countBykeywordwriterContaining(keyword);
			}
		}
    }
	
	// 단일 변수 입력 시 2, 3번째 매개변수 공백 입력 처리
	public Integer[] getPageList(Integer curPageNum) {
		return getPageList(curPageNum, "", "");
	}
	
	public Integer[] getPageList(Integer curPageNum, String keyword, String filter) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount(keyword, filter));

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
    public AlertBoardResponseDto getPost(int abindex) {
        Optional<AlertBoardEntity> AlertBoardEntityWrapper = AlertBoardRepository.findById(abindex);
        // findbyId : Primary key 값을 where 조건으로 데이터를 DB로부터 가져오는 method
        AlertBoardEntity AlertBoardEntity = AlertBoardEntityWrapper.get();
        // Wrapper.get()로 AlertBoardEntity 객체를 가져옴

        // BoardDto boardDTO = convertEntityToDto(AlertBoardEntity);
        AlertBoardResponseDto boardDTO = convertEntityToDto(AlertBoardEntity);

        return boardDTO;
    }
	
	@Transactional
	public int updateCount(int no) {
		return AlertBoardRepository.updateCount(no);
	}
	
	
	// insert
	@Transactional
	public int savePost(String id, AlertBoardRequestDto alertBoardRequestDto) {
		
		Optional<MemberEntity> memberEntityWrapper = memberRepository.findById(id);
        MemberEntity member = memberEntityWrapper.get();
        alertBoardRequestDto.setMindex(member); // member 외래키 설정
		
		return AlertBoardRepository.save(alertBoardRequestDto.toEntity()).getAbindex();
	}
	
	// delete
	@Transactional
    public void deletePost(int abindex) {
        AlertBoardRepository.deleteById(abindex);
        // deleteById : Primary key 값을 where 조건으로 데이터를 DB에서 삭제
    }
	
	//search
	@Transactional
	// public List<BoardDto> searchPosts(String keyword, String filter, Integer pageNum) {
	public List<AlertBoardResponseDto> searchPosts(String keyword, String filter, Integer pageNum) {
		Page<AlertBoardEntity> page;
		
		if (filter.equals("title")) {
			page = AlertBoardRepository.findByTitleContaining(keyword, PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "abindex")));
		} else if (filter.equals("contents")) {
			page = AlertBoardRepository.findByContentsContaining(keyword, PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "abindex")));
		} else { // if (filter.equals("writer")) {
			page = AlertBoardRepository.findByWriterContaining(keyword, PageRequest.of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, "abindex")));
		}
		
	    List<AlertBoardEntity> boardEntities = page.getContent(); 
	    List<AlertBoardResponseDto> boardDtoList = new ArrayList<>();
	    
	    if (boardEntities.isEmpty()) {
	    	return boardDtoList;
	    }

	    for (AlertBoardEntity AlertBoardEntity : boardEntities) {
	        boardDtoList.add(this.convertEntityToDto(AlertBoardEntity));
	    }

	    return boardDtoList;
	}
	
	public int getPrev(Integer pageNum, String keyword, String filter) {
		int middle = (BLOCK_PAGE_NUM_COUNT / 2) + 1;  // 리스트의 중간값
		
		return (pageNum <= middle) ? -1 : pageNum - middle;
	}
	
	public int getNext(Integer pageNum, String keyword, String filter) {
		int middle = (BLOCK_PAGE_NUM_COUNT / 2) + 1;  // 리스트의 중간값
		int index = (pageNum <= middle) ? middle - pageNum + 1 : 1;
		boolean checker = true;
		Integer[] lastCheck = this.getPageList(pageNum+index, keyword, filter);
		
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
	
	private AlertBoardResponseDto convertEntityToDto(AlertBoardEntity alertboardEntity) {
	    return new AlertBoardResponseDto(alertboardEntity);
	}
}
