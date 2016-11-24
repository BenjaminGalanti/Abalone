package abalone;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by benja on 22/11/2016.
 */
public class Cell extends Pane {
    public Cell() {
        _piece = null;
        _neighbours = new Cell[6];
        for (int i = 0; i < 6; i++) {
            _neighbours[i] = null;
        }

        _shape = new Circle();
        _shape.setFill(Color.GRAY);
        getChildren().add(_shape);
        _player = null;
    }

    public void resetCell() {
        getChildren().remove(_piece);
        _piece = null;
        _player = null;
    }

    public void setPlayer(Player player) {
        _player = player;
        _piece = new Piece(player);
        getChildren().add(_piece);
    }

    public Player getPlayer() {
        return _player;
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        _shape.setCenterX(30);
        _shape.setCenterY(30);
        _shape.setRadius(30);
        if (_piece != null) {
            _piece.resize(60, 60);
        }
    }

    @Override
    public void relocate(double x, double y) {
        super.relocate(x, y);
    }

    public void setNeighbour(Direction dir, Cell cell) {
        _neighbours[dir.ordinal()] = cell;
    }

    public Cell getNeighbour(Direction dir) {
        return _neighbours[dir.ordinal()];
    }

    @Override
    public String toString() {
        String dir[] = {
                getNeighbour(Direction.TOP_LEFT) != null ? getNeighbour(Direction.TOP_LEFT).num : "NULL",
                getNeighbour(Direction.TOP_RIGHT) != null ? getNeighbour(Direction.TOP_RIGHT).num : "NULL",
                getNeighbour(Direction.LEFT) != null ? getNeighbour(Direction.LEFT).num : "NULL",
                getNeighbour(Direction.RIGHT) != null ? getNeighbour(Direction.RIGHT).num : "NULL",
                getNeighbour(Direction.BOTTOM_LEFT) != null ? getNeighbour(Direction.BOTTOM_LEFT).num : "NULL",
                getNeighbour(Direction.BOTTOM_RIGHT) != null ? getNeighbour(Direction.BOTTOM_RIGHT).num : "NULL"
        };
        return "   " + dir[0] + "  " + dir[1] + "\n " + dir[2] + " " + num + " " + dir[3] + "\n  " + dir[4] + "   " + dir[5] + "\n";
    }

    public String num;

    private Cell[] _neighbours;
    private Piece _piece;
    private Circle _shape;
    private Player _player;
}
