package com.memo.post.bo;

import java.util.Collections;
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
	// private Logger logger = LoggerFactory.getLogger(PostBO.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int POST_MAX_SIZE = 3;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private FileManagerService fileManager;

	public int addPost(int userId, String loginId, String subject, String content, MultipartFile file) {

		String imagePath = null;
		if (file != null) {
			// 서버에 이미지 업로드 후 imagPath 받아옴
			imagePath = fileManager.saveFile(loginId, file);
		}

		return postMapper.insertPost(userId, subject, content, imagePath);
	}

	public void update(int userId, String loginId, int postId, String subject, String content, MultipartFile file) {

		// 기존글을 가져온다(이미지가 교체될 때 기존 이미지 제거를 위해)
		Post post = getPostBypostIduserId(postId, userId);
		if (post == null) {
			logger.warn("[update post] post is null. postId:{}, userId:{}", postId, userId);
			return;
		}

		// 업로드한 이미지가 있으면 서버 업로드 => imagePath 받아옴 => 업로드 성공하면 기존 이미지 제거
		String imagePath = null;
		if (file != null) {
			// 업로드
			imagePath = fileManager.saveFile(loginId, file);

			// 성공 여부 체크 후 기존 이미지 제거
			// -- imagePath가 null이 아닐 때(성공) 그리고 기존 이미지가 있을 때 => 기존 이미지 삭제
			if (imagePath != null && post.getImagePath() != null) {
				// 이미지 제거
				fileManager.deleteFile(post.getImagePath());
			}
		}

		// DB update
		postMapper.updatePostByPostId(postId, subject, content, imagePath);

	}

	public List<Post> getPostByUserId(int userId, Integer prevId, Integer nextId) {
		// 게시글 번호 : 10 9 8 | 7 6 5 | 4 3 2 | 1
		// 만약 4 3 2 페이지에 있을 때
		// 1) 다음: 2보다 작은 3개 DESC
		// 2) 이전: 4보다 큰 3개 ASC(5 6 7) => List reverse(7 6 5)

		// 3)만약 첫 페이지 일 때(이전, 다음 없음) DESC 3개

		String direction = null; // 방향
		Integer standardId = null; // 기준 postId

		if (prevId != null) { // 이전
			direction = "prev";
			standardId = prevId;

			List<Post> postList = postMapper.selectPostByUserId(userId, direction, standardId, POST_MAX_SIZE);
			// 가져온 리스트를 뒤집는다. 5 6 7 => 7 6 5
			Collections.reverse(postList); // 저장까지 해준다

			// return 결과 => 메소드 종료
			return postList;
		} else if (nextId != null) {
			direction = "next";
			standardId = nextId;
		}

		return postMapper.selectPostByUserId(userId, direction, standardId, POST_MAX_SIZE);
	}

	// input:postId, userId output:Post
	public Post getPostBypostIduserId(int postId, int userId) {
		return postMapper.selectPostBypostIduserId(postId, userId);
	}

	public int deletePostByPostIdUserId(int postId, int userId) {
		// 이미지 확인을 위해 기존글을 가져온다.
		Post post = getPostBypostIduserId(postId, userId);
		if (post == null) {
			logger.error("[delete post] post is null. postId:{}, userId:{}", postId, userId);
			return 0;
		}

		// 기존 이미지가 있으면 삭제
		if (post.getImagePath() != null) {
			fileManager.deleteFile(post.getImagePath());
		}

		return postMapper.deletePostByPostIdUserId(postId, userId);
	}

	// 이전 방향의 끝인지 확인
	public boolean isPrevLastPage(int userId, int prevId) {
		int postId = postMapper.selectPostIdByUserIdSort(userId, "DESC");
		return postId == prevId; // 같으면 끝 아니면 끝 아님
	}

	// 다음 방향의 끝인지 확인
	public boolean isNextLastPage(int userId, int nextId) {
		return nextId == postMapper.selectPostIdByUserIdSort(userId, "ASC"); // 같으면 끝 아니면 끝 아님
	}
}
