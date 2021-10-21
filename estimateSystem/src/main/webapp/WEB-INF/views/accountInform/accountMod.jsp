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
<link rel="stylesheet" type="text/css" href="../css/common/common.css">
<script src="../js/bootstrap.bundle.js"></script>
<script src="../js/inputController.js"></script>
<title>accountMod</title>
<style type="text/css">
</style>
<script>
$(document).ready(function(){
	$("#accountInformName").on("change",function(){limitString("accountInformName",30);})
	$("#bankName").on("change",function(){limitString("bankName",30);})
	$("#depositeClassification").on("change",function(){limitString("depositeClassification",10);})
	$("#branchName").on("change",function(){limitString("branchName",20);})
	$("#accountNumber").on("change",function(){limitString("accountNumber",20);})
	$("#accountName").on("change",function(){limitString("accountName",30);})
	$("#hurigana").on("change",function(){limitString("hurigana",30);})
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

function updateAccount(){
	var accountInformName = $("#accountInformName").val();
	var bankName = $("#bankName").val();
	var depositeClassification = $("#depositeClassification").val();
	var branchName = $("#branchName").val();
	var accountNumber = $("#accountNumber").val();
	var accountName = $("#accountName").val();
	var hurigana = $("#hurigana").val();
	console.log(accountInformName);
	console.log(bankName);
	console.log(depositeClassification);
	console.log(branchName);
	console.log(accountNumber);
	console.log(accountName);
	console.log(hurigana);
	if(accountInformName=""){
		alert("口座情報名を入力してください。情報同士に区別するためです。");
		$("#userId").focus();
		return;
	}
	document.getElementById("updateAccount").submit();
}

function deleteAccount(){
	document.getElementById("deleteAccount").submit();
}



</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	<div class="p-0 mb-3 col-12 container-xl text-center font-weight-bold">
		口座情報修正
	</div>
	<div class="row align-items-center col-12 p-0 m-0">
		<div id="login_div" class="d-flex justify-content-center col-12 p-0">
			<form class="col-12 d-flex justify-content-center p-0" id="updateAccount" action="updateAccount" method="post" accept-charset="utf-8">
				<input type="hidden" id="accountInformNum" name="accountInformNum" value="${account.accountInformNum}">
				<div class="d-flex flex-column col-10 col-md-6 p-0">
					<!-- accountInformName -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="accountInformName" id="inputGroup-sizing-default">*口座情報名</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="accountInformName" name="accountInformName" value="${account.accountInformName}" maxlength="40">
					</div>
					
					<!-- bankName -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="bankName" id="inputGroup-sizing-default">銀行名</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="bankName" name="bankName" value="${account.bankName}" maxlength="30">
					</div>
					
					<!-- depositeClassification -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="depositeClassification" id="inputGroup-sizing-default">預金区分</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="depositeClassification" name="depositeClassification" value="${account.depositeClassification}" maxlength="10">
					</div>
					
					<!-- branchName -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="branchName" id="inputGroup-sizing-default">支店名</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="branchName" name="branchName" value="${account.branchName}" maxlength="20">
					</div>
					<!-- accountNumber -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="accountNumber" id="inputGroup-sizing-default">口座番号</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="accountNumber" name="accountNumber" value="${account.accountNumber}" maxlength="20">
					</div>
					
					<!-- accountName -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="accountName" id="inputGroup-sizing-default">口座名</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="accountName" name="accountName" value="${account.accountName}" maxlength="30">
					</div>
					
					<!-- hurigana -->
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="hurigana" id="inputGroup-sizing-default">hurigana</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="hurigana" name="hurigana" value="${account.hurigana}" maxlength="30">
					</div>
					
					<div class="d-block" style="height: 12px;"></div>
					
				</div>
			</form>
		</div>
	</div>
	
	<!-- update button -->
	<div class="row d-flex justify-content-center col-12 p-0 m-0">
		<div class="p-0 d-flex col-10 col-md-6">
			<div class="p-0 d-flex col-12">
				<div class="pl-2 pr-2 d-flex col-6">
					<button type="button" class="col-12 btn btn-secondary" onclick="updateAccount()">更新</button>
				</div>
				<div class="pl-2 pr-2 d-flex col-6">
					<button type="button" class="col-12 btn btn-secondary" onclick="deleteAccount()">削除</button>
				</div>
			</div>
		</div>
	</div>
	
	<form id="deleteAccount" action="deleteAccount" method="post" accept-charset="utf-8">
		<input type="hidden" name="accountInformNum" value="${account.accountInformNum}">
	</form>
</body>
</html>
