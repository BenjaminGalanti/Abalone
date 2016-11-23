package abalone;

/**
 * Created by benja on 22/11/2016.
 */
public class GameLogic {
    private GameLogic() {}

    private static GameLogic _instance = new GameLogic();

    public static GameLogic getInstance() {
        return _instance;
    }
}
