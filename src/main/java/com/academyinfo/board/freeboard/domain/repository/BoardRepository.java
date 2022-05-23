package com.academyinfo.board.freeboard.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.academyinfo.board.freeboard.domain.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{
	
	// Containing을 붙이면 like검색이 된다.(where title like %keyword%)
	// List<BoardEntity> findByTitleContaining(@Param("keyword") String keyword);
	Page<BoardEntity> findAll(Pageable pageable);
	Page<BoardEntity> findByTitleContaining(@Param("keyword") String keyword, Pageable pageable);
	Page<BoardEntity> findByContentsContaining(@Param("keyword") String keyword, Pageable pageable);
	List<BoardEntity> findByReportGreaterThan(int length);
	
	@Query(value = "select t from BoardEntity t where t.mindex.id like %:keyword%")
	Page<BoardEntity> findByWriterContaining(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("select count(1) from BoardEntity t where t.title like %:keyword%")
	int countBykeywordtitleContaining(@Param("keyword") String keyword);
	
	@Query("select count(1) from BoardEntity t where t.contents like %:keyword%")
	int countBykeywordcontentsContaining(@Param("keyword") String keyword);
	
	@Query("select count(1) from BoardEntity t where t.mindex.id like %:keyword%")
	int countBykeywordwriterContaining(@Param("keyword") String keyword);
	
	@Modifying
	@Query("update BoardEntity t set t.count = t.count + 1 where t.fbindex = :fbindex")
	int updateCount(@Param("fbindex") int fbindex);
	
	@Modifying
	@Query("update BoardEntity t set t.report = t.report + 1 where t.fbindex = :fbindex")
	int updateReport(@Param("fbindex") int fbindex);
}