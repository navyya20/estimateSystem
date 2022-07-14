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
	getBillListAjax();
});
function setToday(){
	var today = new Date();   
	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	$("#year").html(year);
	$("#month").html(month);
}

//readDocument폼테그를 공유하여 사용한다.
function readBill(estimateNum,documentTypeName){
	var url="readEstimate";
	$('#readDocument').attr("action",url);
	$('#documentNum').val(estimateNum);
	$('#documentTypeName').val(documentTypeName);
	$('#readDocument').submit();
}


//클릭횟수(홀,짝)감지기. 홀,짝에의해 해당 컬럼의 정렬이 오름일지 내림일지 결정된다.
	//flags. 처음들어오면 전부 0으로 세팅. 모델에 정보가있으면 그걸로 세팅.
var flagObjString='${flagObj}'
var flagObj=new Object();
if(flagObjString!=""){
	flagObj=JSON.parse(flagObjString);
}else{
	//si이외의 플레그
	flagObj.totalDOTbillDateFlg=0;
	flagObj.totalDOTdocumentNumFlg=0;
	flagObj.totalDOTreceiverFlg=0;
	flagObj.totalDOTsumWithTax2Flg=0;

	//si플래그
	flagObj.bDOTbillDateFlg=0;
	flagObj.iDOTdocumentNumFlg=0;
	flagObj.bDOTreceiverFlg=0;
	flagObj.iDOTmonthlyUnitPriceFlg=0;
	flagObj.iDOTworkTimeFlg=0;
	flagObj.iDOTextraTimeFlg=0;
	flagObj.iDOToverTimeUnitPriceFlg=0;
	flagObj.iDOTunderTimeUnitPriceFlg=0;
	flagObj.iDOTpriceFlg=0;
	flagObj.iDOTexpenseFlg=0;
	flagObj.iDOTbenefitFlg=0;
	flagObj.iDOTsubtotalFlg=0;
	flagObj.iDOTworkerNameFlg=0;
}
	//flag가 짝수면 asc정렬  flag가 홀수면 desc정렬. 
function countClickNum(o){
	var targetId = o.getAttribute('id');
	var target = o.getAttribute('id').replace("DOT",".");
		flagObj[targetId+"Flg"]=flagObj[targetId+"Flg"]+1;
	var flg=flagObj[targetId+"Flg"]%2;
	switch(flg) {
		case 0:
			sort(target+" desc");
	    break;
		case 1:
    		sort(target+" asc");
        break;
	    
	}
}
//여기까지클릭횟수(홀,짝)감지기


//option내용대로 order by 절 내용이 들어간다.
function sort(order){
	getBillListAjax(order)
}

function getBillListAjax(order){
	var billType = $("#typeSelect").val();
	var year = $("#year").html();
	var month = $("#month").html();
	$.ajax({
		url: "monthlyBillTotal/monthlyBillTotalAjax",
		type: "get",
		data: {"billType" : billType, "year": year, "month": month, "order": order},
		success : function(result){
			var html = jQuery('<div>').html(result);
			if(html.find("title").html()=="Login"){
				alert("ログインが解除されました。再度ログインしてください。");
				document.location.href="../";
			}
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
	getBillListAjax();
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
	getBillListAjax();
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
						<select id="typeSelect" class="custom-select border border-secondary" onchange="getBillListAjax()">
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
	
	<input type="hidden" id="option" name="option" value="">
</body>
</html>
