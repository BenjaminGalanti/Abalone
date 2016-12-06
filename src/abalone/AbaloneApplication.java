package abalone;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by benja on 22/11/2016.
 */
public class AbaloneApplication extends Application {
    @Override
    public void init() throws Exception {
        super.init();
        _mainLayout = new StackPane();
        _customControl = new CustomControl();
        _mainLayout.getChildren().add(_customControl);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(new Scene(_mainLayout, 1100, 700));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private StackPane _mainLayout;
    private CustomControl _customControl;
}
