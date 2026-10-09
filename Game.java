import java.util.ArrayList;
import java.util.List;



public class Game {
    
    private Board board;
    private PieceColor currentTurn;
    private List<Move> history;

    public Game() {
        newGame();
    }

    public void newGame() {

        board = new Board();
        
        currentTurn = PieceColor.WHITE;     
        
        history = new ArrayList<>();
    }

    public Board getBoard() { 
    
        return board;
    
    }
    
    public PieceColor getCurrentTurn() { 
        
        return currentTurn;
    
    }
    
    public List<Move> getHistory() { 
        
        return history;
    
    }

    public List<Position> getLegalMoves(Position from) {
        
        List<Position> legal = new ArrayList<>();
        
        Piece piece = board.getPiece(from);
        
        if (piece == null || piece.getColor() != currentTurn) {
            
            return legal;                   
        
        }

        for (Position to : piece.getPossibleMoves(board, from)) {
            
            if (!leavesOwnKingInCheck(from, to)) {
                
                legal.add(to);
            
            }
        }

        return legal;
    }

    private boolean leavesOwnKingInCheck(Position from, Position to) {
    
        Piece mover = board.getPiece(from);
    
        Piece captured = board.getPiece(to);

        board.setPiece(to, mover);          

        board.setPiece(from, null);

        boolean inCheck = isInCheck(mover.getColor());

        board.setPiece(from, mover);        
        board.setPiece(to, captured);
        
        return inCheck;
    }

    public boolean isInCheck(PieceColor color) {
        
        Position kingSquare = board.findKing(color);
        
        return board.isSquareAttacked(kingSquare, color.opposite());
    }

    public boolean makeMove(Position from, Position to) {
        
        if (!getLegalMoves(from).contains(to)) {
        
            return false;
        
        }
        
        Piece mover = board.getPiece(from);
        
        Piece captured = board.getPiece(to);

        board.setPiece(to, mover);
        board.setPiece(from, null);

        if (mover instanceof Pawn && (to.getRow() == 0 || to.getRow() == 7)) {
        
            board.setPiece(to, new Queen(mover.getColor()));
        
        }

        history.add(new Move(from, to, mover, captured));
        
        currentTurn = currentTurn.opposite();
        
        return true;
    }

    public boolean undo() {
        
        if (history.isEmpty()) {
            return false;
        }
        
        Move last = history.remove(history.size() - 1);
        
        board.setPiece(last.getFrom(), last.getMovedPiece());   // original piece goes back
        board.setPiece(last.getTo(), last.getCapturedPiece());  // captured piece (or null)
        currentTurn = currentTurn.opposite();
        
        return true;
    }

    private boolean currentPlayerHasLegalMove() {
        
        for (int r = 0; r < 8; r++) {
            
            for (int c = 0; c < 8; c++) {
                
                Piece p = board.getPiece(r, c);
                
                if (p != null && p.getColor() == currentTurn) {
                    
                    if (!getLegalMoves(new Position(r, c)).isEmpty()) {
                    
                        return true;
                    
                    }

                }
            }
        }

        return false;
    }

    public GameStatus getStatus() {
        
        boolean check = isInCheck(currentTurn);
        boolean canMove = currentPlayerHasLegalMove();
        
        if (!canMove) {
        
            return check ? GameStatus.CHECKMATE : GameStatus.STALEMATE;
        
        }
        
        return check ? GameStatus.CHECK : GameStatus.PLAYING;
    }
}
