package templates.network.multiuser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class MultiUserServer {

	static int MAX_ACTIVE_CONNECTIONS = 5;
	static int PORT = 9999;
	private static AtomicInteger currentConnections;
	private static MultiUserServer instance;
	
	private AtomicBoolean serverUp;
	private AtomicBoolean listening;
	private ServerSocket serverSocket;
	private Thread listenThread;
	private ArrayList<HandleClient> clients;
	
	/**
	 * Initiates the MultiUserServer class
	 * @throws IOException 
	 *
	 */
	public MultiUserServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
		serverSocket.setReuseAddress(true);
		currentConnections = new AtomicInteger();
		serverUp = new AtomicBoolean(true);
		listening = new AtomicBoolean(false);
		instance = this;
		clients = new ArrayList<>();
		System.out.println("server initiated");
		listen();
	}
	
	/**
	 * Listens for incoming connections from clients
	 * @throws IOException
	 */
	public void listen() throws IOException{
		if(!listening.get()&&serverUp.get()){
			listening.set(true);
			listenThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try{
						//serverUp checked twice so it will continue to check after server is full, but will stop once 
						while(serverUp.get()){
							while(currentConnections.get()<MAX_ACTIVE_CONNECTIONS&&serverUp.get()){
								if(serverSocket.isClosed()){
									serverSocket = new ServerSocket(PORT);
									serverSocket.setReuseAddress(true);
								}
								Socket sock = serverSocket.accept();
								HandleClient newClient = new HandleClient(sock);
								clients.add(newClient);
								newClient.start();
							}
							serverSocket.close();
							Thread.sleep(100);
						}
					}catch (IOException e){
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			listenThread.start();
			
		}
	}
	
	/**
	 * kills all current connections
	 */
	public void shutDown(){
		serverUp.set(false);
		listenThread.interrupt();
		try {
			if(!serverSocket.isClosed())
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (clients.size()>0){
			clients.get(0).disconnect(true);
		}
		currentConnections.set(0);
	}
	
	/**
	 * will kill all processes associated with the client and remove all references
	 * @param client to disconnect from the server
	 */
	public void disconnectClient(HandleClient client){
		if(client.clientConnected.get()){
			client.disconnect(true);
		}else{
			try {
				client.getSocket().close();
				client.interrupt();
				clients.remove(client);
				currentConnections.decrementAndGet();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * @return current instance of the server
	 */
	public static MultiUserServer getInstance(){
		return instance;
	}
	
	
	class HandleClient extends ConnectionHandler {

		private AtomicBoolean connect;
		/**
		 * Initiates the MultiUserServer.HandleClient class
		 *
		 */
		public HandleClient(Socket sock) {
			super(sock);
			currentConnections.incrementAndGet();
			System.out.println("Curent Connections:"+currentConnections.get());
			connect = new AtomicBoolean(true);
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try{
				handleConnection();
			}catch (InterruptedException e){
			}
			disconnect(true);
		}
		
		
		/**
		 * Safely kill the connection between the client and server
		 */
		public void disconnect(boolean waitForReply){
			if(connect.get()){
				kill(waitForReply);
				
				MultiUserServer.getInstance().disconnectClient(this);
			}
		}

		/**
		 * Forces the instance to stop 
		 */
		public void kill(boolean waitForReply) {
			try {
				Thread.sleep(1000);
				connect.set(false);
				while(!toWrite.isEmpty()){
					toWrite.dequque();
				}
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
			}
			try{
				write(new Kill());
				if (waitForReply&&!recievedKill) {
					
					long time = System.currentTimeMillis();
					loop:while(System.currentTimeMillis()-time<60000){
						Object o = read();
						if(o.getClass().equals(Kill.class)){
							break loop;
						}else{
							Thread.sleep(100);
						}
					}
				}
				Thread.sleep(1000);
				System.out.println("client kill");
				
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			try{
				readThread.interrupt();
				readThread = null;
				writeThread.interrupt();
				writeThread = null;
				out.close();
				in.close();
			}catch(NullPointerException | IOException e){}
		}
		
		/**
		 * 
		 * @return the {@link Socket} associated with this connection
		 */
		public Socket getSocket(){
			return socket;
		}

		/* (non-Javadoc)
		 * @see templates.network.multiuser.ConnectionHandler#handleConnection()
		 */
		@Override
		void handleConnection() throws InterruptedException {
			while(connect.get()){
				if(!recieved.isEmpty()){
					Object data = recieved.dequque();
					System.out.println(data);
				}else{
					sleep(100);
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		new MultiUserServer();
	}
	
}
