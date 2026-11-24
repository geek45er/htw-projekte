package chess.ui;

import chess.pieces.ChessBoard;
import chess.pieces.ChessField;
import chess.pieces.ChessPiece;
import chess.pieces.ChessSprite;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

// zeichnet das ganze brett selbst in paintComponent statt mit JLabels
public class DrawGamePanel extends GamePanel implements MouseMotionListener {
    private Color highlightColor = new Color(125, 125, 255);
    private Color darkColor = Color.LIGHT_GRAY;
    private Color lightColor = Color.WHITE;
    private ChessBoard board;
    private int fieldSize = 60;
    // figur über der die maus gerade ist, damit nicht bei jeder bewegung alles neu gemacht wird
    private ChessPiece hoveredPiece = null;

    public DrawGamePanel(int fieldSize) {
        super(fieldSize);
        this.fieldSize = fieldSize;
        this.board = getBoard(); // das board aus GamePanel nehmen, sonst gibt es zwei verschiedene
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(fieldSize * 8, fieldSize * 8));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBoard((Graphics2D) g);
        // figuren über das brett malen
        for (ChessPiece piece : board.getPieces()) {
            g.drawImage(piece.getCurrentIcon().getImage(),
                    piece.getBoardX() * fieldSize, piece.getBoardY() * fieldSize, null);
        }
    }

    // felder ausfüllen und danach die trennlinien drüber zeichnen
    public void paintBoard(Graphics2D g2d) {
        ChessField[][] fields = board.getFields();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ChessField field = fields[x][y];
                if (field.isHighlighted()) {
                    g2d.setColor(highlightColor);
                } else if ((x + y) % 2 == 0) {
                    g2d.setColor(lightColor);
                } else {
                    g2d.setColor(darkColor);
                }
                g2d.fillRect(x * fieldSize, y * fieldSize, fieldSize, fieldSize);
            }
        }
        g2d.setColor(Color.BLACK);
        for (int i = 0; i <= 8; i++) {
            g2d.drawLine(i * fieldSize, 0, i * fieldSize, 8 * fieldSize); // senkrecht
            g2d.drawLine(0, i * fieldSize, 8 * fieldSize, i * fieldSize); // waagerecht
        }
    }

    @Override
    public void updateGUI() {
        repaint();
    }

    // liegt der punkt (in pixeln) im feld der figur?
    private boolean overPiece(Point point, ChessPiece piece) {
        return point.x / fieldSize == piece.getBoardX() && point.y / fieldSize == piece.getBoardY();
    }

    private void highlightMovableFields(ChessPiece p) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (p.canMoveTo(x, y)) {
                    board.highlight(x, y);
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point point = e.getPoint();
        // immer noch über der gleichen figur -> nichts zu tun
        if (hoveredPiece != null && overPiece(point, hoveredPiece)) {
            return;
        }
        ChessPiece newPiece = null;
        for (ChessPiece piece : board.getPieces()) {
            if (overPiece(point, piece)) {
                newPiece = piece;
                break;
            }
        }
        // vorher keine figur und jetzt auch keine -> auch nichts neu zeichnen
        if (newPiece == null && hoveredPiece == null) {
            return;
        }
        // alte hervorhebung weg
        if (hoveredPiece != null) {
            hoveredPiece.setHighlighted(false);
        }
        board.unhighlightAll();
        // neue figur hervorheben
        if (newPiece != null) {
            newPiece.setHighlighted(true);
            highlightMovableFields(newPiece);
        }
        hoveredPiece = newPiece;
        updateGUI();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
