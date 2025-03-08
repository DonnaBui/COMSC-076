package Chapter_24_ImplementingLists;
/*
 * Donna Bui - November 16, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
 * This driver program is written based on the instructions for the assignment Implementing Lists on Canvas. 
 * It will initialize a linked list with 10 values and test each newly-implemented method in the MyLinkedList class to ensure it works properly.
 */
import java.util.Collection;

public class TestMyLinkedList { 

	public static void main (String[] args) {

		String [] arr = {"One","Two","Three","Four","Six","Seven","Eight"};
		MyLinkedList<String> list = new MyLinkedList<>(arr); // Tests the constructor which creates a list from an array of objects
		System.out.println("Original   " + list);
		
		list.add("Nine"); // Test standard add method
		System.out.println("Added Nine " + list);
		
		list.add(4, "Five"); // Test add method with index
		System.out.println("Added Five " + list);
		
		list.addFirst("Zero"); // Test adding value to beginning of list
		System.out.println("Added Zero " + list);
		
		list.addLast("Ten"); // Test adding value to end of list
		System.out.println("Added Ten  " + list);
		
		System.out.println("Current List Size: " + list.size() + " values\n"); // Test size method
		
		// Test getting first and last element in list
		System.out.println("First element: " + list.getFirst()); // Zero
		System.out.println("Last element: " + list.getLast()); // Ten

		System.out.println("Second element in list: " + list.get(1) + "\n"); // Test getting a value with index 1 - One

		// Test indexOf method
		System.out.println("Index of Seven: " + list.indexOf("Seven")); // 7
		System.out.println("Index of Five: " + list.indexOf("Five")); // 5
		
		list.add(9, "Five"); // Adds a second Five value at index 9 to test lastIndexOf method
		System.out.println("Last Index of Five: " + list.lastIndexOf("Five") + "\n"); // 9
		
		// Test contains() method
		System.out.println("List has Eleven? " + list.contains("Eleven")); // false
		System.out.println("List has Three? " + list.contains("Three") + "\n"); // true
		
		// Test remove first value in list method - removes Zero from the list
		list.removeFirst();
		System.out.println("New first element: " + list.getFirst()); // One
		
		// Test remove last value in list method - removes Ten from the list
		list.removeLast();
		System.out.println("New last element: " + list.getLast() + "\n"); // Nine

		// Test remove at index method - removes Five from the list
		System.out.println("Original value at index 8: " + list.get(8)); // Five
		list.remove(8);
		System.out.println("New value at index 8: " + list.get(8) + "\n"); // null because the element was removed
		
		// Test set at index method
		System.out.println("Current List: " + list); // Current List containing values from one to nine
		list.set(8, "Not Nine");
		System.out.println("Changed Index 8: " + list); // Changed value at index 8 from Nine to Not Nine
		list.set(4, "Not Five");
		System.out.println("Changed Index 4: " + list); // Changed value at index 4 from Five to Not Five
	}
}




/*
 * This MyLinkedList class is written based on the instructions for Exercise 24.2 in the textbook. 
 * It will include the source code already provided in the textbook, and then implement the contains(), get(), indexOf(), lastIndexOf(), set(), and remove() methods which were omitted from Listing 24.5 in the textbook.
 */
class MyLinkedList<E> implements MyList<E> {

	private Node<E> head, tail;
	private int size = 0; // Number of elements in the list

	/** Create an empty list */
	public MyLinkedList() {
	}

	/** Create a list from an array of objects */
	public MyLinkedList(E[] objects) {
		for (int i = 0; i < objects.length; i++)
			add(objects[i]); 
	}

	/** Return the head element in the list */
	public E getFirst() {
		if (size == 0) {
			return null;
		}
		else {
			return head.element;
		}
	}

	/** Return the last element in the list */
	public E getLast() {
		if (size == 0) {
			return null;
		}
		else {
			return tail.element;
		}
	}

	/** Add an element to the beginning of the list */
	public void addFirst(E e) {
		Node<E> newNode = new Node<>(e); // Create a new node
		newNode.next = head; // link the new node with the head
		head = newNode; // head points to the new node
		size++; // Increase list size

		if (tail == null) // the new node is the only node in list
			tail = head;
	}

	/** Add an element to the end of the list */
	public void addLast(E e) {
		Node<E> newNode = new Node<>(e); // Create a new for element e

		if (tail == null) {
			head = tail = newNode; // The new node is the only node in list
		}
		else {
			tail.next = newNode; // Link the new with the last node
			tail = newNode; // tail now points to the last node
		}

		size++; // Increase size
	}

	@Override /** Add a new element at the specified index in this list. The index of the head element is 0 */
	public void add(int index, E e) {
		if (index == 0) {
			addFirst(e);
		}
		else if (index >= size) {
			addLast(e);
		}
		else {
			Node<E> current = head;
			for (int i = 1; i < index; i++) {
				current = current.next;
			}
			Node<E> temp = current.next;
			current.next = new Node<>(e);
			(current.next).next = temp;
			size++;
		}
	}

	/** Remove the head node and return the object that is contained in the removed node. */
	public E removeFirst() {
		if (size == 0) {
			return null;
		}
		else {
			E temp = head.element;
			head = head.next;
			size--;
			if (head == null) {
				tail = null;
			}
			return temp;
		}
	}

	/** Remove the last node and return the object that is contained in the removed node. */
	public E removeLast() {
		if (size == 0) {
			return null;
		}
		else if (size == 1) {
			E temp = head.element;
			head = tail = null;
			size = 0;
			return temp;
		}
		else {
			Node<E> current = head;

			for (int i = 0; i < size - 2; i++) {
				current = current.next;
			}

			E temp = tail.element;
			tail = current;
			tail.next = null;
			size--;
			return temp;
		}
	}

	@Override /** Remove the element at the specified position in this list. Return the element that was removed from the list. */
	public E remove(int index) {   
		if (index < 0 || index >= size) {
			return null;
		}
		else if (index == 0) {
			return removeFirst();
		}
		else if (index == size - 1) {
			return removeLast();
		}
		else {
			Node<E> previous = head;

			for (int i = 1; i < index; i++) {
				previous = previous.next;
			}

			Node<E> current = previous.next;
			previous.next = current.next;
			size--;
			return current.element;
		}
	}

	@Override /** Override toString() to return elements in the list */
	public String toString() {
		StringBuilder result = new StringBuilder("[");

		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			result.append(current.element);
			current = current.next;
			if (current != null) {
				result.append(", "); // Separate two elements with a comma
			}
			else {
				result.append("]"); // Insert the closing ] in the string
			}
		}

		return result.toString();
	}

	@Override /** Clear the list */
	public void clear() {
		size = 0;
		head = tail = null;
	}

	@Override /** Return true if this list contains the element e */
	public boolean contains(Object e) {
		for (E o : this) {
			if(o.equals(e)) {
				return true;
			}	     
		}
		return false;
	}

	@Override /** Return the element at the specified index */
	public E get(int index) {
	     if (index < size()-1){ // make sure it's within a valid range
	         Node<E> current = head;
	         for (int i = 0; i < index; i++){ // iterates through each value and stops at 1 before the index so that current can be set to the next value
	             current = current.next; 
	         }
	         return current.element;
	     }
	     return null; // if outside of valid range, return null
	}

	@Override /** Return the index of the head matching element in this list. Return -1 if no match. */
	public int indexOf(Object e) {
        Node<E> current = head;
        int index = 0;
        for (int i = index; i < size()-1; index++){ // iterates through each value and checks if the element at that index is equal to the object e
            if(current.element.equals(e)) {
                return index; // immediately returns index when a match is found
            }
            current = current.next; // changes current to the next value to be checked
        }
        return -1; // returns -1 if no match can be found
    }

	@Override /** Return the index of the last matching element in this list. Return -1 if no match. */
	public int lastIndexOf(E e) {
		Node<E> current = head;
		int currentMatch = -1;
		
        for (int i = 0; i < size()-1; i++){ // Same as indexOf method - iterates through each value and checks if the element at that index is equal to the object e
            if(current.element.equals(e)) {
            	currentMatch = i; // sets the temporary current match index to i 
            }
            current = current.next; // changes current to the next value to be checked
        }
        return currentMatch; // after whole list is checked, returns the index of the last matching element and the default value -1 if no match can be found
    }
	

	@Override /** Replace the element at the specified position in this list with the specified element. */
	public E set(int index, E e) {
		 if (index <= size()-1){ // make sure it's within a valid range
	         Node<E> current = head;
	         for (int i = 0; i < index; i++){ // iterates through each value and stops at 1 before the index 
	             current = current.next; 
	         }
	         current.element = e;
	     }
	     return null; // if outside of valid range, return null
	}

	@Override /** Return the number of elements in this list */
	public int size() {
		return size;
	}
	
	public static class Node<E> {
		E element;
		Node<E> next;

		public Node(E element) {
			this.element = element;
		}
	}
	
	// Mostly unused methods/classes
	@Override /** Override iterator() defined in Iterable */
	public java.util.Iterator<E> iterator() {
		return new LinkedListIterator();
	}

	private class LinkedListIterator implements java.util.Iterator<E> {
		private Node<E> current = head; // Current index 
		private Node<E> previous = null; // temporary value to be used in remove()
		private Node<E> beforePrev = null; // temporary value to be used in remove()

		@Override
		public boolean hasNext() {
			return (current != null);
		}

		@Override
		public E next() {
			E e = current.element;
			beforePrev = previous; 
			previous = current; 
			current = current.next; 
			return e;
		}

		@Override
		public void remove() {
			if (beforePrev == null) { 
				head = current; 
			} 
			else { 
				beforePrev.next = current; 
			} 
			size--;	 
		}
	}
}




/* MyList class source code taken from textbook */

interface MyList<E> extends Collection<E> {
  /** Add a new element at the specified index in this list */
  public void add(int index, E e);

  /** Return the element from this list at the specified index */
  public E get(int index);

  /** Return the index of the first matching element in this list.
   *  Return -1 if no match. */
  public int indexOf(Object e);

  /** Return the index of the last matching element in this list
   *  Return -1 if no match. */
  public int lastIndexOf(E e);

  /** Remove the element at the specified position in this list
   *  Shift any subsequent elements to the left.
   *  Return the element that was removed from the list. */
  public E remove(int index);

  /** Replace the element at the specified position in this list
   *  with the specified element and returns the new set. */
  public E set(int index, E e);
  
  @Override /** Add a new element at the end of this list */
  public default boolean add(E e) {
    add(size(), e);
    return true;
  }

  @Override /** Return true if this list contains no elements */
  public default boolean isEmpty() {
    return size() == 0;
  }

  @Override /** Remove the first occurrence of the element e 
   *  from this list. Shift any subsequent elements to the left.
   *  Return true if the element is removed. */
  public default boolean remove(Object e) {
    if (indexOf(e) >= 0) {
      remove(indexOf(e));
      return true;
    }
    else
      return false;
  }

  @Override
  public default boolean containsAll(Collection<?> c) {
    return true;
  }

  @Override
  public default boolean addAll(Collection<? extends E> c) {
    return true;
  }

  @Override
  public default boolean removeAll(Collection<?> c) {
    return true;
  }

  @Override
  public default boolean retainAll(Collection<?> c) {
    return true;
  }

  @Override
  public default Object[] toArray() {
    return null;
  }

  @Override
  public default <T> T[] toArray(T[] array) {
    return null;
  }
}
