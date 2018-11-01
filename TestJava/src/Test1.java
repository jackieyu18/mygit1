import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import sun.misc.BASE64Decoder;


public class Test1 {
	
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
		    return sb.toString().toUpperCase();
		  }
	  
	static boolean isUtf8(byte[] bs, String strCode){
		try {
			String strn1 = new String(bs,"utf-8");
			String newCode = byteArr2HexStr(strn1.getBytes("utf-8"));
			if (strCode.equals(newCode))
				return true;
			else 
				return false;
		} catch (Exception e) {
			System.out.println("error:"+e.getMessage());
			return false;
		}
		
	}

	static void Test1(){
		String strutf8 = "E6B78941424331303233636261E6B789"; //utf8
		String strgbk="B8DFB6C84162633130323363626161B8DFB6C8313233";
		//strgbk = strgbk.replace("%", "");
		try {
			int len = strutf8.length()/2;
			byte[] bs1 = new byte[len];
			for (int i=0;i<len;i++) {
				bs1[i] = (byte)Integer.parseInt(strutf8.substring(i*2,i*2+2),16);
			}
			
			String strn1 = new String(bs1,"utf-8");
			System.out.println("utf utf:"+ strn1);
			System.out.println(" utf8 utf8 utf8:"+ byteArr2HexStr(strn1.getBytes("utf-8")));
			System.out.println(" utf8 utf8 gbk :"+ byteArr2HexStr(strn1.getBytes("gbk")));
			
			String strn2 = new String(bs1,"gbk");
			System.out.println("utf gbk:"+ strn2);
			System.out.println(" utf8 gbk utf8:"+ byteArr2HexStr(strn2.getBytes("utf-8")));
			System.out.println(" utf8 gkb gbk :"+ byteArr2HexStr(strn2.getBytes("gbk")));
			
			
			len = strgbk.length()/2;
			byte[] bs2 = new byte[len];
			for (int i=0;i<len;i++) {
				bs2[i] = (byte)Integer.parseInt(strgbk.substring(i*2,i*2+2),16);
			}
			
			
			String strn3 = new String(bs2,"utf-8");
			System.out.println("gbk utf:"+ strn3);
			System.out.println(" gbk utf8 utf8:"+ byteArr2HexStr(strn3.getBytes("utf-8")));
			System.out.println(" gbk utf8 gbk :"+ byteArr2HexStr(strn3.getBytes("gbk")));
			
			
			
			String strn4 = new String(bs2,"gbk");
			System.out.println("gbk gbk:"+ strn4);			
			System.out.println(" gbk gkb utf8:"+ byteArr2HexStr(strn4.getBytes("utf-8")));
			System.out.println(" gbk gkb gbk :"+ byteArr2HexStr(strn4.getBytes("gbk")));
			
		} catch (Exception e) {
			System.out.println("error:"+e.getMessage());
		}
		
	}

	static void Test2(String strCode){
		int len = strCode.length()/2;
		byte[] bs = new byte[len];
		for (int i=0;i<len;i++) {
			bs[i] = (byte)Integer.parseInt(strCode.substring(i*2,i*2+2),16);
		}
		
		try {
			String strUtf8 = new String(bs,"utf-8");
			String newUtf8 = byteArr2HexStr(strUtf8.getBytes("utf-8"));
			if (strCode.equals(newUtf8))
				System.out.println("is utf8 :"+ strUtf8);
			else {
				String strGbk = new String(bs,"gbk");
				String newGbk = byteArr2HexStr(strGbk.getBytes("gbk"));
				if (strCode.equals(newGbk))
					System.out.println("is gbk :"+ strGbk);
				else {
					System.out.println("neither utf8 nor gbk");
				}	
				
			}	
		} catch (Exception e) {
			System.out.println("error:"+e.getMessage());
		}
		
	}
		
	

	static void CreateFileFromStr(String strCode){
		
		int len = strCode.length()/2;
		byte[] bs = new byte[len];
/*		
		for (int i=0;i<len;i++) {
			bs[i] = (byte)Integer.parseInt(strCode.substring(i*2,i*2+2),16);
		}
*/		

		try {
			 BASE64Decoder decoder = new BASE64Decoder();
		     byte[] b = decoder.decodeBuffer(strCode);
		     
			 File file = new File("b.txt");
			 if (file.exists())
				 file.delete();
			 FileOutputStream fosb = new FileOutputStream(file);
/*			 
			 fosb.write(49);
			 fosb.write(48);
			 fosb.write(48);
*/
		       //byte[] bsb="hello".getBytes();
		        //fosb.write(bsb);
		        //fosb.write(bs);
		        fosb.write(b);
			 fosb.close();


			 
	        //FileOutputStream fos = new FileOutputStream("1.png");
/*			 
			 File file1 = new File("1");
			 FileOutputStream fos = new FileOutputStream(file1);
	        
	        fos.write(bs,0,2);
	        //byte[] bs2="hello".getBytes();
	        //fos.write(bs2);
	        //fos.write(bs2,0,1);
	        fos.close();
*/	        
/*			
			InputStream is = new FileInputStream("wall.png");
            FileOutputStream fos2 = new FileOutputStream("2");
            byte[] buffer=new byte[2048];
            int lenread = 0;
            while ((lenread=is.read(buffer))!=-1) {
                fos2.write(buffer,0,lenread);
            }
            fos2.flush();
            fos2.close();
            is.close();
*/            
			
		} catch (Exception e) {
			System.out.println("error:"+e.getMessage());
		}
		
	}

	
	
	public static void main(String[] args) {
		System.out.println("begin");
		//Test1();
		//Test2("E6B78941424331303233636261E6B789"); //utf8
		//Test2("B8DFB6C84162633130323363626161B8DFB6C8313233"); //gbk
		//CreateFileFromStr("B8DFB6C84162633130323363626161B8DFB6C8313233"); 
		//CreateFileFromStr("B8DFB6");  //error 
		//CreateFileFromStr("68C0B6"); //error 
		//CreateFileFromStr("68BF6c"); //correct
		String strbase64 = "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";
		CreateFileFromStr(strbase64);  
		System.out.println("end");
	}

}
