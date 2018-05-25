import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import java.util.LinkedList;

public class PointSET {
    
    private final TreeSet<Point2D> setofpoints;
    
    public PointSET() {
        setofpoints = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty() {
        return setofpoints.isEmpty();
    }
    
    public int size() {
        if (isEmpty())
            return 0;
        else
            return setofpoints.size();
    }
    
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null.");
        
            setofpoints.add(p);
    }
    
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null");
        
        if (isEmpty())
            return false;
        else
            return setofpoints.contains(p);
    }
    
    public void draw() {
        for (Point2D p : setofpoints)
            p.draw();
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null");
        
        LinkedList<Point2D> points = new LinkedList<Point2D>();
        
        for (Point2D p : setofpoints) {
            if (rect.contains(p))
                   points.add(p);
        }
        
        return points;
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null");
        
        Point2D nearestpoint = null;
        double distance = 1000;
        
        if (setofpoints.isEmpty())
            return nearestpoint;
        
        for (Point2D x : setofpoints) {
            double thisdistance = p.distanceSquaredTo(x);
            if (nearestpoint == null || thisdistance < distance) {
                distance = thisdistance;
                nearestpoint = x;
            }
        }
    
        return nearestpoint;
    }
    
}

                                                     
        
        
    
    
            
            
    
    
        