package com.academyinfo.common.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

// 로그인 실패처리 핸들러
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
	@Override
    //httpServletRequest -> request 에 대한 정보 , httpServletResponse -> response 에 대해 설정할 수 있는 변수
    //AuthenticationException e -> 로그인 실패 시 예외에 대한 정보를 담고 있음.
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("login fail handler");

        String error;
        if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException){
            error="아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (e instanceof UsernameNotFoundException){
            error="존재하지 않는 아이디입니다.";
        }
        else{
            error="알 수 없는 이유로 로그인이 안 되고 있습니다.";
        }

        error= URLEncoder.encode(error,"UTF-8");//한글 인코딩 깨지는 문제 방지
        setDefaultFailureUrl("/user/login?error=" + error);
        super.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
    }
}
