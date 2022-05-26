<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="jp.co.interline.service.GetProperties"%>
<% GetProperties properties= new GetProperties(); %>

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
<title>monthlyBillTotal</title>
<style type="text/css">
</style>
<script>
$(document).ready(function(){
	setToday();
	var billType = $("#typeSelect").val();
	getBillListAjax(billType);
});
function setToday(){
	var today = new Date();   
	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	$("#year").html(year);
	$("#month").html(month);
}

//이전에 sort했던 옵션. 페이지navi 이동해도 sort가 그대로 적용되도록 유지하기위해.
var option="${option}";
if(option=""){
	option="wf.documentNum asc";
}
//페이지네비게이션(정렬 옵션을 유지하기위해  option, flagObj도 가져감.)
function formSubmit(page){
	var countPerPage = $("#countPerPage").val();
	document.location.href = "approvedList?page=" + page +"&option="+option+"&flagObj="+encodeURIComponent(JSON.stringify(flagObj))+"&countPerPage="+countPerPage;
}

//readDocument폼테그를 공유하여 사용한다.
function readBill(estimateNum,documentTypeName){
	var url="readEstimate";
	$('#readDocument').attr("action",url);
	$('#documentNum').val(estimateNum);
	$('#documentTypeName').val(documentTypeName);
	$('#readDocument').submit();
}


//더블클릭 감지기
var numClicks = 0;
var timeOut;
function startClick(o) {
	numClicks++;
    var op = o.getAttribute('id').replace("DOT",".");
    switch(numClicks) {
        case 2:
            doDouble(op);
            break;
        case 1:
            timeOut = setTimeout("doSingle('"+op+"')", 500);
            break;
    }
}

function doSingle(op) {
	numClicks=0;
	sort(op+" desc");
}

function doDouble(op) {
	clearTimeout(timeOut);
	numClicks=0;
	sort(op+" asc");
}
//여기까지 더블클릭 감지

//클릭횟수(홀,짝)감지기. 홀,짝에의해 해당 컬럼의 정렬이 오름일지 내림일지 결정된다.
	//flags. 처음들어오면 전부 0으로 세팅. 모델에 정보가있으면 그걸로 세팅.
var flagObjString='${flagObj}'
var flagObj=new Object();
if(flagObjString!=""){
	flagObj=JSON.parse(flagObjString);
}else{
	flagObj.wfDOTupdateDateFlg=0;
	flagObj.ui2DOTuserNameFlg=0;
	flagObj.wfDOTinsertDateFlg=0;
	flagObj.ui1DOTuserNameFlg=0;
	flagObj.dtDOTexplanationFlg=0;
	flagObj.wfDOTdocumentNumFlg=0;
	flagObj.emDOTreceiverFlg=0;
	flagObj.emDOTdocumentNameFlg=0;
}
	//flag가 짝수면 asc정렬  flag가 홀수면 desc정렬. 
function countClickNum(o){
	var targetId = o.getAttribute('id');
	var target = o.getAttribute('id').replace("DOT",".");
	var flg=flagObj[targetId+"Flg"]%2;
	switch(flg) {
	    case 0:
			flagObj[targetId+"Flg"]=flagObj[targetId+"Flg"]+1;
	    	sort(target+" asc");
	        break;
	    case 1:
	    	flagObj[targetId+"Flg"]=flagObj[targetId+"Flg"]+1;
	    	sort(target+" desc");
	        break;
	}
}
//여기까지클릭횟수(홀,짝)감지기


//option내용대로 order by 절 내용이 들어간다.
function sort(option){
	var page = $("#page").val();
	var countPerPage = $("#countPerPage").val();
	document.location.href ="approvedList?page="+page+"&option="+option+"&flagObj="+encodeURIComponent(JSON.stringify(flagObj))+"&countPerPage="+countPerPage;
}

function getBillListAjax(type){
	var year = $("#year").html();
	var month = $("#month").html();
	$.ajax({
		url: "monthlyBillTotal/monthlyBillTotalAjax",
		type: "get",
		data: {"billType" : type, "year": year, "month": month},
		success : function(result){
			var html = jQuery('<div>').html(result);
			var contents = html.find("div#ajaxBody").html();
			$("#ajaxContents").html(contents);
		},
		error : function(e){
			console.log(e);
			alert("error");
		}
	});
}

function previousMonth(){
	var year = $("#year").html();
	var month = $("#month").html();
	month--;
	if(month == 0){
		month+=12;
		year--;
	}
	$("#year").html(year);
	$("#month").html(month);
	var billType = $("#typeSelect").val();
	getBillListAjax(billType);
}
function nextMonth(){
	var year = $("#year").html();
	var month = $("#month").html();
	month++;
	if(month==13){
		month-=12;
		year++;
	}
	$("#year").html(year);
	$("#month").html(month);
	var billType = $("#typeSelect").val();
	getBillListAjax(billType);
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../../menubar.jsp"></jsp:include>
	</header>
	<div class="mb-3 col-12 container-lg text-center font-weight-bold">
		月別請求確認
	</div>
	<div class="container-lg pr-md-3 pl-md-3">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-6">
				<div class="p-0 d-flex col-12 col-md-12">
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<label class="input-group-text bg-light" for="inputGroupSelect02">請求書タイプ</label>
						</div>
						<select id="typeSelect" class="custom-select border border-secondary" onchange="getBillListAjax(this.value)">
							<option value="all" selected>全体</option>
							<c:forEach items="${typeList}" var="type" varStatus="status">
								<option value="${type.documentTypeName}">${type.explanation}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-lg pr-md-3 pl-md-3">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-6">
				<div class="p-0 d-flex col-6">
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<button class="btn btn-outline-secondary" type="button" onclick="previousMonth()"><</button>
						</div>
						<div class="form-control text-center bg-light"><span id="year">2022</span>年 <span id="month">5</span>月</div>
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="button" onclick="nextMonth()">></button>
						</div>
					</div>
				</div>
			</div>
			<div class="p-0 d-flex col-6 justify-content-end">
				<div class="pr-2 d-flex col-4 col-md-2">
				</div>
			</div>
		</div>
	</div>
	<div id="ajaxContents">
	</div>
	
	<form id="readDocument" action="" method="post" accept-charset="utf-8">
		<input type="hidden" id="documentNum" name="documentNum" value="">
		<input type="hidden" id="documentTypeName" name="documentTypeName" value="">
	</form>
	
</body>
</html>
