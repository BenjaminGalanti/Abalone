package abalone;

import javafx.scene.paint.Color;

/**
 * Created by benja on 22/11/2016.
 */
public enum Player {
//    BLACK(Color.BLACK, "player black"),
//    WHITE(Color.WHITE, "player white"),
//    RED(Color.RED, "player red"),
//    BLUE(Color.BLUE, "player blue"),
//    YELLOW(Color.YELLOW, "player yellow"),
//    GREEN(Color.GREEN, "player green");
    //TODO Just for debug
    BLACK(Color.BLACK, "pl", "\u001B[35m"),
    WHITE(Color.WHITE, "ci", "\u001B[36m"),
    RED(Color.RED, "rd", "\u001B[31m"),
    BLUE(Color.BLUE, "bl", "\u001B[34m"),
    YELLOW(Color.YELLOW, "yw", "\u001B[33m"),
    GREEN(Color.GREEN, "gr", "\u001B[32m");

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
