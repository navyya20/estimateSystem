<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="jp.co.interline.service.GetProperties"%>
<% GetProperties properties= new GetProperties(); %>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1" >
<meta name="format-detection" content="telephone=no">
<script src="../js/jquery-3.5.1.min.js"></script>



<script src="http://<%out.print(properties.getOzIP());%>/oz80/ozhviewer/jquery-2.0.3.min.js"></script>
<link rel="stylesheet" href="http://<%out.print(properties.getOzIP());%>/oz80/ozhviewer/jquery-ui.css" type="text/css"/>
<script src="http://<%out.print(properties.getOzIP());%>/oz80/ozhviewer/jquery-ui.min.js"></script>
<link rel="stylesheet" href="http://<%out.print(properties.getOzIP());%>/oz80/ozhviewer/ui.dynatree.css" type="text/css"/>
<script type="text/javascript" src="http://<%out.print(properties.getOzIP());%>/oz80/ozhviewer/jquery.dynatree.js" charset="utf-8"></script>
<script type="text/javascript" src="http://<%out.print(properties.getOzIP());%>/oz80/ozhviewer/OZJSViewer.js" charset="utf-8"></script>
<!-- If you want to run the HTML5SVG viewer please change the OZJSViewer.js to OZJSSVGViewer.js.
<script type="text/javascript" src="http://192.168.0.103:8888/oz80/ozhviewer/OZJSSVGViewer.js" charset="utf-8"></script>   OZJSViewer.js   OZJSSVGViewer.js
-->

<script type="text/javascript" src="../js/jQuery-FontSpy.js" charset="utf-8"></script>


<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/common/common.css">
<script src="../js/bootstrap.bundle.js"></script>
<title>readEstimate</title>
<style type="text/css">
</style>
<script>

//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}

function modButton(){
	var inputJsonString = OZViewer.GetInformation("INPUT_JSON_ALL");
	var inputJson=JSON.parse(inputJsonString);
	var documentTypeName = inputJson["documentTypeName"];
	var documentNum = inputJson["documentNum"];
	var url = "mod"+documentTypeName.charAt(0).toUpperCase()+documentTypeName.slice(1);
	$('#modDocument').attr("action",url);
	$('#documentNum').val(documentNum);
	$('#documentTypeName').val(documentTypeName);
	$('#modDocument').submit();
}
function requestButton(){
	var inputJsonString = OZViewer.GetInformation("INPUT_JSON_ALL");
	var inputJson=JSON.parse(inputJsonString);
	$.ajax(
			{
				url: "requestApproval",
				type: 'POST',
				data: {"documentTypeName":inputJson["documentTypeName"], "documentNum":inputJson["documentNum"], "systemNum":inputJson["systemNum"]},
				dataType:"text",
				success: function(r){
					alert(r);
					location.href="estimateList";
				},
				error: function(e){
					console.log(JSON.stringify(e));
					alert('エラー！');
				}
			}		
	);
}
function approveButton(){
	var inputJsonString = OZViewer.GetInformation("INPUT_JSON_ALL");
	var inputJson=JSON.parse(inputJsonString);
	$.ajax(
			{
				url: "approval",
				type: 'POST',
				data: {"documentTypeName":inputJson["documentTypeName"], "documentNum" : inputJson["documentNum"], "workflowNum":inputJson["workflowNum"]},
				dataType:"text",
				success: function(r){
					alert(r);
					location.href="estimateList";
				},
				error: function(e){
					console.log(JSON.stringify(e));
					alert('エラー！');
				}
			}		
	);
}
function rejectButton(){
	var inputJsonString = OZViewer.GetInformation("INPUT_JSON_ALL");
	var inputJson=JSON.parse(inputJsonString);
	$.ajax(
			{
				url: "reject",
				type: 'POST',
				data: {"documentTypeName":inputJson["documentTypeName"], "documentNum" : inputJson["documentNum"], "workflowNum":inputJson["workflowNum"]},
				dataType:"text",
				success: function(r){
					alert(r);
					location.href="estimateList";
				},
				error: function(e){
					console.log(JSON.stringify(e));
					alert('エラー！');
				}
			}		
	);
}


function copyButton(){
	var inputJsonString = OZViewer.GetInformation("INPUT_JSON_ALL");
	var inputJson=JSON.parse(inputJsonString);
	var documentTypeName = inputJson["documentTypeName"];
	var url = "write"+documentTypeName.charAt(0).toUpperCase()+documentTypeName.slice(1);
	if(inputJson.hasOwnProperty("updater"))delete inputJson.updater;
	if(inputJson.hasOwnProperty("userNum"))delete inputJson.userNum;
	if(inputJson.hasOwnProperty("userName"))delete inputJson.userName;
	if(inputJson.hasOwnProperty("userPosition"))delete inputJson.userPosition;
	if(inputJson.hasOwnProperty("userDepartment"))delete inputJson.userDepartment;
	if(inputJson.hasOwnProperty("workflowNum"))delete inputJson.workflowNum;
	if(inputJson.hasOwnProperty("logoFileName"))delete inputJson.logoFileName;
	if(inputJson.hasOwnProperty("documentNum"))delete inputJson.documentNum;
	if(inputJson.hasOwnProperty("documentTypeName"))delete inputJson.documentTypeName;
	if(inputJson.hasOwnProperty("nextDocumentTypeName"))delete inputJson.nextDocumentTypeName;
	var jsonString = JSON.stringify(inputJson);
	console.log(jsonString);
	alert();
	$('#copyDocument').attr("action",url);
	$('#copy').val(jsonString);
	$('#copyDocument').submit();
}


</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	
	<div id="OZViewer" style="width:99%;height:97.6%"></div>
	
	<!--buttons -->
	<div class="p-0 d-flex col-12">
		<div class="pl-2 pr-2 d-flex col-3">
			<!-- (유져&작성중)  or 관리자-->
			<c:if test="${(userInform.auth eq 'u' and state eq 'wri') or userInform.auth eq 'a'}">
				<button type="button" class="col-12 mr-2 ml-2 btn btn-secondary" onclick="modButton()">修正モード</button>
			</c:if>
		</div>
		<div class="pl-2 pr-2 d-flex col-3">
			<!-- (유져&작성중)  or 관리자-->
			<c:if test="${state eq 'wri' and userNum eq userInform.userNum}">
				<button type="button" class="col-12 mr-2 ml-2 btn btn-secondary" onclick="requestButton()">承認依頼</button>
			</c:if>
			<c:if test="${approveMode eq 'on'}">
				<button type="button" class="col-12 mr-2 ml-2 btn btn-secondary" onclick="approveButton()">承認</button>
			</c:if>
		</div>
		<div class="pl-2 pr-2 d-flex col-3">
			<c:if test="${approveMode eq 'on'}">
				<button type="button" class="col-12 mr-2 ml-2 btn btn-secondary" onclick="rejectButton()">差し戻し</button>
			</c:if>
			<c:if test="${approveMode eq null}">
				<button type="button" class="col-12 mr-2 ml-2 btn btn-secondary" onclick="copyButton()">コピーする</button>
			</c:if>
		</div>
		<div class="pl-2 pr-2 d-flex col-3">
			<button type="button" class="col-12 mr-2 ml-2 btn btn-secondary" onclick="location.href='estimateList'">戻り</button>
		</div>
	</div>	

	<script type="text/javascript" >
		function SetOZParamters_OZViewer(){			
			var oz;
			var systemName='${systemName}';
			var documentTypeName = '${documentTypeName}';
			var reportName = systemName+"/"+documentTypeName+"/read"+documentTypeName.charAt(0).toUpperCase()+documentTypeName.slice(1)+".ozr";
			var odiName = "read"+documentTypeName.charAt(0).toUpperCase()+documentTypeName.slice(1);
			oz = document.getElementById("OZViewer");
			oz.sendToActionScript("viewer.ignore_disable_color_inputcomponent","true");
			oz.sendToActionScript("connection.servlet","http://<%out.print(properties.getOzIP());%>/oz80/server");
			oz.sendToActionScript("connection.reportname",reportName);
			oz.sendToActionScript("connection.pcount","1");
			oz.sendToActionScript("connection.args1","path=http://"+'<%out.print(properties.getWebIP());%>'+'/<%out.print(properties.getProjectRoot());%>/resources/uploaded/');

			oz.sendToActionScript("global.language", "ja_JP");
			oz.sendToActionScript("odi.odinames", odiName);
	 		oz.sendToActionScript("odi."+odiName+".pcount", "1");
			oz.sendToActionScript("odi."+odiName+".args1", "documentNum="+'${estimateNum}');
			
			oz.sendToActionScript("pdf.fontembedding","true");
			return true;
		}
		
		var opt = [];
		opt["print_exportfrom"] = "server"; //인쇄 PDF 익스포트 작업을 서버와 통신하여 동작
		opt["save_exportfrom"] = { "pdf" : "server" }; //PDF 익스포트 작업을 서버와 통신하여 동작 
		var isFont = false;		
		function start_viewer() {
            if (isFont) {
            	start_ozjs("OZViewer","http://<%out.print(properties.getOzIP());%>/oz80/ozhviewer/", opt);
            }
        }
		console.log("fontSpy함수를 실행합니다.");
        fontSpy("SawarabiGothic", { //위의 font-face에서 설정한 이름을 여기에 설정해주시기 바랍니다.
            success: function() {
            	isFont = true;
            	console.log("뷰어를 실행합니다.")
                start_viewer();
            },
            failure: function() {
				console.log("isFont is false");
            }
        });
	</script>
	<form id="modDocument" action="" method="post" accept-charset="utf-8">
		<input type="hidden" id="documentNum" name="documentNum" value="">
		<input type="hidden" id="documentTypeName" name="documentTypeName" value="">
	</form>
	<form id="copyDocument" action="" method="post" accept-charset="utf-8">
		<input type="hidden" id="copy" name="copy" value="">
	</form>
</body>
</html>
