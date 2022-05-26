<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="UTF-8">
</head>

<body class="body" style="height: 100%;">
<div id="ajaxBody">
	<div class="container-lg pr-md-3 pl-md-3">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-6">
			</div>
			<div class="p-0 d-flex col-6 justify-content-end">
				<div class="pr-2 d-flex col-4 col-md-2">total:&nbsp;<span><fmt:parseNumber value="${total}" var="totalInt" integerOnly="true"/>${totalInt}</span>&nbsp;円</div>
			</div>
		</div>
	</div>
	<div class="container-lg pr-md-3 pl-md-3">
		<div class="row p-0 m-0 bg-dark text-white">
			<div class="col-12 row p-0 m-0 text-center" style="border-right: 1px solid white;">
  				<div class="col-12 row p-0 m-0">
  					<div class="col-2 col-md-2 p-0 m-0 d-none d-md-inline" id="wfDOTupdateDate" onclick="countClickNum(this);">請求月</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="ui2DOTuserName" onclick="countClickNum(this);">請求書番号</div>
  					<div class="col-3 col-md-3 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">顧客名</div>
  					<div class="col-3 col-md-3 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">件名</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="ui1DOTuserName" onclick="countClickNum(this);">合計金額</div>
  				</div>
			</div>
		</div>
	</div>
	<c:forEach items="${billList}" var="bill" varStatus="status">
		<div class="d-block" style="height: 3px;"></div>
		<div class="d-block d-md-none" style="height: 5px;"></div>
		<div class="container-lg pr-md-3 pl-md-3 smallSize">
			<div class="row p-0 m-0 bgGray3">
				<div class="col-12 row p-0 m-0 text-center" style="border-right: 1px solid white;">
	  				<div class="col-12 row p-0 m-0">
	  					<div class="col-2 col-md-2 p-0 m-0 d-none d-md-inline"><fmt:parseDate value="${bill.updateDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM"/></div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center link" onclick="readBill('${bill.documentNum}','${bill.documentTypeName}')">${bill.documentNum}</div>
	  					<div class="col-3 col-md-3 p-0 m-0 align-self-center">${bill.receiver}</div>
	  					<div class="col-3 col-md-3 p-0 m-0 align-self-center">${bill.documentName}</div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center"><span id="sum${status.index}"><fmt:parseNumber value="${bill.sumWithTax}" var="sum" integerOnly="true"/>${sum}</span> 円</div>
	  				</div>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
</body>
</html>
