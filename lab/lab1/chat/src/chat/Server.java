package chat;

import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.synth.SynthOptionPaneUI;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterAbortException;
import java.io.*;

public class Server implements Runnable {

	private ArrayList<Socket> clientList;
	private ArrayList<String> messages;
	private ServerSocket serverSocket;
	private Thread mainThread;
	private Socket adminSocket = null;
	private File file = new File("chat.txt");
	private PrintWriter pw;

	public Server() {

		clientList = new ArrayList<Socket>();
		try {
			System.out.println("Binding to port " + 1222 + ", please wait  ...");
			serverSocket = new ServerSocket(1222);
			System.out.println("Server started: " + serverSocket);
			pw = new PrintWriter(file);
			messages = new ArrayList<String>();
			start();
		} catch (IOException ioe) {
			System.out.println("Can not bind to port " + 1222 + ": " + ioe.getMessage());
		}

	}

	@Override
	public void run() {
		Socket clientSocket = null;
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				addThread(clientSocket);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	private void addThread(Socket socket) throws IOException {

		clientList.add(socket);

		Thread t = new Thread() {
			@Override
			public void run() {
				getMsg(socket);
			}
		};
		t.start();
	}

	private void getMsg(Socket socket) {
		String msg = "";
		try {
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			int len;
			while (true) {
				while ((len = bis.available()) > 0) {
					byte bf[] = new byte[len];
					bis.read(bf);
					msg = strDecryption(bf);
					handleMsg(msg, socket, bf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void handleMsg(String msg, Socket socket, byte[] bf) {
		if (msg.startsWith("usrnme")) {
			String username = msg.substring(6);
			if (username.equals("admin")) {
				adminSocket = socket;
				System.out.println("Admin connected!");
			} else {
				System.out.println(username + " connected!");
			}
		} else if (msg.startsWith("msg")) {
			// write msg to file
			msg = msg.substring(3);
			pw.println(msg);
			pw.flush();

			for (Socket s : clientList) {
				if (s.equals(socket))
					continue;

				PrintWriter out;
				try {
					out = new PrintWriter(new DataOutputStream(s.getOutputStream()));
					out.println(msg);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // End for loop
		} else if (msg.equals("listadmin")) {
			// list messages from chat.txt
			try {
				BufferedReader listChat = new BufferedReader(new FileReader("chat.txt"));
				PrintWriter out;
				String line;
				int lineNum = 0;
				while ((line = listChat.readLine()) != null) {
					out = new PrintWriter(new DataOutputStream(adminSocket.getOutputStream()));
					out.println(lineNum + "> " + line);
					lineNum = lineNum + 1;
					out.flush();
				}
				listChat.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("chat.txt is here:");
		} else if (msg.startsWith("delete")) {
			// delete a line from chat.txt
			String lineNum = msg.substring(6);
			try {
				File in = new File("chat.txt");
				File temp = new File(in.getAbsolutePath() + ".tmp");
				BufferedReader br = new BufferedReader(new FileReader("chat.txt"));
				PrintWriter tempPW = new PrintWriter(new FileWriter(temp));

				String line;
				int currentline = 0;
				while ((line = br.readLine()) != null) {
					if (!lineNum.equals(currentline + "")) {
						tempPW.println(line);
						tempPW.flush();
					}
					currentline = currentline + 1;
				}
				tempPW.close();
				br.close();

				// Delete old file
				if (!in.delete())
					System.out.println("delete old file");

				// Rename new file
				if (!temp.renameTo(in))
					System.out.println("rename new file");
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("deleted line " + msg);
		} else {
			try {
				BufferedImage img = imgDecryptionSend(bf);

				for (Socket s : clientList) {
					if (s.equals(socket))
						continue;
					PrintWriter out;
					try {
						out = new PrintWriter(new DataOutputStream(s.getOutputStream()));
						out.println("image send: ");
						out.flush();

						ImageIO.write(img, "png", s.getOutputStream());
						img.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private String strDecryption(byte[] bf) throws UnsupportedEncodingException {
		char key = 0xf0;
		for (byte b : bf)
			b ^= key;
		return new String(bf, "UTF-8");
	}

	private BufferedImage imgDecryptionSend(byte[] bf) throws IOException, ClassNotFoundException {
		byte[] result = new byte[bf.length / 4 * 3];

		for (int i = 0; i < bf.length; i += 4) {
			result[i / 4 * 3] = (byte) ((0xfc & bf[i]) | (0x03 & (bf[i + 1] >> 6)));
			result[i / 4 * 3 + 1] = (byte) ((0xf0 & (bf[i + 1] << 2)) | (0x0f & (bf[i + 2] >> 4)));
			result[i / 4 * 3 + 2] = (byte) ((0xc0 & (bf[i + 2] << 4)) | (0x3f & (bf[i + 3] >> 2)));
		}

		InputStream in = new ByteArrayInputStream(result);
		BufferedImage img = ImageIO.read(in);
		return img;

	}

	private void start() {
		mainThread = new Thread(this);
		mainThread.start();
	}

	public static void main(String[] args) {
		Server s = new Server();
	}

}
