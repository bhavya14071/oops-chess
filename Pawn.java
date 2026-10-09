import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position from) {
        
        List<Position> moves = new ArrayList<>();

        int direction = (color == PieceColor.WHITE) ? -1 : 1;
        int startRow = (color == PieceColor.WHITE) ? 6 : 1;

        int row = from.getRow() + direction;
        int col = from.getCol();

        if (board.isInside(row, col) && board.getPiece(row, col) == null) {
            
            moves.add(new Position(row, col));

            int twoAhead = row + direction;
            
            if (from.getRow() == startRow && board.getPiece(twoAhead, col) == null) {
                
                moves.add(new Position(twoAhead, col));
            
            }
        }

        int[] sideSteps = { -1, 1 };
        
        for (int side : sideSteps) {
            int c = col + side;
            
            if (board.isInside(row, c)) {
                
                Piece other = board.getPiece(row, c);
                
                if (other != null && other.getColor() != color) {
                
                    moves.add(new Position(row, c));
                
                }
            }
        }


        return moves;
    }

    @Override
    public String getName() { 
    
        return "Pawn"; 
    
    }

    @Override
    public String getSymbol() { 
    
        return "♟"; 
    
    }
}
