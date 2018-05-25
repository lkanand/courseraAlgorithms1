import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.LinkedList;

public class KdTree {
    
    private Node root;
    private int numberofpoints;
    
    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private int level;
        private double minx, miny, maxx, maxy;
        
        private Node(Point2D p) {
            int layer = 1;
            double maximumx = 1, maximumy = 1;
            double minimumx = 0, minimumy = 0; 
            Node temp = root;
            
            while (true) {
                if (temp == null) {
                    point = p;
                    left = null;
                    right = null;
                    level = layer;
                    minx = minimumx;
                    miny = minimumy;
                    maxx = maximumx;
                    maxy = maximumy;
                    return;
                }
                else if (temp.level % 2 != 0 && p.x() <= temp.point.x()) {
                    layer++;
                    maximumx = temp.point.x();
                    temp = temp.left;
                }
                else if (temp.level % 2 == 0 && p.y() <= temp.point.y()) {
                    layer++;
                    maximumy = temp.point.y();
                    temp = temp.left;
                }
                else if (temp.level % 2 != 0 && p.x() > temp.point.x()) {
                    layer++;
                    minimumx = temp.point.x();
                    temp = temp.right;
                }
                else {
                    layer++;
                    minimumy = temp.point.y();
                    temp = temp.right;
                }
            }
        }
    }
        
    
    public KdTree() {
        root = null;
        numberofpoints = 0;
    }
    
    public boolean isEmpty() {
        return (numberofpoints == 0);
    }
    
    public int size() {
        return numberofpoints;
    }
    
    public void insert(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null");
        
        if (contains(p))
            return;
        else {
            root = insertNode(p, root);
            numberofpoints++;
        }
    }
    
    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null");
        
        Node temp = root;
        
        while (root != null) {
            if (root.point.equals(p)) {
                root = temp;
                return true;
            }
            else if (root.level % 2 != 0 && p.x() <= root.point.x())
                root = root.left;
            else if (root.level % 2 == 0 && p.y() <= root.point.y())
                root = root.left;
            else
                root = root.right;
        }
        
        root = temp;
        return false;
    }
    
    public void draw() {
        drawNode(root);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null");
        
        LinkedList<Point2D> tracker = new LinkedList<Point2D>();
        return rangeLinkedList(rect, root, tracker);
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null");
        
        return nearest(p, root, 1000);
    }
    
    private Node insertNode(Point2D p, Node bar) {
        if (bar == null) {
            Node next = new Node(p);
            bar = next;
        }
        else if ((bar.level % 2 != 0 && p.x() <= bar.point.x()) || (bar.level % 2 == 0 && p.y() <= bar.point.y())) {
            bar.left = insertNode(p, bar.left);
        }
        else {
            bar.right = insertNode(p, bar.right);
        }
        
        return bar;
    }      
       
    private void drawNode(Node g) {
        if (g == null)
            return;
        
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(g.point.x(), g.point.y());
        StdDraw.setPenRadius();
        if (g.level % 2 != 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(g.point.x(), g.miny, g.point.x(), g.maxy);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(g.minx, g.point.y(), g.maxx, g.point.y());
        }
        
        drawNode(g.left);
        drawNode(g.right);
    }
    
    private LinkedList<Point2D> rangeLinkedList(RectHV rect, Node g, LinkedList<Point2D> points) {
        if (g == null)
            return points;
        
        double gx = g.point.x();
        double gy = g.point.y();
        double rectxmin = rect.xmin();
        double rectymin = rect.ymin();
        
        Point2D temp = new Point2D(gx, gy);
        if (rect.contains(temp))
            points.add(temp);
        
        if ((g.level % 2 != 0 && (rect.xmax() < gx || rectxmin > gx)) || (g.level % 2 == 0 && (rect.ymax() < gy || rectymin > gy))) {
            if ((g.level % 2 != 0 && gx < rectxmin) || (g.level % 2 == 0 && gy < rectymin))
                points = rangeLinkedList(rect, g.right, points);
            else
                points = rangeLinkedList(rect, g.left, points);
        }
        else {
            points = rangeLinkedList(rect, g.left, points);
            points = rangeLinkedList(rect, g.right, points);
        }
        
        return points;
    }
    
    private Point2D nearest(Point2D that, Node here, double comparisondistance) {
        if (that == null)
            throw new java.lang.IllegalArgumentException("Argument can't be null.");
        
        Point2D closest = null;
        Point2D comparisonpoint = null;
        double distance = comparisondistance;
        double rootdistance;
        int searchright = 0, searchleft = 0;
        RectHV templeft, tempright, temp;
        
        if (here == null)
            return closest;
        else {
            temp = new RectHV(here.minx, here.miny, here.maxx, here.maxy);
            if (temp.distanceSquaredTo(that) >= distance)
                return closest;
            else {
                rootdistance = here.point.distanceSquaredTo(that);
                if (rootdistance < distance) {
                    distance = rootdistance;
                    closest = here.point;
                }
            }
        }
        
        Node hereleft = here.left;
        if (hereleft != null) {
            templeft = new RectHV(hereleft.minx, hereleft.miny, hereleft.maxx, hereleft.maxy);
            if (templeft.distanceSquaredTo(that) < distance)
                searchleft = 1;
        }
        
        Node hereright = here.right;
        if (hereright != null) {    
            tempright = new RectHV(hereright.minx, hereright.miny, hereright.maxx, hereright.maxy);
            if (tempright.distanceSquaredTo(that) < distance)
                searchright = 1;
        }
        
        if (searchright == 0 && searchleft == 0)
            return closest;
        else if (searchright == 1 && searchleft == 0) {
            comparisonpoint = nearest(that, here.right, distance);
            if (comparisonpoint != null)
                closest = comparisonpoint;
        }
        else if (searchright == 0 && searchleft == 1) {
            comparisonpoint = nearest(that, here.left, distance);
            if (comparisonpoint != null)
                closest = comparisonpoint;
        }
        else {
            if ((here.level % 2 != 0 && that.x() >= here.point.x()) || (here.level % 2 == 0 && that.y() >= here.point.y())) {
                comparisonpoint = nearest(that, here.right, distance);
                if (comparisonpoint != null) {
                    closest = comparisonpoint;
                    distance = closest.distanceSquaredTo(that);
                }
                templeft = new RectHV(hereleft.minx, hereleft.miny, hereleft.maxx, hereleft.maxy);
                if (templeft.distanceSquaredTo(that) < distance) {
                    comparisonpoint = nearest(that, here.left, distance);
                    if (comparisonpoint != null)
                        closest = comparisonpoint;
                }
            }
            else {
                comparisonpoint = nearest(that, here.left, distance);
                if (comparisonpoint != null) {
                    closest = comparisonpoint;
                    distance = closest.distanceSquaredTo(that);
                }
                tempright = new RectHV(hereright.minx, hereright.miny, hereright.maxx, hereright.maxy);
                if (tempright.distanceSquaredTo(that) < distance) {
                    comparisonpoint = nearest(that, here.right, distance);
                    if (comparisonpoint != null)
                        closest = comparisonpoint;
                }
            }
        }
        
        return closest;
    }   
    
    public static void main(String[] args) {
        Point2D a = new Point2D(0.7, 0.2);
        Point2D b = new Point2D(0.5, 0.4);
        Point2D c = new Point2D(0.2, 0.3);
        Point2D d = new Point2D(0.4, 0.7);
        Point2D e = new Point2D(0.9, 0.6);
        
        KdTree test = new KdTree();

        test.insert(a);
        test.insert(b);
        test.insert(c);
        test.insert(d);
        test.insert(e);
        
        Point2D that = new Point2D(0.1, 0.4);
        
        Point2D solution = test.nearest(that);
        System.out.println(solution.toString());
    }
        
}
            
           
                
        
    
    
        
        
    
    
        
    
    
    