package templates.extensable;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;



/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class DictionaryService {
	private static DictionaryService instance;
	private ServiceLoader<Dictionary> loader;
	
	/**
	 * Initiates the DictionaryService class
	 */
	private DictionaryService(){
		loader = ServiceLoader.load(Dictionary.class);
	}
	
	public static synchronized DictionaryService getInstance() {
        if (instance != null) {
        	return instance;
        }else{
        	instance = new DictionaryService();
        	return instance;
        }
    }
	
	public DictionaryItem getItem(Object key){
		DictionaryItem item = null;
		
		 try {
		 	Iterator<Dictionary> dictionaries = loader.iterator();
            while (item == null && dictionaries.hasNext()) {
            	Dictionary d = dictionaries.next();
            	item = d.getItem(key);
            }
            return item;
		 } catch (ServiceConfigurationError serviceError) {
			 serviceError.printStackTrace();
			 return null;
		 }
	}
	
}
