package chat;

import java.io.UnsupportedEncodingException;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String msg = "hello world!";
		byte[] en = encrypMSG(msg);
		String de = decrypMSG(en);
		System.out.println(de);
		
		
	}
	public static byte[] encrypMSG (String msg) throws UnsupportedEncodingException{
		byte[] bf = msg.getBytes();
		byte[] encrypBF = new byte[bf.length];
		for(int i = 0; i< bf.length; i++){
			encrypBF[i] = (byte) (bf[i] ^ 240);
//			System.out.print(String.format("%x, ", encrypBF[i]));
		}
//		System.out.println();
		return encrypBF;
	}
	
	public static String decrypMSG (byte[] encrypBF) throws UnsupportedEncodingException{
		byte[] decrypBF = new byte[encrypBF.length];
		for(int i = 0; i< encrypBF.length; i++){
			decrypBF[i] = (byte) (encrypBF[i] ^ 240);
//			System.out.print(String.format("%x, ", decrypBF[i]));
		}
//		System.out.println();
		return new String(decrypBF, "UTF-8");
	}

}
