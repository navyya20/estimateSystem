<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1" >
<meta name="format-detection" content="telephone=no">
<!-- 아래 주석은 IE와 Opera에서의 뷰포트 룰. 필요하다면 css파일에 셋팅해주어야한다. -->
<!-- <style type="text/css">
@-ms-viewport{width:device-width;}
@-o-viewport{width:device-width;}
@viewport{width:device-width;}
</style> -->


</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<a class="navbar-brand" href="#">ホーム</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavDropdown">
			<ul class="navbar-nav">
				<li class="nav-item active">
					<c:if test="${sessionScope.userInform.auth eq 'a' or sessionScope.userInform.auth eq 'u'}">
						<a class="nav-link" href="./estimateList">見積請求書 <span class="sr-only">(current)</span></a>
					</c:if>
				</li>
				<li class="nav-item active">
					<!-- sa는 접근x -->
					<c:if test="${sessionScope.userInform.auth eq 'a'}">
						<a class="nav-link" href="workflowWaitingList">承認待機(<span id=waitApproval></span>)</a>
					</c:if>
				</li>
				
				<li class="nav-item dropdown active">
					<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						設定
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<c:if test="${sessionScope.userInform.auth eq 'sa'}">
							<a class="dropdown-item" href="./imgList">印鑑・LOGO</a>
							<a class="dropdown-item" href="./workflowList">Workflow</a>
						</c:if>
						<c:if test="${sessionScope.userInform.auth eq 'sa' or sessionScope.userInform.auth eq 'a'}">
							<a class="dropdown-item" href="./companyList">会社情報</a>
							<a class="dropdown-item" href="./accountList">口座情報</a>
						</c:if>
						<a class="dropdown-item" href="./userList">ユーザ情報</a>
					</div>
				</li>
				<li class="nav-item active">
					<a class="nav-link" href="../">LogOut</a>
				</li>
			</ul>
		</div>
	</nav>
	<script>
	$(document).ready(function(){
		getWatingWorkflow();
	})
	function getWatingWorkflow(){
		$.ajax(
				{
					url: "getWatingWorkflow",
					type: 'get',
					data: {},
					dataType:"text",
					success: function(r){
						$('#waitApproval').html(r);
					},
					error: function(e){
						console.log(JSON.stringify(e));
						alert('エラー！');
					}
				}		
		);
	}
	</script>
</body>
</html>