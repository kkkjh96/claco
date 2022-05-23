package com.academyinfo.member.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.academy.dto.AcademyResponseDto;
import com.academyinfo.board.alertboard.domain.entity.AlertBoardEntity;
import com.academyinfo.board.alertboard.dto.AlertBoardResponseDto;
import com.academyinfo.board.comment.domain.entity.CommentEntity;
import com.academyinfo.board.comment.dto.CommentResponseDto;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;
import com.academyinfo.board.freeboard.dto.BoardResponseDto;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member_class.domain.entity.MemberClassEntity;
import com.academyinfo.member_class.dto.MemberClassResponseDto;
import com.academyinfo.review.domain.entity.ReviewEntity;
import com.academyinfo.review.dto.ReviewResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class MemberResponseDto {
	// Member 관련 내용을  DB >> Entity 전달
		private Long mindex;
		 
		private String id;
		private String pwd;
		private String name;
		private String phone;
		private String email;
		private String role;
		
		private List<AcademyResponseDto> aindex;
		
		private List<AlertBoardResponseDto> abindex;
		private List<BoardResponseDto> fbindex;
		private List<CommentResponseDto> cmtindex;
		private List<MemberClassResponseDto> mcindex;
		private List<ReviewResponseDto> rvindex;
		
		@Builder
		public MemberResponseDto(Long mindex, String id, String pwd, String name, String phone, String email, String companynum, String address, String role, List<AcademyEntity> aindex, List<AlertBoardEntity> abindex, List<BoardEntity> fbindex, List<CommentEntity> cmtindex, List<MemberClassEntity> mcindex, List<ReviewEntity> rvindex) {
		     this.mindex = mindex;
		     this.id = id;
		     this.pwd = pwd;
		     this.name = name;
		     this.phone = phone;
		     this.email = email;
		     this.role = role;
		     
		     this.aindex = aindex.stream().map(AcademyResponseDto::new).collect(Collectors.toList());
		     this.abindex = abindex.stream().map(AlertBoardResponseDto::new).collect(Collectors.toList());
		     this.fbindex = fbindex.stream().map(BoardResponseDto::new).collect(Collectors.toList());
		     this.cmtindex = cmtindex.stream().map(CommentResponseDto::new).collect(Collectors.toList());
		     this.mcindex = mcindex.stream().map(MemberClassResponseDto::new).collect(Collectors.toList());
		     this.rvindex = rvindex.stream().map(ReviewResponseDto::new).collect(Collectors.toList());
		 }
		 
		 @Builder
		 public MemberResponseDto(MemberEntity memberEntity) {
		     this.mindex = memberEntity.getMindex();
		     this.id = memberEntity.getId();
		     this.pwd = memberEntity.getPwd();
		     this.name = memberEntity.getName();
		     this.phone = memberEntity.getPhone();
		     this.email = memberEntity.getEmail();
		     this.role = memberEntity.getRole();
		     
		     this.aindex = memberEntity.getAindex().stream().map(AcademyResponseDto::new).collect(Collectors.toList());
		     this.abindex = memberEntity.getAbindex().stream().map(AlertBoardResponseDto::new).collect(Collectors.toList());
		     this.fbindex = memberEntity.getFbindex().stream().map(BoardResponseDto::new).collect(Collectors.toList());
		     this.cmtindex = memberEntity.getCmtindex().stream().map(CommentResponseDto::new).collect(Collectors.toList());
		     this.mcindex = memberEntity.getMcindex().stream().map(MemberClassResponseDto::new).collect(Collectors.toList());
		     this.rvindex = memberEntity.getRvindex().stream().map(ReviewResponseDto::new).collect(Collectors.toList());
		 }
}
