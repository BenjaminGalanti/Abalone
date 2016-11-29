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
        _back.setFill(Color.BLUEVIOLET);

        getChildren().addAll(_back);

        drawMap();

        buildCells();
        System.out.println(_board[0][0]);
        GameLogic.getInstance().setBoard(_board);
        GameLogic.getInstance().setNbPlayer(5);
        initScores();
        GameLogic.getInstance().initPlayerCells();
        _currentPlayer = GameLogic.getInstance().getNextPlayer(null);
        //TODO for debug
        movePieces(Direction.BOTTOM_RIGHT, _board[1][1], _board[1][2], _board[1][3]);
        movePieces(Direction.RIGHT, _board[2][2]);
        movePieces(Direction.RIGHT, _board[2][3]);
        movePieces(Direction.TOP_LEFT, _board[2][4], _board[2][5]);
        movePieces(Direction.RIGHT, _board[1][3]);
        movePieces(Direction.RIGHT, _board[0][3]);
        movePieces(Direction.BOTTOM_RIGHT, _board[0][4]);
        movePieces(Direction.BOTTOM_RIGHT, _board[1][5]);
        movePieces(Direction.BOTTOM_LEFT, _board[0][0]);
        movePieces(Direction.LEFT, _board[0][1]);
        movePieces(Direction.BOTTOM_LEFT, _board[0][0]);

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

    private void draw_hex_corner(double x, double y, double size) {
        Polygon polygon = new Polygon();
        int i = 0;
        while (i < 6) {
            double angle_deg = (double) (60 * i + 30);
            double angle_rad = Math.PI / 180 * angle_deg;
            polygon.getPoints().addAll(x + size * Math.cos(angle_rad), y + size * Math.sin(angle_rad));
            i++;
        }

        polygon.setFill(Color.BLUE);
        polygon.setStroke(Color.HOTPINK);
        getChildren().addAll(polygon);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        _back.setWidth(width);
        _back.setHeight(height);
    }

    public void resetGame() {
        int rowSize = 5;
        int inc = 1;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < rowSize; j++) {
                _board[i][j].setPlayer(null);
            }
            if (rowSize == 9) { inc = -1; }
            rowSize += inc;
        }
        GameLogic.getInstance().initPlayerCells();
        System.out.println(_board[0][0]);
        _currentPlayer = GameLogic.getInstance().getNextPlayer(null);
        initScores();
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
                cell.num = String.format("%02d;%02d", i, j); //TODO just debug

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

    private void movePieces(Direction direction, Cell mandatoryCell, Cell... otherCells) {
        if (GameLogic.getInstance().canBeMoved(direction, mandatoryCell, otherCells)) {
            if (GameLogic.getInstance().isPieceOut(mandatoryCell, direction)) {
                GameLogic.getInstance().addScore(mandatoryCell.getPlayer());
                //TODO for debug
                System.out.println(mandatoryCell.getPlayer() + " : " + _scores[mandatoryCell.getPlayer().ordinal()]);
                if (GameLogic.getInstance().isWinner(mandatoryCell.getPlayer())) {
                    //TODO for debug
                    System.out.println(mandatoryCell.getPlayer() + " win the game !");
                    resetGame();
                    return;
                }
            }
            pushPiece(mandatoryCell, direction);
            for (Cell otherCell: otherCells) {
                pushPiece(otherCell, direction);
            }
        }
    }

    private void pushPiece(Cell cell, Direction direction) {
        if (cell.getNeighbour(direction) != null) {
            if (cell.getNeighbour(direction).getPlayer() != null) {
                pushPiece(cell.getNeighbour(direction), direction);
            }
            cell.getNeighbour(direction).setPlayer(cell.getPlayer());
            cell.setPlayer(null);
        }
    }

    private void initScores() {
        if (_scores == null) {
            _scores = new int[6];
            GameLogic.getInstance().setScores(_scores);
        }
        for (int i = 0; i < 6; i++) {
            _scores[i] = 0;
        }
    }

    // Debug function
    public void showGrid() {
        int rowSize = 5;
        int inc = 1;

        System.out.println("\n\n\n\n\n\n\n\n\n");
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9 - rowSize; k++) {
                System.out.print("   ");
            }
            for (int j = 0; j < rowSize; j++) {
                if (_board[i][j].getPlayer() != null) System.out.print(_board[i][j].getPlayer().getAnsi());
                System.out.print(_board[i][j].num + " ");
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
    private int[] _scores;
}
