public class Position {
    
    private final int row;
    private final int col;

    public Position(int row, int col) {
        
        this.row = row;
        this.col = col;
    
    }

    public int getRow() { 
        
        return row;
    
    }

    public int getCol() { 
    
        return col; 
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Position)) {
            return false;
        }
        
        Position p = (Position) other;
        
        return row == p.row && col == p.col;
    }

    @Override
    public int hashCode() {
        
        return row * 8 + col;
    
    }

    
    
    @Override
    public String toString() {
        
        char file = (char) ('a' + col);
        int rank = 8 - row;
        return "" + file + rank;
    }
}
