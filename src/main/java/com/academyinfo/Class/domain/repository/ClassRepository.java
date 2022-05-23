package com.academyinfo.Class.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.academyinfo.Class.domain.entity.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity,Integer>{
	
	List<ClassEntity> findByStatus(@Param("status") String status, Sort sort);
	
	Page<ClassEntity> findByNameContainingAndStatus(@Param("name") String name, @Param("status") String status, Pageable pageable);
	
	@Modifying
	@Query("update ClassEntity c set c.status = 'approval' where c.cindex = :cindex")
	int updateStatus(@Param("cindex") int cindex);
	
	//추천강좌 SQL문
	@Query(value = "select * from (select *, rank() over(order by score desc) as 'ranking' from class where status IN('approval')) as t where  t.ranking<=4 limit 4", nativeQuery = true)
	public List<ClassEntity> selectRecommendedCourse();
	
	//키워드 필터 SQL문
	@Query(value = "SELECT * FROM class WHERE status IN('approval') && name LIKE %:keyword%", nativeQuery = true)
	public List<ClassEntity> findByKeyword(@Param("keyword") String keyword);
	
	//키워드와 서울지역 필터 SQL문
	@Query(value = "SELECT * FROM class WHERE status IN('approval') && (name LIKE %:keyword% && alocation IN :arr_location_S)", nativeQuery = true)
	public List<ClassEntity> testFilter_S(@Param("keyword") String keyword, @Param("arr_location_S") String[] arr_location_S);
	
	//키워드와 부산지역 필터 SQL문
	@Query(value = "SELECT * FROM class WHERE status IN('approval') && (name LIKE %:keyword% && location IN :arr_location_B)", nativeQuery = true)
	public List<ClassEntity> testFilter_B(@Param("keyword") String keyword, @Param("arr_location_B") String[] arr_location_B);
	
	//SQL
	/*@Query(value = "SELECT c.*, a.address from class as c inner join academy as a on a.aindex = c.aindex WHERE c.status IN('approval') && (c.name LIKE %:keyword% && a.address IN :arr_location)", nativeQuery = true)
	public List<ClassEntity> selectFilter(@Param("keyword") String keyword, @Param("arr_location") String[] arr_location_S);
	*/
	
	//SQL
	@Query(value = "SELECT c.*, a.address from class as c inner join academy as a on a.aindex = c.aindex WHERE c.status IN('approval') && (c.name LIKE %:keyword% && a.address IN :arr_location)", nativeQuery = true)
	public Page<ClassEntity> selectFilter(@Param("keyword") String keyword, @Param("arr_location") String[] arr_location_S, Pageable pageable);
	
	@Query(value = "select count(1) from class as c inner join academy as a on a.aindex = c.aindex WHERE c.status IN('approval') && (a.address IN :arr_location)", nativeQuery = true)
	int countLocationFilter(@Param("arr_location") String[] arr_location_S);
	
	@Query(value = "select count(1) from class as c inner join academy as a on a.aindex = c.aindex WHERE c.status IN('approval') && (c.name LIKE %:keyword%)", nativeQuery = true)
	int countKeywordFilter(@Param("keyword") String keyword);
	
	@Query(value = "select count(1) from class as c inner join academy as a on a.aindex = c.aindex WHERE c.status IN('approval') && (c.name LIKE %:keyword% && a.address IN :arr_location)", nativeQuery = true)
	int countSelectFilter(@Param("keyword") String keyword, @Param("arr_location") String[] arr_location_S);
	
}
 