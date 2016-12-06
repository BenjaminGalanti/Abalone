package abalone;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Created by benja on 22/11/2016.
 */
public class CustomControl extends Control {
    public CustomControl() {
        super();
        setSkin(new CustomControlSkin(this));
        _abaloneBoard = new AbaloneBoard();
        getChildren().add(_abaloneBoard);

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                _abaloneBoard.selectCell(event.getX(), event.getY(), event.isControlDown());
            }
        });

        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    _abaloneBoard.resetGame();
                } else if (event.getCode() == KeyCode.T) {
                    _abaloneBoard.move(Direction.TOP_LEFT);
                } else if (event.getCode() == KeyCode.Y) {
                    _abaloneBoard.move(Direction.TOP_RIGHT);
                } else if (event.getCode() == KeyCode.H) {
                    _abaloneBoard.move(Direction.RIGHT);
                } else if (event.getCode() == KeyCode.B) {
                    _abaloneBoard.move(Direction.BOTTOM_RIGHT);
                } else if (event.getCode() == KeyCode.V) {
                    _abaloneBoard.move(Direction.BOTTOM_LEFT);
                } else if (event.getCode() == KeyCode.F) {
                    _abaloneBoard.move(Direction.LEFT);
                }
            }
        });
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        _abaloneBoard.resize(width, height);
    }

    AbaloneBoard _abaloneBoard;
}
