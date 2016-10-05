package templates.network;

import java.net.Socket;
import java.util.ArrayList;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class ServerTimer {

	static ServerTimer timer;
	private int currentConnections = 0;
	private int maxConnections;
	ArrayList<Thread> threads;
	
	/**
	 * Initiates the ServerTimer class
	 *
	 */
	public ServerTimer(int maxConnections) {
		this.maxConnections = maxConnections;
		threads = new ArrayList<>(maxConnections);
	}
	
	public synchronized void connect(Socket sock){
		currentConnections++;
		ServerThread serverThread = new ServerThread(sock);
		threads.add(new Thread(serverThread));
	}
	
	private synchronized void disconnect(Thread thread){
		currentConnections --;
		threads.remove(thread);
		notify();
	}
	
	public synchronized void waitForLegalConnections(){
		if(currentConnections>=maxConnections){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	class ServerThread implements Runnable{
		private Socket sock;
		/**
		 * Initiates the Server.ServerThread class
		 *
		 */
		public ServerThread(Socket sock) {
			this.setSock(sock);
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			
			
			disconnect(Thread.currentThread());
		}

		public Socket getSock() {
			return sock;
		}

		public void setSock(Socket sock) {
			this.sock = sock;
		}
		
	}
	
	
}
