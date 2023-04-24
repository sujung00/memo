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
				<button type="button" id="saveBtn" class="btn btn-warning" data-post-id="${post.id}">수정</button>
			</div>
		</div>
	</div>
</div>

<script>
$(document).ready(function(){
	// 수정 버튼 클릭
	$("#saveBtn").on("click", function(){
		let subject = $("#subject").val().trim();
		let content = $("#content").val();
		let file = $("#file").val(); // 파일 이름 => C:\fakepath\cherry-blossom-tree-1225186_960_720.jpg
		let postId = $(this).data("post-id");
		
		console.log(subject);
		console.log(content);
		console.log(file);
		console.log(postId);

		if(!subject) {
			alert("제목을 입력하세요");
			return;
		}
		if(!content) {
			alert("내용을 입력하세요");
			return;
		}
		
		// 파일이 업로드 된 경우 확장자 체크
		if(file){
			let ext = file.split(".").pop().toLowerCase();
			
			if($.inArray(ext, ["jpg", "jpeg", "png", "gif"]) == -1){
				alert("이미지 파일만 업로드 할 수 있습니다.");
				
				$("#file").val(""); // 파일을 비운다.
				return;
			}
		}
		
		// 폼 태그를 자바스크립트에서 만든다(이미지 때문)
		let formData = new FormData();
		formData.append("postId", postId);
		formData.append("subject", subject);
		formData.append("content", content);
		formData.append("file", $("#file")[0].files[0]);
		
		$.ajax({
			//request
			type:"PUT"
			, url:"/post/update"
			, data:formData
			, encType:"multipart/form-data" // 파일 업로드를 위한 필수 설정
			, processData:false // 파일 업로드를 위한 필수 설정
			, contentType:false // 파일 업로드를 위한 필수 설정
			
			//response
			, success:function(data){
				if(data.code == 1){
					alert("메모가 수정되었습니다");
					location.reload();
				} else{
					alert(data.errorMessage);
				}
			}
			, error:function(request, status, error){
				alert("메모 수정 시 실패했습니다");
			}
		})
	});
});
</script>