
public class TestCharset {
	static void Test1(){	
		String str = "¸ß¶È300Ã×";
		try {
			byte[] bs1 = str.getBytes();
			byte[] bs2 = str.getBytes("utf-8");
			byte[] bs3 = str.getBytes("gbk");
			byte[] bs4 = str.getBytes("unicode");
			
			String str1 = new String(bs2,"gbk");
			System.out.println("str1="+str1);
			System.out.println("success");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static  void Test2(){
		String input = "This is   a  big   and   red  apple ! ";
		input = input.replace(" ", "\n");
		System.out.println("new value="+input);
		System.out.println("*******");
		String[] output = input.split(" ");
		for (int i=0;i<output.length;i++) {
			System.out.println(""+i+": "+output[i]);
		}
	}

	static boolean isGBK(String code){
		System.out.println(" hex:"+code.substring(0,2));
		int firstnum=Integer.parseInt(code.substring(0,2), 16);
		if (firstnum>128&&firstnum<255) {
			if (code.length()<4) {
				return false;
			}
			System.out.println(" hex:"+code.substring(2,4));
			int secondnum=Integer.parseInt(code.substring(2,4), 16);
			if (secondnum>63&&secondnum<255) {
				code = code.substring(4);
				if (!"".equals(code))
					return isGBK(code);
				else
					return true;
			} else
				return false;
		}	
		
		code = code.substring(2);
		if (!"".equals(code))
			return isGBK(code);
		else
			return true;
	}
	
	static boolean isUTF8(String code){
		int firstnum=Integer.parseInt(code.substring(0,2), 16);
		String strByte1 = Integer.toBinaryString(firstnum);
		int bytenum = strByte1.indexOf("0");
		if (strByte1.length()<8)
			bytenum = 0;

		System.out.println(" hex:"+code.substring(0,2) +" Byte:"+strByte1 + " bytenum="+bytenum);
		
		if (bytenum<2) {//not hz 
			code = code.substring(2);
			return isUTF8(code);
		}	
		boolean isCorrect = true;
		for (int i=1;i<bytenum;i++) {
			int nextnum=Integer.parseInt(code.substring(i*2,i*2+2), 16);
			String nextByte = Integer.toBinaryString(nextnum);
			System.out.println(" nextnum:"+code.substring(i*2,i*2+2)+" nextByte:"+nextByte);
			if (!"10".equals(nextByte.substring(0,2))) {
				isCorrect = false;
			}
		}
		if (isCorrect) {
			if (bytenum>2)
				return true;
			code = code.substring(2*bytenum);
			if ("".equals(code))
				return true;
			else
				return isUTF8(code);
		} else
			return false;
	}
	
	static int getUTF8Len(String code){
		int firstnum=Integer.parseInt(code.substring(0,2), 16);
		String strByte1 = Integer.toBinaryString(firstnum);
		int bytenum = strByte1.indexOf("0");
		if (strByte1.length()<8)
			bytenum = 0;

		System.out.println(" hex:"+code.substring(0,2) +" Byte:"+strByte1 + " bytenum="+bytenum);
		
		if (bytenum<2) {//not hz 
			return 1;
		}	
		boolean isCorrect = true;
		for (int i=1;i<bytenum;i++) {
			int nextnum=Integer.parseInt(code.substring(i*2,i*2+2), 16);
			String nextByte = Integer.toBinaryString(nextnum);
			System.out.println(" nextnum:"+code.substring(i*2,i*2+2)+" nextByte:"+nextByte);
			if (!"10".equals(nextByte.substring(0,2))) {
				isCorrect = false;
				break;
			}
		}
		if (isCorrect) {
			return bytenum;
		} else
			return -1;
	}
	

	static int getGBKLen(String code){
		int firstnum=Integer.parseInt(code.substring(0,2), 16);
		if (firstnum>128&&firstnum<255) {
			int secondnum=Integer.parseInt(code.substring(2,4), 16);
			if (secondnum>63&&secondnum<255) {
				return 2;
			}
			return -1;
		}
		return 1;
	}
	
	static int getUnicodeLen(String code){
		int firstnum=Integer.parseInt(code.substring(0,2), 16);
		if (firstnum>128&&firstnum<255) {
			return -1;
		}
		return 2;
	}
	
	
	  static String addpercent(String strIn) {
		  StringBuffer sb = new StringBuffer();
		  for (int i=0;i<strIn.length()-1;i=i+2) {
			  sb.append("%"+strIn.substring(i,i+2));
		  }
		  return sb.toString();
	  }
	
	  static String byteArr2HexStr(byte[] arrB)  {
		    int iLen = arrB.length;
		    StringBuffer sb = new StringBuffer(iLen * 2);
		    for (int i = 0; i < iLen; i++) {
		      int intTmp = arrB[i];
		      while (intTmp < 0) {
		        intTmp = intTmp + 256;
		      }
		      if (intTmp < 16) {
		        sb.append("0");
		      }
		      sb.append(Integer.toString(intTmp, 16));
		    }
		    return addpercent(sb.toString().toUpperCase());
		  }
	  
	
	static void GetHzCode(String strHz){
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("UTF-8±àÂë: "+ byteArr2HexStr(strHz.getBytes("utf-8")))
			.append("\nGBK±àÂë: "+ byteArr2HexStr(strHz.getBytes("gbk")))
			.append("\nUNICODE±àÂë: "+ byteArr2HexStr(strHz.getBytes("unicode")))
			.append("\nBIG±àÂë: "+ byteArr2HexStr(strHz.getBytes("big5")))
			.append("\nUTF-16BE±àÂë: "+ byteArr2HexStr(strHz.getBytes("utf-16be")))
			.append("\nUTF-16LE±àÂë: "+ byteArr2HexStr(strHz.getBytes("utf-16le")))
			.append("\nUTF-16±àÂë: "+ byteArr2HexStr(strHz.getBytes("utf-16")));
		} catch (Exception e) {
			sb.append(""+ strHz);
		}
		System.out.println(""+ sb.toString());
	}
	
	static void GetHzFromCode(String strCode){
		StringBuffer sb = new StringBuffer();
		strCode = strCode.replace("%", "");		
		strCode = strCode.replace("\\u", "");		

		try {
			int len = strCode.length()/2;
			byte[] bs = new byte[len];
			for (int i=0;i<len;i++) {
				bs[i] = (byte)Integer.parseInt(strCode.substring(i*2,i*2+2),16);
			}
			
			sb.append("UTF-8: "+ new String(bs,"utf-8"))
			.append("\nGBK: "+ new String(bs,"gbk"))
			.append("\nUNICODE: "+ new String(bs,"unicode"))
			.append("\nBIG5: "+ new String(bs,"big5"))
			.append("\nUTF-16BE: "+ new String(bs,"utf-16be"))
			.append("\nUTF-16LE: "+ new String(bs,"utf-16le"))
			.append("\nUTF-16: "+ new String(bs,"utf-16"));
		} catch (Exception e) {
			sb.append(""+ strCode);
		}
		System.out.print(""+ sb.toString());
	}
	
	static void Test3(String code){		
		if (isUTF8(code))
			System.out.println("isUTF8");
		else
			System.out.println("unknown");
	}

	static void Test4(String code){
		boolean isutf8 = true;
		boolean isgbk = true;
		boolean isunicode=true;
		boolean endutf8 = false;
		boolean endgbk = false;
		boolean endunicode = false;
		
		int utf8index=0;
		int gbkindex=0;
		int unicodeindex=0;
		String strUtf8 = code;
		String strGbk = code;
		String strUnicode = code;

		int passlen;
		
		do {
			if (!endutf8) {
				passlen = getUTF8Len(strUtf8);
				if (passlen==-1) {
					isutf8 = false;
					endutf8 = true;
				} else {
					utf8index += passlen*2;
					strUtf8 = code.substring(utf8index);
					if ("".equals(strUtf8)) {
						endutf8 = true;
					}
				}
			}
			
			if (!endgbk) {
				passlen = getGBKLen(strGbk);
				if (passlen==2) {
					isgbk = true;
					endgbk = true;
				} else {
					gbkindex += passlen*2;
					strGbk = code.substring(gbkindex);
					if ("".equals(strGbk)) {
						endgbk = true;
					}
				}
			}

			if (!endunicode) {
				passlen = getUnicodeLen(strUtf8);
				if (passlen==-1) {
					isunicode = false;
					endunicode = true;
				} else {
					unicodeindex += passlen*2;
					strUnicode = code.substring(unicodeindex);
					if ("".equals(strUnicode)) {
						endunicode = true;
					}
				}
			}
		} while (!endutf8&&!endgbk&&!endunicode);
		StringBuffer sb = new StringBuffer();
		sb.append(isutf8?"1":"0");
		sb.append(isgbk?"1":"0");
		sb.append(isunicode?"1":"0");
		System.out.println("utf8 gbk unicode:"+sb.toString());
	}
	
	public static void main(String[] args) {
		System.out.println("begin");
		//Test1();
		//Test2();
		//Test3("e6b78941424331303233636261e6b789");
		//Test4("e6b78941424331303233636261e6b789");
		//GetHzCode("¸ß¶ÈAbc1023cbaa¸ß¶È123");
		//String strCode = "e6b78941424331303233636261e6b789"; //utf8
		String strCode="%B8%DF%B6%C8%41%62%63%31%30%32%33%63%62%61%61%B8%DF%B6%C8%31%32%33";
		strCode = strCode.replace("%", "");
		try {
			int len = strCode.length()/2;
			byte[] bs = new byte[len];
			for (int i=0;i<len;i++) {
				bs[i] = (byte)Integer.parseInt(strCode.substring(i*2,i*2+2),16);
			}


			if (isUTF8(strCode)) {
				System.out.println("is  utf8");
				System.out.println("bs to string:" + byteArr2HexStr(bs));
				System.out.println("result: " + new String(bs,"utf-8"));
			} else if (isGBK(strCode)) {
					System.out.println("is  gbk");
					System.out.println("bs to string:" + byteArr2HexStr(bs));
					System.out.println("result: " + new String(bs,"gbk"));
			} else {
				System.out.println("neither gbk nor utf8");
			}	
		} catch (Exception e) {
			System.out.println("error:"+e.getMessage());
		}
		System.out.println("end");
	}

}
