package com.academyinfo.board.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.academyinfo.board.comment.dto.CommentRequestDto;

public interface CommentController {
	
	public ResponseEntity commentSave(@PathVariable("no") int no, @RequestBody CommentRequestDto dto);
	
	public ResponseEntity update(@PathVariable int no, @PathVariable("no2") int no2, @RequestBody CommentRequestDto dto);
	
	public ResponseEntity delete(@PathVariable("no") int no, @PathVariable("no2") int no2);
	
	public String updateReport(@PathVariable("no") int no, @PathVariable("no2") int no2);
	// public void updateReport(@PathVariable("no") int no, @PathVariable("no2") int no2, HttpServletResponse response) throws Exception;
}
