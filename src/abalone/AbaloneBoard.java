package abalone;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by benja on 22/11/2016.
 */
public class AbaloneBoard extends Pane {
    public AbaloneBoard() {
        _back = new Rectangle();
        _back.setFill(Color.BLUEVIOLET);

        getChildren().addAll(_back);

        _cellHeight = 710.0 / 7.0;
        _cellWidth = Math.sqrt((Math.pow(_cellHeight / 2, 2) - Math.pow(_cellHeight / 4, 2))) * 2;
        buildCells();

        GameLogic.getInstance().setBoard(_board);
        GameLogic.getInstance().setNbPlayer(2);
        GameLogic.getInstance().initPlayerCells();

        initScores();
        _selectedCells = new Cell[3];
        resetSelectedCells();

        _currentPlayer = GameLogic.getInstance().getNextPlayer(null);
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
                _board[i][j].resetCell();
            }
            if (rowSize == 9) { inc = -1; }
            rowSize += inc;
        }
        GameLogic.getInstance().initPlayerCells();
        _currentPlayer = GameLogic.getInstance().getNextPlayer(null);
        initScores();
        resetSelectedCells();
    }

    public void selectCell(final double x, final double y, boolean ctrl) {
        int rowSize = 5;
        int inc = 1;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < rowSize; j++) {
                Cell cell = _board[i][j];
                Point2D point = cell.parentToLocal(x, y);
                if (Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2)) < _cellWidth / 2 && cell.getPlayer() == _currentPlayer) {
                    if (!ctrl) {
                        resetSelectedCells();
                    }
                    if (cell != _selectedCells[0] && cell != _selectedCells[1] && cell != _selectedCells[2]) {
                        if (_selectedCells[0] == null) {
                            cell.setSelected(true);
                            _selectedCells[0] = cell;
                        } else if (_selectedCells[1] == null) {
                            _selectedCellsDirection = _selectedCells[0].getDirection(cell);
                            if (_selectedCellsDirection != null) {
                                cell.setSelected(true);
                                _selectedCells[1] = cell;
                            }
                        } else if (_selectedCells[2] == null) {
                            if (_selectedCells[0].getNeighbour(Direction.values()[(_selectedCellsDirection.ordinal() + 3) % 6]) == cell
                                    || _selectedCells[1].getNeighbour(_selectedCellsDirection) == cell) {
                                cell.setSelected(true);
                                _selectedCells[2] = cell;
                            }
                        }
                    }
                    return;
                }
            }
            if (rowSize == 9) { inc = -1; }
            rowSize += inc;
        }
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

                cell.relocate(_cellWidth / 2 + (9 - rowSize) * _cellWidth / 2 + j * _cellWidth, _cellHeight / 2 + (3 * _cellHeight / 4) * i);
                cell.resize(_cellWidth, _cellHeight);
                getChildren().add(cell);
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
        Player playerPlaying = mandatoryCell.getPlayer();

        if (GameLogic.getInstance().canBeMoved(direction, mandatoryCell, otherCells)) {
            if (GameLogic.getInstance().isPieceOut(mandatoryCell, direction)) {
                GameLogic.getInstance().addScore(playerPlaying);
                //TODO for debug
                System.out.println(playerPlaying + " : " + _scores[playerPlaying.ordinal()]);
            }
            pushPiece(mandatoryCell, direction);
            for (Cell otherCell: otherCells) {
                pushPiece(otherCell, direction);
            }
            if (GameLogic.getInstance().isWinner(playerPlaying)) {
                //TODO for debug
                System.out.println(playerPlaying + " win the game !");
                return;
            }
            _currentPlayer = GameLogic.getInstance().getNextPlayer(_currentPlayer);
        }
    }

    public void move(Direction direction) {
        if (_selectedCells[0] != null) {
            if (_selectedCells[1] == null) {
                movePieces(direction, _selectedCells[0]);
            } else if (_selectedCells[2] == null) {
                movePieces(direction, _selectedCells[0], _selectedCells[1]);
            } else {
                movePieces(direction, _selectedCells[0], _selectedCells[1], _selectedCells[2]);
            }
            resetSelectedCells();
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

    private void resetSelectedCells() {
        for (int i = 0; i < 3; i++) {
            if (_selectedCells[i] != null) _selectedCells[i].setSelected(false);
            _selectedCells[i] = null;
        }
        _selectedCellsDirection = null;
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
    private Cell[][] _board;
    private int[] _scores;
    private double _cellHeight;
    private double _cellWidth;
    private Cell[] _selectedCells;
    private Direction _selectedCellsDirection;
}
