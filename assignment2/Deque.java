import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int numberOfItems;
    
    private class Node {
        private Item item;
        private Node next; 
        private Node previous;
    }
    
    public Deque() {
        first = null;
        last = null;
        numberOfItems = 0;
    }
    
    public boolean isEmpty() {
        return (first == null || last == null);
    }
    
    public int size() {
        return numberOfItems;
    }
    
    public void addFirst(Item item) {
        if (item == null)
            throw new 
            java.lang.IllegalArgumentException("Attempted to add null object.");
        
        numberOfItems++;
        
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.next = null;
            first.previous = null;
            last = first;
        }
        else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            first.previous = null;
            oldfirst.previous = first;
        }
    }
    
    public void addLast(Item item) {
        if (item == null)
            throw new 
            java.lang.IllegalArgumentException("Attempted to add null object.");
        
        numberOfItems++;
        
        if (isEmpty()) {
            last = new Node();
            last.item = item;
            last.next = null;
            last.previous = null;
            first = last;
        }
        else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.previous = oldlast;
            oldlast.next = last;
        }
    }
    
    public Item removeFirst() {
        if (isEmpty())
            throw new 
            java.util.NoSuchElementException("Deque is empty.");
        
        numberOfItems--;
        Item temp = first.item;
        first = first.next;
        
        if (!isEmpty())
            first.previous = null;
        else
            last = null;
        
        return temp;
    }
    
    public Item removeLast() {
        if (isEmpty())
            throw new 
            java.util.NoSuchElementException("Deque is empty.");
        
        numberOfItems--;
        Item temp = last.item;
        last = last.previous;
        
        if (!isEmpty())
            last.next = null;
        else
            first = null;
        
        return temp;
    }
    
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Does not support operation.");
        }
        
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("No more items to return.");
            
            Item temp = current.item;
            current = current.next;
            return temp;
        }
    }

}
        
        
        
        