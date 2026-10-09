public class ConsoleDemo {
    public static void main(String[] args) {
        Board board = new Board();

        printBoard(board);

        Position e2 = new Position(6, 4);

        Piece pawn = board.getPiece(e2);

        System.out.println(pawn.getName() + " on " + e2 + " can go to: " + pawn.getPossibleMoves(board, e2));

        Position b1 = new Position(7, 1);
        
        Piece knight = board.getPiece(b1);
        
        System.out.println(knight.getName() + " on " + b1 + " can go to: " + knight.getPossibleMoves(board, b1));
    }

    static void printBoard(Board board) {
        
        for (int row = 0; row < 8; row++) {
            
            System.out.print((8 - row) + "  ");
            
            for (int col = 0; col < 8; col++) {
            
                Piece p = board.getPiece(row, col);
                System.out.print((p == null ? "." : letter(p)) + " ");
            
            }
            
            System.out.println();
        }

        System.out.println("   a b c d e f g h\n");
    }

    
    static String letter(Piece p) {
    
        String name = p.getName().equals("Knight") ? "N" : p.getName().substring(0, 1);
    
        return p.getColor() == PieceColor.WHITE ? name : name.toLowerCase();
    }
}
