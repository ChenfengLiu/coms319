
package chat;

import java.net.*;
import java.nio.file.Files;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Client implements Runnable {

	private String username;
	private Socket serverSocket;
	private DataOutputStream streamOut = null;
	private Thread clientThread = null;
	private Scanner in = new Scanner(System.in);

	public Client() {
		this.username = login();
		try {
			serverSocket = new Socket("localhost", 1222);
			this.streamOut = new DataOutputStream(serverSocket.getOutputStream());
			start();
		} catch (UnknownHostException h) {
			System.out.println("Unknown Host " + h.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
			System.exit(1);
		}
	}

	@Override
	public void run() {

		strEncryptionSend("usrnme" + username);

		if (username.equals("admin")) {
			System.out.printf(
					"1. To broadcast, please enter \"broadcast\"\n2. To list messages so far, please enter \"list\"\n3. To delete message, please enter \"delete\"\n");
		} else {
			System.out.printf("To send an image, please enter \"image\".Otherwise, it is considered a message.\n");
		}

		while (true) {

			if (username.equals("admin")) {
				
				while (!in.hasNextLine());
				String msg = in.nextLine();

				if (msg.equals("broadcast")) {
					System.out.println("what do you want to broadcast?");
					while (!in.hasNextLine());
					String temp = in.nextLine();
					strEncryptionSend("msg" + "admin" + ": " + temp);
				}else if(msg.equals("list")){
					strEncryptionSend("list" + "admin");
				}else if(msg.equals("delete")){
					System.out.println("which line do you want to delete?");
					while (!in.hasNextLine());
					String temp = in.nextLine();
					strEncryptionSend("delete" + temp);
				}else {
					System.out.println("Please enter a valid input!");
					System.out.println("You can enter \"broadcast\"");
					System.out.println("You can enter \"list\"");
					System.out.println("You can enter \"delete\"");
				}
			} else {

				while (!in.hasNextLine());
				String msg = in.nextLine();

				if (msg.equals("image")) {
					System.out.println("Please enter your image name: (i.e. happy.png)");
					String imageName = in.nextLine();

					// send image file to server
					File f = new File("res/" + imageName);
					imgEncryptionSend(f);
				} else {
					strEncryptionSend("msg" + username + ": " + msg);
				}

			}
		} // End while(true)
	}// End run()

	public String login() {
		System.out.print("Please enter your username: ");
		return in.nextLine();
	}

	public void start() {
		clientThread = new Thread(this);
		clientThread.start();

		Thread t2 = new Thread() {
			@Override
			public void run() {
				while (true) {
					Scanner in;
					try {
						in = new Scanner(new DataInputStream(serverSocket.getInputStream()));
						while (in.hasNextLine()) {
							String msg = in.nextLine();
							if (msg.startsWith("image send: ")) {
								BufferedImage img = ImageIO
										.read(ImageIO.createImageInputStream(serverSocket.getInputStream()));
								JFrame f = new JFrame();
								f.getContentPane().add(new JLabel(new ImageIcon(img)));
								f.pack();
								f.setVisible(true);
							}
							System.out.println(msg);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t2.start();
	}

	public synchronized void handleChat(String msg) {
		PrintWriter out = new PrintWriter(streamOut);
		out.println(msg);
		out.flush();
	}

	private synchronized void strEncryptionSend(String msg) {
		char key = 0xf0;
		byte[] msgBytes = msg.getBytes();
		for (byte b : msgBytes)
			b ^= key;

		try {
			streamOut.write(msgBytes);
			streamOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void imgEncryptionSend(File f) {
		byte[] bf;
		try {
			bf = Files.readAllBytes(f.toPath());
			int r = bf.length % 3;
			byte[] result;
			if (r == 1) {
				result = new byte[bf.length * 4 / 3 + 2];
			} else if (r == 2) {
				result = new byte[bf.length * 4 / 3 + 3];
			} else {
				result = new byte[bf.length * 4 / 3];
			}


			for (int i = 0; i < bf.length; i += 3) {
				result[i / 3 * 4] = (byte) ((0xfc & bf[i]));
				if (i / 3 * 4 + 1 < result.length)
					result[i / 3 * 4 + 1] = (byte) ((0xc0 & (bf[i] << 6)) | (0x3c & (bf[i+1] >> 2)));
				if (i / 3 * 4 + 2 < result.length)
					result[i / 3 * 4 + 2] = (byte) ((0xf0 & (bf[i+1] << 4)) | (0x0c & (bf[i+2] >> 4)));
				if (i / 3 * 4 + 3 < result.length)
					result[i / 3 * 4 + 3] = (byte) ((0xfc & (bf[i+2] << 2)));
			}
			
			if (bf.length % 3 == 2) {
				result[result.length - 1] = 0x00;
				result[result.length - 2] = 0x00;
			} else if (bf.length % 3 == 1) {
				result[result.length - 1] = 0x00;
			}
			streamOut.write(result);
			streamOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Client c = new Client();

	}

}
