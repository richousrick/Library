package synchronization;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class Synchronization {

	public synchronized void waitForUpdate() {
		try{
			wait();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public synchronized void update() {
		notifyAll();
	}
}


