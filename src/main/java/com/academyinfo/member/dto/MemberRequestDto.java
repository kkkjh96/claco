package com.academyinfo.member.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.academyinfo.academy.domain.entity.AcademyEntity;
import com.academyinfo.board.alertboard.domain.entity.AlertBoardEntity;
import com.academyinfo.board.comment.domain.entity.CommentEntity;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;
import com.academyinfo.member.domain.entity.MemberEntity;
import com.academyinfo.member_class.domain.entity.MemberClassEntity;
import com.academyinfo.review.domain.entity.ReviewEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberRequestDto {
	// Member 관련 내용을 Service >> Entity 전달
	private Long mindex;
	
	@NotBlank(message = "아이디를 입력하지 않았습니다.")
	private String id;
	
	@NotBlank(message = "비밀번호를 입력하지 않았습니다.")
	private String pwd;
	
	@NotBlank(message = "이름을 입력하지 않았습니다.")
	private String name;
	
	@NotBlank(message = "전화번호를 입력하지 않았습니다.")
	private String phone;
	
	@NotBlank(message = "이메일을 입력하지 않았습니다.")
	private String email;
	private String role;
	
    private List<AcademyEntity> aindex;
	private List<AlertBoardEntity> abindex;
	private List<BoardEntity> fbindex;
	private List<CommentEntity> cmtindex;
	private List<MemberClassEntity> mcindex;
   	private List<ReviewEntity> rvindex;
	 
	public MemberEntity toEntity() {
		MemberEntity memberEntity = MemberEntity.builder()
				 .mindex(mindex)
	             .id(id)
	             .pwd(pwd)
	             .name(name)
	             .phone(phone)
	             .email(email)
	             .role(role)
	             .aindex(aindex)
	             .abindex(abindex)
	             .fbindex(fbindex)
	             .cmtindex(cmtindex)
	             .mcindex(mcindex)
	             .rvindex(rvindex)
	             .build();
		
		return memberEntity;
	}
}
