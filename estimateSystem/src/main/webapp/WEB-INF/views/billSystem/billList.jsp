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
<title>billList</title>
<style type="text/css">
</style>
<script>
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}
var option="${option}";
function goToUserMod(userNum){
	$("#userNum").val(userNum);
	$("#goToUserMod").submit();
}

function formSubmit(page){
	var pp = document.getElementById('page');
	
	pp.value=page;
							
	document.location.href = "estimateList?page=" + pp.value+"&option="+option;
}
function writeBill(estimateNum,nextDocumentTypeName){
	if(nextDocumentTypeName==""){
		alert("この書式は連係がない書式です。");
	}
	var url = "write"+nextDocumentTypeName.charAt(0).toUpperCase()+nextDocumentTypeName.slice(1);
	location.href=url+"?estimateNum="+estimateNum;
}

function readBill(billNum,documentTypeName){
	var url="readEstimate";
	$('#readDocument').attr("action",url);
	$('#documentNum').val(billNum);
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
					document.location.href = "estimateList";
				},
				error: function(e){
					console.log(JSON.stringify(e));
					alert('エラー！');
				}
			}		
	);
}

/* $(document).ready(function(){
	$("#eDOTupdateDate,#bDOTupdateDate").click(function(e){
		var object = $(e.target);
		var option = object.attr('id').replace("DOT",".")+" desc";
		sort(option);
	});
	$("#eDOTupdateDate,#bDOTupdateDate").dblclick(function(e){
		alert("db");
		var object = $(e.target);
		var option = object.attr('id').replace("DOT",".")+" asc";
		sort(option);
	});
})
 */
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
function sort(option){
	document.location.href = "billList?option="+option;
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	
	<div class="p-0 mb-3 col-12 container-xl text-center font-weight-bold">
		請求書リスト
	</div>
	
	<div class="p-0 container-xl">
		複数設定可能。
	</div>
	<div class="p-0 container-xl">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-6">
				<div class="p-0 d-flex col-12 col-md-4">
					<button type="button" class="col-12 btn btn-secondary" onclick="location.href='selectBill'">新規作成</button>
				</div>
			</div>
			<div class="p-0 d-flex col-6 justify-content-end">
				<div class="p-0 d-flex col-6 col-md-2">
					<button type="button" class="col-12 btn btn-secondary" onclick="deleteSheets()">削除</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="d-block" style="height: 8px;"></div>
	
	<div class="container-xl p-0">
		<div class="row p-0 m-0 bg-dark text-white">
			<div class="col-11 p-0 m-0 text-center" style="border-right: 1px solid white;">
  				<div class="col-12 row p-0 m-0">
  					<div class="col-3 col-md-2 p-0 m-0" id="bDOTdocumentNum" onclick="startClick(this);">文書番号</div>
  					<div class="col-2 col-md-2 p-0 m-0 d-none d-md-inline" id="bDOTreceiver" onclick="startClick(this);">顧客</div>
  					<div class="col-4 col-md-2 p-0 m-0">件名</div>
  					<div class="col-3 col-md-2 p-0 m-0" id="bDOTupdateDate" onclick="startClick(this);">保存日時</div>
  					<div class="col-2 col-md-1 p-0 m-0" id="bDOTstateName" onclick="startClick(this);">状態</div>
  					<div class="col-md-2 p-0 m-0 d-none d-md-inline" id="bDOTuserName" onclick="startClick(this);">作成者</div>
  					<div class="col-md-1 p-0 m-0 d-none d-md-inline" id="bDOTuserDepartment" onclick="startClick(this);">所属</div>
  				</div>
			</div>
			<div class="col-1 p-0 m-0 align-self-center text-center">削除</div>
		</div>
	</div>
	<c:forEach items="${billList}" var="bill" varStatus="status">
		<div class="d-block" style="height: 3px;"></div>
		<div class="d-block d-md-none" style="height: 5px;"></div>
		<div class="container-xl p-0 smallSize">
			<div class="row p-0 m-0 bgGray3">
				<div class="col-11 p-0 m-0 text-center" style="border-right: 1px solid white;">
	  				<div class="col-12 row p-0 m-0">
	  					<div class="col-3 col-md-2 p-0 m-0 link" onclick="readBill('${bill.documentNumB}','${bill.documentTypeNameB}')">
	  						${fn:substring(bill.documentNumB,0,4)}<br>${fn:substring(bill.documentNumB,5,14)}
	  					</div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center d-none d-md-inline">${bill.receiverB}</div>
	  					<div class="col-4 col-md-2 p-0 m-0 align-self-center">${bill.documentNameB}</div>
	  					<div class="col-3 col-md-2 p-0 m-0"><fmt:parseDate value="${bill.updateDateB}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/><br><fmt:formatDate value="${noticePostDate}" pattern="HH:mm"/></div>
	  					<div class="col-2 col-md-1 p-0 m-0 align-self-center">${bill.stateNameB}</div>
	  					<div class="col-md-2 p-0 m-0 align-self-center d-none  d-md-inline">${bill.userNameB}</div>
	  					<div class="col-md-1 p-0 m-0 align-self-center d-none d-md-inline">${bill.userDepartmentB}</div>
	  				</div>
				</div>
				<div class="col-1 p-0 m-0 align-self-center text-center">
					<input id='row${status.count}' type='checkbox' name='selectedRow' value='${bill.documentNumB}' ${userInform.auth == 'u' and bill.stateB != 'wri' ? 'disabled':''}>
				</div>
			</div>
		</div>
	</c:forEach>

	<div id = "navigator" style="text-align: center;">
		<a href="javascript:formSubmit(${pn.currentPage - pn.pagePerGroup})">◁◁</a>&nbsp;
		<a href="javascript:formSubmit(${pn.currentPage-1})">◀</a> &nbsp;&nbsp;
		
		<c:forEach var="counter" begin="${pn.startPageGroup}" end="${pn.endPageGroup}">
		<c:if test="${counter == pn.currentPage}"><b></c:if>
		<a href="javascript:formSubmit(${counter})">${counter}</a>&nbsp;
		<c:if test="${counter == pn.currentPage}"></b></c:if>
		</c:forEach>
		&nbsp;&nbsp;
		<a href="javascript:formSubmit(${pn.currentPage + 1})">▶</a> &nbsp;&nbsp;
		<a href="javascript:formSubmit(${pn.currentPage + pn.pagePerGroup})">▷▷</a>
		<input type="hidden" id="page">
	</div>
	
	<form id="readDocument" action="" method="post" accept-charset="utf-8">
		<input type="hidden" id="documentNum" name="documentNum" value="">
		<input type="hidden" id="documentTypeName" name="documentTypeName" value="">
	</form>
</body>
</html>
