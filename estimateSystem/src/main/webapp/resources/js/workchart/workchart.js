/*
parameter: 근무시작시각(String), 퇴근시각(String), 휴계시간(String)
return: 근무시간(String)
설명: ex) "01:00", "03:00", "00:30" 을 받으면  03:00 - 01:00 - 00:30의 결과를 리턴
*/
function getWorkTime(startTime,endTime,breakTime){
	var time = translateToMinute(endTime) - translateToMinute(startTime) - translateToMinute(breakTime);
	return translateToHourMinuteString(time);
}
//string을 파라메터로 받고 int를 리턴한다. ex) 01:40 -> 100
function translateToMinute(time){
	var timeSplit = time.split(":");
	var minute = parseInt(timeSplit[0])*60 + parseInt(timeSplit[1]);
	return minute;
}
//int를 파라메터로 받고  string을 리턴한다. ex)  100 -> 01:40
function translateToHourMinuteString(time){
	var hour = parseInt(time/60) + "";
	var min = (time%60) + "";
	return fillZero(2, hour)+":"+fillZero(2, min);
}
//자릿수만큼 모자른부분은 0을 체운다.
function fillZero(width, str){
    return str.length >= width ? str:new Array(width-str.length+1).join('0')+str;//남는 길이만큼 0으로 채움
}

//각 날자의 요일을 넣는다.
function insertDays(year, month, maxDate){
	var aa = new Date(year,month-1,"01");  // new Date("year-month-day"); 는 ios에서 안먹힘. 
	var firstDayLabel = aa.getDay();
	var week = ['日', '月', '火', '水', '木', '金', '土'];
	var dayForFirstDate = week[firstDayLabel];
	console.log("이달의 1일은 "+dayForFirstDate+"요일 입니다.");
	var dayLabel=0;
	for(var i=1 ; i<=maxDate ; i++){
		dayLabel=(firstDayLabel+i-1)%7;
		$("#day"+i).val(week[dayLabel]);
	}
}

//글자 휴일, 일요일 -> 빨간색     / 토요일 -> 파란색
function showHoliday(holidayArray, maxDate){
	var day;
	for(var i = 1 ; i <= maxDate ; i++){
		day = $("#day"+i).val();
		if(day=="土"){
			$("#day"+i).addClass("inputBlueText");
			$("#dateCategory"+i).val("休日");
		}
		if(day=="日"){
			$("#day"+i).addClass("inputRedText");
			$("#dateCategory"+i).val("休日");
		}
	}
	for(var h in holidayArray){
		$("#day"+holidayArray[h]).addClass("inputRedText");
		$("#dateCategory"+holidayArray[h]).val("休日");
	}
}

//1달치의 나마데이터를 jsonset으로 만듬  key=일자  value=데이터
function getData(maxDate){
	var obj = new Object();
	for(var i=1 ; i<=maxDate ; i++){
		var frame = {date:"",day:"",dateCategory:"",startTime:"",endTime:"",breakTime:"",workTime:"",content:""};
		frame.date=$("#date"+i).val();
		frame.day=$("#day"+i).val();
		frame.dateCategory=$("#dateCategory"+i).val();
		frame.startTime=$("#startTime"+i).val();
		frame.endTime=$("#endTime"+i).val();
		frame.breakTime=$("#breakTime"+i).val();
		frame.workTime=$("#workTime"+i).val();
		frame.content=$("#content"+i).val();
		obj[i+""]=frame;
	}
	return obj;
}

//총 근무시간 추출
function getTotalWorkingTime(maxDate){
	var timeString;
	var time;
	var hour=0;
	var min=0;
	for(var i=1 ; i<=maxDate ; i++){
		timeString = $("#workTime"+i).val();
		if(timeString!=""){
			time=timeString.split(":");
			hour+=parseInt(time[0]);
			min+=parseInt(time[1]);
		}
	}
	hour+=parseInt(min/60);
	min=min%60;
	$("#totalWorkingTime").html(hour+":"+fillZero(2, min+"")); 
}
//엽업일 추출
function getTotalSalesDay(maxDate){
	var cnt=0;
	for(var i=1 ; i<=maxDate ; i++){
		if($("#dateCategory"+i).val()!="休日") cnt++
	}
	$("#totalSalesDay").html(cnt);
	return cnt;
}

//평일출근일 추츨
function getWorkedDayInWKD(maxDate){
	var cnt=0;
	for(var i=1 ; i<=maxDate ; i++){
		var workTimeString = $("#workTime"+i).val();
		var workTime=0;
		if(workTimeString!="") workTime=translateToMinute(workTimeString);
		if(($("#dateCategory"+i).val()=="平日"||$("#dateCategory"+i).val()=="休暇"||$("#dateCategory"+i).val()=="代休") && workTime>0) cnt++;
	}
	$("#workedDayInWKD").html(cnt);
	return cnt;
}

//휴일출근일 추출
function getWorkedDayInHLD(maxDate){
	var cnt=0;
	for(var i=1 ; i<=maxDate ; i++){
		var workTimeString = $("#workTime"+i).val();
		var workTime=0;
		if(workTimeString!="") workTime=translateToMinute(workTimeString);
		if($("#dateCategory"+i).val()=="休日" && workTime>0) cnt++;
	}
	$("#workedDayInHLD").html(cnt); 
	return cnt;
}

//대휴일 추출
function getReplacedWorkedDay(maxDate){
	var cnt=0;
	for(var i=1 ; i<=maxDate ; i++){
		var workTimeString = $("#workTime"+i).val();
		var workTime=0;
		if(workTimeString!="") workTime=translateToMinute(workTimeString);
		if($("#dateCategory"+i).val()=="代休" && workTime>0){
			cnt=cnt+0.5 
		}else if($("#dateCategory"+i).val()=="代休" && workTime==0){
			cnt++;
		}
	}
	$("#replacedWorkedDay").html(cnt); 
	return cnt;
}

//유급휴가 추출
function getTotalPaidVacation(maxDate){
	var cnt=0;
	for(var i=1 ; i<=maxDate ; i++){
		var workTimeString = $("#workTime"+i).val();
		var workTime=0;
		if(workTimeString!="") workTime=translateToMinute(workTimeString);
		if($("#dateCategory"+i).val()=="休暇" && workTime>0){
			cnt=cnt+0.5;
		}else if($("#dateCategory"+i).val()=="休暇" && workTime==0){
			cnt++;
		}
	}
	$("#totalPaidVacation").html(cnt); 
	return cnt;
}

//무급휴가 추출
function getTotalAbsent(maxDate){
	var cnt=0;
	for(var i=1 ; i<=maxDate ; i++){
		var workTimeString = $("#workTime"+i).val();
		var workTime=0;
		if(workTimeString!="") workTime=translateToMinute(workTimeString);
		if($("#dateCategory"+i).val()=="平日" && workTime==0) cnt++;
	}
	$("#totalAbsent").html(cnt);
	return cnt;
}

//저장된 근무표 가져와서 넣기
function showWorkchart(workchart){
	var obj = JSON.parse(workchart["wData"].replace(/'/gi, "\""));
	for(var i=1 ; i<=Object.keys(obj).length ; i++){
		$("#dateCategory"+i).val(obj[i+""]["dateCategory"]);
		$("#startTime"+i).val(obj[i+""]["startTime"]);
		$("#endTime"+i).val(obj[i+""]["endTime"]);
		$("#breakTime"+i).val(obj[i+""]["breakTime"]);
		$("#workTime"+i).val(obj[i+""]["workTime"]);
		$("#content"+i).val(obj[i+""]["content"]);
	}
	$("#totalWorkingTime").html(workchart["totalWorkingTime"]);
	$("#totalSalesDay").html(workchart["totalSalesDay"]);
	$("#workedDayInWKD").html(workchart["workedDayInWKD"]);
	$("#workedDayInHLD").html(workchart["workedDayInHLD"]);
	$("#replacedWorkedDay").html(workchart["replacedWorkedDay"]);
	$("#totalPaidVacation").html(workchart["totalPaidVacation"]);
	$("#totalAbsent").html(workchart["totalAbsent"]);
	$("#workchartComment").html(workchart["workchartComment"]!=null ? workchart["workchartComment"]:"");
	if($("#commentInput")!=null){
		$("#commentInput").val(workchart["workchartComment"]!=null ? workchart["workchartComment"]:"");
	}
}

//인풋 컴포넌트의 입력을 막는다. 수정불가능하게
function blockInputComponent(maxDate){
	for(var i=1 ; i<=maxDate ; i++){
		$("#dateCategory"+i).attr("disabled",true);
		$("#startTime"+i).attr("readonly",true);
		$("#endTime"+i).attr("readonly",true);
		$("#breakTime"+i).attr("readonly",true);
		$("#workTime"+i).attr("readonly",true);
		$("#content"+i).attr("readonly",true);
	}
}

//저장 리퀘스트를 보낸다. 저장모드에는 2가지가 있다. saveMode=0.임시저장  1.제출  
//DBFlag가0이면 insert  DBFlag가1이면 update이다.
function sendRequest(year,month,maxDate,saveMode,DBFlag,auth){
	var data = getData(maxDate);
	var processedData = new Object();
	processedData.wData=JSON.stringify(data).replace(/"/gi, "'");
	processedData.wYear=year;
	processedData.wMonth=month;
	processedData.totalWorkingTime=$("#totalWorkingTime").html();
	processedData.totalSalesDay=$("#totalSalesDay").html();
	processedData.workedDayInWKD=$("#workedDayInWKD").html();
	processedData.workedDayInHLD=$("#workedDayInHLD").html();
	processedData.replacedWorkedDay=$("#replacedWorkedDay").html();
	processedData.totalPaidVacation=$("#totalPaidVacation").html();
	processedData.totalAbsent=$("#totalAbsent").html();
	processedData.workchartComment=$("#workchartComment").html();
	switch (saveMode) {
	case 0:
		processedData.workchartStatusFlg=0;
		confirm("臨時格納しますか。");
		break;
	case 1:
		processedData.workchartStatusFlg=1;
		if(auth==0){
			processedData.workchartComment=$("#commentInput").val();
			processedData.userCode=$("#userCode").html();
			confirm("管理者権限として格納しますか。");			
			
		}else{
			confirm("提出したら修正できません。提出しますか。");			
		}
		break;
	default:
		break;
	}
	processedData.DBFlag=DBFlag;
	console.log("格納しに行きます。");
	$.ajax({
		type:"post",
		url:"saveWorkchart",
		data:processedData,
		dataType:"json",
		error : function(e){
			alert("error");
            console.log(JSON.stringify(e));
        },
		success:function(result){
			if(result.result!=0){
				switch (saveMode) {
				case 0:
					alert("格納しました。");
					break;
				case 1:
					if(auth==0){
						alert("修正しました。");
					}else if(auth==1){
						alert("提出しました。");
					}
					break;
				default:
					break;
				}
				
				
				if (auth==1) {
					location.href="../staff/workchartList?year="+year;
				}else{
					location.href="../admin/workchartList";
				}
			}else{
				alert("格納に問題が発生しました。管理者にお問い合わせてください。");
			}
		}
	});
	
}

// 제작성 요청 버튼(관리자기능)
function sendRequestRewrite(){
	var workchartCode = $("#workchartCode").html();
	var comment = $("#commentInput").val();
	$.ajax({
		url: "../admin/workchartRewrite",
		type: "POST",
		data: {workchartCode : workchartCode, workchartComment : comment},
		success : function(result){
			if(result == 1){
				location.href = "../admin/workchartList";
			}else{
				alert("再作成要請が失敗しました。");
				return false;
			}
		},
		error : function(){
			//todo
		}
	});
}

//뒤로가기 버튼. 근무표 리스트 페이지로 보낸다.
function cancel(year){
	location.href="../staff/workchartList?year="+year;
}
function cancelAdmin(){
	location.href="../admin/workchartList";
}

