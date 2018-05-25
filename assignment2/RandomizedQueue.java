import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] queue; 
    private int n;
    
    public RandomizedQueue() {
        n = 0;
        queue = (Item[]) new Object[2];
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Cannot add a null item.");
        
        if (n == queue.length)
            resizeArray(queue.length * 2);
        
        queue[n++] = item;
    }
    
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Randomized queue is empty.");
        
        int j = StdRandom.uniform(n);
        Item temp = queue[j];
        
        if (n == queue.length / 4)
            resizeArray(queue.length / 2);
        
        queue[j] = queue[n-1];
        queue[--n] = null;
 
        return temp;
    }
    
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Randomized queue is empty.");
        
        int j = StdRandom.uniform(n);
        return queue[j];
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        
        private int current = 0;
        private final Item[] randomized;
        
        
        public RandomizedQueueIterator() {
            randomized = (Item []) new Object[n];
            
            for (int i = 0; i < n; i++)
                randomized[i] = queue[i];
        
            StdRandom.shuffle(randomized);
        
        }
        
        public boolean hasNext() {
            return (current < n);
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Unsupported operation");
        }
        
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("No more items to return.");
            
            return randomized[current++];
        }
    }
    
    private void resizeArray(int newcapacity) {
        Item[] a = (Item []) new Object[newcapacity];
        
        for (int i = 0; i < n; i++)
            a[i] = queue[i];
        
        queue = a;
    }
}