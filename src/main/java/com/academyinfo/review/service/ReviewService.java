package com.academyinfo.review.service;

import java.util.List;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.review.dto.ReviewRequestDto;
import com.academyinfo.review.dto.ReviewResponseDto;

public interface ReviewService {
	public List<ReviewResponseDto> getReview();
	public List<ReviewResponseDto> getReviewByCreatedDate();
	public int reviewSave(String id, int cindex, ReviewRequestDto dto);
	public void update(int rvindex, ReviewRequestDto dto);
	public void delete(int rvindex);
	public int updateStatus(int rvindex);
	public List<ReviewResponseDto> getReportReviewlist();
	public int updateReport(int no);
	public void calculateScore(double score, ClassEntity classes, int sign);
}
