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
			<div class="p-0 d-flex col-6"></div>
			<div class="p-0 d-flex col-6 justify-content-end">
				合計(税込):&nbsp;<span><fmt:parseNumber value="${total}" var="totalInt" integerOnly="true"/><fmt:formatNumber value="${totalInt}" type="currency" currencySymbol=""/></span>&nbsp;円
			</div>
		</div>
	</div>
	<div class="container-lg pr-md-3 pl-md-3">
		<div class="row p-0 m-0 bg-dark text-white">
			<div class="col-12 row p-0 m-0 text-center" style="border-right: 1px solid white;">
  				<div class="col-12 row p-0 m-0">
  					<div class="col-2 col-md-2 p-0 m-0 d-none d-md-inline" id="totalDOTbillDate" onclick="countClickNum(this);">請求月</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="totalDOTdocumentNum" onclick="countClickNum(this);">請求書番号</div>
  					<div class="col-6 col-md-6 p-0 m-0" id="totalDOTreceiver" onclick="countClickNum(this);">顧客名</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="totalDOTsumWithTax2" onclick="countClickNum(this);">合計金額</div>
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
	  					<div class="col-2 col-md-2 p-0 m-0 d-none d-md-inline"><fmt:parseDate value="${bill.billDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM"/></div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center link" onclick="readBill('${bill.documentNum}','${bill.documentTypeName}')">${bill.documentNum}</div>
	  					<div class="col-6 col-md-6 p-0 m-0 align-self-center">${bill.receiver}</div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center"><span id="sum${status.index}"><fmt:parseNumber value="${bill.sumWithTax2}" var="sum" integerOnly="true"/><fmt:formatNumber value="${sum}" type="currency" currencySymbol=""/></span> 円</div>
	  				</div>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
</body>
</html>
