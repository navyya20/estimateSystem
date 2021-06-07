<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
	<title>Login</title>
<style type="text/css">
</style>
<script>
var selectedSystem;
var order;
//인터셉터로 로그인 들어왔는데 현제페이지가 아이프레임 내부일경우 부모요소를 로그인 페이지로 페이지이동
if ( self !== top ) {
	  // you're in an iframe
	window.parent.location.href="login";
}

function selectedSystem(systemNum,n){
	selectedSystem=systemNum;
	order=n;
}
function saveApprover(){

	var userNum = $("input[name='userNum']:checked").val(); 
	
	
	$.ajax({
		type:"get",
		url:"updateWorkflowInform",
		traditional: true,
		data:{"userNum":userNum,"systemNum":selectedSystem,"order":order},
		dataType:"json",
		success:function(result){
			if(result.error!=null){
				alert(result.error);
				location.href=location.href;
			}else if(result.error==null){
				location.href=location.href;
			}
		},
		error:function(e){
				console.log(e);
				alert("通信エラー");
			}
	});

	$("input[name='userNum']:checked").prop("checked", false);
	
}

</script>
</head>

<body class="body" style="height: 100%;">
	<header class="mb-3">
		<jsp:include page="../menubar.jsp"></jsp:include>
	</header>
	<div class="row align-items-center col-12 p-0 m-0" style="height: 80%;">
		<div id="login_div" class="d-flex justify-content-center col-12 p-0">
			<form class="col-12 d-flex justify-content-center" id="insertUser" action="insertUser" method="post" accept-charset="utf-8">
				<div class="d-flex flex-column col-10 col-md-6">
				
					<!-- estimateSystem -->
					<div class="col-12">見積書</div>
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="approver1" id="inputGroup-sizing-default">承認者1</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="approver1Estimate" name="approver1" value="${estimateSystem.approver1 eq -1? '設定なし':estimateSystem.approver1Name}" readonly onclick="selectedSystem(1,1)" data-toggle="modal" data-target="#exampleModal">
					</div>
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="approver2" id="inputGroup-sizing-default">承認者2</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="approver2Estimate" name="approver2" value="${estimateSystem.approver2 eq -1? '設定なし':estimateSystem.approver2Name}" readonly onclick="selectedSystem(1,2)" data-toggle="modal" data-target="#exampleModal">
					</div>
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="approver3" id="inputGroup-sizing-default">承認者3</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="approver3Estimate" name="approver3" value="${estimateSystem.approver3 eq -1? '設定なし':estimateSystem.approver3Name}" readonly onclick="selectedSystem(1,3)" data-toggle="modal" data-target="#exampleModal">
					</div>
					<div class="d-block" style="height: 3px;"></div>
					<div class="d-block d-md-none" style="height: 5px;"></div>
					
					<!-- billSystem -->
					<div class="col-12">請求書</div>
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="approver1" id="inputGroup-sizing-default">承認者1</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="approver1Estimate" name="approver1" value="${billSystem.approver1 eq -1? '設定なし':billSystem.approver1Name}" readonly onclick="selectedSystem(2,1)" data-toggle="modal" data-target="#exampleModal">
					</div>
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="approver2" id="inputGroup-sizing-default">承認者2</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="approver2Estimate" name="approver2" value="${billSystem.approver2 eq -1? '設定なし':billSystem.approver2Name}" readonly onclick="selectedSystem(2,2)" data-toggle="modal" data-target="#exampleModal">
					</div>
					<div class="input-group">
						<div class="p-0 col-4 input-group-prepend">
							<label class="col-12 input-group-text d-flex justify-content-center" for="approver3" id="inputGroup-sizing-default">承認者3</label>
						</div>
						<input type="text" class="pl-2 pr-0 col-8 form-control" id="approver3Estimate" name="approver3" value="${billSystem.approver3 eq -1? '設定なし':billSystem.approver3Name}" readonly onclick="selectedSystem(2,3)" data-toggle="modal" data-target="#exampleModal">
					</div>
					
					
					
					
					
					<!-- Modal -->
					<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								</div>
								<div class="modal-body">
									<div class="p-0 container-lg">
										<div class="p-0 d-flex bg-dark text-white">
											<div class="p-0 col-12 d-flex">
												<div class="mt-2 mb-2 col-4 col-md-4 p-0 text-center">ID</div>
												<div class="mt-2 mb-2 col-4 col-md-4 p-0 text-center">名前</div>
												<div class="mt-2 mb-2 col-3 col-md-3 p-0 text-center">所属</div>
												<div class="mt-2 mb-2 col-1 col-md-1 p-0 text-center"></div>
											</div>
										</div>
									</div>
									
									<div class="p-0 container-lg">
										<div class="p-0 d-flex">
											<div class="p-0 col-12 d-flex">
												<div class="col-11 col-md-11 p-0 text-center align-self-center">設定なし</div>
												<div class="col-1 col-md-1 p-0 text-center"><input type="radio" name="userNum" value="-1"></div>
											</div>
										</div>
									</div>
									<div class="d-block" style="height: 3px;"></div>
									<div class="d-block d-md-none" style="height: 5px;"></div>
									
									<c:forEach items="${userList}" var="user" varStatus="status">
										<div class="p-0 container-lg">
											<div class="p-0 d-flex">
												<div class="p-0 col-12 d-flex">
													<div class="col-4 col-md-4 p-0 text-center align-self-center">${user.userId}</div>
													<div class="col-4 col-md-4 p-0 text-center align-self-center">${user.userName}</div>
													<div class="col-3 col-md-3 p-0 text-center align-self-center">${user.department}</div>
													<div class="col-1 col-md-1 p-0 text-center"><input type="radio" name="userNum" value="${user.userNum}"></div>
												</div>
											</div>
										</div>
										<div class="d-block" style="height: 3px;"></div>
										<div class="d-block d-md-none" style="height: 5px;"></div>
									</c:forEach>
									
									
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveApprover()">Save changes</button>
								</div>
							</div>
						</div>
					</div>
					
					
				</div>
			</form>
		</div>
	</div>
</body>
</html>
