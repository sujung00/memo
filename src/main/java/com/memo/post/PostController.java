package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	private PostBO postBO;

	// http://localhost/post/post_list_view
	@GetMapping("/post_list_view")
	public String postListView(
			@RequestParam(value="prevId", required=false) Integer prevIdParam,
			@RequestParam(value="nextId", required=false) Integer nextIdParam,
			Model model, HttpSession session) {
		Integer userId = (Integer)session.getAttribute("userId");
		
		if(userId == null) {
			// 비로그인 이면 로그인 페이지로 이동
			return "redirect:/user/sign_in_view";
		}
		// 0 1 2
		// 10 9 8
		int prevId = 0;
		int nextId = 0;
		List<Post> postList = postBO.getPostByUserId(userId, prevIdParam, nextIdParam);
		if(postList.isEmpty()== false) { // postList가 비어있을 때 에러 방지 => []
			// 리스트가 비어있지 않으면 처리
			prevId = postList.get(0).getId(); // 가져온 리스트의 맨 앞(큰 id)
			nextId = postList.get(postList.size()-1).getId(); // 가져온 리스트의 가장 끝 값(작은 id)
		
			// 이전 방향의 끝인가?
			// prevId와 post 테이블의 가장 큰 id와 같다면 이전 페이지 없음
			if(postBO.isPrevLastPage(userId, prevId)) {
				prevId = 0;
			}
			
			// 다음 방향의 끝인가?
			// nextId와 post 테이블의 가장 작은 id와 같다면 다음 페이지 없음
			if(postBO.isNextLastPage(userId, nextId)) {
				nextId = 0;
			}
		}
		
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
		
		model.addAttribute("posts", postList);
		model.addAttribute("view", "post/postList");
		
		return "template/layout";
	}
	
	/**
	 * 글쓰기 화면
	 * @param model
	 * @return
	 */
	// http://localhost/post/post_create_view
	@GetMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("view", "post/postCreate");
		
		return "template/layout";
	}
	
	/**
	 * 글 상세 화면
	 * @param postId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("post_detail_view")
	public String postDetailView(
			@RequestParam("postId") int postId,
			HttpSession session,
			Model model) {
		// db select by postId, (userId)
		int userId = (int)session.getAttribute("userId");
		Post post = postBO.getPostBypostIduserId(postId, userId);
		
		model.addAttribute("post", post);
		model.addAttribute("view", "post/postDetail");
		
		return "template/layout";
	}
}
