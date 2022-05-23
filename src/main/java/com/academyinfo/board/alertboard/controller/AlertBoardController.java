package com.academyinfo.board.alertboard.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.academyinfo.board.alertboard.dto.AlertBoardRequestDto;

public interface AlertBoardController {
	public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum);
	public String write(Model model);
    public String detail(@PathVariable("no") int no, Model model);
    public String edit(@PathVariable("no") int no, Model model);
    public String update(AlertBoardRequestDto boardDTO);
    public String delete(@PathVariable("no") int no);
    public String search(@RequestParam(value="keyword", defaultValue="") String keyword, @RequestParam(value="page", defaultValue = "1") Integer pageNum, @RequestParam(value="filter", defaultValue="title") String filter, Model model);
}