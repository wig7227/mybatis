<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#list-area {
		border: 1px solid;
		border-collapse: collapse;
	}
	#list-area tr, #list-area th, #list-area td {
		border: 1px solid;
	} 
	.outer a {
		text-decoration: none;
		color: black;
	}
</style>

<script>
	$(() => {
		$('#replyInsert').click(function() {
			// serialize() : 폼안의 input, select, textarea등의 value의 값을 간단하게 표준 url인코딩 형태 문자열로 만들어줌
			// content=내용&id=값&writer=값
			$.ajax({
				url: 'detail.bo',
				data: {
					bno: ${b.boardNo},
					content: $('#content').val(),
					writer: "${loginUser.userName}"
				},
				type: 'post',
				success: function(result) {
					if(result > 0) {
						replyList();
					}
				},
				error: function() {
					console.log("댓글달기 통신 실패");
				}
			})
		})
	})	
</script>

</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>
	<div class="outer">
		<h2>게시판 상세보기</h2>
		
		<table id="list-area" align="center">
			<tr>
				<td>글번호</td>
				<td>${ b.boardNo }</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>${ b.boardTitle }</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${ b.boardWriter }</td>
			</tr>
			<tr>
				<td>조회수</td>
				<td>${ b.count }</td>
			</tr>
			<tr>
				<td>작성일</td>
				<td>${ b.createDate }</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>${ b.boardContent }</td>
			</tr>
		</table>
		<br>
		<table id="list-area" align="center">
			<c:choose>
				<c:when test="${ !empty loginUser }">
				<tr>
					<td>댓글 작성</td>
					<td><textarea rows="3" cols="50" id="content"></textarea></td>
					<td><input type="button" id="replyInsert" value="댓글작성"></td>	
				</tr>
				</c:when>
				<c:otherwise>
					<td>댓글 작성</td>
					<td><textarea rows="3" cols="50" readonly></textarea></td>
					<td><input type="button" value="로그인 후 이용가능" disabled/></td>
				</c:otherwise>
			</c:choose>
			<tr>
				<td colspan="3" style="text-align:center"> 댓글 : ${reply.size() }</td>
			</tr>
			<c:forEach var="r" items="${reply}">
				<tr>
					<td>${r.replyWriter }</td>
					<td>${r.replyContent }</td>
					<td>${r.createDate }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>