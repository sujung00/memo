package com.memo.commom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // 일반적인 spring bean (자식으로는 controller, service, repository, 등등)
public class FileManagerService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 실제 업로드 된 이미지가 저장될 경로(서버)
	public static final String FILE_UPLOAD_PATH = "C:\\SUJUNG\\6_spring_project\\memo\\workspace\\images/";
	
	// input:MultipartFile(이미지파일), loginId
	// output:image path(String)
	public String saveFile(String loginId, MultipartFile file) {
		// 파일 디렉토리(폴더) 	ex) aaaa_165645231/sun.png => 사용자명_시간String
		
		// aaaa_165645231/
		String directoryName = loginId + "_" + System.currentTimeMillis() + "/";
		// C:\\SUJUNG\\6_spring_project\\memo\\workspace\\images/aaaa_165645231/
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(filePath);
		if(directory.mkdir() == false) {
			return null; // 폴더 만드는데 실패 시 이미지경로 null
		}
		
		// 파일 업로드: byte 단위로 업로드
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename()); // 디렉토리명 + originalFileName(사용자가 올린 파일명)(한글명 업로드 불가능) => 나중에 이미지명 변경
			Files.write(path, bytes); // 파일 업로드
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		// 파일 업로드가 성공했으면 이미지 url path를 리턴한다.
		// http://localhost/images/aaaa_165645231/sun.png
		return "/images/" + directoryName + file.getOriginalFilename(); // originalFilename은 사용자가 올린 파일명
	}
	
	// input:imagePath(String)	output:X
	public void deleteFile(String imagePath) { // imagePath: /images/aaaa_165645231/sun.png
		// C:\\SUJUNG\\6_spring_project\\memo\\workspace\\images/aaaa_165645231/sun.png
		// 겹치는 images 경로를 제거 - imagePath 안에 있는 /images/ 구문 제거
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		// 이미지 제거
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[이미지 삭제] 이미지 삭제 실패. imagePath:{}", imagePath);
				return;
			}
		}
		
		// 디렉토리(폴더) 삭제
		path = path.getParent();
		if(Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[이미지 삭제] 디렉토리 삭제 실패. imagePath:{}", imagePath);
			}
		}
		
	}
}
