package synchronization;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class SyncronisedObjectManager <E> {

	private E data;
	
	/**
	 * Initiates the SyncronisedObjectManager class
	 *
	 */
	public SyncronisedObjectManager(E data) {
		this.data = data;
	}
	
	public synchronized void updateValue(E newValue){
		synchronized (data) {
			data = newValue;
		}
	}
}
