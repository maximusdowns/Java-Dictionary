/* Max Downs
 * masc0437
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDS<E> implements ListADT<E> {

	private Node<E> head, tail;
	private int currentSize;
	
	// LinkedList DS constructor
	public LinkedListDS(){
		head = tail = null;
		currentSize = 0;
	}
	
	// nested class for Node
	class Node<E>{
		E data;
		Node <E> next;
		
		// node constructor
		public Node (E obj){
			data = obj;
			next = null;
		}
	}
	
	class IteratorHelper implements Iterator<E>{
		Node<E> iterPointer;
		
		// should these methods be in IteratorHelper()
		public IteratorHelper()
		{
			iterPointer = head;
		}
		
		public boolean hasNext(){
			return iterPointer != null;
		}
		
		public E next(){
			if(!hasNext())
				throw new NoSuchElementException();
			E temp = iterPointer.data;
			iterPointer = iterPointer.next;
			return temp;
		}

		public void remove() {
			throw new UnsupportedOperationException();		
		}
	}
	
//  Adds the Object obj to the beginning of the list
	public void addFirst(E obj){
		Node<E> newNode = new Node<E>(obj);
		if(head == null)
			head = tail = newNode;
		else{
			newNode.next = head;
			head = newNode;
		}
		currentSize++;
	}
	
//  Adds the Object obj to the end of the list
	public void addLast (E obj){
		Node<E> newNode = new Node<E>(obj);
		if(head == null)
				head = tail = newNode;
		else{
			tail.next = newNode;
			tail = newNode;
		}
		currentSize++;
	}
	
//  Removes the first Object in the list and returns it.
//  Returns null if the list is empty.
	public E removeFirst(){
		// list is empty
		if(head == null) 
			return null;
		E temp = head.data;
		head = head.next;
		if(head == null)
			tail = null;
		currentSize--;
		return temp;
	}
	
//  Removes the last Object in the list and returns it.
//  Returns null if the list is empty.
	public E removeLast(){
		// list is empty
		if(head == null)
			return null;
		E temp = tail.data;
		// one element in the list
		if(head == tail)
		{
			head = head.next;
			return temp;
		}
		Node<E> current = head, previous = null;
		while(current.next != null)
		{
			previous = current;
			current = current.next;
		}
		previous.next = null;
		tail = previous;
		currentSize--;
		return temp;
	}
	
//  Returns true if the list contains the Object obj, otherwise false
	public boolean contains (E  obj){
		Node<E> current = head;
		while(current != null && ((Comparable<E>) obj).compareTo(current.data) != 0)
		{
			if(current == obj)
				return true;
			current = current.next;
		}
		return false;
	}
	
//  Returns true if the list is full, otherwise false
	public boolean isFull(){
		return false;
	}
	
//  The list is returned to an empty state.
	public void makeEmpty(){
		head = tail = null;
		currentSize = 0;
	}
	
//  Removes the first instance of the specific Object obj from the list, if it exists.
//  Returns true if the Object obj was found and removed, otherwise false
	public boolean remove(E obj){
		Node<E> previous = null, current = head;
		while(current  != null && ((Comparable<E>)obj).compareTo(current.data) != 0)
		{
			previous = current;
			current = current.next;
		}
		// empty list or not found
		if(current == null)
			return false;
		if(current == head)
			removeFirst();
		else if (current == tail)
			removeLast();
		else{
			previous.next = current.next;
			currentSize--;
		}
		return true;
	}

//  Returns the first Object in the list, but does not remove it.
//  Returns null if the list is empty.
	public E peekFirst() {
		// list is empty
		if (head == null)
			return null;
		return head.data;
	}

//  Returns the last Object in the list, but does not remove it.
//  Returns null if the list is empty.
	public E peekLast() {
		// list is empty
		if (head == null)
			return null;
		return tail.data;
	}

//  Finds and returns the Object obj if it is in the list, otherwise
//  returns null.  Does not modify the list in any way
	public E find(E obj) {
		Node<E> previous = null, current = head;
		
		// go through the linked list
		while(current != null && ((Comparable<E>)obj).compareTo(current.data) != 0)
			current = current.next;
		// determine if the object is in the list
		if(current != null)
			return current.data;
		return null;
	}

//  Returns true if the list is empty, otherwise false
	public boolean isEmpty() {
		if(head == null)
			return true;
		return false;
	}

//  Returns the number of Objects currently in the list.
	public int size() {
		return currentSize;
	}

//  Returns an Iterator of the values in the list, presented in
//  the same order as the list.
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
}
