<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1" >
<meta name="format-detection" content="telephone=no">

<script src="../js/jquery-3.5.1.min.js"></script>
 
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/common/common.css">
<script src="../js/bootstrap.bundle.js"></script>
<title>userList</title>
<style type="text/css">
</style>
<script>
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}

function goToUserMod(userNum){
	$("#userNum").val(userNum);
	$("#goToUserMod").submit();
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	
	<div class="p-0 mb-3 col-12 container-xl text-center font-weight-bold">
		ユーザリスト
	</div>
	
	<!-- 퀀한 검사로 sa만 보이도록 -->
	<div class="p-0 container-lg">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-6 col-md-2">
				<c:if test="${userInform.auth eq 'sa'}">
					<button type="button" class="col-12 btn btn-secondary" onclick="location.href='userReg'">新規作成</button>
				</c:if>
			</div>
		</div>
	</div>
	
	<div class="d-block" style="height: 8px;"></div>
	
	<div class="p-0 container-lg">
		<div class="p-0 d-flex bg-dark text-white">
			<div class="p-0 col-12 col-md-6 d-flex">
				<div class="mt-2 mb-2 col-3 col-md-3 p-0 text-center">ID</div>
				<div class="mt-2 mb-2 col-3 col-md-3 p-0 text-center">名前</div>
				<div class="mt-2 mb-2 col-3 col-md-3 p-0 text-center">所属</div>
				<div class="mt-2 mb-2 col-3 col-md-3 p-0 text-center">役職</div>
			</div>
			<div class="p-0 col-0 col-md-6 d-flex">
				<div class="mt-2 mb-2 col-md-2 p-0 text-center d-none d-md-inline">権限</div>
				<div class="mt-2 mb-2 col-md-5 p-0 text-center d-none d-md-inline">前回ログイン</div>
				<div class="mt-2 mb-2 col-md-5 p-0 text-center d-none d-md-inline">作成日</div>
			</div>
		</div>
	</div>
	
	<c:forEach items="${userList}" var="user" varStatus="status">
		<div class="p-0 container-lg">
			<div class="p-0 d-flex">
				<div class="p-0 col-12 col-md-6 d-flex">
					<div class="col-3 col-md-3 p-0 text-center align-self-center"><a onclick="goToUserMod(${user.userNum})" style="cursor: pointer; color: blue;">${user.userId}</a></div>
					<div class="col-3 col-md-3 p-0 text-center align-self-center">${user.userName}</div>
					<div class="col-3 col-md-3 p-0 text-center align-self-center">${user.department}</div>
					<div class="col-3 col-md-3 p-0 text-center align-self-center">${user.position}</div>
				</div>
				<div class="p-0 col-0 col-md-6 d-flex">
					<div class="col-md-2 p-0 text-center align-self-center d-none d-md-inline">
						<c:choose>
							<c:when test="${user.auth == 'u'}">ユーザ</c:when>
							<c:when test="${user.auth == 'a'}">管理者</c:when>
							<c:when test="${user.auth == 'sa'}">システム</c:when>
						</c:choose>  
					</div>
					<div class="col-md-5 p-0 text-center align-self-center d-none d-md-inline">
						<fmt:parseDate value="${user.loginDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd HH:mm:ss"/>
					</div>
					<div class="col-md-5 p-0 text-center align-self-center d-none d-md-inline">
						<fmt:parseDate value="${user.insertDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd HH:mm:ss"/>
					</div>
				</div>
			</div>
		</div>
		<div class="d-block" style="height: 3px;"></div>
		<div class="d-block d-md-none" style="height: 5px;"></div>
	</c:forEach>
	
	<form id="goToUserMod" action="userMod" method="post" accept-charset="utf-8">
		<input type="hidden" id="userNum" name="userNum" value="">
	</form>
</body>
</html>
