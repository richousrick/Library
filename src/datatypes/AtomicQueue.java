package datatypes;

import java.util.ArrayList;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class AtomicQueue <E> {

	private ArrayList<E> data;
	
	/**
	 * Initiates the AtomicQueue class
	 *
	 */
	public AtomicQueue() {
		data = new ArrayList<>();
	}
	
	/**
	 * Adds the object to the queue<br>
	 * WARNING: any object passed into this should be set to null<br>
	 * this is because i have not figured out how to clone generic's and so have added the pointer the the queue
	 * 
	 * @param object to add the the queue
	 */
	public synchronized void enqueue(E object){
		data.add(object);
		notifyAll();
	}
	
	/**
	 * Gets the next object in the queue
	 * @return next object or null if the queue is empty
	 */
	public synchronized E dequque(){
		if(data.size()>0){
			E returnVar = data.get(0);
			data.remove(0);
			return returnVar;
		}else {
			return null;
		}
	}
	
	/**
	 * Dequeue without removing the item
	 * @return next object or null if the queue is empty
	 */
	public synchronized E peek(){
		if(data.size()>0){
			return data.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * @return the number of items in the queue
	 */
	public synchronized int getSize(){
		return data.size();
	}
	
	/**
	 * @return true if there are no items in the queue
	 */
	public synchronized boolean isEmpty() {
		return data.size()==0;
	}
	
	/**
	 * Waits till the queue stops being empty
	 * @param millis how often to check 
	 */
	public synchronized void waitForData(int millis){
		if(data.size()==0){
			try {
				while(data.size()==0){
					wait(millis);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * Waits till the queue stops being empty
	 */
	public synchronized void waitForData(){
		waitForData(100);
	}
	
	
}
