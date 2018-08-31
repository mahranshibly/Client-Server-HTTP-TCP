package server;


/**
 *
 * @author MahranSh
 */

import java.io.*;
import java.net.*;
 

public class  Server implements Runnable{

	private chatServerGui frame = null;
	private Thread runningThread = null;
	
	private int   serverPort   = 45000; // The port that this server is listening on
	private ServerSocket serverSocket = null;  // Server socket that will listen for incoming connections
	private boolean  isStopped  = true;
	
	public Server(int port,  chatServerGui guiFrame ){
		this.serverPort = port;
		this. frame = guiFrame;
	}

	public void run(){
		this.isStopped = false;
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		//open server Socket
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			this. frame.getMassagesBox().append("Cannot listen on this port."+ e.getMessage()+"\n");
			System.exit(1);
		}    
		while(!isStopped()){
			Socket clientSocket = null;  // socket created by accept
			try {
				clientSocket = this.serverSocket.accept(); // wait for a client to connect
				this. frame.getMassagesBox().append("client press to connect\n");
			} catch (IOException e) {
				if(isStopped()) {
					this. frame.getMassagesBox().append("IOException: Server Stopped.\n");
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);    //Accept failed
			}
			//server code here ...
			this. frame.getMassagesBox().append("Server accepts the client connection.\n");
			 

			// Client information               
			InetAddress addr = clientSocket.getInetAddress();
			this. frame.getMassagesBox().append("Server: Received a new connection from ("+ addr.getHostAddress()  + "): " + addr.getHostName() + " on port: " + clientSocket.getPort()+".\n" );
		//	 ((WorkerRunnable)runningThread).out.("Server: Received a new connection from ("+ addr.getHostAddress()  + "): " + addr.getHostName() + " on port: " + clientSocket.getPort()+".\n" );
			String clientInfo="";
			clientInfo= "Client on port " + clientSocket.getPort();
			 
			new Thread( new WorkerRunnable(clientSocket, clientInfo)).start();
		 
		}

		this. frame.getMassagesBox().append("Server Stopped.");            

	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop(){
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}


}
 
/*
 * 
 */
class WorkerRunnable implements Runnable{

	protected Socket clientSocket = null;
	protected String serverText   = null;
	PrintWriter out= null;
	BufferedReader in= null;
	public WorkerRunnable(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText   = serverText;
	}

	public void run() {
		try {
			 out = new PrintWriter(clientSocket.getOutputStream(), true);
			  in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			  out.println("acsept\n");
			String msg;
			while ((msg = in.readLine()) != null) {
				out.println(msg);
				if (msg.equals("**bye"))
					break;
			}
			out.println("endWhile\n");
			out.println(serverText + " leaved.");
			out.close();
			in.close();
			clientSocket.close();

		} catch (IOException e) {
			System.err.println("Error " + e.getMessage());	               
		}
	}
}
