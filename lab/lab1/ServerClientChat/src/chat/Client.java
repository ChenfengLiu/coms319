package chat;

import java.net.*;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.io.*;

public class Client implements Runnable
{
	private Socket socket = null;
	private Thread clientThread = null;
	private DataOutputStream streamOut = null;
	//private ClientThread client = null;
	private String username;
	private ChatGUI frame;
	ServerListener SL;

	public Client(String ipAddr, String username, int serverPort)
	{
		this.username = username;
		
		// set up the socket to connect to the gui
		try
		{
			socket = new Socket(ipAddr, serverPort);
			start();
		} catch (UnknownHostException h)
		{
			JOptionPane.showMessageDialog(new JFrame(), "Unknown Host " + h.getMessage());
			System.exit(1);
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(new JFrame(), "IO exception: " + e.getMessage());
			System.exit(1);
		}
	}

	public void run()
	{
		//TODO check for a new message, once we receive it, steamOut will send it to the server
		while(true){
			String msg = frame.getMessage();
			if(msg!=null){
				handleChat(msg);
			}
		}
	}

	public synchronized void handleChat(String msg)
	{
		//TODO
		PrintWriter out = new PrintWriter(streamOut);
		frame.recieveMessage(msg + '\n');
		out.println(msg);
		out.flush();
		
	}

	public void start() throws IOException
	{
		frame = new ChatGUI(username);
		frame.setVisible(true);
		//TODO 
		clientThread = new Thread(this);
		clientThread.start();
		
		SL = new ServerListener(this, socket);
		new Thread(SL).start();
	}

	public void stop()
	{
		//TODO
	
	}

	
}
class ServerListener implements Runnable{
	Client c;
	Scanner in;
	
	ServerListener(Client c, Socket soc){
		try{
			this.c = c;
			in = new Scanner(new BufferedInputStream(soc.getInputStream()));
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while(true){
			while(in.hasNextLine()){
				String msg = in.nextLine();
				
			}
		}
	}
}
