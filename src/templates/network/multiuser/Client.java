package templates.network.multiuser;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class Client extends ConnectionHandler{

	
	/**
	 * Initiates the Client class
	 * @throws IOException 
	 * @throws UnknownHostException 
	 *
	 */
	public Client(String ip, int port) throws UnknownHostException, IOException {
		super(new Socket(ip, port));
		System.out.println("Connected="+socket.isConnected());
	}
	
	public boolean connected(){
		return socket.isConnected();
	}
	
	public void disconnect(boolean waitForReply){
		try {
			Thread.sleep(1000);
			while(!toWrite.isEmpty()){
				toWrite.dequque();
			}
			Thread.sleep(1000);
			write(new Kill());
			if(waitForReply&&!recievedKill){
				long time = System.currentTimeMillis();
				loop:while(System.currentTimeMillis()-time<60000){
					Object o = read();
					if(o.getClass().equals(Kill.class)){
						System.out.println("killing now");
						break loop;
					}else{
						Thread.sleep(100);
					}
				}
			}
			Thread.sleep(1000);
			out.close();
			in.close();
			readThread.interrupt();
			readThread = null;
			Thread.sleep(1000);
			writeThread.interrupt();
			writeThread = null;
			
		}catch(InterruptedException | NullPointerException | IOException e){
		}
	}

	/* (non-Javadoc)
	 * @see templates.network.multiuser.ConnectionHandler#handleConnection()
	 */
	@Override 
	void handleConnection() throws InterruptedException{

	}
	
	
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Client c = new Client("localhost", 9999);
	}
	
}
