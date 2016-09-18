package templates.network.multiuser;

import java.io.Serializable;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public interface Handshake extends Serializable{

	/**
	 * Code to run if this packet has been received
	 */
	public void onRecieve(ConnectionHandler h);
	
	/**
	 * Code to run prior to sending this packet
	 */
	public void preSend(ConnectionHandler h);
	
	/**
	 * Code to run after the packet has been sent
	 */
	public void postSend(ConnectionHandler h);
	
	/**
	 * @return true if this packet should be sent
	 */
	public boolean send();
	
	/**
	 * @return true if this packet should be put into received
	 */
	public boolean recieve();
}
