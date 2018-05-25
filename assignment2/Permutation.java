import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    
    public static void main(String[] args) {
        
        RandomizedQueue<String> text = new RandomizedQueue<String>();
        
        int j = Integer.parseInt(args[0]);
        
        int n = 0;
        
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            
            if (n < j)
                text.enqueue(s);
            else {
                int k = StdRandom.uniform(n+1);
                if (k < j) {
                    text.dequeue();
                    text.enqueue(s);
                }
            }
        
            n++;
        }
        
        for (int i = 0; i < j; i++)
            StdOut.println(text.dequeue());
    }
}
            