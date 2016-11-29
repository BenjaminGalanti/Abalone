package abalone;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import javax.swing.text.Position;

/**
 * Created by benja on 22/11/2016.
 */
public class AbaloneBoard extends Pane {
    public AbaloneBoard() {
        _back = new Rectangle();
        _back.setFill(Color.BLUEVIOLET);

        getChildren().addAll(_back);

        drawMap();


        buildCells();
        GameLogic.getInstance().setNbPlayer(2);
        GameLogic.getInstance().initPlayerCells(_board);
        _currentPlayer = GameLogic.getInstance().getNextPlayer(null);
        showGrid();
    }

    private void drawMap() {
        int offset = 3;
        double size = 40;
        double width = Math.sqrt((size * size - (size / 2.0) * (size / 2.0))) * 2;
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 8 - offset; ++j) {
                draw_hex_corner(100 + offset * width / 2 + j * width, size + (3 * size / 2) * i, size);
            }
            if (i < 4)
                --offset;
            else
                ++offset;
        }
    }

    public void draw_hex_corner(double x, double y, double size) {
        double[] tab = new double[12];
        int i = 0;
        while (i < 12) {
            double angle_deg = (double) (30 * i + 30);
            double angle_rad = Math.PI / 180 * angle_deg;
            tab[i] = x + size * Math.cos(angle_rad);
            tab[i + 1] = y + size * Math.sin(angle_rad);
            i += 2;
        }

        Polygon polygon = new Polygon(tab);
        polygon.setFill(Color.BLUE);
        polygon.setStroke(Color.HOTPINK);
        getChildren().addAll(polygon);
    }

    private void print(double tab[]) {
        System.out.println("print");
        for (int i = 0; i < 12; ++i) {
            System.out.println(tab[i]);
        }
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        _back.setWidth(width);
        _back.setHeight(height);
    }

    public void resetGame() {
        _currentPlayer = GameLogic.getInstance().getNextPlayer(null);
        }

    public void placePiece(final double x, final double y) {
        Cell cell = new Cell();
        cell.relocate(x, y);
        cell.setPlayer(_currentPlayer);
        getChildren().add(cell);
    }

    private void buildCells() {
        _board = new Cell[9][];
        int rowSize = 5;
        int inc = 1;

        for (int i = 0; i < 9; i++) {
            Cell left = null;
            _board[i] = new Cell[rowSize];
            for (int j = 0; j < rowSize; j++) {
                Cell cell = new Cell();
                Cell topLeft = null;
                Cell topRight = null;
                cell.num = String.format("%02d%02d", i, j); //TODO just debug

                _board[i][j] = cell;
                if (inc == 1) {
                    if (i != 0) {
                        if (j != 0) topLeft = _board[i - 1][j - 1];
                        if (j != rowSize - 1) topRight = _board[i - 1][j];
                    }
                } else {
                    topLeft = _board[i - 1][j];
                    topRight = _board[i - 1][j + 1];
                }

                cell.setNeighbour(Direction.LEFT, left);
                if (left != null) left.setNeighbour(Direction.RIGHT, cell);
                cell.setNeighbour(Direction.TOP_LEFT, topLeft);
                if (topLeft != null) topLeft.setNeighbour(Direction.BOTTOM_RIGHT, cell);
                cell.setNeighbour(Direction.TOP_RIGHT, topRight);
                if (topRight != null) topRight.setNeighbour(Direction.BOTTOM_LEFT, cell);

                left = cell;
            }
            if (rowSize == 9) { inc = -1; }
            rowSize += inc;
        }
    }

    public void changePlayer(Player player) {
        _currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return _currentPlayer;
    }

    // Debug function
    public void showGrid() {
        int rowSize = 5;
        int inc = 1;

        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9 - rowSize; k++) {
                System.out.print("    ");
            }
            for (int j = 0; j < rowSize; j++) {
                if (_board[i][j].getPlayer() != null) System.out.print(_board[i][j].getPlayer().getAnsi());
                System.out.print(_board[i][j].num + "(" + (_board[i][j].getPlayer() != null ? _board[i][j].getPlayer() : "nn") + ") ");
                if (_board[i][j].getPlayer() != null) System.out.print("\u001B[0m");
            }
            System.out.println("");
            if (rowSize == 9) { inc = -1; }
            rowSize += inc;
        }
    }

    private Player _currentPlayer;
    private Rectangle _back;
    private Polygon _boardShape;
    private Cell[][] _board;
}
