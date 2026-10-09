import java.util.List;



public abstract class Piece {
    
    
    protected final PieceColor color;

    public Piece(PieceColor color) {
    
        this.color = color;
    
    }

    public PieceColor getColor() {
    
        return color;
    
    }

    public abstract List<Position> getPossibleMoves(Board board, Position from);

    public abstract String getName();     
    public abstract String getSymbol();   

    protected void addSlidingMoves(Board board, Position from, int[][] directions, List<Position> moves) {
        
        for (int[] d : directions) {
            
            int r = from.getRow() + d[0];
            int c = from.getCol() + d[1];
            
            while (board.isInside(r, c)) {
                
                Piece other = board.getPiece(r, c);
                
                if (other == null) {
                    
                    moves.add(new Position(r, c));      
                
                } else {

                    if (other.getColor() != color) {
                    
                        moves.add(new Position(r, c));  
                    
                    }
                    
                    break;                              
                }

                r += d[0];
                c += d[1];
            
            
            }
        }


    }

    
    protected void addStepMoves(Board board, Position from, int[][] steps,
                                List<Position> moves) {
        for (int[] s : steps) {
            
            int r = from.getRow() + s[0];
            int c = from.getCol() + s[1];
            
            
            if (!board.isInside(r, c)) {
            
                continue;
            
            }
            
            
            Piece other = board.getPiece(r, c);
            
            if (other == null || other.getColor() != color) {
            
                moves.add(new Position(r, c));
            
            }
        }

        
    }
}
