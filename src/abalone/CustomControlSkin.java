package abalone;

import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;

/**
 * Created by benja on 22/11/2016.
 */
public class CustomControlSkin extends SkinBase<CustomControl> implements Skin<CustomControl> {
    public CustomControlSkin(CustomControl control) {
        super(control);
    }
}
