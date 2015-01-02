public class ProductTester {
    private ProductLookup lookup;

    
    public ProductTester(int maxSize) {
        lookup = new ProductLookup(maxSize);
        addItems();
        runTests();
        }
                        
    private void addItems() {
        StockItem item = new StockItem("AGT-1234","Runner","Nike",22.15f,49.95f);
        lookup.addItem("AGT-1234",item);
        item = new StockItem("PPR-8958","Trainer","Nike",47.15f,89.95f);
        lookup.addItem("PPR-8958",item);        
        item = new StockItem("NRQ-5814","Runner","Nike",19.47f,49.95f);
        lookup.addItem("NRQ-5814",item);        
        item = new StockItem("TTC-9514","Trainer","Addidas",32.50f,79.95f);
        lookup.addItem("TTC-9514",item);        
        item = new StockItem("MJK-3694","Baseball","Nike",18.75f,39.95f);
        lookup.addItem("MJK-3694",item);        
        item = new StockItem("DDW-7748","Basketball","Rebok",8.94f,29.95f);
        lookup.addItem("DDW-7748",item);        
        item = new StockItem("SQR-5291","Runner","Nike",35.85f,69.95f);
        lookup.addItem("SQR-5291",item);        
        item = new StockItem("UNK-2817","CrossFit","Addidas",53.65f,119.95f);
        lookup.addItem("UNK-2817",item);        
        item = new StockItem("LLP-7193","Runner","Nike",26.50f,49.95f);
        lookup.addItem("LLP-7193",item);        
        item = new StockItem("TTG-9731","Runner","Nike",25.50f,69.95f);                                                                
        lookup.addItem("TTG-9731",item);
        }
        
    private void runTests() {
        System.out.println("Printing all products");    
        lookup.printAll();
        System.out.println("\nLooking for Addidas");
        lookup.print("Addidas");
        lookup.deleteItem("UNK-2817");
        System.out.println("\nThe Addidas item UNK-2817 should have been removed.");
        lookup.print("Addidas");  
        if(lookup.getRetail("XXX-1111") != -0.01f) 
            System.out.println("ERROR in getRetail with invalid SKU");
        }        
        
    public static void main(String [] args) {
        new ProductTester(1000);
        }        
    }

