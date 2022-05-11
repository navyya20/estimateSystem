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
<script src="../js/inputController.js"></script>
<title>companyMod</title>
<style type="text/css">
</style>
<script>
$(document).ready(function(){
	$("#companyInformName").on("change",function(){limitString("companyInformName",30);})
	$("#companyName").on("change",function(){limitString("companyName",30);})
	$("#representative").on("change",function(){limitString("representative",30);})
	$("#address").on("change keyup paste",function(){limitLine("address",8);})
	$("#email").on("change",function(){limitString("email",30);})
	$("#incharge").on("change",function(){limitString("incharge",30);})
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

function updateCompany(){
	var companyInformName = $("#companyInformName").val();
	var companyName = $("#companyName").val();
	var representative = $("#representative").val();
	var phoneNumber = $("#phoneNumber").val();
	var address = $("#address").val();
	var post = $("#post").val();
	var email = $("#email").val();
	var incharge = $("#incharge").val();

	if(companyInformName=""){
		alert("会社情報名を入力してください。情報同士に区別するためです。");
		$("#companyInformName").focus();
		return;
	}

	document.getElementById("updateCompany").submit();
}

function deleteCompany(){
	document.getElementById("deleteCompany").submit();
}



</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	
	<div class="p-0 mb-3 col-12 container-xl text-center font-weight-bold">
		自社情報修正
	</div>
	
	<div class="row align-items-center col-12 p-0 m-0">
		<div class="d-flex justify-content-center col-12 p-0">
			<form class="col-12 d-flex justify-content-center p-0" id="updateCompany" action="updateCompany" method="post" accept-charset="utf-8">
				<input type="hidden" id="companyInformNum" name="companyInformNum" value="${company.companyInformNum}">
				<div class="d-flex flex-column col-10 col-md-6 p-0">
					<!-- companyInformName -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="companyInformName" id="inputGroup-sizing-default">*自社情報タイプ</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="companyInformName" name="companyInformName" maxlength="40" value="${company.companyInformName}">
					</div>
					<%-- 
					<!-- companyName -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="companyName" id="inputGroup-sizing-default">会社名</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="companyName" name="companyName" maxlength="40" value="${company.companyName}">
					</div>
					
					<!-- post -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="post" id="inputGroup-sizing-default">post</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="post" name="post" maxlength="8" value="${company.post}">
					</div>
					 --%>
					<!-- address -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="address" id="inputGroup-sizing-default">表示内容<br>（最大8行）</label>
						</div>
						<textarea class="pl-2 pr-0 col-8 form-control" id="address" name="address" maxlength="300" placeholder="社名、住所、電話番号、e-mail、代表者、担当者など" rows="9">${company.address}</textarea>
					</div>
					<%-- 
					<!-- phoneNumber -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="phoneNumber" id="inputGroup-sizing-default">電話番号</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="phoneNumber" name="phoneNumber" maxlength="20" value="${company.phoneNumber}">
					</div>
					
					<!-- representative -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="representative" id="inputGroup-sizing-default">代表者</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="representative" name="representative" maxlength="30" value="${company.representative}">
					</div>
					
					<!-- incharge -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="incharge" id="inputGroup-sizing-default">担当者</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="incharge" name="incharge" maxlength="30" value="${company.incharge}">
					</div>
					
					<!-- email -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="email" id="inputGroup-sizing-default">email</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="email" name="email" maxlength="40" value="${company.email}">
					</div>
					 --%>
					<div class="d-block" style="height: 12px;"></div>
				</div>
			</form>
		</div>
	</div>
	
	<!-- update button -->
	<div class="row d-flex justify-content-center col-12 p-0 m-0">
		<div class="p-0 d-flex col-10 col-md-6">
			<div class="pl-2 pr-2 d-flex col-6">
				<button type="button" class="col-12 btn btn-secondary" onclick="updateCompany()">更新</button>
			</div>
			<div class="pl-2 pr-2 d-flex col-6">
				<button type="button" class="col-12 btn btn-secondary" onclick="deleteCompany()">削除</button>
			</div>
		</div>
	</div>
	
	<form id="deleteCompany" action="deleteCompany" method="post" accept-charset="utf-8">
		<input type="hidden" name="companyInformNum" value="${company.companyInformNum}">
	</form>
</body>
</html>
