import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;

public class BruteCollinearPoints {
    
    private final LineSegment[] listOfLines;
    
    public BruteCollinearPoints(Point[] points) {
        
        int collinearCounter = 0;
        ArrayList<LineSegment> collinear = new ArrayList<LineSegment>();
        
        if (points == null)
            throw new java.lang.IllegalArgumentException("Parameter is null");
        
        Point[] copy = new Point[points.length];
        
        for (int p = 0; p < points.length; p++)
            copy[p] = points[p];
        
        if (findNull(copy))
            throw new java.lang.IllegalArgumentException("Contains null entries");
        
        Arrays.sort(copy, new ComparePoint());
        
        if (findRepeat(copy))
            throw new java.lang.IllegalArgumentException("Contains repeated points");
        
        
        for (int i = 0; i < copy.length - 3; i++) {
            for (int j = i + 1; j < copy.length - 2; j++) {
                for (int k = j + 1; k < copy.length - 1; k++) {
                    for (int r = k + 1; r < copy.length; r++) {   
                        
                        double slopeone = copy[i].slopeTo(copy[j]);
                        double slopetwo = copy[i].slopeTo(copy[k]);
                        double slopethree = copy[i].slopeTo(copy[r]);
                                
                        if (slopeone == slopetwo && slopetwo == slopethree) {
                            LineSegment col = new LineSegment(copy[i], copy[r]);
                            collinear.add(collinearCounter, col);
                            collinearCounter++;
                        }
                    }
                }
            }
        }
    
        listOfLines = new LineSegment[collinearCounter];
        
        for (int d = 0; d < collinearCounter; d++)
            listOfLines[d] = collinear.get(d);
    
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