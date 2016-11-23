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
        _currentPlayer = Player.BLACK;
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
                cell.num = String.format("%02d%02d", i, j); //TODO just debug

                _board[i][j] = cell;
                cell.setNeighbour(Direction.LEFT, left);
                if (i != 0) {
                    if (inc == 1) {
                        if (j != 0) {
                            Cell topLeft = _board[i - 1][j - 1];
                            cell.setNeighbour(Direction.TOP_LEFT, topLeft);
                            topLeft.setNeighbour(Direction.BOTTOM_RIGHT, cell);
                        }
                        if (j != rowSize - 1) {
                            Cell topRight = _board[i - 1][j];
                            cell.setNeighbour(Direction.TOP_RIGHT, topRight);
                            topRight.setNeighbour(Direction.BOTTOM_LEFT, cell);
                        }
                    } else {
                        Cell topLeft = _board[i - 1][j];
                        cell.setNeighbour(Direction.TOP_LEFT, topLeft);
                        topLeft.setNeighbour(Direction.BOTTOM_RIGHT, cell);
                        Cell topRight = _board[i - 1][j + 1];
                        cell.setNeighbour(Direction.TOP_RIGHT, topRight);
                        topRight.setNeighbour(Direction.BOTTOM_LEFT, cell);
                    }
                }

                if (left != null) {
                    left.setNeighbour(Direction.RIGHT, cell);
                }
                left = _board[i][j];
            }
            if (rowSize == 9) { inc = -1; }
            rowSize += inc;
        }
        //TODO just debug
        rowSize = 5;
        inc = 1;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < rowSize; j++) {
                System.out.println(_board[i][j]);
            }
            System.out.println("");
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

    private Player _currentPlayer;
    private Rectangle _back;
    private Polygon _boardShape;
    private Cell[][] _board;
}
