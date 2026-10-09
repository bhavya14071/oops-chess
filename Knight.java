import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    
    private static final int[][] STEPS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
        {1, -2},  {1, 2},  {2, -1},  {2, 1}
    
    };

    public Knight(PieceColor color) {
        
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
    
        return "Knight";
    
    }

    @Override
    public String getSymbol() { 
    
    
        return "♞";
    
    }
}
