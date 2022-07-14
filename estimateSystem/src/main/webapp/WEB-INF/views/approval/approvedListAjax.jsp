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
</head>

<body class="body" style="height: 100%;">
<div id="ajaxBody">
	<div class="container-fluid pr-md-3 pl-md-3">
		<div class="row p-0 m-0 bg-dark text-white">
			<div class="col-12 row p-0 m-0 text-center" style="border-right: 1px solid white;">
  				<div class="col-11 row p-0 m-0">
  					<div class="col-0 col-md-1 p-0 m-0 d-none d-md-inline" id="wfDOTupdateDate" onclick="countClickNum(this);">承認日時</div>
  					<div class="col-0 col-md-1 p-0 m-0 d-none d-md-inline" id="ui2DOTuserName" onclick="countClickNum(this);">承認者</div>
  					<div class="col-3 col-md-1 p-0 m-0" id="wfDOTinsertDate" onclick="countClickNum(this);">承認依頼日時</div>
  					<div class="col-0 col-md-1 p-0 m-0 d-none d-md-inline" id="ui1DOTuserName" onclick="countClickNum(this);">作成者</div>
  					<div class="col-2 col-md-2 p-0 m-0" id="dtDOTexplanation" onclick="countClickNum(this);">種類</div>
  					<div class="col-3 col-md-1 p-0 m-0" id="wfDOTdocumentNum" onclick="countClickNum(this);">文書番号</div>
  					<div class="col-3 col-md-2 p-0 m-0" id="emDOTreceiver" onclick="countClickNum(this);">顧客</div>
  					<div class="col-3 col-md-2 p-0 m-0" id="emDOTdocumentName" onclick="countClickNum(this);">件名</div>
  					<div class="col-1 col-md-1 p-0 m-0">ファイル</div>
  				</div>
  				<div class="col-1 p-0 m-0">
  					<div class="col-12 p-0 m-0 align-self-center">削除</div>
  				</div>
			</div>
		</div>
	</div>
	<c:forEach items="${approvedList}" var="approved" varStatus="status">
		<div class="d-block" style="height: 3px;"></div>
		<div class="d-block d-md-none" style="height: 5px;"></div>
		<div class="container-fluid pr-md-3 pl-md-3 smallSize">
			<div class="row p-0 m-0 bgGray3">
				<div class="col-12 row p-0 m-0 text-center" style="border-right: 1px solid white;">
	  				<div class="col-11 row p-0 m-0">
	  					<div class="col-0 col-md-1 p-0 m-0 d-none d-md-inline"><fmt:parseDate value="${approved.updateDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/><br><fmt:formatDate value="${noticePostDate}" pattern="HH:mm"/></div>
	  					<div class="col-0 col-md-1 p-0 m-0 align-self-center d-none  d-md-inline">${approved.approverName}</div>
	  					<div class="col-3 col-md-1 p-0 m-0"><fmt:parseDate value="${approved.insertDate}" var="noticePostDate" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${noticePostDate}" pattern="yyyy/MM/dd"/><br><fmt:formatDate value="${noticePostDate}" pattern="HH:mm"/></div>
	  					<div class="col-0 col-md-1 p-0 m-0 align-self-center d-none  d-md-inline">${approved.userName}</div>
	  					<div class="col-2 col-md-2 p-0 m-0 align-self-center">${approved.explanation}</div>
	  					<div class="col-3 col-md-1 p-0 m-0 link" onclick="readEstimate('${approved.documentNum}','${approved.documentTypeName}')">
	  						${fn:substring(approved.documentNum,0,4)}<br>${fn:substring(approved.documentNum,5,14)}
	  					</div>
	  					<div class="col-0 col-md-2 p-0 m-0 align-self-center d-none d-md-inline">${approved.receiver}</div>
	  					<div class="col-3 col-md-2 p-0 m-0 align-self-center">${approved.documentName}</div>
	  					<div class="col-1 col-md-1 p-0 m-0 align-self-center">
	  						<c:if test="${approved.fileName != null}">
		  						<a href="http://<%out.print(properties.getWebIP());%>/files/application/pdf/${approved.fileName}" download target='_blank'><img src="../resources/img/downloadButton.png" alt="..." width="30rem" height="30rem"></a>
	  						</c:if>
	  					</div>
	  				</div>
	  				<div class="col-1 row p-0 m-0 text-center">
		  				<div class="col-12 p-0 m-0 align-self-center">
							<input id='row${status.count}' type='checkbox' name='selectedRow' value='${approved.documentNum}' ${userInform.auth == 'u' and (approved.state == 'app') ? 'disabled':''}>
						</div>
	  				</div>
				</div>
			</div>
		</div>
	</c:forEach>

	<div id = "navigator" style="text-align: center;">
		<a href="javascript:formSubmit(${pn.currentPage - pn.pagePerGroup})">◁◁</a>&nbsp;
		<a href="javascript:formSubmit(${pn.currentPage-1})">◀</a> &nbsp;&nbsp;
		
		<c:forEach var="counter" begin="${pn.startPageGroup}" end="${pn.endPageGroup}">
		<c:if test="${counter == pn.currentPage}"><b><a href="javascript:formSubmit(${counter})">${counter}</a>&nbsp;</b></c:if>
		<c:if test="${counter != pn.currentPage}"><a href="javascript:formSubmit(${counter})">${counter}</a>&nbsp;</c:if>
		</c:forEach>
		&nbsp;&nbsp;
		<a href="javascript:formSubmit(${pn.currentPage + 1})">▶</a> &nbsp;&nbsp;
		<a href="javascript:formSubmit(${pn.currentPage + pn.pagePerGroup})">▷▷</a>
		<input type="hidden" id="page" value="${pn.currentPage}">
	</div>
	
	<form id="readDocument" action="" method="post" accept-charset="utf-8">
		<input type="hidden" id="documentNum" name="documentNum" value="">
		<input type="hidden" id="documentTypeName" name="documentTypeName" value="">
	</form>
	
</div>
</body>
</html>
