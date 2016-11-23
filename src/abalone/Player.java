package abalone;

import javafx.scene.paint.Color;

/**
 * Created by benja on 22/11/2016.
 */
public enum Player {
    BLACK(Color.BLACK, "player black"),
    WHITE(Color.WHITE, "player white"),
    RED(Color.RED, "player red"),
    BLUE(Color.BLUE, "player blue"),
    YELLOW(Color.YELLOW, "player yellow"),
    GREEN(Color.GREEN, "player green");

    Player(Color color, String name) {
        _color = color;
        _name = name;
    }

    public Color getColor() {
        return _color;
    }

    @Override
    public String toString() {
        return _name;
    }

    private Color _color;
    private String _name;
}
