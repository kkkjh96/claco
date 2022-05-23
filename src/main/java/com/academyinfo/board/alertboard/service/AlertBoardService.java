package com.academyinfo.board.alertboard.service;

import java.util.List;

import com.academyinfo.board.alertboard.dto.AlertBoardRequestDto;
import com.academyinfo.board.alertboard.dto.AlertBoardResponseDto;

public interface AlertBoardService {
	
	//public List<BoardDto> getBoardlist(Integer pageNum);
	public List<AlertBoardResponseDto> getBoardlist(Integer pageNum);
	
	public AlertBoardResponseDto getPost(int bindex);
	// public BoardDto getPost(int id);
	// public int savePost(AlertBoardRequestDto AlertBoardRequestDto);
	public int savePost(String id, AlertBoardRequestDto AlertBoardRequestDto);
    public void deletePost(int index);
    
    // public List<BoardDto> searchPosts(String keyword, String filter, Integer pageNum);
    public List<AlertBoardResponseDto> searchPosts(String keyword, String filter, Integer pageNum);
    
    public int getBoardCount(String keyword, String filter);
    public Integer[] getPageList(Integer curPageNum);
	public Integer[] getPageList(Integer curPageNum, String keyword, String filter);
	
	public int getPrev(Integer pageNum, String keyword, String filter);
	public int getNext(Integer pageNum, String keyword, String filter);
	public int updateCount(int no);
}
