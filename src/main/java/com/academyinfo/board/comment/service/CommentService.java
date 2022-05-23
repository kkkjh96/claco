package com.academyinfo.board.comment.service;

import java.util.List;

import com.academyinfo.board.comment.dto.CommentRequestDto;
import com.academyinfo.board.comment.dto.CommentResponseDto;

public interface CommentService {
	public int commentSave(String id, int bindex, CommentRequestDto dto);
	
	public void update(int bindex, CommentRequestDto dto);
	
	public void delete(int bindex);
	
	public List<CommentResponseDto> getReportCommentlist();
	
	public int updateReport(int no);
}
