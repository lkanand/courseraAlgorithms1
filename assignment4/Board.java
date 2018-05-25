import java.util.LinkedList;

public class Board {
    
    private int[][] gameboard;
    private int manhattan;
    private final int length;
    
    public Board(int[][] blocks) {
        
        length = blocks[0].length;
        int[][] copy = new int[length][length];
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        
        gameboard = copy;
        
        manhattan = 0;
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (gameboard[i][j] != 0) {
                    int correctx = (gameboard[i][j] - 1) % length;
                    int correcty = (gameboard[i][j] - 1) / length;
                    manhattan = manhattan + Math.abs(i - correcty) + Math.abs(j - correctx);
                }
            }
        }
    }
    
    public int dimension() {
        return length;
    }
    
    public int hamming() {
        int counter = 0;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (((i*length + j + 1) % (length*length) != gameboard[i][j]) && gameboard[i][j] != 0)
                    counter++;
            }
        }
        
        return counter;
    }        

    public int manhattan() {
        return manhattan;
    }
    
    public boolean isGoal() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if ((i*length + j + 1) % (length*length) != gameboard[i][j])
                    return false;
            }
        }
        
        return true;
    }
    
    public Board twin() {
        
        Board twin = new Board(gameboard);
        
        int x = 0, y = 0, p = 0, q = 0;
        int count = 0, index = 0;
        
        while (count < 2 && index < length*length) {
            if (gameboard[index / length][index % length] != 0) {
                if (count == 0) {
                    x = index / length;
                    y = index % length;
                    count++;
                }
                else {
                    p = index / length;
                    q = index % length;
                    count++;
                }
            }
            index++;
        }
    
        twin.swap(x, y, p, q);
        return twin;
    }
    
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;
        if (this.manhattan != ((Board) y).manhattan)
            return false;
        if (this.length != ((Board) y).length)
            return false;
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (gameboard[i][j] != ((Board) y).gameboard[i][j])
                    return false;
            }
        }
        
        return true;
    }
    
    public Iterable<Board> neighbors() {
        int yofzero = 0;
        int xofzero = 0;
        
        for (int i = 0; i < length * length; i++) {
            if (gameboard[i / length][i % length] == 0) {
                yofzero = i / length;
                xofzero = i % length;
                break;
            }
        }
        
        LinkedList<Board> next = new LinkedList<Board>();
        
        if (yofzero != 0) {
            Board adjacent = new Board(gameboard);
            adjacent.swapzero(yofzero, xofzero, yofzero - 1, xofzero);
            next.add(adjacent);
        }
        if (yofzero != length - 1) {
            Board adjacent = new Board(gameboard);
            adjacent.swapzero(yofzero, xofzero, yofzero + 1, xofzero);
            next.add(adjacent);
        }
        if (xofzero != 0) {
            Board adjacent = new Board(gameboard);
            adjacent.swapzero(yofzero, xofzero, yofzero, xofzero - 1);
            next.add(adjacent);
        }
        if (xofzero != length - 1) {
            Board adjacent = new Board(gameboard);
            adjacent.swapzero(yofzero, xofzero, yofzero, xofzero + 1);
            next.add(adjacent);
        }
    
        return next;
    
    }   
        
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(length + "\n");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                s.append(String.format("%2d ", gameboard[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    private void swap(int y1, int x1, int y2, int x2) {
        int temp = gameboard[y1][x1];
        gameboard[y1][x1] = gameboard[y2][x2];
        gameboard[y2][x2] = temp;
        
        manhattan = 0;
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (gameboard[i][j] != 0) {
                    int correctx = (gameboard[i][j] - 1) % length;
                    int correcty = (gameboard[i][j] - 1) / length;
                    manhattan = manhattan + Math.abs(i - correcty) + Math.abs(j - correctx);
                }
            }
        }
    }
    
    private void swapzero(int yofzero, int xofzero, int yofnonzero, int xofnonzero) {
        int correctx = (gameboard[yofnonzero][xofnonzero] - 1) % length;
        int correcty = (gameboard[yofnonzero][xofnonzero] - 1) / length;
        
        int currentmanhattan = Math.abs(yofnonzero - correcty) + Math.abs(xofnonzero - correctx);
        int futuremanhattan = Math.abs(yofzero - correcty) + Math.abs(xofzero - correctx);
        
        gameboard[yofzero][xofzero] = gameboard[yofnonzero][xofnonzero];
        gameboard[yofnonzero][xofnonzero] = 0;
        
        manhattan = manhattan + futuremanhattan - currentmanhattan;
    }

}
        
        
        
                    
        