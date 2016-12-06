package abalone;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

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

        draw_hexagon(710.0 / 14.0);
        _player = null;
    }

    public void draw_hexagon(double size) {
        _shape = new Polygon();
        _shape.setFill(Color.CYAN);
        _shape.setStroke(Color.HOTPINK);
        for (int i = 0; i < 6; i++) {
            double angle_deg = (double) (60 * i + 30);
            double angle_rad = Math.PI / 180 * angle_deg;
            _shape.getPoints().addAll(size * Math.cos(angle_rad), size * Math.sin(angle_rad));
        }

        getChildren().addAll(_shape);
    }

    public void setPlayer(Player player) {
        _player = player;
        if (_piece != null) {
            getChildren().remove(_piece);
        }
        if (player != null) {
            _piece = new Piece(player);
            getChildren().add(_piece);
        }
    }

    public Player getPlayer() {
        return _player;
    }

    public void resetCell() {
        setPlayer(null);
        setSelected(false);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        if (_piece != null) {
            _piece.resize(width, height);
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
                getNeighbour(Direction.TOP_LEFT) != null ? getNeighbour(Direction.TOP_LEFT).num : "XXXXX",
                getNeighbour(Direction.TOP_RIGHT) != null ? getNeighbour(Direction.TOP_RIGHT).num : "XXXXX",
                getNeighbour(Direction.LEFT) != null ? getNeighbour(Direction.LEFT).num : "XXXXX",
                getNeighbour(Direction.RIGHT) != null ? getNeighbour(Direction.RIGHT).num : "XXXXX",
                getNeighbour(Direction.BOTTOM_LEFT) != null ? getNeighbour(Direction.BOTTOM_LEFT).num : "XXXXX",
                getNeighbour(Direction.BOTTOM_RIGHT) != null ? getNeighbour(Direction.BOTTOM_RIGHT).num : "XXXXX"
        };
        return "   " + dir[0] + "  " + dir[1] + "\n " + dir[2] + " " + num + " " + dir[3] + "\n  " + dir[4] + "   " + dir[5] + "\n";
    }

    public Direction getDirection(Cell cell) {
        for (int i = 0; i < 6; i++) {
            if (_neighbours[i] == cell) {
                return Direction.values()[i];
            }
        }
        return null;
    }

    public void setSelected(boolean selected) {
        _selected = selected;
        if (_selected) {
            _shape.setFill(Color.BLANCHEDALMOND);
        } else {
            _shape.setFill(Color.CYAN);
        }
    }

    public boolean isSelected() {
        return _selected;
    }

    public String num;

    private Cell[] _neighbours;
    private Piece _piece;
    private Polygon _shape;
    private Player _player;
    private boolean _selected;
}
