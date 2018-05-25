import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private LinkedList<Board> sequence;
    private final Node last;
    
    private class Node {
        private final Node previous;
        private final int moves;
        private final Board current;
        private final int manhattanofboard;
        private final boolean attachedtotwin;
    
        public Node(Node p, int m, Board c, boolean b) {
            previous = p;
            moves = m;
            current = c;
            manhattanofboard = c.manhattan();
            attachedtotwin = b;
        }
    }
    
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException("Argument cannot be null");
        
        Board copy = initial;
        Board twin = initial.twin();
        
        Node first = new Node(null, 0, copy, false);
        Node firsttwin = new Node(null, 0, twin, true);
        
        MinPQ<Node> nodes = new MinPQ<Node>(new CompareNodes());
        nodes.insert(first);
        nodes.insert(firsttwin);
        
        Node tracker = nodes.delMin();
        
        Iterable<Board> nextnodes;
        
        while (!tracker.current.isGoal()) {
            nextnodes = tracker.current.neighbors();
            for (Board s : nextnodes) {
                if (tracker.previous == null || !s.equals(tracker.previous.current)) {
                    Node nextlevel = new Node(tracker, tracker.moves+1, s, tracker.attachedtotwin);
                    nodes.insert(nextlevel);
                }
            }
            
            tracker = nodes.delMin();
        }
        
        sequence = new LinkedList<Board>();
        last = tracker;
        
        if (tracker.attachedtotwin)
            sequence = null;
        else {
            while (tracker != null) {
                sequence.addFirst(tracker.current);
                tracker = tracker.previous;
            }
        }
    }

    
    public boolean isSolvable() {
        return (sequence != null);
    }
    
    public int moves() {
        if (sequence == null)
            return -1;
        else
            return last.moves;
    }
    
    public Iterable<Board> solution() {
        return sequence;
    }       
    
    private class CompareNodes implements Comparator<Node> {
        
        public int compare(Node a, Node b) {
            int w = a.moves;
            int x = a.manhattanofboard;
            int y = b.moves;
            int z = b.manhattanofboard;
            
            int j = w + x;
            int i = y + z;
            
            if (j < i)
                return -1;
            else if (j > i)
                return 1;
            else if (x < z)
                return -1;
            else if (x > z)
                return 1;
            else
                return 0;
        }
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
        
    
            
            
        
    
    