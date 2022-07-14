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
<title>orderSelect</title>
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
	<div class="p-0 container-fluid">
		<div  class="p-0 d-flex flex-wrap justify-content-center">
			<c:forEach items="${typeList}" var="type" varStatus="status">
				<div class="card" style="width: 20rem;">
					<img src="../resources/img/${type.documentTypeName}.jpg?ver=1" class="card-img-top" alt="...">
					<div class="card-body">
						<p class="card-text">${type.explanation}</p>
						<a href="write${fn:toUpperCase(fn:substring(type.documentTypeName, 0, 1))}${fn:substring(type.documentTypeName, 1, -1)}?documentTypeName=${type.documentTypeName}" class="btn btn-primary">新規作成</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	
</body>
</html>
