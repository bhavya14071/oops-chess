import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BoardPanel extends JPanel {
    
    private static final int SQUARE = 80;                       
    private static final int OFFSET_X = 20;
    private static final int OFFSET_Y = 90;
    private static final Color LIGHT = new Color(240, 217, 181);
    private static final Color DARK = new Color(181, 136, 99);
    private static final Color SELECTED = new Color(246, 246, 105);
    private static final Color TARGET = new Color(90, 160, 90, 170);

    private final Game game;
    private final Runnable onChange;          
    private Position selected = null;
    private List<Position> targets = new ArrayList<>();
    
    private BufferedImage backgroundImage;
    private Map<String, BufferedImage> pieceImages = new HashMap<>();

    public BoardPanel(Game game, Runnable onChange) {
        
        this.game = game;
        this.onChange = onChange;
        
        try {
            backgroundImage = ImageIO.read(new File("assets/board1.png"));
        } catch (Exception e) {
            // ignore if background image doesn't exist
        }

        String[] pieces = {"Pawn", "Rook", "Knight", "Bishop", "Queen", "King"};
        for (String p : pieces) {
            try { pieceImages.put("WHITE_" + p, ImageIO.read(new File("assets/W_" + p + ".png"))); } catch (Exception e) {}
            try { pieceImages.put("BLACK_" + p, ImageIO.read(new File("assets/B_" + p + ".png"))); } catch (Exception e) {}
        }
        
        setPreferredSize(new Dimension(8 * SQUARE + 2 * OFFSET_X, 8 * SQUARE + OFFSET_Y + 40));

        addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
            
                int clickCol = (e.getX() - OFFSET_X) / SQUARE;
                int clickRow = (e.getY() - OFFSET_Y) / SQUARE;
                handleClick(clickRow, clickCol);
            
            }
        });
    }

    private void handleClick(int row, int col) {
        
        if (!game.getBoard().isInside(row, col)) {
        
            return;
        
        }
        
        GameStatus status = game.getStatus();
        
        if (status == GameStatus.CHECKMATE || status == GameStatus.STALEMATE) {
        
            return;                                   // game is over
        
        }

        Position clicked = new Position(row, col);
        
        if (selected != null && targets.contains(clicked)) {
        
            game.makeMove(selected, clicked);         // second click = move
            clearSelection();
        
        } else {

            Piece piece = game.getBoard().getPiece(clicked);
            
            if (piece != null && piece.getColor() == game.getCurrentTurn()) {
            
                selected = clicked;                   // first click = select
                targets = game.getLegalMoves(clicked);
            
            } else {

                clearSelection();
            
            }
        }

        repaint();
        onChange.run();
    }

    public void clearSelection() {
        
        selected = null;
        targets = new ArrayList<>();
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, OFFSET_X, OFFSET_Y, 8 * SQUARE, 655, null);
        }

        for (int row = 0; row < 8; row++) {

            for (int col = 0; col < 8; col++) {
            
                drawSquare(g2, row, col);
                drawPiece(g2, row, col);
                drawTargetDot(g2, row, col);
            
            }
        }
    }

    private void drawSquare(Graphics2D g2, int row, int col) {
        
        if (backgroundImage != null) {
            if (selected != null && selected.getRow() == row && selected.getCol() == col) {
                g2.setColor(new Color(SELECTED.getRed(), SELECTED.getGreen(), SELECTED.getBlue(), 150));
                g2.fillRect(OFFSET_X + col * SQUARE, OFFSET_Y + row * SQUARE, SQUARE, SQUARE);
            }
            return;
        }

        boolean light = (row + col) % 2 == 0;
        
        Color color = light ? LIGHT : DARK;
        
        if (selected != null && selected.getRow() == row && selected.getCol() == col) {
        
            color = SELECTED;
        
        }
        
        g2.setColor(color);
        g2.fillRect(OFFSET_X + col * SQUARE, OFFSET_Y + row * SQUARE, SQUARE, SQUARE);
    }

    private void drawPiece(Graphics2D g2, int row, int col) {
        
        Piece piece = game.getBoard().getPiece(row, col);
        
        if (piece == null) {
        
            return;
        
        }
        
        String key = piece.getColor().name() + "_" + piece.getName();
        BufferedImage img = pieceImages.get(key);
        
        if (img != null) {
            int pieceWidth = SQUARE;
            int pieceHeight = SQUARE * 2;
            int drawX = OFFSET_X + col * SQUARE;
            int drawY = OFFSET_Y + (row + 1) * SQUARE - pieceHeight - 15;
            g2.drawImage(img, drawX, drawY, pieceWidth, pieceHeight, null);
            return;
        }

        g2.setFont(new Font("SansSerif", Font.PLAIN, 60));
        FontMetrics fm = g2.getFontMetrics();
        String symbol = piece.getSymbol();
        int x = OFFSET_X + col * SQUARE + (SQUARE - fm.stringWidth(symbol)) / 2;
        int y = OFFSET_Y + row * SQUARE + (SQUARE + fm.getAscent() - fm.getDescent()) / 2;

        g2.setColor(Color.BLACK);
        g2.drawString(symbol, x - 1, y);
        g2.drawString(symbol, x + 1, y);
        g2.drawString(symbol, x, y - 1);
        g2.drawString(symbol, x, y + 1);

        g2.setColor(piece.getColor() == PieceColor.WHITE ? Color.WHITE : new Color(40, 40, 40));
        g2.drawString(symbol, x, y);
    }

    private void drawTargetDot(Graphics2D g2, int row, int col) {
        
        if (targets.contains(new Position(row, col))) {
        
            g2.setColor(TARGET);
            int d = SQUARE / 3;
            g2.fillOval(OFFSET_X + col * SQUARE + (SQUARE - d) / 2, OFFSET_Y + row * SQUARE + (SQUARE - d) / 2, d, d);
        
        }
    }
}
