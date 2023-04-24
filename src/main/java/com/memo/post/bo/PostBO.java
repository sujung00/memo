package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.commom.FileManagerService;
import com.memo.post.dao.PostMapper;
import com.memo.post.model.Post;

@Service
public class PostBO {
	//private Logger logger = LoggerFactory.getLogger(PostBO.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostMapper postMapper;

	@Autowired
	private FileManagerService fileManager;
	
	public int addPost(int userId, String loginId,
			String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		if(file != null) {
			// 서버에 이미지 업로드 후 imagPath 받아옴
			imagePath = fileManager.saveFile(loginId, file);
		}
		
		return postMapper.insertPost(userId, subject, content, imagePath);
	}
	
	public void update(int userId, String loginId,
			int postId,	String subject, String content, MultipartFile file) {
		
		// 기존글을 가져온다(이미지가 교체될 때 기존 이미지 제거를 위해)
		Post post = getPostBypostIduserId(postId, userId);
		if (post == null) {
			logger.warn("[update post] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}
		
		// 업로드한 이미지가 있으면 서버 업로드 => imagePath 받아옴 => 업로드 성공하면 기존 이미지 제거
		String imagePath = null;
		if(file != null) {
			// 업로드
			imagePath = fileManager.saveFile(loginId, file);
			
			// 성공 여부 체크 후 기존 이미지 제거
			// -- imagePath가 null이 아닐 때(성공) 그리고 기존 이미지가 있을 때 => 기존 이미지 삭제
			if(imagePath != null && post.getImagePath() != null) {
				// 이미지 제거
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		// DB update
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
		
	}
	
	public List<Post> getPostByUserId(int userId){
		return postMapper.selectPostByUserId(userId);
	}
	
	// input:postId, userId		output:Post
	public Post getPostBypostIduserId(int postId, int userId) {
		return postMapper.selectPostBypostIduserId(postId, userId);
	}
}
