package com.academyinfo.board.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.academyinfo.board.comment.domain.entity.CommentEntity;
import com.academyinfo.board.comment.domain.repository.CommentRepository;
import com.academyinfo.board.comment.dto.CommentRequestDto;
import com.academyinfo.board.comment.dto.CommentResponseDto;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;
import com.academyinfo.board.freeboard.domain.repository.BoardRepository;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	
	private static final int MAX_REPORT_COUNT = 5; // 허용 report 수
	
	// 댓글 생성
	@Transactional
	public int commentSave(String id, int bindex, CommentRequestDto dto) {
		Optional<MemberEntity> memberEntityWrapper = memberRepository.findById(id);
        MemberEntity member = memberEntityWrapper.get();
        
		BoardEntity board = boardRepository.findById(bindex).orElseThrow(() ->
		new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + bindex));
		
		dto.setFbindex(board);
		dto.setMindex(member);
		CommentEntity comment = dto.toEntity();
		commentRepository.save(comment);
		
		return dto.getCmtindex();
	}
	
	// 댓글 수정
	@Transactional
	public void update(int cmtindex, CommentRequestDto dto) {
		CommentEntity comment = commentRepository.findById(cmtindex).orElseThrow(() ->
		new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + cmtindex));
		
		comment.update(dto.getComment());
	}
	
	// 댓글 삭제
	@Transactional
	public void delete(int cmtindex) {
		CommentEntity comment = commentRepository.findById(cmtindex).orElseThrow(() ->
		new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + cmtindex));
		
		commentRepository.delete(comment);
	}
	
	// 신고 조건부 조회
	@Transactional(readOnly = true)
	public List<CommentResponseDto> getReportCommentlist() {
		/* list를 읽어와서 표시(report용)
		 */
		
		List<CommentEntity> commentEntities = commentRepository.findByReportGreaterThan(MAX_REPORT_COUNT); 
		List<CommentResponseDto> commentDtoList = new ArrayList<>();
		
		if (commentEntities.size() == 0) {
			return commentDtoList;
		}
		
		for ( CommentEntity commentEntity : commentEntities ) {  
			// 정규식 for
			// 반복문으로 게시판 구성요소들을 불러와 리스트에 추가
			commentDtoList.add(this.convertEntityToDto(commentEntity));
		}
		
		return commentDtoList;
	}
	
	// 댓글 신고 수 증가
	@Transactional
	public int updateReport(int no) {
		return commentRepository.updateReport(no);
	}
	
	private CommentResponseDto convertEntityToDto(CommentEntity commentEntity) {
	    return new CommentResponseDto(commentEntity);
	}
}
