package com.academyinfo.board.freeboard.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.board.freeboard.dto.BoardRequestDto;

public interface BoardController {
	public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum);
	public String write(BoardRequestDto boardRequestDto, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception;
    public String detail(@PathVariable("no") int no, Model model);
    public String edit(@PathVariable("no") int no, Model model);
    public String update(BoardRequestDto boardDTO, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception;
    public String delete(@PathVariable("no") int no);
   
    public String search(@RequestParam(value="keyword", defaultValue="") String keyword, @RequestParam(value="page", defaultValue = "1") Integer pageNum, @RequestParam(value="filter", defaultValue="title") String filter, Model model);
    public String updateReport(@PathVariable("no") int no, Model model);
}
