<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1" >
<meta name="format-detection" content="telephone=no">

 
<title>positionListHtml</title>
</head>
<body class="body" style="height: 100%;">
	<!-- positionList -->
	<div class="container p-0" id="positionList">
		<div class="row justify-content-center" id='position${status.index}'>
			<div class="col-sm-8 col-md-4">
				役職設定
			</div>
		</div>
		<c:forEach items="${positionList}" var="position" varStatus="status">
			<div class="row justify-content-center" id='position${status.index}'>
				<div class="input-group col-sm-8 col-md-4">
					<input type="text" class="form-control" aria-describedby="button-addon4" value="${position.position}" id="positionName${position.positionNum}" maxlength="10">
					<div class="input-group-append" id="button-addon4">
						<button class="btn btn-secondary" type="button" onclick="updatePosition('${position.positionNum}');">変更</button>
						<button class="btn btn-outline-secondary" type="button" onclick="deletePosition('${position.positionNum}','${position.position}');">削除</button>
					</div>
				</div>
			</div>
			<c:if test="${status.last}">
				<input type="hidden" id="lastIndex" value="${status.index }">
			</c:if>
			<div class="d-block" style="height: 1px;"></div>
		</c:forEach>
	
		<div class="container p-0" id="positionListAdd">
			<div class="d-block" style="height: 2px;"></div>
			<div class="row justify-content-center">
				<div class="input-group col-sm-8 col-md-4">
					<input type="text" class="form-control" placeholder="役職名" aria-describedby="button-addon4" id="positionName" maxlength="10">
					<div class="input-group-append" id="button-addon4">
						<button class="btn btn-outline-secondary" type="button" onclick="insertPosition()">追加</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>
