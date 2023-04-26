package com.memo.post.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.model.Post;

@Repository
public interface PostMapper {
	
	public int insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	public List<Post> selectPostByUserId(
			@Param("userId") int userId,
			@Param("direction") String direction,
			@Param("standardId") Integer standardId,
			@Param("limit") int limit);
	
	public Post selectPostBypostIduserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public void updatePostByPostId(
			@Param("postId") int postId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	public int deletePostByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	public int selectPostIdByUserIdSort(
			@Param("userId") int userId,
			@Param("sort") String sort);
	
}
