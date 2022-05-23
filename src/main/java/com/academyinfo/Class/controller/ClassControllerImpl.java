package com.academyinfo.Class.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Class.dto.ClassRequestDto;
import com.academyinfo.Class.dto.ClassResponseDto;
import com.academyinfo.Class.service.ClassService;
import com.academyinfo.Files.Class.dto.ClassFilesResponseDto;
import com.academyinfo.Files.Class.service.ClassFilesService;
import com.academyinfo.member_class.dto.MemberClassResponseDto;
import com.academyinfo.review.dto.ReviewResponseDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("claco")
@Controller
public class ClassControllerImpl implements ClassController {
	private ClassService classService;
	private ClassFilesService classFilesService;
	
	@GetMapping("")
	public String list() {
		return "redirect:/claco/search";
	}
	
	// 강의 등록화면으로 이동
	// no : academy.index
	@GetMapping("add/{no}")
	public String add(@PathVariable("no") int aindex, Model model) {
		
		model.addAttribute("aindex", aindex);
		
		return "claco/add";
	}
	
	
	// 강의 등록화면에서 데이터 넘어오면 DB 저장
	@PostMapping("post/{no}")
	public String write(@PathVariable("no") int aindex, ClassRequestDto classRequestDto, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception {
		
		classService.savePost(aindex, classRequestDto, files);
			
		return "redirect:/user/cpnClassList";
	}
	
	// 강의 상세보기
	@GetMapping("post/{no}")
	public String detail(@PathVariable("no") int no, Model model) {
		ClassResponseDto classDTO = classService.getPost(no);
		List<ReviewResponseDto> reviewList = classDTO.getRvindex();
		List<ReviewResponseDto> approvalReviewList = new ArrayList<>();
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인한 사용자의 정보를 가져온다    
		
		List<MemberClassResponseDto> memberClassDto = classDTO.getMcindex(); // 해당 강의 즐겨찾기 개수 확인용
		 
	    boolean isWriter = false;
	    boolean isLogin = false;
	        
	    // 로그인 상태 체크
	    if (!principal.toString().equals("anonymousUser")) {
	    	String id = ((UserDetails)principal).getUsername(); // 사용자의 Id
	     	isLogin = true;
	     	
	     	model.addAttribute("loginUser", id);
	    	
	       	// 강의정보 작성자 본인확인
	       	if (classDTO.getMid().equals(id)) {
	       		isWriter = true;
	       	}
	    }
	        
	    // 수강후기
	    if (reviewList != null && !reviewList.isEmpty()) {
	    	// 상태가 approval인 수강후기만 출력한다. 
	    	for (ReviewResponseDto rvDto : reviewList) {
	    		if (rvDto.getStatus().equals("approval")) {
	    			approvalReviewList.add(rvDto);
	    		}
	    	}
	    	
	    	if (approvalReviewList != null && !approvalReviewList.isEmpty()) {
	    		model.addAttribute("reviews", approvalReviewList);
		       	model.addAttribute("reviewCnt", approvalReviewList.size());
	    	} else {
	    		model.addAttribute("reviewCnt", 0);
	    	}
	    } else {
	    	model.addAttribute("reviewCnt", 0);
	    }
	    
	 // 이미지 관련 기능
        List<String> imageList = new ArrayList<>();
        
        List<ClassFilesResponseDto> cfDto = classDTO.getIindex();
        
        for (ClassFilesResponseDto dto : cfDto) {
        	String [] str = dto.getPath().split(File.separator + File.separator);
        	
        	imageList.add("/" + str[4] + "/" + str[5] + "/" + str[6]);
        }
        
        model.addAttribute("imageList", imageList);
	    
	    
	    // 즐겨찾기
	    if (memberClassDto != null && !memberClassDto.isEmpty()) {
		   	model.addAttribute("favorite", memberClassDto.size());
	    } else {
	    	model.addAttribute("favorite", 0);
	    }
	      
	    model.addAttribute("isLogin", isLogin);
	    model.addAttribute("isWriter", isWriter);
	   	model.addAttribute("classDto", classDTO);
	        
	   	return "claco/detail";
	 
	}

	// 강의 정보 수정
	@GetMapping("post/edit/{no}")
	public String edit(@PathVariable("no") int no, Model model) {
	    ClassResponseDto classDTO = classService.getPost(no);
	    
	    model.addAttribute("classDto", classDTO);
	    model.addAttribute("aindex", classDTO.getAindex());
	        
	    return "claco/update";
	}

	// 강의 정보 수정 값 DB 저장
	@PutMapping("post/edit/{no}")
	public String update(ClassRequestDto classDTO, @RequestParam(value = "files", required = false) List<MultipartFile> files) throws Exception {
	   	
		int aindex = classDTO.getAindex().getAindex();
	 	
	    classService.updatePost(aindex, classDTO, files);

	    return "redirect:/claco/post/{no}";
	}
	    
	// 강의 정보 삭제 실행
	@DeleteMapping("post/{no}")
	public String delete(@PathVariable("no") int no) {
	    classService.deletePost(no);
	    
	    return "redirect:/user/cpnClassList";
	}
	/*
	// 검색(필터)
	@GetMapping("/search")
	public String search(@RequestParam(value="keyword", required=false) String keyword, @RequestParam(value="arr_location", required=false) String[] arr_location, Model model) {
		List<ClassResponseDto> listFilter = null;
		
		if(arr_location.length != 0) {
			listFilter = classService.searchFilter(keyword,arr_location);
		}
		else {
			listFilter = classService.search(keyword);
		}
		
		model.addAttribute("listFilter", listFilter);
		
		return "/claco/searchlist";
	}
	*/
	
	// 검색(필터)
	@GetMapping("search")
	public String search(@RequestParam(value="page", defaultValue = "1") Integer pageNum, @RequestParam(value="keyword", required=false) String keyword, @RequestParam(value="arr_location", required=false) String[] arr_location, Model model) {
		List<ClassResponseDto> listFilter = null;
		String filter = "";
		
		if(arr_location.length != 0) {
			listFilter = classService.searchFilter(pageNum, keyword, arr_location);
		}
		else {
			listFilter = classService.search(pageNum, keyword);
		}
		
		Integer[] pageList = classService.getPageList(pageNum, keyword, arr_location);
		
		if (arr_location != null) {
			for (int i=0; i<arr_location.length; i++) {
				if (i != 0) {
					filter += "%2C";
				}
				filter += arr_location[i];
			}
		}
		
		if (keyword == null) {
			keyword = "";
		}
		
		List<ClassFilesResponseDto> listImage = classFilesService.getImage(listFilter);
		
		List<String> imageList = new ArrayList<>();
        
        for (ClassFilesResponseDto dto : listImage) {
        	String [] str = dto.getPath().split(File.separator + File.separator);
        	imageList.add("/" + str[4] + "/" + str[5] + "/" + str[6]);
        }
		
		model.addAttribute("listFilter", listFilter);
		model.addAttribute("listImg", imageList);
		model.addAttribute("pageList", pageList);
		model.addAttribute("currentPage", pageNum); // 현재 페이지에 특수효과 주기 위한 attribute
		model.addAttribute("keyword", keyword);
        model.addAttribute("filter", filter);
		
		addPrevNext(pageList, pageNum, keyword, arr_location, model);
		
		return "claco/searchlist";
	}
	
	// prev, next 버튼 구현
    public void addPrevNext(Integer [] pageList, Integer pageNum, String keyword, String [] arr_location, Model model) {
    	
		int prev = classService.getPrev(pageNum, keyword, arr_location);  // << 버튼값
		int next = classService.getNext(pageNum, keyword, arr_location);  // >> 버튼값
    	
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
    }
}
