/* Max Downs
 * masc0437
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinarySearchTree<K,V> implements DictionaryADT<K,V>{
	
	private int currentSize;
	private long modCounter;
	
	// Wrapper class for the BST
	private class Node<K,V>{
		private K key;
		private V value;
		private Node<K,V> leftChild;
		private Node<K,V> rightChild;
		
		public Node(K k, V v){
			key = k;
			value = v;
			leftChild = rightChild = null;
		}
	}
	
	// first node of the tree
	private Node<K,V> root;
	
	// constructor
	public BinarySearchTree(){
		root = null;
		currentSize = 0;
	}
	
	// the Binary Search Tree will never be full for this assignment
	public boolean isFull(){
		return false;
	}

	// recursively insert a new node
	public boolean insert(K k, V v){
		if(root == null)
			root = new Node<K,V>(k,v);
		else
			add(k,v,root,null,false);
		currentSize++;
		modCounter++;
		return true;
	}
	
	// add method 
	public void add(K k, V v, Node<K,V> n, Node<K,V> parent, boolean wasLeft) {
		// this is the leaf node, so perform the insert
		if(n == null){
			if(wasLeft)
				parent.leftChild = new Node<K,V>(k,v);
			else 
				parent.rightChild = new Node<K,V>(k,v);
		}
		else if (((Comparable<K>)k).compareTo((K)n.key) < 0)
			// go left
			add(k,v,n.leftChild,n,true);
		else
			// go right
			add(k,v,n.rightChild,n,false);
	}


	// find node with given key recursively
	public V getValue(K key) {
		return find(key, root);
	}
	
	private V find(K key, Node<K,V> n){
		// stopping condition, empty or run off end of tree
		if(n == null)
			return null;
		if(((Comparable<K>)key).compareTo(n.key) < 0)
			// go left, compareTo returns -1
			return find(key, n.leftChild);
		if(((Comparable<K>)key).compareTo(n.key) > 0)
			// go right, compareTo returns 1
			return find(key, n.rightChild);
		// return the value, if found
		return (V) n.value;
	}
	
	// we will use foundKey to recursively solve the getKey() method
	private K foundKey;
	
	public K getKey(V value) {
		foundKey = null;
		valueFinder(root, value);
		return foundKey;
	}
	
	private void valueFinder(Node<K,V> n, V value){
		// stopping condition
		if(n == null)
			return;
		if(((Comparable <V>)value).compareTo(n.value) == 0){
			foundKey = n.key;
			return;
		}
		valueFinder(n.leftChild,value);
		valueFinder(n.rightChild,value);
	}
	
	
	// recursively determine if the key is contained in the binary search tree
	public boolean contains(K key) {
		return contains2(key, root);
	}
	
	public boolean contains2(K key, Node<K,V> current){
		// stopping condition, running off the end of the tree
		if(current == null)
			return false;
		if(((Comparable<K>)key).compareTo(current.key) < 0)
				return contains2(key, current.leftChild);
		if(((Comparable<K>)key).compareTo(current.key) > 0)
				return contains2(key, current.rightChild);
		// this is the case where the compareTo method returns 0, or ==
		return true;
		
	}

	// the remove method will be solved recursively
	public boolean remove(K key){
		if(root==null)
			return false;
		// increment and decrement accordingly if deletion occurs
		if(delete(key,  root, null, false)){
			currentSize--;
			modCounter++;
			return true;
		}
		else
			return false;
	}
	
	// delete method to be called recursively in remove method
	// move through the tree with a node pointing to current and a parent pointer pointing to the last node passed
	// keep track if the last move was to the left
	private boolean delete(K key, Node<K,V> current, Node<K,V> parent, boolean isLeft){
		while(current!= null && (((Comparable<K>)key).compareTo(current.key) != 0)){
			if((((Comparable<K>)key).compareTo(current.key) > 0)){
				parent = current;
				current = current.rightChild;
				isLeft = false;
			}
			else {
				parent = current;
				current = current.leftChild;
				isLeft = true;
			}
		}
		// empty tree
		if(current == null)
			return false;
		
		// no children
		if(current.rightChild ==null && current.leftChild == null){
			if(current == root)
				root = null;
			else if(isLeft)
				parent.leftChild = null;
			else
				parent.rightChild = null;
		}
		
		// one child, left
		else if(current.rightChild == null){
			if(current == root)
				root = current.leftChild;
			else if (isLeft)
				parent.leftChild = current.leftChild;
			else
				parent.rightChild = current.leftChild;
		}
		
		// one child, right
		else if(current.leftChild == null){
			if(current == root)
				root = current.rightChild;
			else if(isLeft)
				parent.leftChild = current.rightChild;
			else
				parent.rightChild = current.rightChild;
		}
		
		// use inOrderSuccessor to traverse the tree
		else {
			Node<K,V> successor = getSuccessor(current);
			if(current == root){
				Node<K,V> tmp = successor;
				delete(successor.key, root, null, false);
				root.key = tmp.key;
				root.value = tmp.value;
			}
			
			else {
				Node<K,V> tmp = successor;
				delete(successor.key, root, null, false);
				current.key = tmp.key;
				current.value = tmp.value;
			}
		}
		return true;
	}
	
	private Node<K,V> getSuccessor(Node<K,V> n){
		n = n.rightChild;
		while(n.leftChild != null)
			n = n.leftChild;
		return n;	
	}

	// return the currentSize of the binary tree
	public int size() {
		return currentSize;
	}

	// simply returns if currentSize is 0, indicating an empty tree
	public boolean isEmpty() {
		if  (currentSize == 0)
			return true;
		return false;
	}

	// clears the entire binary tree
	public void clear() {
		root = null;
		currentSize = 0;
		modCounter = 0;
	}

	public Iterator<K> keys() {
		return new KeyIteratorHelper();
	}
	
	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}
	
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected Node <K,V>[] array;
		protected long modCheck;
		protected int iterIndex;
		
		public IteratorHelper(){
			array = new Node[currentSize];
			modCheck = modCounter;
			iterIndex = 0;
			inOrderFillArray(root);
			iterIndex = 0;
		}
		
		// fill an auxillary array
		public void inOrderFillArray(Node<K,V> n)
		{
			if (n == null)
				return;
			inOrderFillArray(n.leftChild);
			array[iterIndex++] = n;
			inOrderFillArray(n.rightChild);		
		}
		
		public boolean hasNext(){
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
			return (iterIndex < currentSize);
		}
		
		public abstract E next();
		
		public void remove(){
			throw new UnsupportedOperationException();	
		}
	}

	protected class KeyIteratorHelper<K> extends IteratorHelper<K>{
		public KeyIteratorHelper(){
			super();
		}

		public K next() {
			return (K) array[iterIndex++].key;
		}
	}
	
	protected class ValueIteratorHelper<K> extends IteratorHelper<V>{
		public ValueIteratorHelper(){
			super();
		}

		public V next() {
			return (V) array[iterIndex++].value;
		}
	}
}
