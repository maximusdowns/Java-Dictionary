/* Max Downs
 * masc0437
 */

import data_structures.*;
import java.util.Iterator;

public class ProductLookup {
	
	private int maxSize;
	
	Hashtable<String,StockItem> hash;
   
    // Constructor.  There is no argument-less constructor, or default size
    public ProductLookup(int maxSize){
    	hash = new Hashtable<String, StockItem>(maxSize);
    }
       
    // Adds a new StockItem to the dictionary
    public void addItem(String SKU, StockItem item){
    	hash.insert(SKU, item);
    }
           
    // Returns the StockItem associated with the given SKU, if it is
    // in the ProductLookup, null if it is not.
    public StockItem getItem(String SKU){
    	return hash.getValue(SKU);
 
    }
       
    // Returns the retail price associated with the given SKU value.
    // -.01 if the item is not in the dictionary
    public float getRetail(String SKU){
    	if(hash.getValue(SKU) != null)
    		return hash.getValue(SKU).getRetail();
    	return -.01f;
    }
    
    // Returns the cost price associated with the given SKU value.
    // -.01 if the item is not in the dictionary
    public float getCost(String SKU){
    	if(hash.getValue(SKU) != null)
    		return hash.getValue(SKU).getCost();
    	return -.01f;
    }
    
    // Returns the description of the item, null if not in the dictionary.
    public String getDescription(String SKU){
    	if(hash.getValue(SKU) != null)
    		return hash.getValue(SKU).getDescription();
    	return null;
    }
       
    // Deletes the StockItem associated with the SKU if it is
    // in the ProductLookup.  Returns true if it was found and
    // deleted, otherwise false.  
    public boolean deleteItem(String SKU){
    	return hash.remove(SKU);
    }
       
    // Prints a directory of all StockItems with their associated
    // price, in sorted order (ordered by SKU).
    public void printAll(){
    	Iterator<StockItem> iter = hash.values();
    	while(iter.hasNext()){
    		StockItem ex = iter.next();
    		//ex.toString();
    		System.out.println(ex.toString());
    	}
    }
    
    
    // Prints a directory of all StockItems from the given vendor, 
    // in sorted order (ordered by SKU).
    public void print(String vendor){
    	Iterator<StockItem> iter = values();
    	while(iter.hasNext()){
    		StockItem tmp = iter.next();
    		if(tmp.getVendor().compareTo(vendor) == 0)
    			System.out.println(tmp);
    	}
    }
    
    // An iterator of the SKU keys.
    public Iterator<String> keys(){
    	return hash.keys();
    }
    
    // An iterator of the StockItem values.    
    public Iterator<StockItem> values(){
    	return hash.values();
    }
}
