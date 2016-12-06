package abalone;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

/**
 * Created by benja on 22/11/2016.
 */
public class Piece extends Group {
    public Piece(Player player) {
        _shape = new Circle();
        _shape.setFill(player.getColor());
        _shape.setCenterX(0);
        _shape.setCenterY(0);
        getChildren().add(_shape);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        _shape.setRadius(width / 2 - 2);
    }

    private Circle _shape;
}
