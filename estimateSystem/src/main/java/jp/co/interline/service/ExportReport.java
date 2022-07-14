package jp.co.interline.service;
import java.util.HashMap;

import oz.framework.api.Scheduler;
import oz.framework.api.Service;
import oz.framework.cp.OZCPException;
import oz.scheduler.DirectExportResult;
import oz.scheduler.SchedulerException;
import oz.scheduler.ServerInfo;
import oz.util.SortProperties;

public class ExportReport {
	

	GetProperties properties = new GetProperties();

	String serverUrl;
	
	String schedulerIp;

	int	schedulerPort;

	

	String		exportFormat	;
	String		exportFileName	;
	
	String 	ozServerID;
	String 	ozServerPW;
	
  	ServerInfo	serverInfo	= null;
  	SortProperties	configMap	= null;
  	SortProperties	exportMap	= null;
  	Scheduler	scheduler	= null;
  	String		taskID		= "";
	String folderName = "12345";                    // 가정의 변경된 폴더 명
	public ExportReport(String ozId, String ozPW, 
			String OZserverURL , String ipScheduler,int portScheduler) {
		ozServerID 		= ozId;
		ozServerPW 		= ozPW;
		serverUrl =OZserverURL;
		schedulerIp=ipScheduler;
		schedulerPort=portScheduler;
	}
	public HashMap<String,String> exportMethod(String jsonData,String nameOzr, String[] ozrParamValue, 
			String nameOdi, String[] odiParamValue,
			String formatExport, String fileNameExport) throws Exception {
		
		String		ozrName		= nameOzr;
		String[]	ozrParamVal	= ozrParamValue;
		int		ozrParamCnt=ozrParamVal == null ? 0 : ozrParamVal.length;

		String		odiName		=nameOdi;
		String[] 	odiParamVal	= odiParamValue;
		int		odiParamCnt=odiParamVal == null ? 0 : odiParamVal.length;
		
		String		exportFormat= formatExport;
		String		exportFileName= fileNameExport;
		
		HashMap<String, String> result = new HashMap<>();
		try {
			Service service = new Service(serverUrl, ozServerID, ozServerPW, false, false);
			scheduler = new Scheduler(schedulerIp, schedulerPort);
	
			boolean serverAlive	= service.ping();
			boolean schedulerAlive	= scheduler.ping();
	
			if (serverAlive && schedulerAlive) {
	
				// 오즈 서버 정보, 계정, 타입
				serverInfo = new ServerInfo();
				serverInfo.setID(ozServerID);
				serverInfo.setPWD(ozServerPW);
				serverInfo.setIsDaemon(false);
				serverInfo.setURL(serverUrl);
	
				// 오즈 스케줄러 정보 설정
				configMap = new SortProperties();
				
				//configMap = scheduler.getConfiguration(serverInfo); // 스케쥴러 설정을 가져 옴
				
				// 뷰어 패러미터 그대로 사용
				configMap.setProperty("task_type", "viewerTag");
				// 즉시 실행
		 		configMap.setProperty("launch_type", "immediately");
		 		// Task 의 타입 new, edit
				configMap.setProperty("cfg.type", "new");
				
				
	
				//configMap.setProperty("SchedulerPort", "9521"); // 스케쥴러 포트 재지정
				//scheduler.modifyConfiguration(serverInfo, configMap); // 수정된 설정을 적용
	
				//// 오즈 스케줄러에 작업 설정
				exportMap = new SortProperties();
				exportMap.setProperty("connection.servlet", serverUrl);
	
				// 폼패러미터 설정
				exportMap.setProperty("connection.reportname", ozrName);
				exportMap.setProperty("connection.pcount", Integer.toString(ozrParamCnt));
				for (int i = 0; i < ozrParamCnt; i++)
					exportMap.setProperty("connection.args" + Integer.toString(i + 1), ozrParamVal[i]);

				// ODI패러미터 설정
				if(odiName!=null) {
					exportMap.setProperty("odi.odinames", odiName);
					exportMap.setProperty("odi." + odiName + ".pcount", Integer.toString(odiParamCnt));
					for (int i = 0; i < odiParamCnt; i++)
						exportMap.setProperty("odi." + odiName + ".args" + Integer.toString(i + 1), odiParamVal[i]);
				}
				// 익스포트 정보
				exportMap.setProperty("viewer.mode", "export");
				//exportMap.setProperty("viewer.largebundle", "true");
				exportMap.setProperty("export.mode", "silent");
				exportMap.setProperty("export.confirmsave", "false");
				exportMap.setProperty("export.format", exportFormat);//확장자 ,로 여러개 지정
				
				
				
				
				exportMap.setProperty("font.fontnames", "font1");
				exportMap.setProperty("font.font1.name", "Sawarabi Gothic");
				exportMap.setProperty("font.font1.url", "http://"+properties.getWebIP()+"/files/fonts/SawarabiGothic-Regular.ttf");
				
				exportMap.setProperty("tiff.encode", "JPG");
				exportMap.setProperty("tiff.savemultipage", "true");
				exportMap.setProperty("pdf.title", "INTERLINE");
				exportMap.setProperty("pdf.author", "INTERLINE");
				exportMap.setProperty("pdf.fontembedding", "true");
				exportMap.setProperty("pdf.fontembedding_subset", "true");
				exportMap.setProperty("ozd.includeedits", "true");
				
				// 추출한 json 값을 보고서에 입히는 패러미터
				exportMap.setProperty("connection.inputjson", jsonData);
				
				//String [] exportFormatA = exportFormat.split(",");	
				
				//exportMap.setProperty("word.filename", exportFileName+".doc");
				//exportMap.setProperty("ozd.filename", exportFileName+".ozd");   
				exportMap.setProperty(exportFormat+".filename", exportFileName+"."+exportFormat);
				/*
				for(int i = 0; i < exportFormatA.length; i++){
					if(exportFormatA[i].equals("doc")){
						exportMap.setProperty("word.filename", exportFileName+".doc");
					}else if(exportFormatA[i].equals("xls")){
						exportMap.setProperty("excel.filename", exportFileName+".xls");
					}else if(exportFormatA[i].equals("hml")){
						exportMap.setProperty("han97.filename", exportFileName+".hml");
					}else if(exportFormatA[i].equals("tif")){
						exportMap.setProperty("tiff.filename", exportFileName+".tif");
					}else if(exportFormatA[i].equals("txt")){
						exportMap.setProperty("text.filename", exportFileName+".txt");
					}else if(exportFormatA[i].equals("pdf")){
						exportMap.setProperty("pdf.filename", exportFileName+".pdf");   
					}else{
						exportMap.setProperty(exportFormatA[i]+".filename", exportFileName+"."+exportFormatA[i]);
					}
				}*/
				
				scheduler.directExport(serverInfo, configMap, exportMap);
				
				DirectExportResult t = scheduler.directExport(serverInfo, configMap, exportMap);
				/*
				result="";
				result+="<div>오즈스케줄러 DirectExport 결과</div>\r";
				result+="<table border=1>\r";
				result+="<tr><td>태스크 아이디</td><td>" + t.taskID+"</td></tr>\r";
				result+="<tr><td>태스크 이름</td><td>" + t.taskName+"</td></tr>\r";
				result+="<tr><td>태스크 그룹이름</td><td>" + t.taskGroupName+"</td></tr>\r";
				result+="<tr><td>성공여부</td><td>" + t.isSuccessful+"</td></tr>\r";
				result+="<tr><td>수행시간</td><td>" + t.executeTime + "sec</td></tr>\r";
				result+="<tr><td>완료시간</td><td>" + t.completedTime+"</td></tr>\r";
				result+="<tr><td>export파일경로</td><td>" + t.exportFileList+"</td></tr>\r";
				result+="<tr><td>보고서명</td><td>" + t.formName+"</td></tr>\r";
				result+="<tr><td>익스포트된 페이지 수 </td><td>" + t.pageCount+"</td></tr>\r";
				result+="</table>";
				 */
				
				result.put("result", "true");
				result.put("filePath", t.exportFileList);
				result.put("fileName", exportFileName+"."+exportFormat);
				result.put("fileSuccess", t.isSuccessful? "true":"false");
				
			}
			else {
				if (!serverAlive)
					throw new Exception("OZserver is dead.");
				if (!schedulerAlive)
					throw new Exception("OZCserver is dead.");
			}
	
		}
		
		catch (Exception e) {
			throw e;
		}
		return result;
	}
}
