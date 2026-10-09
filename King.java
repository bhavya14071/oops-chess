import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private static final int[][] STEPS = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1},
        {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    public King(PieceColor color) {
        
        super(color);
    
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position from) {
        
        List<Position> moves = new ArrayList<>();
        
        addStepMoves(board, from, STEPS, moves);
        
        return moves;
    }

    @Override
    public String getName() { 
    
        return "King";
    
    }

    @Override
    public String getSymbol() { 
    
        return "♚";
    
    }
}
