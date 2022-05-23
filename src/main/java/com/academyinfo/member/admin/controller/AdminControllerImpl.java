package com.academyinfo.member.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.academyinfo.Class.dto.ClassRequestDto;
import com.academyinfo.Class.dto.ClassResponseDto;
import com.academyinfo.Class.service.ClassService;
import com.academyinfo.board.comment.dto.CommentResponseDto;
import com.academyinfo.board.comment.service.CommentService;
import com.academyinfo.board.freeboard.dto.BoardResponseDto;
import com.academyinfo.board.freeboard.service.BoardService;
import com.academyinfo.member.dto.MemberResponseDto;
import com.academyinfo.member.service.MemberService;
import com.academyinfo.review.dto.ReviewRequestDto;
import com.academyinfo.review.dto.ReviewResponseDto;
import com.academyinfo.review.service.ReviewService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("admin")
public class AdminControllerImpl implements AdminController {
	private ReviewService reviewService;
	private CommentService commentService;
	private ClassService classService;
	private BoardService boardService;
	private MemberService memberService;
	
	
	// 어드민 페이지
    @GetMapping("")
    public String dispAdmin() {
        return "admin/adminPage";
    }
	
   
	// 회원 관리 페이지
	@GetMapping("userManagePage")
	public String dispUserManagePage(Model model) {
		List<MemberResponseDto> memberList = memberService.getMemberlist();
		
		model.addAttribute("memberList", memberList);
		
		return "admin/userManage";
	}
	
	// 게시글 관리 페이지
	@GetMapping("articleManagePage")
	public String dispArticleManagePage(Model model) {
		
		List<BoardResponseDto> boardList = boardService.getReportBoardlist();
		List<CommentResponseDto> commentList = commentService.getReportCommentlist();
		List<ReviewResponseDto> reviewList = reviewService.getReportReviewlist();
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("commentList", commentList);
		model.addAttribute("reviewList", reviewList);
		
		return "admin/articleManage";
	}
	
	// 권한인증 페이지
	@GetMapping("authPage")
	public String dispAuthPage(Model model) {
		
		List<ClassResponseDto> classList = classService.getClasslist();
		/*수강후기*/
		List<ReviewResponseDto> reviewList = reviewService.getReview();
		
		model.addAttribute("classList", classList);
		model.addAttribute("reviewList", reviewList);
		
		return "admin/authorize";
	}
	
	// 강의 권한인증 허용
	@PutMapping("edit/claco")
	public String update(ClassRequestDto classDTO) {
		
		int cindex = classDTO.getCindex();
		
		classService.updateStatus(cindex);
		
		return "redirect:/admin/authPage";
	}
	
	// 수강후기 권한인증 허용
	@PutMapping("edit/review")
	public String update(ReviewRequestDto reviewDTO) {
		
		int rvindex = reviewDTO.getRvindex();
		
		reviewService.updateStatus(rvindex);
		
		return "redirect:/admin/authPage";
	}
	
	// 게시글 삭제 실행
    @DeleteMapping("freeboard/post/{no}")
    public String deleteBoard(@PathVariable("no") int no) {
        boardService.deletePost(no);

        return "redirect:/admin/articleManagePage";
    }
    
    // 댓글 삭제 실행
    @DeleteMapping("freeboard/post/{no}/comments/{no2}")
    public String deleteComment(@PathVariable("no") int no, @PathVariable("no2") int no2) {
        commentService.delete(no2);

        return "redirect:/admin/articleManagePage";
    }
    
    // 수강후기 삭제 실행
    @DeleteMapping("claco/post/{no}/review/{no2}")
    public String deleteReview(@PathVariable("no") int no, @PathVariable("no2") int no2) {
        reviewService.delete(no2);

        return "redirect:/admin/articleManagePage";
    }
    
 	// 회원 삭제 실행
    @DeleteMapping("member/post/{no}")
    public String deleteMember(@PathVariable("no") Long no) {
        memberService.delete(no);

        return "redirect:/admin/userManagePage";
    }
}

