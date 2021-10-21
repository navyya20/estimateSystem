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
<title>accountList</title>
<style type="text/css">
</style>
<script>
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}

function goToAccountMod(accountInformNum){
	$("#accountInformNum").val(accountInformNum);
	$("#goToAccountMod").submit();
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	
	<div class="p-0 mb-3 col-12 container-xl text-center font-weight-bold">
		口座リスト
	</div>
	
	<!-- 퀀한 검사로 sa만 보이도록 -->
	<div class="p-0 container-lg">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-6 col-md-2">
				<c:if test="${userInform.auth eq 'sa'}">
					<button type="button" class="col-12 btn btn-secondary" onclick="location.href='accountReg'">新規作成</button>
				</c:if>
			</div>
		</div>
	</div>
	
	<div class="d-block" style="height: 8px;"></div>
	
	<div class="p-0 container-lg">
		<div class="p-0 d-flex bg-dark text-white">
			<div class="p-0 col-4 col-md-3 d-flex">
				<div class="mt-2 mb-2 col-12 col-md-12 p-0 text-center">口座情報名</div>
			</div>
			<div class="p-0 col-8 col-md-9 d-flex">
				<div class="mt-2 mb-2 col-4 col-md-2 p-0 text-center">銀行名</div>
				<div class="mt-2 mb-2 col-md-2 p-0 text-center d-none d-md-inline">支店名</div>
				<div class="mt-2 mb-2 col-md-2 p-0 text-center d-none d-md-inline">預金区分</div>
				<div class="mt-2 mb-2 col-4 col-md-2 p-0 text-center">口座番号</div>
				<div class="mt-2 mb-2 col-4 col-md-2 p-0 text-center">口座名</div>
				<div class="mt-2 mb-2 col-md-2 p-0 text-center d-none d-md-inline">フリカナ</div>
			</div>
		</div>
	</div>
	
	<c:forEach items="${accountList}" var="account" varStatus="status">
		<div class="p-0 container-lg">
			<div class="p-0 d-flex">
				<div class="p-0 col-4 col-md-3 d-flex">
					<div class="mt-2 mb-2 col-12 col-md-12 p-0 text-center text-break"><a onclick="goToAccountMod(${account.accountInformNum})" style="cursor: pointer; color: blue;">${account.accountInformName}</a></div>
				</div>
				<div class="p-0 col-8 col-md-9 d-flex">
					<div class="mt-2 mb-2 col-4 col-md-2 p-0 text-center text-break">${account.bankName}</div>
					<div class="mt-2 mb-2 col-md-2 p-0 text-center text-break d-none d-md-inline">${account.branchName}</div>
					<div class="mt-2 mb-2 col-md-2 p-0 text-center text-break d-none d-md-inline">${account.depositeClassification}</div>
					<div class="mt-2 mb-2 col-4 col-md-2 p-0 text-center text-break">${account.accountNumber}</div>
					<div class="mt-2 mb-2 col-4 col-md-2 p-0 text-center text-break">${account.accountName}</div>
					<div class="mt-2 mb-2 col-md-2 p-0 text-center text-break d-none d-md-inline">${account.hurigana}</div>
				</div>
			</div>
		</div>	
	</c:forEach>
	
	<form id="goToAccountMod" action="accountMod" method="post" accept-charset="utf-8">
		<input type="hidden" id="accountInformNum" name="accountInformNum" value="">
	</form>
</body>
</html>
