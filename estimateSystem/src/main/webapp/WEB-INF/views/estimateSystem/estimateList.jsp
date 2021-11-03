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
<title>estimateList</title>
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
	if(nextDocumentTypeName==""||nextDocumentTypeName==null){
		alert("この書式は連係の請求書がない書式です。");
		return;
	}
	var url = "write"+nextDocumentTypeName.charAt(0).toUpperCase()+nextDocumentTypeName.slice(1);
	location.href=url+"?estimateNum="+estimateNum;
}

//readDocument폼테그를 공유하여 사용한다.
function readEstimate(estimateNum,documentTypeName){
	var url="readEstimate";
	$('#readDocument').attr("action",url);
	$('#documentNum').val(estimateNum);
	$('#documentTypeName').val(documentTypeName);
	$('#readDocument').submit();
}

//readDocument폼테그를 공유하여 사용한다.
function readBill(billNum,documentTypeName){
	//read는 어차피 ozr,odi읽는 것이므로 readEstimate와 통일
	var url="readEstimate";
	$('#readDocument').attr("action",url);
	$('#documentNum').val(billNum);
	$('#documentTypeName').val(documentTypeName);
	$('#readDocument').submit();
}

//readDocument폼테그를 공유하여 사용한다.
function copyDocument(documentNum,documentTypeName){
	var url="copyDocument"
	$('#readDocument').attr("action",url);
	$('#documentNum').val(documentNum);
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

//option내용대로 order by 절 내용이 들어간다.
function sort(option){
	document.location.href = "estimateList?option="+option;
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	<div class="mb-3 col-12 container-xl text-center font-weight-bold">
		見積書リスト
	</div>
	<div class="container-fluid pr-md-3 pl-md-3">
		※「保存日時」labelを　「クリック時→降順」　「ダブルクリック時→昇順」　整列
	</div>
	<div class="container-fluid pr-md-3 pl-md-3">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-6">
				<div class="p-0 d-flex col-12 col-md-4">
					<button type="button" class="col-12 btn btn-secondary" onclick="location.href='selectEstimate'">新規作成</button>
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
	
	<div class="container-fluid pr-md-3 pl-md-3">
		<div class="row p-0 m-0 bg-dark text-white">
			<div class="col-8 p-0 m-0 text-center" style="border-right: 1px solid white;">
				<div class="col-12 p-0 m-0 text-center">見積書</div>
  				<div class="col-12 row p-0 m-0">
  					<div class="col-3 col-md-1 p-0 m-0" id="eDOTdocumentNum" onclick="startClick(this);">文書番号</div>
  					<div class="col-0 col-md-3 p-0 m-0 d-none d-md-inline" id="eDOTreceiver" onclick="startClick(this);">顧客</div>
  					<div class="col-3 col-md-3 p-0 m-0" id="eDOTdocumentName" onclick="startClick(this);">件名</div>
  					<div class="col-3 col-md-1 p-0 m-0" id="eDOTupdateDate" onclick="startClick(this);">保存日時</div>
  					<div class="col-md-1 p-0 m-0 d-none d-md-inline" id="eDOTuserName" onclick="startClick(this);">作成者</div>
  					<div class="col-2 col-md-1 p-0 m-0" id="eDOTstateName" onclick="startClick(this);">状態</div>
  					<div class="col-0 col-md-1 p-0 m-0 d-none d-md-inline" id="eDOTapprovedDate" onclick="startClick(this);">承認日時</div>
  					<div class="col-1 col-md-1 p-0 m-0">コピー</div>
  				</div>
			</div>
			<div class="col-4 row p-0 m-0 text-center">
				<div class="col-11 p-0 m-0"  style="border-right: 1px solid white;">
					<div class="col-12 p-0 m-0 text-center">請求書</div>
  					<div class="col-12 row p-0 m-0">
  						<div class="col-8 col-md-3 p-0 m-0" id="bDOTdocumentNum" onclick="startClick(this);">文書番号</div>
  						<div class="col-md-3 p-0 m-0 d-none d-md-inline" id="bDOTuserName" onclick="startClick(this);">作成者</div>
  						<div class="col-4 col-md-3 p-0 m-0" id="bDOTstateName" onclick="startClick(this);">状態</div>
  						<div class="col-md-3 p-0 m-0 d-none d-md-inline" id="bDOTapprovedDate" onclick="startClick(this);">承認日時</div>
  					</div>
				</div>
				<div class="col-1 p-0 m-0 align-self-center">削除</div>
			</div>
		</div>
	</div>
	<c:forEach items="${estimateList}" var="estimate" varStatus="status">
		<div class="d-block" style="height: 3px;"></div>
		<div class="d-block d-md-none" style="height: 5px;"></div>
		<div class="container-fluid pr-md-3 pl-md-3 smallSize">
			<div class="row p-0 m-0 bgGray3">
				<div class="col-8 p-0 m-0 text-center" style="border-right: 1px solid white;">
	  				<div class="col-12 row p-0 m-0">
	  					<div class="col-3 col-md-1 p-0 m-0 link" onclick="readEstimate('${estimate.documentNumE}','${estimate.documentTypeNameE}')">
	  						${fn:substring(estimate.documentNumE,0,4)}<br>${fn:substring(estimate.documentNumE,5,14)}
	  					</div>
	  					<div class="col-0 col-md-3 p-0 m-0 align-self-center d-none d-md-inline">${estimate.receiverE}</div>
	  					<div class="col-3 col-md-3 p-0 m-0 align-self-center">${estimate.documentNameE}</div>
	  					<div class="col-3 col-md-1 p-0 m-0"><fmt:parseDate value="${estimate.updateDateE}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/><br><fmt:formatDate value="${noticePostDate}" pattern="HH:mm"/></div>
	  					<div class="col-md-1 p-0 m-0 align-self-center d-none  d-md-inline">${estimate.userNameE}</div>
	  					<div class="col-2 col-md-1 p-0 m-0 align-self-center">${estimate.stateNameE}</div>
	  					<div class="col-0 col-md-1 p-0 m-0 d-none d-md-inline"><fmt:parseDate value="${estimate.approvedDateE}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/><br><fmt:formatDate value="${noticePostDate}" pattern="HH:mm"/></div>
	  					<div class="col-1 col-md-1 p-0 m-0 align-self-center link" onclick="copyDocument('${estimate.documentNumE}','${estimate.documentTypeNameE}');">コピー</div>
	  				</div>
				</div>
				<div class="col-4 row p-0 m-0 text-center">
					<div class="col-11 p-0 m-0"  style="border-right: 1px solid white;">
	  					<div class="col-12 row p-0 m-0">
	  						<div class="col-8 col-md-3 p-0 m-0 align-self-center link" onclick="${estimate.documentNumB == null ? 'writeBill(':'readBill('}'${estimate.documentNumB == null ? estimate.documentNumE:estimate.documentNumB}','${estimate.documentNumB == null ? estimate.nextDocumentTypeNameE:estimate.documentTypeNameB}')">
	  							${estimate.documentNumB == null ? '新規作成':''}${fn:substring(estimate.documentNumB,0,4)}${estimate.documentNumB == null ? '':'<br>'}${fn:substring(estimate.documentNumB,5,14)}
	  						</div>
	  						<div class="col-0 col-md-3 p-0 m-0 align-self-center d-none d-md-inline">${estimate.userNameB}</div>
	  						<div class="col-4 col-md-3 p-0 m-0 align-self-center">${estimate.stateNameB}</div>
	  						<div class="col-0 col-md-3 p-0 m-0 align-self-center d-none d-md-inline"><fmt:parseDate value="${estimate.approvedDateB}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/><br><fmt:formatDate value="${noticePostDate}" pattern="HH:mm"/></div>
	  					</div>
					</div>
					<div class="col-1 p-0 m-0 align-self-center">
						<input id='row${status.count}' type='checkbox' name='selectedRow' value='${estimate.documentNumE}' ${userInform.auth == 'u' and ((estimate.userNumE != userInform.userNum or estimate.stateE != 'wri') or ((estimate.userNumB != '0' and estimate.userNumB != userInform.userNum) or (estimate.stateB != null and estimate.stateB != 'wri'))) ? 'disabled':''}>
					</div>
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
