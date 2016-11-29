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

        draw_hex_corner(0, 0, 40);
        _player = null;
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

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
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
    private Polygon _shape;
    private Player _player;
}
