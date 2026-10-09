import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ChessFrame extends JFrame {

    private final Game game = new Game();
    private final JLabel statusLabel = new JLabel();
    private final BoardPanel boardPanel;

    public ChessFrame() {

        super("Java Chess");
        boardPanel = new BoardPanel(game, this::updateStatus);

        JButton newGameButton = new JButton("New Game");
        
        newGameButton.addActionListener(e -> {
            game.newGame();
            boardPanel.clearSelection();
            updateStatus();
        });

        JButton undoButton = new JButton("Undo");
        
        undoButton.addActionListener(e -> {
            game.undo();
            boardPanel.clearSelection();
            updateStatus();
        });

        JPanel bottom = new JPanel();
        
        bottom.add(statusLabel);
        bottom.add(newGameButton);
        bottom.add(undoButton);

        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        updateStatus();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);       
    }

    private void updateStatus() {
        
        String player = (game.getCurrentTurn() == PieceColor.WHITE) ? "White" : "Black";
        
        switch (game.getStatus()) {
            
            case CHECK:
                statusLabel.setText(player + " is in CHECK!");
                break;
            case CHECKMATE:
                String winner = (game.getCurrentTurn() == PieceColor.WHITE) ? "Black" : "White";
                statusLabel.setText("Checkmate! " + winner + " wins.");
                break;
            case STALEMATE:
                statusLabel.setText("Stalemate - draw.");
                break;
            default:
                statusLabel.setText(player + " to move");
        }

        boardPanel.repaint();
    
    }
}
