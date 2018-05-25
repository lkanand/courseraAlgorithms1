import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;

public class FastCollinearPoints {
    
    private final LineSegment[] listOfLines;
    
    public FastCollinearPoints(Point[] points) {
        
        if (points == null)
            throw new java.lang.IllegalArgumentException("Parameter is null");
        
        Point[] copy = new Point[points.length]; 
        
        for (int p = 0; p < points.length; p++)
            copy[p] = points[p];
        
        if (findNull(copy))
            throw new java.lang.IllegalArgumentException("Contains null entries");
        
        Arrays.sort(copy, new ComparePoint());
        
        if (findRepeat(copy))
            throw new java.lang.IllegalArgumentException("Contains repeats");
        
        int collinearCounter = 0;
        ArrayList<LineSegment> collinear = new ArrayList<LineSegment>();
        
        
        for (int i = 0; i < copy.length; i++) {
            
            Arrays.sort(copy, new ComparePoint());

            Arrays.sort(copy, copy[i].slopeOrder());
                
            int m = 1;    
            int n = 1;
                
            while (n < copy.length) {
                while (n < copy.length && copy[0].slopeTo(copy[m]) == copy[0].slopeTo(copy[n]))
                    n++;
                if (n - m > 2) {
                    if (copy[0].compareTo(copy[m]) < 0) {
                        LineSegment coll = new LineSegment(copy[0], copy[n - 1]);
                        collinear.add(collinearCounter, coll);
                        collinearCounter++;
                    }
                }  
                m = n;
            }
            
        }
        
        listOfLines = new LineSegment[collinearCounter];
        
        for (int i = 0; i < collinearCounter; i++)
            listOfLines[i] = collinear.get(i);         
    }
    
    public int numberOfSegments() {
        return listOfLines.length;
    }
    
    public LineSegment[] segments() {
        LineSegment[] listOfLinesCopy = new LineSegment[listOfLines.length];
        
        for (int d = 0; d < listOfLines.length; d++)
            listOfLinesCopy[d] = listOfLines[d];
        
        return listOfLinesCopy;
    }
    
    private boolean findNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                return true;
        }
        return false;
    }
    
    private boolean findRepeat(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if (points[i-1].compareTo(points[i]) == 0)
                return true;
        }
        return false;
    }

    private class ComparePoint implements Comparator<Point> {

        public int compare(Point v, Point w) {
            return v.compareTo(w);
        }
    }      
    
}
                
                
                
    
    