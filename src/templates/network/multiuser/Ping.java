package templates.network.multiuser;

/**
 * Blank class used for handshaking between {@link Client} and {@link MultiUserServer}
 * @author Richousrick
 */
public class Ping implements Handshake{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7259769442335021811L;

	/* (non-Javadoc)
	 * @see templates.network.multiuser.Handshake#onRecieve()
	 */
	@Override
	public void onRecieve(ConnectionHandler h) {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see templates.network.multiuser.Handshake#preSend()
	 */
	@Override
	public void preSend(ConnectionHandler h) {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see templates.network.multiuser.Handshake#postSend()
	 */
	@Override
	public void postSend(ConnectionHandler h) {
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see templates.network.multiuser.Handshake#send()
	 */
	@Override
	public boolean send() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.richousrick.rat.network.Handshake#recieve()
	 */
	@Override
	public boolean recieve() {
		return false;
	}

}
