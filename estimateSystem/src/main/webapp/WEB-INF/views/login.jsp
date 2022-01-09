<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1" >
<meta name="format-detection" content="telephone=no">
<script src="js/jquery-3.5.1.min.js"></script>
 
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script src="js/bootstrap.bundle.js"></script>
	<title>Login</title>
<style type="text/css">
</style>
<script>
$(document).ready(function(){
	$('#btn_login').click(input_Check);
	$("#login_id").keyup(function(e){if(e.keyCode == 13)  input_Check(); });
	$("#login_pw").keyup(function(e){if(e.keyCode == 13)  input_Check(); });
	ozlicenseCheck();
	
	function input_Check(){
		var id=$("#login_id").val();
		var pw=$("#login_pw").val();
		
		var check = /^[A-Za-z0-9]{3,20}$/ ;
		if(id.length == 0){alert("IDを入力してください。");$("#login_id").focus();return false;}
		if(pw.length == 0){alert("PWを入力してください。");$("#login_pw").focus();return false;}

		var filter = "win16|win32|win64|mac|macintel";
		var checkDevice="PC";
		if( navigator.platform  ){
			if( filter.indexOf(navigator.platform.toLowerCase())<0 ){
				checkDevice="MB";
		    }else{
				checkDevice="PC";		    	
		    }
		}
		
		console.log("ID/PW를 확인하러 갑니다.");
		$.ajax({
			type:"post",
			url:"login",
			traditional: true,
			data:{"userId":id,"password":pw,"checkDevice":checkDevice},
			dataType:"json",
			success:function(result){
				if(result.error!=null){
					alert(result.error);
				}else if(result.error==null){
					location.href=result.url;
				}
			}	
		});
		$("#login_id").val("");
		$("#login_pw").val("");

		return false;
	}

	
});

function ozlicenseCheck(){
	var expirationDate = new Date(2022,5,1) ;//라이센스받으면 만료일 경신 필요함.
	var nowDate = new Date();
	var startDate = new Date(nowDate.getFullYear(),nowDate.getMonth()+1,nowDate.getDate());
	var diff = (expirationDate-startDate)/(1000*60*60*24);	
	if(diff<=14){
		var text = $("#notice").html();
		$("#notice").html(text+"\n"+"教育用OZLicense満了まで"+diff+"日残っています。");
	}
}
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}

</script>
</head>

<body class="body" style="height: 100%;">
<div id="notice" style="position: absolute; left: 0;top: 0;">
VER 1.00	
</div>
	<div class="row align-items-center col-12 p-0 m-0" style="height: 100%;">
		<div id="login_div" class="d-flex justify-content-center col-12 p-0">
			<div class="d-flex flex-column col-10 col-md-4">
				<img src="resources/img/interline_logo.png" class="img-fluid">
				<h5 style="text-align: center; color: #495057;">見積・請求書システム</h5>
				<div class="input-group input-group-default">
					<div class="input-group-prepend col-4 p-0">
						<div class="input-group-text col-12 p-0" id="inputGroup-sizing-default"><div class="col-12 p-0 text-center">ID</div></div>
					</div>
					<input type="text" id="login_id" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" maxlength="50">
				</div>
				<div class="input-group input-group-default">
					<div class="input-group-prepend col-4 p-0">
						<div class="input-group-text col-12 p-0" id="inputGroup-sizing-default"><div class="col-12 p-0 text-center">Password</div></div>
					</div>
					<input type="password" id="login_pw" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" maxlength="50">
				</div>
				<div class="d-block" style="height: 3px;"></div>
				<button id="btn_login" type="button" class="btn btn-outline-secondary">Login</button>
			</div>
		</div>
	</div>
</body>
</html>
