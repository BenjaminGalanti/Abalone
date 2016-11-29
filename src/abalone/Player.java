package abalone;

import javafx.scene.paint.Color;

/**
 * Created by benja on 22/11/2016.
 */
public enum Player {
    BLACK(Color.BLACK, "player black", "\u001B[35m"),
    WHITE(Color.WHITE, "player white", "\u001B[36m"),
    RED(Color.RED, "player red", "\u001B[31m"),
    BLUE(Color.BLUE, "player blue", "\u001B[34m"),
    YELLOW(Color.YELLOW, "player yellow", "\u001B[33m"),
    GREEN(Color.GREEN, "player green", "\u001B[32m");

    Player(Color color, String name, String ansi) {
        _color = color;
        _name = name;
        _ansi = ansi;
    }

    public Color getColor() {
        return _color;
    }

    public String getAnsi() {
        return _ansi;
    }

    @Override
    public String toString() {
        return _name;
    }

    private Color _color;
    private String _name;
    private String _ansi;
}
