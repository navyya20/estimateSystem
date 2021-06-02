function showList(workchartList){
	var state=["作成中","提出","修正要"]
	var month;
	for(var key in workchartList){
		month=workchartList[key].wMonth;
		console.log(month);
		$("#workchartCode"+month).val(workchartList[key].workchartCode);
		$("#workchartStatusFlg"+month).html(state[workchartList[key].workchartStatusFlg]);
		$("#totalWorkingTime"+month).html(workchartList[key].totalWorkingTime);
		$("#totalSalesDay"+month).html(workchartList[key].totalSalesDay);
		$("#workedDayInWKD"+month).html(workchartList[key].workedDayInWKD);
		$("#workedDayInHLD"+month).html(workchartList[key].workedDayInHLD);
		$("#replacedWorkedDay"+month).html(workchartList[key].replacedWorkedDay);
		$("#totalPaidVacation"+month).html(workchartList[key].totalPaidVacation);
		$("#totalAbsent"+month).html(workchartList[key].totalAbsent);
	}
}

function sendRquest(workchartCode,year,index){
	location.href="../both/workchart?workchartCode="+(workchartCode?workchartCode:-1)+"&wYear="+year+"&wMonth="+index;
}

function moveYear(i){
	location.href="workchartList?year="+(parseInt($("#year").html())+i);
}
