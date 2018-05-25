import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private boolean[][] percolationGrid;
    private int gridLength;
    private WeightedQuickUnionUF percolationTree; 
    private WeightedQuickUnionUF percolationTreeNoBackwash;
    private int openCounter;
    
    public Percolation(int n) {
        if (n <= 0) 
            throw new IllegalArgumentException("Dimensions must be positive.");
        
        percolationGrid = new boolean[n][n];
        gridLength = n;
        openCounter = 0;
        percolationTree = new WeightedQuickUnionUF(n*n+2);
        // two extra cells (one above grid and one below grid) for efficient 
        // percolation calculation
        percolationTreeNoBackwash = new WeightedQuickUnionUF(n*n+1);
        // avoids backwash by excluding cell below the grid
    }
    
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > gridLength || col > gridLength) {
            throw new IllegalArgumentException("Please enter valid inputs.");
        }
        
        if (percolationGrid[row-1][col-1]) {
            return;
        }
        
        percolationGrid[row-1][col-1] = true;
        openCounter++;
        
        int index = gridLength*(row-1)+col;
        if (row == 1) {
            percolationTree.union(index, 0);
            percolationTreeNoBackwash.union(index, 0);
        }
        else if (percolationGrid[row-2][col-1]) {
            percolationTree.union(index, index-gridLength);
            percolationTreeNoBackwash.union(index, index-gridLength);
        }
        
        if (row == gridLength)
            percolationTree.union(index, gridLength*gridLength+1);
        else if (percolationGrid[row][col-1]) {
            percolationTree.union(index, index+gridLength);
            percolationTreeNoBackwash.union(index, index+gridLength);
        }
        
        if (col != 1 && percolationGrid[row-1][col-2]) {
            percolationTree.union(index, index-1);
            percolationTreeNoBackwash.union(index, index-1);
        }
        
        if (col != gridLength && percolationGrid[row-1][col]) {
            percolationTree.union(index, index+1);
            percolationTreeNoBackwash.union(index, index+1);
        }
    
    }
    
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > gridLength || col > gridLength)
            throw new IllegalArgumentException("Please enter valid inputs.");
        return percolationGrid[row-1][col-1];
    }
    
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > gridLength || col > gridLength)
            throw new IllegalArgumentException("Please enter valid inputs.");
        int index = gridLength*(row-1)+col;
        
        return percolationTreeNoBackwash.find(index) == 
            percolationTreeNoBackwash.find(0);
    }      
    
    public int numberOfOpenSites() {
        return openCounter;
    }
    
    public boolean percolates() {
        return percolationTree.connected(0, gridLength*gridLength+1);
    }
               
}
    
    
    
    