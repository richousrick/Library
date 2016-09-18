package templates.network.multiuser;

/**
 * Sent after one end closes the connection
 * @author Richousrick
 */
public class Kill implements Handshake{

	/**
	 * 
	 */
	private static final long serialVersionUID = 645361740897729027L;

	/* (non-Javadoc)
	 * @see templates.network.multiuser.Handshake#onRecieve()
	 */
	@Override
	public void onRecieve(ConnectionHandler h) {
		System.out.println("hi");
		if(!h.recievedKill){
			h.recievedKill = true;
			h.disconnect(false);
		}
		
	}

	/* (non-Javadoc)
	 * @see templates.network.multiuser.Handshake#preSend()
	 */
	@Override
	public void preSend(ConnectionHandler h) {
		System.out.println("test");
	}

	/* (non-Javadoc)
	 * @see templates.network.multiuser.Handshake#postSend()
	 */
	@Override
	public void postSend(ConnectionHandler h) {
	}

	/* (non-Javadoc)
	 * @see templates.network.multiuser.Handshake#send()
	 */
	@Override
	public boolean send() {
		System.out.println("send");
		return true;
	}

	/* (non-Javadoc)
	 * @see com.richousrick.rat.network.Handshake#recieve()
	 */
	@Override
	public boolean recieve() {
		return true;
	}
	
	


}
