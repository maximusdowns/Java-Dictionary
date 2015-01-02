package data_structures;

public class HashTest {
	private static final int SIZE = 1000;
    private static int [] array = new int[SIZE];
    private static String [] s;
    
    public static void main(String [] args) {
        initArray();
        s = getRandStockNumbers();   
           
		System.out.println("Exponentiation method");
        for(int i=0; i < SIZE; i++)
            array[hashExp(s[i])] += 1;            
        getStats();            
    	initArray();
		System.out.println("Add method");
		for(int i=0; i < SIZE; i++)
            array[hashAdd(s[i])] += 1;            
        getStats(); 
    	initArray();
		System.out.println("Multiply method");
		for(int i=0; i < SIZE; i++)
            array[hashMult(s[i])] += 1;            
        getStats();
    	initArray();
		System.out.println("Folding method");
		for(int i=0; i < SIZE; i++)
            array[hashFold(s[i])] += 1;            
        getStats(); 
    	initArray();
		System.out.println("Xor method");
		for(int i=0; i < SIZE; i++)
            array[hashXor(s[i])] += 1;            
        getStats();
    	initArray();
		System.out.println("Shift method");
		for(int i=0; i < SIZE; i++)
            array[hashShift(s[i])] += 1;            
        getStats();  
    	initArray();
		System.out.println("Java native hash method");
		for(int i=0; i < SIZE; i++)
            array[hashJava(s[i])] += 1;            
        getStats();                                                				
		
    }
    
    public static int hashExp(String s) {
        byte [] b = s.getBytes();
        double x,a;
		long hash=0;
        int j=0;
        for(int i=(s.length()-1)/2; i >= 0; i--) {  
		    x = (double) (b[j++]-96);
			a = (double) i;						
            hash += (long) Math.pow(26.0,a)*x;
        }
        hash = hash & 0x0000ffff;	
       
        return (int) hash % SIZE;
    }
	
	public static int hashAdd(String s) {
		byte [] b = s.getBytes();
		int hash = SIZE;
		for(int i=0; i < s.length(); i++)
			hash += (b[i]-26);		
		return hash % SIZE;
	}
	
	public static int hashMult(String s) {
		byte [] b = s.getBytes();
		long hash = 1;
		for(int i=0; i < s.length(); i++)
			hash *= b[i];
		if(hash < 0) hash *= -1;						
		return (int) (hash % SIZE);
	}
    
    public static int hashFold(String s) {
        byte [] b = s.getBytes();
        long hash, part1=0, part2=0;
        long offset = 1;
        //add the two parts AAA and the two parts 000 and take sum
        for(int i=0; i < 3; i++) {
            part1 += (int) b[i]*offset;	
            part2 += (int) b[i+3]*offset;
            offset <<= 3;
            }
        hash = part1 + part2;
        return (int) hash % SIZE;
    }
    
    public static int hashXor(String s) {
        byte [] b = s.getBytes();
        long hash = 0;
        for(int i=0; i < s.length(); i++)
            hash += b[i] ^ 55;
        return (int) hash % SIZE;
        }
        
    public static int hashShift(String s) {
        byte [] b = s.getBytes();

        long hashVal = 0;
           
        for(int i=s.length()-1; i >= 0; i--) 
             hashVal = (hashVal << 5) + b[i]; 
        hashVal &= 0x000000007FFFFFF;          
        return (int) (hashVal % (long) SIZE);
        }        
         
    public static int hashJava(String s) {    
        int hashValue = s.hashCode() % SIZE;
        if(hashValue < 0) hashValue = -hashValue;
        return hashValue;
        }        

            
    public static String getRandString(int length)  {
         String randString = "";
         byte b;
                
         for(int i=0; i < length; i++) {
               b = (byte) (26*Math.random()+97);
               randString += (char) b;
         }
         return randString;
    }
    
    private static void initArray() {
        for(int i=0; i < SIZE; i++)
            array[i] = 0;
    }
    
    private static void getStats() {
		System.out.println("Distribution of keys:");
		for(int i=0; i < SIZE; i++)
			System.out.print(array[i] + " ");
		System.out.println("\n");
       array = insertionSort(array);
        int hitCount = 1;
        for(int i=0; i < SIZE-1; ) {
            do {
                i++;
                hitCount++;
               } while(i < SIZE-1 && array[i] == array[i+1]);
            System.out.println(hitCount + " cells have " + 
                               array[i] + " elements");
            hitCount = 0;                                           
        }
        System.out.println("==============================================");    
            
    }
    
    private static String[] getRandStockNumbers() {
        
                String [] str = new String[SIZE];
                String temp = "";
                int where=0;
                byte [] b = {0x41,0x41,0x41,0x30,0x30,0x30};
        
        for(int i=0; i < 10; i++)
            for(int j=0; j < 10; j+=(int)5*Math.random()+1)
                for(int k=0; k < 10; k+=(int)5*Math.random()+1)
                    for(int l=0; l < 26; l+=(int)2*Math.random()+1)
                        for(int m=0; m < 26; m+=(int) 2*Math.random()+1)
                            for(int n=0; n < 26; n++) {
                                if(where >= SIZE) break; 
                                str[where++] = ""+                             
                                ((char)(b[0]+n)) +                                
                                ((char)(b[1]+m)) +
                                ((char)(b[2]+l)) +
                                ((char)(b[3]+k)) +
                                ((char)(b[4]+j)) +
                                ((char)(b[5]+i));
                            }
                return str;
        }
       
	private static int[] insertionSort(int array[]) {
        int [] n = array;
		int in, out, temp;
		for(out = 1; out < n.length; out++) {
			temp = n[out];
			in = out;
			while(in > 0 && n[in-1] > temp) {
				n[in] = n[in-1];
				in--;
			}
			n[in] = temp;
		}
		return n;
	}
}
