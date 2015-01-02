package data_structures;
/* Max Downs
 * masc0437
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Hashtable<K,V> implements DictionaryADT<K,V> {

	private int maxSize;
	private int currentSize;
	private long modCounter;
	private int tableSize;
	
	// Linked List will be used because it will be faster than an order list
	private LinkedListDS<DictionaryNode<K,V>> [] list;
	
	// Wrapper class, designed to store two things in one cell
	class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{
		K key;
		V value;
		
		public DictionaryNode(K k, V v){
			key = k;
			value = v;
		}
		
		// compares instances of dictionary nodes using the key
		public int compareTo(DictionaryNode<K,V> node){
			return ((Comparable<K>)key).compareTo((K)node.key);
		}
	}
	
	// Hashtable constructor
	public Hashtable(int n){
		currentSize = 0;
		maxSize = n;
		modCounter = 0;
		tableSize = (int)(maxSize * 1.3f);
		list = new LinkedListDS[tableSize];
		for(int i=0; i < tableSize; i++)
			list[i] = new LinkedListDS<DictionaryNode<K,V>>();
	}
	
	/* the method in the Hashtable class that gets the hash code from the key and returns the index in the array */
	private int getIndex(K key){
		// make positive and mod it on the tableSize
		return (key.hashCode() & 0x7FFFFFFF) % tableSize;
	}
	
	// Returns the Dictionary object to an empty state.
	public void clear(){
		currentSize = 0;
		modCounter = 0; // list still has things in them at this point
		for(int i = 0; i < tableSize; i++)
			list[i].makeEmpty(); 
	}

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key) {
		list[getIndex(key)].contains(new DictionaryNode<K,V>(key,null));
		return false;
	}

	// Adds the given key/value pair to the dictionary.  Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean insert(K key, V value) {
		
		// if the node that we are trying to insert into the linked list already exists return false
		if(list[getIndex(key)].contains(new DictionaryNode<K,V> (key, null))){
			return false;
		}
		// otherwise create and add a new Node with the passed parameter vaues and increment the counter variabes
		list[getIndex(key)].addFirst(new DictionaryNode<K,V>(key,value));
		currentSize++;
		modCounter++;
		return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean remove(K key) {
		if(list[getIndex(key)].remove(new DictionaryNode<K,V>(key,null)))
		{
			currentSize--;
			modCounter++;
			return true;
		}
		return false;
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key) {
		DictionaryNode<K,V> tmp = list[getIndex(key)].find(new DictionaryNode<K,V>(key, null));
		if (tmp == null)
			return null;
		return tmp.value;
	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more
	// than one key exists that matches the given value, returns the
	// first one found.
	public K getKey(V value) {
		
		// go through the hash table
		for(int i = 0; i < tableSize; i++)
			
			// go through each linked list
			for(DictionaryNode<K,V> n : list[i])
				
				// fine compare the values and return the key
				if(((Comparable<V>)n.value).compareTo(value) == 0)
					return n.key;
		return null;
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size() {
		return currentSize;
	}

	// Returns true if the dictionary is at max capacity
	public boolean isFull() {
		if (currentSize == maxSize)
			return true;
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty() {
		if  (currentSize == 0)
			return true;
		return false;
	}

	public Iterator<K> keys() {
		return new KeyIteratorHelper<K>();
	}


	public Iterator<V> values() {
		return new ValueIteratorHelper<V>();
	}
	
	
	// Iterator helper abstract class to be inherited from for Value and Key
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected DictionaryNode [] nodes;
		protected int idx;
		protected long modCheck;
		
		public IteratorHelper(){
			nodes = new DictionaryNode[currentSize];
			idx = 0;
			int j = 0;
			modCheck = modCounter;
			// fill in auxiliary array
			for (int i=0; i < tableSize; i++)
				for(DictionaryNode n : list[i])
					nodes[j++] = n;
			// use shell sort to organize Dictionary Nodes
			nodes = (DictionaryNode[]) shellSort(nodes);
		}

		
		public boolean hasNext() {
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
			return (idx < currentSize);
		}

		//
		public E next(){
			if(!hasNext())
				throw new NoSuchElementException();
			return (E) nodes[idx++].key;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();	
		}
		
		// Implement shell sort in order to organize array
		public DictionaryNode[] shellSort(DictionaryNode<K,V> array[]){
			DictionaryNode<K,V>[] n = array;
			int in,  out,  h = 1;
			DictionaryNode<K,V> temp;
			int  size = n.length;
			
			while(h <= size/3)
				h = h*3+1;
			while(h > 0){
				for(out=h; out < size; out++){
					temp = n[out];
					in = out;
					while (in > h-1 && (((Comparable)n[in-h]).compareTo(temp) >= 0)){
						n[in] = n[in - h];
						in -= h;
					}
					n[in] = temp;
				}
				h = (h-3)/3;
			} // end for
			return n;
		}
	}
	
	protected class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper(){
			super();
		}

		public K next() {
			
			return (K) nodes[idx++].key;
		}
	}
	
	protected class ValueIteratorHelper<K> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}

		public V next() {
			
			return (V) nodes[idx++].value;
		}
	}
}
