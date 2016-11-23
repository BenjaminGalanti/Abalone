package abalone;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;

/**
 * Created by benja on 22/11/2016.
 */
public class Piece extends Group {
    public Piece(Player player) {
        _pos = new Translate(0, 0);
        _shape = new Circle();
        _shape.setFill(player.getColor());
        _shape.getTransforms().add(_pos);
        getChildren().add(_shape);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        _shape.setCenterX(width / 2);
        _shape.setCenterY(height / 2);
        _shape.setRadius(width / 2);
    }

    @Override
    public void relocate(double x, double y) {
        super.relocate(x, y);
        _pos.setX(x);
        _pos.setY(y);
    }

    private Circle _shape;
    private Translate _pos;
}
