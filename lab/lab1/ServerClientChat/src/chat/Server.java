package chat;

import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import java.io.*;

public class Server implements Runnable {

	private ServerSocket serverSocket = null;
	private Thread mainThread = null;
	private File file = new File("chat.txt");
	private PrintWriter writer;
	private ServerGUI frame;
	private Thread guiMessageThread;
	
	private int numClient;

	public Server(int port) {
		// TODO Binding and starting server
		numClient = 0;
		try {
			System.out.println("Binding to port " + port + ", please wait  ...");
			serverSocket = new ServerSocket(port);
			System.out.println("Server started: " + serverSocket);
			start();
		} catch (IOException ioe) {
			System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}

	public void run() {
		// TODO wait for a client or show error
		while(true){
			Socket clientSoc = null;
			try{
				System.out.println("Waiting for client " + (numClient + 1) + " to connect!");
				clientSoc = serverSocket.accept();
				System.out.println("Server got connected to client: " + ++numClient);
				addThread(clientSoc,numClient-1);
			}catch(IOException e){
				System.out.println("Accpet failed: 1222");
				System.exit(-1);
			}
		}

	}

	public void start() {
		frame = new ServerGUI();
		frame.setVisible(true);
		// TODO launch a thread to read for new messages by the server
		mainThread = new Thread(this);
		mainThread.start();

	}

	public void stop() {
		// TODO

	}

	private int findClient(int ID) {
		// TODO Find Client
		
		return -1;
	}

	public synchronized void handle(String input) {
		// TODO new message, send to clients and then write it to history

		// TODO update own gui

	}

	public synchronized void remove(int ID) {
		// TODO get the serverthread, remove it from the array and then
		// terminate it

	}

	private void addThread(Socket socket, int clientID) {
		// TODO add new client
		Thread t = new Thread(new ListClientHandler(socket, clientID));
		t.start();
	}

	public static void main(String args[]) {
		
		Server server = null;
		server = new Server(1222);
	}
}
class ListClientHandler implements Runnable{
	
	Socket soc;
	int id;
	
	ListClientHandler(Socket soc, int id){
		this.soc = soc;
		this.id = id;
	}
	
	@Override
	public void run() {
		Scanner in;
		PrintWriter out;
		
		try{
			in = new Scanner(new BufferedInputStream(soc.getInputStream()));
			out = new PrintWriter(new BufferedOutputStream(soc.getOutputStream()));
			
			out.println("hello. Client id is " + id);
			out.flush();
			
			System.out.println("Server is waiting to read: ");
			String msg = in.nextLine();
			System.out.println(msg);
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
}