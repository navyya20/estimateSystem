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
	<div class="container-fluid pr-md-3 pl-md-3">
		<div class="p-0 d-flex">
			<div class="p-0 d-flex col-8">
			</div>
			<div class="p-0 d-flex col-4">
				<div class="p-0 d-flex col-2"></div>
				<div class="p-0 d-flex col-6">
					金額合計:&nbsp;<span><fmt:parseNumber value="${totalPrice}" var="totalPriceInt" integerOnly="true"/><fmt:formatNumber value="${totalPriceInt}" type="currency" currencySymbol=""/></span>&nbsp;円
				</div>
				<div class="p-0 d-flex col-4">
					経費合計:&nbsp;<span><fmt:parseNumber value="${totalExpenses}" var="totalExpensesInt" integerOnly="true"/><fmt:formatNumber value="${totalExpensesInt}" type="currency" currencySymbol=""/></span>&nbsp;円
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid pr-md-3 pl-md-3 smallSize">
		<div class="row p-0 m-0 bg-dark text-white">
			<div class="col-12 row p-0 m-0 text-center" style="border-right: 1px solid white;">
  				<div class="col-4 row p-0 m-0">
  					<div class="col-3 col-md-3 p-0 m-0" id="wfDOTupdateDate" onclick="countClickNum(this);">請求月</div>
  					<div class="col-4 col-md-4 p-0 m-0" id="ui2DOTuserName" onclick="countClickNum(this);">請求書番号</div>
  					<div class="col-5 col-md-5 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">顧客名</div>
  				</div>
  				<div class="col-4 row p-0 m-0">
  					<div class="col-2 col-md-2 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">月額単価</div>
  					<div class="col-3 col-md-3 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">基準時間</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">作業時間</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">過不足時間</div>
  					<div class="col-3 col-md-3 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">超過時間単価</div>
  				</div>
  				<div class="col-4 row p-0 m-0">
  					<div class="col-3 col-md-3 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">控除時間単価</div>
  					<div class="col-3 col-md-3 p-0 m-0" id="ui1DOTuserName" onclick="countClickNum(this);">金額</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">旅費</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">日当</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">小計</div>
  				</div>
			</div>
		</div>
	</div>
	<c:forEach items="${billList}" var="bill" varStatus="status">
		<div class="d-block" style="height: 3px;"></div>
		<div class="d-block d-md-none" style="height: 5px;"></div>
		<div class="container-fluid pr-md-3 pl-md-3 smallSize">
			<div class="row p-0 m-0 bgGray3">
				<div class="col-12 row p-0 m-0 text-center" style="border-right: 1px solid white;">
	  				<div class="col-4 row p-0 m-0">
	  					<div class="col-3 col-md-3 p-0 m-0 align-self-center"><fmt:parseDate value="${bill.updateDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM"/></div>
	  					<div class="col-4 col-md-4 p-0 m-0 align-self-center link" onclick="readBill('${bill.documentNum}','${bill.documentTypeName}')">${bill.documentNum}</div>
	  					<div class="col-5 col-md-5 p-0 m-0 align-self-center">${bill.receiver}</div>
	  				</div>
	  				<div class="col-4 row p-0 m-0">
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center">${bill.monthlyUnitPrice}</div>
	  					<div class="col-3 col-md-3 p-0 m-0 align-self-center"><span><fmt:formatNumber value="${bill.standardMin}" pattern=".00"/></span>&nbsp;~&nbsp;<span><fmt:formatNumber value="${bill.standardMax}" pattern=".00"/></span></div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center"><span><fmt:formatNumber value="${bill.workTime}" pattern=".00"/></span></div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center"><span><fmt:formatNumber value="${bill.extraTime}" pattern=".00"/></span></div>
	  					<div class="col-3 col-md-3 p-0 m-0 align-self-center"><fmt:formatNumber value="${bill.overTimeUnitPrice}" type="currency" currencySymbol=""/></div>
	  				</div>
	  				<div class="col-4 row p-0 m-0">
	  					<div class="col-3 col-md-3 p-0 m-0 align-self-center"><fmt:formatNumber value="${bill.underTimeUnitPrice}" type="currency" currencySymbol=""/></div>
	  					<div class="col-3 col-md-3 p-0 m-0 align-self-center"><span><fmt:parseNumber value="${bill.price}" var="priceInt" integerOnly="true"/><fmt:formatNumber value="${priceInt}" type="currency" currencySymbol=""/></span>&nbsp;円</div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center"><fmt:formatNumber value="${bill.expense}" type="currency" currencySymbol=""/></div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center"><fmt:formatNumber value="${bill.benefit}" type="currency" currencySymbol=""/></div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center"><span><fmt:parseNumber value="${bill.subtotal}" var="subtotalInt" integerOnly="true"/><fmt:formatNumber value="${subtotalInt}" type="currency" currencySymbol=""/></span>&nbsp;円</div>
	  				</div>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
</body>
</html>
