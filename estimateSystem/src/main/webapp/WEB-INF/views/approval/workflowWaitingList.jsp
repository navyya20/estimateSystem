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

function readDocument(documentTypeName,documentNum){
	//location.href="read"+documentTypeName.charAt(0).toUpperCase()+documentTypeName.slice(1)+"?documentNum="+documentNum+"&approveMode=on";
	$('#readDocument').attr("action","read"+documentTypeName.charAt(0).toUpperCase()+documentTypeName.slice(1));
	$('#documentNum').val(documentNum);
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
	
	<div class="p-0 container-lg">
		<div class="p-0 d-flex bg-dark text-white">
			<div class="p-0 col-12 d-flex">
				<div class="mt-2 mb-2 col-2 p-0 text-center">カテゴリー</div>
				<div class="mt-2 mb-2 col-2 p-0 text-center">タイプ</div>
				<div class="mt-2 mb-2 col-2 p-0 text-center">文書番号</div>
				<div class="mt-2 mb-2 col-2 p-0 text-center">起案元</div>
				<div class="mt-2 mb-2 col-2 p-0 text-center">起案日時</div>
				<div class="mt-2 mb-2 col-2 p-0 text-center">更新日時</div>
			</div>
		</div>
	</div>
	<c:set var = "arr" value='<%= new String[]{"","見積書","請求書"} %>' />
	<c:forEach items="${workflowList}" var="w" varStatus="status">
		<div class="p-0 container-lg">
			<div class="p-0 d-flex ">
				<div class="p-0 col-12 d-flex">
					<div class="mt-2 mb-2 col-2 p-0 text-center">${ arr[w.systemNum] }</div>
					<div class="mt-2 mb-2 col-2 p-0 text-center">${w.documentTypeName}</div>
					<div class="mt-2 mb-2 col-2 p-0 text-center link" onclick="readDocument('${w.documentTypeName}','${w.documentNum}')">${w.documentNum}</div>
					<div class="mt-2 mb-2 col-2 p-0 text-center">${w.userDepartment}(${w.userName})</div>
					<div class="mt-2 mb-2 col-2 p-0 text-center"><fmt:parseDate value="${w.insertDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/></div>
					<div class="mt-2 mb-2 col-2 p-0 text-center"><fmt:parseDate value="${w.updateDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/></div>
				</div>
			</div>
		</div>
	</c:forEach>
	
	<form id="readDocument" action="companyMod" method="post" accept-charset="utf-8">
		<input type="hidden" id="documentNum" name="documentNum" value="">
		<input type="hidden" id="approveMode" name="approveMode" value="">
	</form>
</body>
</html>
