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

<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>

<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/common/common.css?ver=2">
<script src="../js/bootstrap.bundle.js"></script>
<title>approvedList</title>
<style type="text/css">
</style>
<script>
$(document).ready(function(){
	getApprovedListAjax();
	$("#datepicker,#datepicker2").on('focusout', function(event) {
		var dateString = $(this).val();
		if(dateString.length >= 11){
			dateString = dateString.substr(0,10);
			$(this).val(dateString);
		}
	    var datatimeRegexp = /[0-9]{4}-[0-9]{2}-[0-9]{2}/;
	    if (dateString!="" && !datatimeRegexp.test(dateString) ) {
	        alert("yyyy-mm-dd形式で入力してください。");
	        $(this).val("");
	        return false;
	    }
    });
});
$( function() {
    $( "#datepicker,#datepicker2" ).datepicker({
		monthNames : ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
		monthNamesShort : ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
        changeMonth : true,
        changeYear : true,
        dateFormat : "yy-mm-dd"
        });
  } );
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}
//이전에 sort했던 옵션. 페이지navi 이동해도 sort가 그대로 적용되도록 유지하기위해.
var presentOption = null;
var presentPage = null;

function formSubmit(page){
	presentPage = page;
	getApprovedListAjax();
}

//readDocument폼테그를 공유하여 사용한다.
function readEstimate(estimateNum,documentTypeName){
	var url="readEstimate";
	$('#readDocument').attr("action",url);
	$('#documentNum').val(estimateNum);
	$('#documentTypeName').val(documentTypeName);
	$('#readDocument').submit();
}


function deleteSheets(){
	var checkBox=$('input[name="selectedRow"]');
	var documentArr=new Array();
	for(var i=0 ; i<checkBox.length ; i++) {
	    if (checkBox[i].checked == true){
	    	documentArr.push(checkBox[i].value);
	    }
	}
	console.log("documentArr:"+documentArr);
	if(documentArr.length　==　0){alert("削除対象が選択されていません。"); return false;}
	if(!confirm("削除しますか。")){ return false; }
	$.ajax(
			{
				url: "deleteSheets",
				type: 'POST',
				traditional : true,
				data: {"documentArr":documentArr},
				success: function(data){
					document.location.href = "approvedList";
				},
				error: function(e){
					console.log(JSON.stringify(e));
					alert('エラー！');
				}
			}		
	);
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
	presentOption = option;
	getApprovedListAjax();
}

function getApprovedListAjax(){
	var countPerPage = $("#countPerPage").val();
	var page = presentPage;
	var option = presentOption;
	var start = $("#datepicker").val();
	var end = $("#datepicker2").val();
	var searchString = $("#searchString").val();
	$.ajax({
		url: "approvedList/approvedListAjax",
		type: "get",
		data: {"countPerPage" : countPerPage, "page": page, "option": option, "start": start, "end": end, "searchString": searchString},
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

function searchButton(){
	getApprovedListAjax();
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	<div class="mb-3 col-12 container-lg text-center font-weight-bold">
		承認済み文書リスト
	</div>
	<div class="container-fluid pr-md-3 pl-md-3 ">
		<div class="row text-center pr-md-3 pl-md-3 col-12 col-md-10">
			<div class="p-0 col-2">
				<input type="text" id="datepicker" class="form-control" placeholder="yyyy-mm-dd">
			</div>
			<div class="p-0 col-1 align-self-center">から</div>
			<div class="p-0 col-2">
				<input type="text" id="datepicker2" class="form-control" placeholder="yyyy-mm-dd">
			</div>
			<div class="p-0 col-1 align-self-center">まで</div>
			<div class="p-0 col-3">
				<input type="text" id="searchString" class="form-control" placeholder="検索語">
			</div>
			<div class="pl-2 col-1">
				<button type="button" class="col-12 btn btn-secondary" onclick="searchButton()">検索</button>
			</div>
		</div>
	</div>
	<div class="container-fluid pr-md-3 pl-md-3">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-6">
				<div class="p-0 d-flex col-12 col-md-4">
					<!-- <button type="button" class="col-12 btn btn-secondary" onclick="location.href='selectEstimate'">新規作成</button> -->
				</div>
			</div>
			<div class="p-0 d-flex col-6 justify-content-end">
				<div class="pr-2 d-flex col-4 col-md-2">
					<select class="custom-select" id="countPerPage" onchange="formSubmit(1)">
					    <option value="20" <c:if test="${countPerPage eq 20 }">selected</c:if>>20</option>
					    <option value="50" <c:if test="${countPerPage eq 50 }">selected</c:if>>50</option>
					    <option value="100" <c:if test="${countPerPage eq 100 }">selected</c:if>>100</option>
					</select>
				</div>
				<div class="p-0 d-flex col-4 col-md-2">
					<button type="button" class="col-12 btn btn-secondary" onclick="deleteSheets()">削除</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="d-block" style="height: 8px;"></div>
	<div id="ajaxContents">
	</div>
	
	<form id="readDocument" action="" method="post" accept-charset="utf-8">
		<input type="hidden" id="documentNum" name="documentNum" value="">
		<input type="hidden" id="documentTypeName" name="documentTypeName" value="">
	</form>
	
</body>
</html>
