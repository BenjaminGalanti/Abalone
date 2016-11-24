package abalone;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * Created by benja on 22/11/2016.
 */
public class AbaloneBoard extends Pane {
    public AbaloneBoard() {
        _back = new Rectangle();
        _back.setFill(Color.GRAY);
        _boardShape = new Polygon(
                225.0, 650.0,
                575.0, 650.0,
                800.0, 320.0,
                575.0, 0.0,
                225.0, 0.0,
                0.0, 320.0);
        _boardShape.setFill(Color.DARKRED);

        getChildren().addAll(_back, _boardShape);
        buildCells();
        GameLogic.getInstance().setNbPlayer(2);
        GameLogic.getInstance().initPlayerCells(_board);
        _currentPlayer = Player.BLACK;
        showGrid();
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        _back.setWidth(width);
        _back.setHeight(height);
    }

    public void resetGame() {
        _currentPlayer = Player.BLACK;
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
