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
<script type="text/javascript" src="../js/dataCompensation/dataCompensation.js" charset="utf-8"></script>


<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/common/common.css?ver=1.130">
<script src="../js/bootstrap.bundle.js"></script>
<title>writeEstimateLanguage</title>
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
		<jsp:include page="../../menubar.jsp"></jsp:include>
	</header>
	
	<div id="OZViewer" style="width:99%;height:97.6%"></div>
	<div class="d-block" style="height: 8px;"></div>
	<!--buttons -->
	<div class="p-0 d-flex col-12 justify-content-center">
		<button type="button" class="col-3 mr-2 ml-2 btn btn-secondary" onclick="saveButton('wri')">一時保存</button>
		<button type="button" class="col-3 mr-2 ml-2 btn btn-secondary" onclick="saveAndRequestButton('wri')">承認依頼</button>
		<button type="button" class="col-3 mr-2 ml-2 btn btn-secondary" onclick="location.href='estimateList'">戻る</button>
	</div>	

	<script type="text/javascript" >
		var copy = "";
		<c:if test="${copy != null}">
			copy = '${copy}';
			copy=copy.replace(/\r/gi, '\\r').replace(/\n/gi, '\\n');
		</c:if>
		var repeat=5;

		var userString = '${user}';
		var user=JSON.parse(userString);
		function SetOZParamters_OZViewer(){
			
			var oz;
			oz = document.getElementById("OZViewer");
			oz.sendToActionScript("viewer.ignore_disable_color_inputcomponent","true");
			oz.sendToActionScript("viewer.external_functions_path","ozp://estimateSystem/estimateLanguage/JS/estimateLanguage.js");
			oz.sendToActionScript("connection.servlet","http://<%out.print(properties.getOzIP());%>/oz80/server");
			oz.sendToActionScript("connection.reportname","estimateSystem/estimateLanguage/writeEstimateLanguage.ozr");
			oz.sendToActionScript("connection.inputjson",copy);
			oz.sendToActionScript("global.language", "ja_JP");
			oz.sendToActionScript("connection.pcount","6");
			oz.sendToActionScript("connection.args1","repeat="+repeat);
			oz.sendToActionScript("connection.args2","userNum="+user.userNum);
			oz.sendToActionScript("connection.args3","userPosition="+(user.position==null? '':user.position));
			oz.sendToActionScript("connection.args4","userDepartment="+(user.department==null? '':user.department));
			oz.sendToActionScript("connection.args5","userName="+user.userName);
			oz.sendToActionScript("connection.args6","path=http://"+'<%out.print(properties.getWebIP());%>'+"/files/estimateSystem/uploaded/");
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
        fontSpy("Sawarabi Gothic", { //위의 font-face에서 설정한 이름을 여기에 설정해주시기 바랍니다.
            success: function() {
            	isFont = true;
            	console.log("뷰어를 실행합니다.")
                start_viewer();
            },
            failure: function() {
				console.log("isFont is false");
            }
        });


		//뷰어의 모든 값을 제이슨스트링으로 가져와 컨트롤러가 받을수있게 함.
		//int형같은경우 null은 안되고
		//금액같은경우 쉼표와 ￥표시는 없어야함.
		function processJson(state){
			var inputJsonString = OZViewer.GetInformation("INPUT_JSON_ALL");
			var inputJson=JSON.parse(inputJsonString);
			var numberFieldArr = ["sum", "taxRate", "tax", "sumWithTax", "sumWithTax2","discountRate","discount"];
			numberFieldArr.forEach(function(field){
				inputJson[field] = getPureNumber(inputJson[field]);
			})
			var emptyToZeroFieldArr=["workflowNum", "taxRate","discountRate"];
			emptyToZeroFieldArr.forEach(function(field){
				if(inputJson[field]==""){inputJson[field]=0};
			})
			inputJson["items"] = getItems(inputJson,["rowNum","itemName","unit"],["unitPrice","amount","price"],repeat);
			inputJson.state=state;
			console.log("제이슨:"+JSON.stringify(inputJson));
			return inputJson;
		}

      //보존버튼을 누루면 작동. 
		function saveButton(state){
			var inputJson = processJson(state);
			$.ajax(
					{
						url: "insertEstimateLanguage",
						type: 'POST',
						data: {"jsonStr":JSON.stringify(inputJson)},
						success: function(r){
							if(r["errorFlag"]==0){
								alert("見積書を作成しました。");
							}else{
								alert(r["error"]);
							}
							location.href="estimateList";
						},
						error: function(e){
							console.log(JSON.stringify(e));
							alert('エラー！');
						}
					}		
			);
			
		}



		function saveAndRequestButton(state){
			var inputJson = processJson(state);
			$.ajax(
					{
						url: "insertEstimateLanguage",
						type: 'POST',
						data: {"jsonStr":JSON.stringify(inputJson)},
						success: function(r){
							if(r["errorFlag"]==0){
								alert("見積書を作成しました。");
								requestButton(r["documentNum"]);
							}else{
								alert(r["error"]);
							}
						},
						error: function(e){
							console.log(JSON.stringify(e));
							alert('エラー！');
						}
					}		
			);
			
		}

		function requestButton(documentNum){
			var inputJsonString = OZViewer.GetInformation("INPUT_JSON_ALL");
			var inputJson=JSON.parse(inputJsonString);
			$.ajax(
					{
						url: "requestApproval",
						type: 'POST',
						data: {"documentTypeName":inputJson["documentTypeName"], "documentNum":documentNum, "systemNum":inputJson["systemNum"]},
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
	</script>
</body>
</html>





