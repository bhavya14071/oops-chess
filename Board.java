import java.util.List;

public class Board {
    
    private final Piece[][] grid = new Piece[8][8];

    public Board() {
    
        setupStartingPosition();
    
    }

    public void clear() {
    
        for (int r = 0; r < 8; r++) {
    
            for (int c = 0; c < 8; c++) {
    
                grid[r][c] = null;
    
            }
        }
    }

    public void setupStartingPosition() {
    
        clear();
    
        PieceColor[] colors = { PieceColor.BLACK, PieceColor.WHITE };
    
        for (PieceColor color : colors) {
            int backRow = (color == PieceColor.BLACK) ? 0 : 7;
            int pawnRow = (color == PieceColor.BLACK) ? 1 : 6;

            grid[backRow][0] = new Rook(color);
            grid[backRow][1] = new Knight(color);
            grid[backRow][2] = new Bishop(color);
            grid[backRow][3] = new Queen(color);
            grid[backRow][4] = new King(color);
            grid[backRow][5] = new Bishop(color);
            grid[backRow][6] = new Knight(color);
            grid[backRow][7] = new Rook(color);

            for (int c = 0; c < 8; c++) {
                grid[pawnRow][c] = new Pawn(color);
            }
        }
    }

    public boolean isInside(int row, int col) {
    
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    
    }

    public Piece getPiece(int row, int col) {
    
        return grid[row][col];
    
    }

    public Piece getPiece(Position p) {
    
        return grid[p.getRow()][p.getCol()];
    
    }

    public void setPiece(Position p, Piece piece) {
    
        grid[p.getRow()][p.getCol()] = piece;
    
    }

    public Position findKing(PieceColor color) {
    
        for (int r = 0; r < 8; r++) {
    
            for (int c = 0; c < 8; c++) {
    
                Piece p = grid[r][c];
    
                if (p instanceof King && p.getColor() == color) {
    
                    return new Position(r, c);
    
                }
            }

        }

        return null;

    }

    public boolean isSquareAttacked(Position target, PieceColor byColor) {

        for (int r = 0; r < 8; r++) {

            for (int c = 0; c < 8; c++) {

                Piece p = grid[r][c];

                if (p != null && p.getColor() == byColor) {

                    List<Position> moves = p.getPossibleMoves(this, new Position(r, c));

                    if (moves.contains(target)) {

                        return true;

                    }
                }
            }
        }

        return false;

    }
}
