package com.academyinfo.board.alertboard.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.academyinfo.board.alertboard.domain.entity.AlertBoardEntity;

@Repository
public interface AlertBoardRepository extends JpaRepository<AlertBoardEntity, Integer>{
	
	// Containing을 붙이면 like검색이 된다.(where title like %keyword%)
	// List<AlertBoardEntity> findByTitleContaining(@Param("keyword") String keyword);
	Page<AlertBoardEntity> findAll(Pageable pageable);
	Page<AlertBoardEntity> findByTitleContaining(@Param("keyword") String keyword, Pageable pageable);
	Page<AlertBoardEntity> findByContentsContaining(@Param("keyword") String keyword, Pageable pageable);
	
	@Query(value = "select t from AlertBoardEntity t where t.mindex.id like %:keyword%")
	Page<AlertBoardEntity> findByWriterContaining(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("select count(1) from AlertBoardEntity t where t.title like %:keyword%")
	int countBykeywordtitleContaining(@Param("keyword") String keyword);
	
	@Query("select count(1) from AlertBoardEntity t where t.contents like %:keyword%")
	int countBykeywordcontentsContaining(@Param("keyword") String keyword);
	
	@Query("select count(1) from AlertBoardEntity t where t.mindex.id like %:keyword%")
	int countBykeywordwriterContaining(@Param("keyword") String keyword);
	
	@Modifying
	@Query("update AlertBoardEntity t set t.count = t.count + 1 where t.abindex = :abindex")
	int updateCount(@Param("abindex") int abindex);
}