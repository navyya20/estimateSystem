//순수 숫자 스트링으로 만들어주는 함수
//숫자형데이터, 예를들어 "1,500￥"가 인수로오면 "1500"만 남기고 잘라냄.
function getPureNumber(numberStr){
	return numberStr.replace(/%/gi, "").replace(/,/gi, "").replace(/￥/gi, "").replace("JPY ", "").replace("人月", "");
}

//1차원 아이템들을  배열로 만들어줌.
//예를들어  아이템이름1, 아이템가격1, 아이템이름2, 아이템가격2 -> [{아이템이름:,아이템가격:},{아이템이름:,아이템가격:}]
//여기서  필드들(아이템이름, 아이템가격)의 String[]를 받아야하는데 두분류로 나눠받음
//strFieldList:그대로 사용해도 상관없는것 , numFieldList: 수량,가격등 순수숫자가필요하여 getPureNumber함수를 거쳐야하는것.
function getItems(inputJson,strFieldList,numFieldList,repeat){
	var itemArr = [];
	for(i=1 ; i<=repeat ; i++){
		var itemObj = new Object();
		
		for(j=0 ; j < strFieldList.length ; j++){
			itemObj[strFieldList[j]] = inputJson[strFieldList[j]+i];
			delete inputJson[strFieldList[j]+i];
		}
		
		for(j=0 ; j < numFieldList.length ; j++){
			if(inputJson[numFieldList[j]+i] != ""){
				itemObj[numFieldList[j]] = getPureNumber(inputJson[numFieldList[j]+i]);				
			}else{
				itemObj[numFieldList[j]] = "null";
			}
			
			delete inputJson[numFieldList[j]+i];
		}
		itemArr.push(itemObj);
	}
	return itemArr;
}

