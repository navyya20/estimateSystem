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
<link rel="stylesheet" type="text/css" href="../css/common/common.css?ver=1.130">
<script src="../js/bootstrap.bundle.js"></script>
<title>groupList</title>
<style type="text/css">
</style>
<script>
$(document).ready(function(){
	//화면 초기 데이터 설정
	positionListAjax();
	departmentListAjax();
})
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}

function insertPosition(){
	var positionName = $("#positionName").val();
	var result = universalAjax("insertPosition",{position:positionName},"positionList");
}

function insertDepartment(){
	var departmentName = $("#departmentName").val();
	var result = universalAjax("insertDepartment",{department:departmentName},"departmentList");
}

function deletePosition(pNum,name){
	var str = "役職「"+name+"」を削除しましか？";
	if(confirm(str)){
		var result = universalAjax("deletePosition",{positionNum:pNum},"positionList");
	}
}

function deleteDepartment(dNum,name){
	var str = "部署「"+name+"」を削除しましか？";
	if(confirm(str)){
		var result = universalAjax("deleteDepartment",{departmentNum:dNum},"departmentList");
	}
}

function updatePosition(pNum){
	var positionName = $("#positionName"+pNum).val();
	var result = universalAjax("updatePosition",{positionNum:pNum, position:positionName},"positionList");
	if(result){
		alert("更新完了");
	}
}

function updateDepartment(dNum){
	var departmentName = $("#departmentName"+dNum).val();
	var result = universalAjax("updateDepartment",{departmentNum:dNum, department:departmentName},"departmentList");
	if(result){
		alert("更新完了");
	}
}

function positionListAjax(){
	$.ajax({
		url: "positionListHtml",
		type: "post",
		success: function(result){
			var html = jQuery('<div>').html(result);
			var contents = html.find("div#positionList").html();
			console.log(contents);
			$("#positionList").html(contents);
		},
		error: function(){
			alert("役職リストを読み込み中エラーが発生しました。");
		}
	});
}

function departmentListAjax(){
	$.ajax({
		url: "departmentListHtml",
		type: "post",
		success: function(result){
			var html = jQuery('<div>').html(result);
			var contents = html.find("div#departmentList").html();
			console.log(contents);
			$("#departmentList").html(contents);
		},
		error: function(){
			alert("部署リストを読み込み中エラーが発生しました。");
		}
	});
}

function universalAjax(URL,DATA,ID){
	var result = true;
	$.ajax({
		url: URL,
		type: "post",
		data: DATA,
		success: function(result){
			var html = jQuery('<div>').html(result);
			var contents = html.find("div#"+ID).html();
			$("#"+ID).html(contents);
			result = true;
		},
		error: function(){
			alert("AJAX処理中エラーが発生しました。");
			result = false;
		}
	});
	return result;
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	
	<div class="p-0 mb-3 col-12 container-xl text-center font-weight-bold">
		役職・組織設定
	</div>
	
	<div class="d-block" style="height: 8px;"></div>
	
	
	<!-- positionList -->
	<div class="container p-0" id="positionList">
	</div>
	
	<div class="d-block" style="height: 16px;"></div>
	<div class="d-block" style="height: 16px;"></div>
	
	<!-- departmentList -->
	<div class="container p-0" id="departmentList">
	</div>
	
	<div class="d-block" style="height: 16px;"></div>
</body>
</html>
