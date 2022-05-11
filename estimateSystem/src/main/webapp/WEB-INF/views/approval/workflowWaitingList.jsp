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
<link rel="stylesheet" type="text/css" href="../css/common/common.css?ver=1">
<script src="../js/bootstrap.bundle.js"></script>
<title>requestList</title>
<style type="text/css">
</style>
<script>
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}

function readDocument(system,documentTypeName,documentNum){
	//billSystem은 열람페이지를 estimateSystem와 공유하기때문에 bill로 들어올경우 estimate로 바꿔준다.
	if(system == "bill"){system="estimate"}
	
	$('#readDocument').attr("action","read"+system.charAt(0).toUpperCase()+system.slice(1));
	$('#documentNum').val(documentNum);
	$('#documentTypeName').val(documentTypeName);
	$('#approveMode').val("on");
	
	$('#readDocument').submit();
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	
	
	<div class="d-block" style="height: 8px;"></div>
	
	<div class="container-fluid pr-md-3 pl-md-3">
		<div class="p-0 d-flex bg-dark text-white">
			<div class="p-0 col-12 d-flex">
				<div class="mt-2 mb-2 col-2 p-0 text-center">種類</div>
				<div class="mt-2 mb-2 col-1 p-0 text-center">文書番号</div>
				<div class="mt-2 mb-2 col-2 p-0 text-center">顧客</div>
  				<div class="mt-2 mb-2 col-2 p-0 text-center">件名</div>
				<div class="mt-2 mb-2 col-1 p-0 text-center">部署</div>
				<div class="mt-2 mb-2 col-1 p-0 text-center">作成者</div>
				<div class="mt-2 mb-2 col-2 p-0 text-center">承認依頼日時</div>
				<!--지금은 어차피 승인경로가1명이라 필요없는 정보 <div class="mt-2 mb-2 col-2 p-0 text-center">更新日時</div> -->
			</div>
		</div>
	</div>
	<c:set var = "arr" value='<%= new String[]{"","見積書","請求書"} %>' />
	<c:set var = "sysArr" value='<%= new String[]{"","estimate","bill"} %>' />
	<c:forEach items="${workflowList}" var="w" varStatus="status">
		<div class="container-fluid pr-md-3 pl-md-3 smallSize">
			<div class="p-0 d-flex ">
				<div class="p-0 col-12 d-flex">
					<div class="mt-2 mb-2 col-2 p-0 text-center align-self-center">${w.explanation}</div>
					<div class="mt-2 mb-2 col-1 p-0 text-center align-self-center link" onclick="readDocument('${sysArr[w.systemNum]}','${w.documentTypeName}','${w.documentNum}')">${w.documentNum}</div>
					<div class="mt-2 mb-2 col-2 p-0 text-center align-self-center d-none d-md-inline">${w.receiver}</div>
	  				<div class="mt-2 mb-2 col-2 p-0 text-center align-self-center">${w.documentName}</div>
					<div class="mt-2 mb-2 col-1 p-0 text-center align-self-center">${w.userDepartment}</div>
					<div class="mt-2 mb-2 col-1 p-0 text-center align-self-center">${w.userName}</div>
					<div class="mt-2 mb-2 col-2 p-0 text-center"><fmt:parseDate value="${w.insertDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/><br><fmt:formatDate value="${noticePostDate}" pattern="HH:mm"/></div>
					<!--지금은 어차피 승인경로가1명이라 필요없는 정보 <div class="mt-2 mb-2 col-2 p-0 text-center"><fmt:parseDate value="${w.updateDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/><br><fmt:formatDate value="${noticePostDate}" pattern="HH:mm"/></div> -->
				</div>
			</div>
		</div>
	</c:forEach>
	
	<form id="readDocument" action="companyMod" method="post" accept-charset="utf-8">
		<input type="hidden" id="documentNum" name="documentNum" value="">
		<input type="hidden" id="documentTypeName" name="documentTypeName" value="">
		<input type="hidden" id="approveMode" name="approveMode" value="">
	</form>
</body>
</html>
