package com.academyinfo.board.comment.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.academyinfo.board.comment.domain.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
	List<CommentEntity> findByReportGreaterThan(int length);
	
	@Modifying
	@Query("update CommentEntity c set c.report = c.report + 1 where c.cmtindex = :cmtindex")
	int updateReport(@Param("cmtindex") int cmtindex);
}
