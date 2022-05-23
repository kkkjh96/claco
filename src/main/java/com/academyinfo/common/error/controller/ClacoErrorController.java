package com.academyinfo.common.error.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 에러페이지를 처리하기 위한 컨트롤러 (ErrorController를 구현해야함)
@Controller
public class ClacoErrorController implements ErrorController {
	
	// 에러발생시 기본으로 매핑됨
	@GetMapping("/error")
    public String handleError(HttpServletRequest request) {
		// 요청한 페이지의 에러코드를 읽어온다
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            
            // template안의 404 에러 처리 페이지로 이동
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } 
            // template안의 500 에러 처리 페이지로 이동
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	return "error/500";
            }          
            // 이외의 에러 발생 시 이동
            else {
                return "error/error";
            }
        }

        return "error/error";
    }
}

