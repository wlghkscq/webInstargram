package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{ // web설정파일 
	
	@Value("${file.path}") // application.yml file path 경로 == C:/workspace/springbootwork/upload/
	private String uploadFolder;
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			
			registry 
				.addResourceHandler("/upload/**") // jsp 페이지에서 /upload/** 이런 주소패턴이 나오면 발동 
				.addResourceLocations("file:///"+uploadFolder)// -> file:///C:/workspace/springbootwork/upload/ 로 경로 설정 
				.setCachePeriod(60*10*6) // 60초*10*6 = 1시간 동안 이미지 캐싱 
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
		}
}
