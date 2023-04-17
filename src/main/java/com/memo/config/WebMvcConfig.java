package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.commom.FileManagerService;

@Configuration // 설정을 위한 spring bean
public class WebMvcConfig implements WebMvcConfigurer {
	
	// 서버에 업로드 된 이미지와 웹 이미지 주소와의 매핑 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // 웹 이미지의 주소:http://localhost/images/sujung_1681727936198/ballon.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 위치
	}
}
