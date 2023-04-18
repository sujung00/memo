<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 상세/수정</h1>
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요" value="${post.subject}">
		<textarea rows="10" class="form-control" id="content" placeholder="글 내용을 입력하세요">${post.content}</textarea>
		
		<%-- 이미지가 있을 때만 이미지 영역 추가 --%>
		<c:if test="${not empty post.imagePath}">
			<div class="mt-3">
				<img alt="업로드된 이미지" src="${post.imagePath}" width="300">
			</div>
		</c:if>
		
		<div class="d-flex justify-content-end my-3">
			<input type="file" id="file" accept=".jpg, .jpeg, .png, .gif">
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="postDeleteBtn" class="btn btn-secondary">삭제</button>
			<div>
				<a href="/post/post_list_view" class="btn btn-dark">목록으로</a>
				<button type="button" id="saveBtn" class="btn btn-warning">수정</button>
			</div>
		</div>
	</div>
</div>