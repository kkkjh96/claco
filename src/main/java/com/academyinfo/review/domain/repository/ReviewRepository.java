package com.academyinfo.review.domain.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.academyinfo.review.domain.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Integer>{
	
	List<ReviewEntity> findAll();
	List<ReviewEntity> findAll(Sort sort);
	List<ReviewEntity> findByReportGreaterThan(int length);
	
	@Modifying
	@Query("update ReviewEntity r set r.status = 'approval' where r.rvindex = :rvindex")
	int updateStatus(@Param("rvindex") int rvindex);
	
	@Modifying
	@Query("update ReviewEntity r set r.report = r.report + 1 where r.rvindex = :rvindex")
	int updateReport(@Param("rvindex") int rvindex);
	
	//추천강좌 SQL문
	@Query(value = "select * from review where status = 'approval' order by created_date desc limit 6", nativeQuery = true)
	public List<ReviewEntity> selectTop6();
}
