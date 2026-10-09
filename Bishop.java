import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private static final int[][] DIRECTIONS = { {-1, -1}, {-1, 1}, {1, -1}, {1, 1} };

    public Bishop(PieceColor color) {
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
    
        return "Bishop";
    
    }

    @Override
    public String getSymbol() { 
    
        return "♝";
    
    }
}
