<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 목록</h1>
		
		<table class="table">
			<thead>
				<tr>
					<th>No.</th>
					<th>제목</th>
					<th>작성날짜</th>
					<th>수정날짜</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${posts}" var="postList" varStatus="status">
				<tr>
					<td>${status.count}</td>
					<td>${postList.subject}</td>
					<td>
						<fmt:formatDate value="${postList.createdAt}" pattern="yyyy-MM-dd"/>
					</td>
					<td>
					<fmt:formatDate value="${postList.updatedAt}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="d-flex justify-content-end">
			<a href="/post/post_create_view" class="btn btn-warning">글쓰기</a>
		</div>
	</div>
</div>