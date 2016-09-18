package templates.extensable;

import java.util.HashMap;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class ExampleDictionary implements Dictionary{

	private HashMap<Object, DictionaryItem> dictionary;
	
	/**
	 * Initiates the ExampleDictionary class
	 *
	 */
	public ExampleDictionary() {
		dictionary = new HashMap<>();
		
		dictionary.put("test", new DictionaryItem() {
			
		});
	}
	
	/* (non-Javadoc)
	 * @see templates.extensable.Dictionary#getItem(java.lang.Object)
	 */
	@Override
	public DictionaryItem getItem(Object key) {
		return dictionary.get(key);
	}

}
