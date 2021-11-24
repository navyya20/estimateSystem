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

<link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="../css/common/common.css">
<script src="../js/bootstrap.bundle.js"></script>
<title>imgList</title>
<style type="text/css">
</style>
<script>
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동

$(document).ready(function() {
    $("#logoFile").on("change", handleImgFileSelect);
    $("#stampFile").on("change", handleImgFileSelect);
});

if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}
var logoFileFlg=true;
var stampFileFlg=true;
//이미지 미리보기
var sel_file;
function handleImgFileSelect(e) {
    var tagId=e.target.attributes[1].nodeValue;
    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    var reg = /(.*?)\/(jpg|jpeg|png|bmp|gif|JPG|JPEG|PNG|BMP|GIF)$/;
	
    filesArr.forEach(function(f) {
        if (!f.type.match(reg)) {
            alert("拡張子はjpg,jpeg,png,bmpでお願いします。");

			//INPUT초기화
            var agent = navigator.userAgent.toLowerCase();
            if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
                // ie 일때 input[type=file] init.
                $("#"+tagId).replaceWith( $("#"+tagId).clone(true) );
            } else {
                //other browser 일때 input[type=file] init.
                $("#"+tagId).val("");
            }

            return false;
        }
		
        sel_file = f;

        var reader = new FileReader();
        reader.onload = function(e) {
            console.log(e.target.result);
            $("#"+tagId+"Img").attr("src", e.target.result);
        }
        reader.readAsDataURL(f);
    });
}
</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	<div class="p-0 container-lg d-flex justify-content-center">
		<div class="card col-12 col-md-6">
			<div class="card-body">
				<h5 class="card-title">LOGO</h5>
				<div class="p-0 mb-3 col-12 d-flex justify-content-center"><img src="http://<%out.print(properties.getWebIP());%>/files/estimateSystem/uploaded/${logoFileName.fileName}" id="logoFileImg" width="300" height="100"></div>
				<form id="form1" action="uploadImgFile" method="post" enctype="multipart/form-data">
					<input type="file" id="logoFile" name="imgFile" class="mb-3" required>
					<input type="hidden" name="category" class="mb-3" value="logo">
					<button type="submit" class="btn btn-secondary">image登録</button>
				</form>
			</div>
		</div>
	</div>
	<div class="p-0 container-lg d-flex justify-content-center">
		<div class="card col-12 col-md-6">
			<div class="card-body">
				<h5 class="card-title">印鑑</h5>
				<div class="p-0 mb-3 col-12 d-flex justify-content-center"><img src="http://<%out.print(properties.getWebIP());%>/files/estimateSystem/uploaded/${stampFileName.fileName}" id="stampFileImg" width="100" height="100"></div>
				<form id="form2" action="uploadImgFile" method="post" enctype="multipart/form-data">
					<input type="file" id="stampFile" name="imgFile" class="mb-3" required>
					<input type="hidden" name="category" class="mb-3" value="stamp">
					<button type="submit" class="btn btn-secondary">image登録</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
