package com.academyinfo.review.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.academyinfo.Class.domain.entity.ClassEntity;
import com.academyinfo.common.entity.TimeEntity;
import com.academyinfo.member.domain.entity.MemberEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="review")
public class ReviewEntity extends TimeEntity {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rvindex;
	
	@Column
	private double score;
	
	@Column
	private String contents;
	
	@Column(columnDefinition = "varchar(10) default 'limited'")
	private String status;
	
	/* 신고 카운트 */
	@Column(columnDefinition = "integer default 0")
	private int report;
	
	// Class 테이블과 N:1 
	@ManyToOne
	@JoinColumn(name = "cindex")
	private ClassEntity cindex;
	
	/* 
	 * >> 강의 이름
	 * @Column
	 * private String cname;
	 * 
	*/
	
	// Member 테이블과 N:1
	@ManyToOne
	@JoinColumn(name = "mindex")
	private MemberEntity mindex;
	
	/* >> 작성자
	@Column
	private String mid;
	*/

	@Builder
	public ReviewEntity(int rvindex, double score, String contents, int report, String status, LocalDateTime createdDate, LocalDateTime modifiedDate, ClassEntity cindex, MemberEntity mindex) {
		this.rvindex = rvindex;
		this.score = score;
		this.contents = contents;
		this.report = report;
		this.status = status;
		this.cindex = cindex;
		this.mindex = mindex;
	}
	
	// 수강후기 수정 기능 구현을 위한 contents setter
		public void update(String contents) {
				this.contents = contents;
		}
}
