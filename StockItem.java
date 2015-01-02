/* Max Downs
 * masc0437
 */



public class StockItem implements Comparable<StockItem> {
    
	// class level private variables
	String SKU;				// SKU - Stock Keeping Unit
    private String description;
    private String vendor;
    private float cost;
    private float retail;
   
    // Constructor.  Creates a new StockItem instance.  
    public StockItem(String SKU, String description, String vendor,
                     float cost, float retail){
    	
    	// initialize parameters
    	this.SKU = SKU;
    	this.description = description;
    	this.vendor = vendor;
    	this.cost = cost;
    	this.retail = retail;
    }
    
    // Follows the specifications of the Comparable Interface.
    // The SKU is always used for comparisons, in dictionary order.  
    public int compareTo(StockItem n){
    	return SKU.compareTo(n.SKU);
    }
       
    // Returns an int representing the hashCode of the SKU.
    public int hashCode()   {
    	return SKU.hashCode();
    }
       
    // standard get methods
    public String getDescription(){
    	return description;
    }
    
    public String getVendor(){
    	return vendor;
    }
    
    public float getCost(){
    	return cost;
    }
    
    public float getRetail(){
    	return retail;
    }
        
    // All fields in one line, in order   
    public String toString(){
    	String desc = "SKU: " + SKU + "\nDescription: " + description + ".\n" + "Vendor: " + vendor + ".\n" + "Cost: " + cost + ".\n" + "Retail: " + retail + ".\n";
    	return desc;
    }
}     
