<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1" >
<meta name="format-detection" content="telephone=no">

<script src="../js/jquery-3.5.1.min.js"></script>
 
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/common/common.css?ver=2">
<script src="../js/bootstrap.bundle.js"></script>
<title>billSelect</title>
<style type="text/css">
</style>
<script>
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}

</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	<div class="p-0 container-lg">
		<div  class="p-0 d-flex flex-wrap justify-content-center">
			<!-- 
			<div class="card" style="width: 17rem;">
				<img src="../resources/img/billSheet1.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<p class="card-text">汎用見積書・請求書セット</p>
					<a href="writeBillSheet1" class="btn btn-primary">新規作成</a>
				</div>
			</div>
			 -->
			
			<div class="card" style="width: 17rem;">
				<img src="../resources/img/billSolution.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<p class="card-text">請求書A</p>
					<a href="writeBillSolution" class="btn btn-primary">新規作成</a>
				</div>
			</div>
			<div class="card" style="width: 17rem;">
				<img src="../resources/img/billSi.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<p class="card-text">請求書B</p>
					<a href="writeBillSi" class="btn btn-primary">新規作成</a>
				</div>
			</div>
			<div class="card" style="width: 17rem;">
				<img src="../resources/img/billC.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<p class="card-text">請求書C</p>
					<a href="writeBillC" class="btn btn-primary">新規作成</a>
				</div>
			</div>
			<div class="card" style="width: 17rem;">
				<img src="../resources/img/billC.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<p class="card-text">請求書D</p>
					<a href="writeBillD" class="btn btn-primary">新規作成</a>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>
