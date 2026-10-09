public class ChessTest {
    static int failures = 0;

    static void check(String name, boolean ok) {

        System.out.println((ok ? "PASS  " : "FAIL  ") + name);

        if (!ok) {
            failures++;

        }
    }

    static Position p(String square) {          

        int col = square.charAt(0) - 'a';
        
        int row = 8 - (square.charAt(1) - '0');
        
        return new Position(row, col);
    }

    static boolean play(Game g, String from, String to) {
        
        return g.makeMove(p(from), p(to));
    
    }

    public static void main(String[] args) {
        Game g = new Game();

        int total = 0;
        
        for (int r = 0; r < 8; r++) {
            
            for (int c = 0; c < 8; c++) {
                total += g.getLegalMoves(new Position(r, c)).size();
            }

        }

        check("White has exactly 20 legal moves at the start", total == 20);

        check("Rook cannot jump over its own pawn", !play(g, "a1", "a4"));
        check("Cannot move Black's piece on White's turn", !play(g, "e7", "e5"));
        check("Pawn e2-e4 is allowed", play(g, "e2", "e4"));
        check("It is now Black's turn", g.getCurrentTurn() == PieceColor.BLACK);

        g.undo();
        check("Undo puts the pawn back on e2", g.getBoard().getPiece(p("e2")) instanceof Pawn);
        check("Undo gives the turn back to White", g.getCurrentTurn() == PieceColor.WHITE);

        Game f = new Game();
        play(f, "f2", "f3");
        play(f, "e7", "e5");
        play(f, "g2", "g4");
        check("Before the last move: not mate yet", f.getStatus() == GameStatus.PLAYING);
        play(f, "d8", "h4");
        check("Fool's mate is CHECKMATE", f.getStatus() == GameStatus.CHECKMATE);

        Game pin = new Game();
        pin.getBoard().clear();
        pin.getBoard().setPiece(p("e1"), new King(PieceColor.WHITE));
        pin.getBoard().setPiece(p("e2"), new Rook(PieceColor.WHITE));
        pin.getBoard().setPiece(p("e8"), new Rook(PieceColor.BLACK));
        pin.getBoard().setPiece(p("a8"), new King(PieceColor.BLACK));
        check("Pinned rook cannot leave the e-file", !play(pin, "e2", "d2"));
        check("Pinned rook CAN capture the attacker", play(pin, "e2", "e8"));

        Game s = new Game();
        s.getBoard().clear();
        s.getBoard().setPiece(p("a8"), new King(PieceColor.BLACK));
        s.getBoard().setPiece(p("b6"), new Queen(PieceColor.WHITE));
        s.getBoard().setPiece(p("h1"), new King(PieceColor.WHITE));
        play(s, "h1", "g1");                      // White makes any quiet move
        check("Stalemate is detected", s.getStatus() == GameStatus.STALEMATE);

        System.out.println(failures == 0 ? "\nAll tests passed." : "\n" + failures + " test(s) failed.");
    }
}
