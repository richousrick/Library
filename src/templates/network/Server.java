package templates.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class Server{
	boolean serverUp = true;
	
	/**
	 * Initiates the Server class
	 * @throws IOException 
	 *
	 */
	public Server(int port, int maxConnections) throws IOException {
		ServerSocket sSocket = new ServerSocket(port);
		ServerTimer timer = new ServerTimer(maxConnections);
		while(serverUp){
			timer.waitForLegalConnections();
			timer.connect(sSocket.accept());
		}
		
	}
	
	
}
