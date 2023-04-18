package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.commom.FileManagerService;
import com.memo.post.dao.PostMapper;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
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
	
	public List<Post> getPostByUserId(int userId){
		return postMapper.selectPostByUserId(userId);
	}
	
	// input:postId, userId		output:Post
	public Post getPostBypostIduserId(int postId, int userId) {
		return postMapper.selectPostBypostIduserId(postId, userId);
	}
}
