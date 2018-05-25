import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;

public class PercolationStats {
    
    private double[] probabilities;
    private int numberOfTrials;
    
    public PercolationStats(int n, int trials) {
        
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Please enter valid inputs.");
        
        probabilities = new double[trials];
        numberOfTrials = trials;
        Percolation test;
        
        for (int i = 0; i < trials; i++) {
            test = new Percolation(n);
        
            while (!test.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                
                while (test.isOpen(row, col)) {
                     row = StdRandom.uniform(n) + 1;
                     col = StdRandom.uniform(n) + 1;
                }
                    test.open(row, col);  
            }
            
            int numberOpen = test.numberOfOpenSites();
            probabilities[i] = (double) numberOpen / (n*n);
        }
        
    }
    
    public double mean() {
        
        double average = StdStats.mean(probabilities);
        return average;
    }
    
    public double stddev() {

        double standardDeviation = StdStats.stddev(probabilities);
        return standardDeviation;
        
    }
    
    public double confidenceLo() {
        
        double average = mean();
        double standarddeviation = stddev();
        return average - (1.96 * standarddeviation) / Math.sqrt(numberOfTrials);
    }        
        
    public double confidenceHi() {
        
        double average = mean();
        double standarddeviation = stddev();
        return average + (1.96 * standarddeviation) / Math.sqrt(numberOfTrials);
    } 
     
    public static void main(String[] args) {
        
        int gridSize = StdIn.readInt();
        int t = StdIn.readInt();
        
        PercolationStats trial = new PercolationStats(gridSize, t);
        
        System.out.println("% java PercolationStats " + gridSize + "   " + t);
        System.out.println("mean                    = " + trial.mean());
        System.out.println("stddev                  = " + trial.stddev());
        System.out.println("95% confidence interval = [" + trial.confidenceLo() 
        + ", " + trial.confidenceHi() + "]");
    }
    
}
        
         
    