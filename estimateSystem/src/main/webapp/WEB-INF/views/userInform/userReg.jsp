<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1" >
<meta name="format-detection" content="telephone=no">
<script src="../js/jquery-3.5.1.min.js"></script>

<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/common/common.css?ver=1.130">
<script src="../js/bootstrap.bundle.js"></script>
	<title>userReg</title>
<style type="text/css">
</style>
<script>
$(document).ready(function(){
	$("#userName").on("change",function(){limitString("userName",30);})
});
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}
//이메일 체크 정규식
function isEmail(asValue) {
	var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	return regExp.test(asValue); // 형식에 맞는 경우 true 리턴	
}
// 핸드폰 번호 체크 정규식
function isPhone(asValue) {
	var regExp = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;
	return regExp.test(asValue); // 형식에 맞는 경우 true 리턴
}
//ID,비밀번호 체크 정규식
function isPassword(asValue) {
	var regExp = /^[A-Za-z0-9]{3,20}$/; //  3 ~ 20자 영문, 숫자 조합
	return regExp.test(asValue); // 형식에 맞는 경우 true 리턴
}

function insertUser(){
	var userId = $("#userId").val();
	var password = $("#password").val();
	var userName = $("#userName").val();
	var departmentNum = $("#departmentNum").val();
	var positionNum = $("#positionNum").val();
	var auth = $("#auth").val();
	console.log(userId);
	console.log(password);
	console.log(userName);
	console.log(departmentNum);
	console.log(positionNum);
	console.log(auth);
	if(!isPassword(userId)){
		alert("ID又passwordは英数字３～２０文字でお願いします。");
		$("#userId").focus();
		return;
	}
	if(!isPassword(password)){
		alert("ID又passwordは英数字３～２０文字でお願いします。");
		$("#password").focus();
		return;
	}
	document.getElementById("insertUser").submit();
}

</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	
	<div class="p-0 mb-3 col-12 container-xl text-center font-weight-bold">
		ユーザ情報登録
	</div>
	
	<div class="row align-items-center col-12 p-0 m-0">
		<div id="login_div" class="d-flex justify-content-center col-12 p-0">
			<form class="col-12 d-flex justify-content-center" id="insertUser" action="insertUser" method="post" accept-charset="utf-8">
				<div class="d-flex flex-column col-10 col-md-6">
					<!-- id -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="userId" id="inputGroup-sizing-default">*ID</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="userId" name="userId">
					</div>
					
					<!-- pw -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="password" id="inputGroup-sizing-default">*PW</label>
						</div>
						<input type="password" class="pl-2 pr-0 col-8 form-control" id="password" name="password">
					</div>
					
					<!-- name -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="userName" id="inputGroup-sizing-default">名前</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="userName" name="userName" maxlength="30">
					</div>
					
					<!-- department -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="departmentNum" style="text-align: center;">所属</label>
						</div>
						<select class="p-0 col-8 custom-Select" id="departmentNum" name="departmentNum">
							<option selected value="0">選択してください．．．</option>
							<c:forEach items="${departments}" var="department" varStatus="status">
								<option value="${department.departmentNum}">${department.department}</option>
							</c:forEach>
						</select>
					</div>
					
					<!-- position -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="positionNum" style="text-align: center;">役職</label>
						</div>
						<select class="p-0 col-8 custom-Select" id="positionNum" name="positionNum">
							<option selected value="0">選択してください．．．</option>
							<c:forEach items="${positions}" var="position" varStatus="status">
								<option value="${position.positionNum}">${position.position}</option>
							</c:forEach>
						</select>
					</div>
					
					<!-- auth -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="auth" style="text-align: center;">権限</label>
						</div>
						<select class="p-0 col-8 custom-Select" id="auth" name="auth">
							<option selected value="u">選択してください．．．</option>
							<c:forEach items="${auths}" var="auth" varStatus="status">
								<option value="${auth.auth}">
									<c:choose>
										<c:when test="${auth.auth == 'u'}">ユーザ</c:when>
										<c:when test="${auth.auth == 'a'}">管理者</c:when>
										<c:when test="${auth.auth == 'sa'}">システム管理者</c:when>
									</c:choose>
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="d-block" style="height: 8px;"></div>
	<div class="row align-items-center col-12 p-0 m-0">
		<div id="login_div" class="d-flex justify-content-center col-12 p-0">
			<div class="pl-4 pr-4 d-flex flex-column col-10 col-md-6">
				<!-- 登録button -->
					<button type="button" class="col-12 btn btn-secondary" onclick="insertUser()">ユーザ登録</button>
			</div>
		</div>
	</div>
</body>
</html>
