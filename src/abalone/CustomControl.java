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
                _abaloneBoard.placePiece(event.getX(), event.getY());
            }
        });

        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    _abaloneBoard.resetGame();
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
