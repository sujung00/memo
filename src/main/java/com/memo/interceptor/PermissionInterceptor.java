package com.memo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component // 스프링 빈
public class PermissionInterceptor implements HandlerInterceptor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		
		// 요청 url을 가져온다
		String uri = request.getRequestURI();
		logger.info("[$$$$$$$ preHandler] uri:{}", uri);
		
		// 로그인 여부 확인 - 세션 확인
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// 비로그인 && /post 로 온 경우 => 로그인 페이지로 redirect, return false(기존 컨트롤러 수행 방지) 
		if(userId == null && uri.startsWith("/post")) {
			response.sendRedirect("/user/sign_in_view");
			return false; // controller 수행 안함
		}
		
		// 로그인 && /user 로 온 경우 => 글 목록 페이지로 redirect, return false(기존 컨트롤러 수행 방지)
		if(userId != null && uri.startsWith("/user")) {
			response.sendRedirect("/post/post_list_view");
			return false;
		}
		
		return true; // 컨트롤러 수행
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mav) {
		
		// view 객체가 존재한다는 것은 아직 jsp가 HTML로 변환되기 전이다.
		logger.info("[####### postHandle]");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		// jsp가 HTML로 최종 변환 된 후
		logger.info("[@@@@@@@ afterCompletion]");
	}
}
