package com.academyinfo.Class.domain.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.academyinfo.Files.Class.domain.entity.ClassFilesEntity;
import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.member_class.domain.entity.MemberClassEntity;
import com.academyinfo.review.domain.entity.ReviewEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="class")
public class ClassEntity {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cindex;
	
	@Column
	private String name;
	
	@Column
	private String category;
	
	@Column(columnDefinition = "integer default 0")
	private int price;
	
	@Column(columnDefinition = "double default 0")
	private double score;
	
	@Column(columnDefinition = "varchar(10) default 'limited'")
	private String status;
	
	@Column
	private Date startperiod;
	
	@Column
	private Date endperiod;
	
	@Column
	private String info;
	
	@ManyToOne
	@JoinColumn(name = "aindex")
	private AcademyEntity aindex;
	
	/* 조인으로 읽어옴
	 * @Column
	 * private String aname;
	 * @Column
	 * private int agrade;
	 * @Column
	 * private String alocation;
	*/
	
	// image 조인
	@Column
	@OneToMany(mappedBy = "cindex", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ClassFilesEntity> iindex;
	
	// 수강후기
	@OneToMany(mappedBy = "cindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<ReviewEntity> rvindex;
	
	/* 
     * member_class와 1:N 매핑(N:M 매핑 조인테이블)
     */
    @OneToMany(mappedBy = "cindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<MemberClassEntity> mcindex;
	
	@Builder
	public ClassEntity(int cindex, String name, String category, int price, double score, String status, Date startperiod, Date endperiod, String info, AcademyEntity aindex,  List<ClassFilesEntity> iindex, List<ReviewEntity> rvindex, List<MemberClassEntity> mcindex) {
		this.cindex = cindex;
		this.name = name;
		this.category = category;
		this.price = price;
		this.score = score;
		this.status = status;
		this.startperiod = startperiod;
		this.endperiod = endperiod;
		this.info = info;
		
		this.iindex = iindex;
		this.aindex = aindex;
		this.rvindex = rvindex;
		this.mcindex = mcindex;
	}
	
	public void addClassFilesEntity(ClassFilesEntity cfEntity) {
		this.iindex.add(cfEntity);
		
		if (cfEntity.getCindex() != this) {
			cfEntity.setCindex(this);
		}
	}
}
