package templates.network.multiuser;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import datatypes.AtomicQueue;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public abstract class ConnectionHandler extends Thread{

	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	protected Socket socket;
	protected AtomicQueue<Serializable> toWrite;
	protected AtomicQueue<Object> recieved;
	protected AtomicBoolean clientConnected;
	protected Thread writeThread;
	protected Thread readThread;
	private static int MS_TILL_PING = 60000;
	boolean recievedKill = false;
	
	
	/**
	 * Initiates the ConnectionHandler class
	 * @param sock of the connection
	 */
	public ConnectionHandler(Socket sock) {
		this.socket = sock;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			toWrite = new AtomicQueue<>();
			recieved = new AtomicQueue<>();
			clientConnected = new AtomicBoolean(true);
			initWriteThread();
			initReadThread();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Code to run when the connection has been made
	 */
	abstract void handleConnection() throws InterruptedException;		

	/**
	 * Initiates the write thread
	 */
	public void initWriteThread(){
		writeThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					long lastTime = System.currentTimeMillis();
					while(clientConnected.get()){
						if(toWrite.getSize()>0){
							Object o = toWrite.dequque();
							if(o instanceof Handshake){
								((Handshake) o).preSend(ConnectionHandler.this);
								if(((Handshake) o).send()){
									out.writeObject(o);
									out.flush();
									
								}
								((Handshake) o).postSend(ConnectionHandler.this);
								if(o.getClass().equals(Kill.class)){
									return;
								}
							}else{
								out.writeObject(o);
								out.flush();
							}
							lastTime = System.currentTimeMillis();
						}
						Thread.sleep(100);
						if(System.currentTimeMillis()-lastTime>MS_TILL_PING){
							out.writeObject(new Ping());
							out.flush();
							lastTime=System.currentTimeMillis();
						}
					}
				}catch (InterruptedException e){
					
				}catch (IOException e){
					disconnect(false);
				}
			}
		});
		writeThread.start();
	}
	
	/**
	 * Initiates the read thread
	 */
	public void initReadThread(){
		readThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					while(clientConnected.get()){
						Object o;
						try {
							o = in.readObject();
							System.out.println(o);
							if(o!=null){
								if(o instanceof Handshake){
									((Handshake) o).onRecieve(ConnectionHandler.this);
									if(((Handshake) o).recieve()){
										recieved.enqueue(o);
										if(o.getClass().equals(Kill.class)){
											return;
										}
									}
								}else{
									recieved.enqueue(o);
								}
							}
						}catch (EOFException e){
						}
					}
				}catch (ClassNotFoundException | IOException e) {
					disconnect(false);
				}
			}
		});
		readThread.start();
	}

	/**
	 * waits for and returns the next object read from instream
	 * Warning this will pause the thread so only use this if you are sure that you will get data
	 * @return
	 */
	public Object read(){
		recieved.waitForData();
		return recieved.dequque();
	}
	
	/**
	 * write the object to the outstream
	 * @param o
	 */
	public void write(Serializable o){
		toWrite.enqueue(o);
	}
	
	/**
	 * Code to run before the connection is closed
	 * @param b
	 */
	protected abstract void disconnect(boolean b);
}
