//charByteSize(ch) : 한글자에 대한 byte를 계산합니다.
//getByteLength(s) : 입력된 글자 전체의 byte를 계산해서 알려줍니다.
//cutByteLength(s,len) : 원하는 byte 만큼 글자를 잘라서 반환합니다.
//cutEnter : function(strArray,allow) : 허용 개행 만큼빼고 그후론 잘라 버린다. 단, strArray = str.split("\n");
var calByte = {
	getByteLength : function(s) {

		if (s == null || s.length == 0) {
			return 0;
		}
		var size = 0;

		for ( var i = 0; i < s.length; i++) {
			size += this.charByteSize(s.charAt(i));
		}
		return size;
	},
		
	cutByteLength : function(s, len) {
		if (s == null || s.length == 0) {
			return 0;
		}
		var size = 0;
		var rIndex = s.length;
		for ( var i = 0; i < s.length; i++) {
			size += this.charByteSize(s.charAt(i));
			if( size == len ) {
				rIndex = i + 1;
				break;
			} else if( size > len ) {
				rIndex = i;
				break;
			}
		}
		return s.substring(0, rIndex);
	},
	
	charByteSize : function(ch) {
		if (ch == null || ch.length == 0) {
			return 0;
		}
		var charCode = ch.charCodeAt(0);
		if (charCode <= 0x00007F) {
			return 1;
		} else if (charCode <= 0x0007FF) {
			return 2;
		} else if (charCode <= 0x00FFFF) {
			return 2;//3
		} else {
			return 2;//4
		}
	},
	
	cutEnter : function(strArray,maxLine) {
		var newStrArray = new Array();
		for(var i =0 ; i < maxLine ; i++){
			newStrArray.push(strArray[i]);
		}
		var newStr=newStrArray.join("\n");
		return newStr;
	}
}
function limitString(id,maxByte){
	var str = $("#"+id).val();
	var len = calByte.getByteLength(str) //입력된 글자 전체의 byte를 계산해서 알려줍니다.
	if (len>maxByte){
		console.log("입력제한 초과");
		$("#"+id).val(calByte.cutByteLength(str,maxByte));//원하는 byte 만큼 글자를 잘라서 반환합니다.
	}
}