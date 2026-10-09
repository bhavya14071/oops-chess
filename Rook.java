import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private static final int[][] DIRECTIONS = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position from) {
        
        List<Position> moves = new ArrayList<>();
        addSlidingMoves(board, from, DIRECTIONS, moves);
        
        return moves;
    }

    @Override
    public String getName() { 
    
        return "Rook";
    
    }

    @Override
    public String getSymbol() { 
        
        return "♜";
    
    }
}
