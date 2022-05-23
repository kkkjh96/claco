package com.academyinfo.member.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.board.alertboard.domain.entity.AlertBoardEntity;
import com.academyinfo.board.comment.domain.entity.CommentEntity;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;
import com.academyinfo.member_class.domain.entity.MemberClassEntity;
import com.academyinfo.review.domain.entity.ReviewEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {
	// 실제 테이블에 저장되어 있는 회원 데이터
	
    @Id 
    @Column(name = "mindex")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mindex;
    
    @Column(length = 20, nullable = false)
    private String id;
    
    @Column(length = 100, nullable = false)
    private String pwd;
    
    @Column(length = 20, nullable = false)
    private String name;
    
    @Column(length = 20, nullable = false)
    private String phone;
    
    @Column(length = 30, nullable = false)
    private String email;
    
    
    
    @Column(length = 20)
    private String role;
    
    /* academy와 1:N 매핑
     *  companynum, address, name, grade
     *  
     *  
     */
    @OneToMany(mappedBy = "mindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AcademyEntity> aindex;
    
    /* alertboard와 1:N 매핑
     *  
     */
    @OneToMany(mappedBy = "mindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<AlertBoardEntity> abindex;
    
    /* freeboard와 1:N 매핑
     *  
     */
    @OneToMany(mappedBy = "mindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<BoardEntity> fbindex;
    
    /* 
     * comment와 1:N 매핑
     */
    @OneToMany(mappedBy = "mindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<CommentEntity> cmtindex;
    
    /* 
     * member_class와 1:N 매핑(N:M 매핑 조인테이블)
     */
    @OneToMany(mappedBy = "mindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<MemberClassEntity> mcindex;
    
    /* 
     * review와 1:N 매핑
     */
    @OneToMany(mappedBy = "mindex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
   	private List<ReviewEntity> rvindex;
    
    @Builder
    public MemberEntity(Long mindex, String id, String pwd, String name, String phone, String email, String role, List<AcademyEntity> aindex, List<AlertBoardEntity> abindex, List<BoardEntity> fbindex, List<CommentEntity> cmtindex, List<MemberClassEntity> mcindex, List<ReviewEntity> rvindex) {
        this.mindex = mindex;
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        
        this.aindex = aindex;
        this.abindex = abindex;
        this.fbindex = fbindex;
        this.cmtindex = cmtindex;
        this.mcindex = mcindex;
        this.rvindex = rvindex;
    }
    
    /* 회원정보 수정 시 수정 가능한 데이터  */
    public void modify(String pwd, String phone, String email) {
	    this.pwd = pwd;
	    this.phone = phone;
	    this.email = email;
    }
}