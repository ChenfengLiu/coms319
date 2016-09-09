package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		String msg = "hello world!";
		DataOutputStream streamOut = new DataOutputStream(new FileOutputStream("res/file.txt"));
		PrintWriter out = new PrintWriter(streamOut);
		byte[] en = encrypMSG(msg);
		for (int i = 0; i < en.length; i++) {
			out.print(en[i]);
			out.print(" ");
		}

		out.println();
		out.flush();

		/////////////////////////////////////////////////////////////////////////////////////////////
		Scanner in = new Scanner(new FileInputStream("res/file.txt"));
		ArrayList<Byte> bf = new ArrayList<>();

		while (in.hasNextInt()) {
			int tempInt = in.nextInt();
			byte tempByte = (byte) tempInt;
			bf.add(tempByte);
		}

		String de = decrypMSG(bf);
		System.out.println(de);

		out.close();
		in.close();
	}

	public static byte[] encrypMSG(String msg) throws UnsupportedEncodingException {
		byte[] bf = msg.getBytes();
		byte[] encrypBF = new byte[bf.length];
		for (int i = 0; i < bf.length; i++) {
			encrypBF[i] = (byte) (bf[i] ^ 240);
			// System.out.print(String.format("%x, ", encrypBF[i]));
		}
		// System.out.println();
		return encrypBF;
	}

	public static String decrypMSG(ArrayList<Byte> encrypBF) throws UnsupportedEncodingException {
		byte[] decrypBF = new byte[encrypBF.size()];
		for (int i = 0; i < encrypBF.size(); i++) {
			decrypBF[i] = (byte) (encrypBF.get(i) ^ 240);
//			System.out.print(String.format("%x, ", decrypBF[i]));
		}
//		System.out.println();
		return new String(decrypBF, "UTF-8");
	}

}
