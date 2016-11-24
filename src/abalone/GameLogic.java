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

    public Player getNextPlayer(Player player) {
        if (player == Player.BLACK) {
            return Player.WHITE;
        } else if (player == Player.WHITE) {
            return Player.RED;
        } else if (player == Player.RED) {
            return Player.BLUE;
        } else if (player == Player.BLUE) {
            return Player.YELLOW;
        } else if (player == Player.YELLOW) {
            return Player.GREEN;
        }  else if (player == Player.GREEN) {
            return Player.BLACK;
        }
        return Player.BLACK;
    }

    public void initPlayerCells(Cell[][] board) {
        Player player = getNextPlayer(null);

        if (_nbPlayer == 2) {
            fillLine(board[0][0], -1, player, Direction.RIGHT);
            fillLine(board[1][0], -1, player, Direction.RIGHT);
            fillLine(board[2][2], 3, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(board[6][2], 3, player, Direction.RIGHT);
            fillLine(board[7][0], -1, player, Direction.RIGHT);
            fillLine(board[8][0], -1, player, Direction.RIGHT);
        } else if (_nbPlayer == 3) {
            fillLine(board[0][0], -1, player, Direction.RIGHT);
            fillLine(board[1][0], -1, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(board[3][7], -1, player, Direction.BOTTOM_LEFT);
            fillLine(board[4][8], -1, player, Direction.BOTTOM_LEFT);
            player = getNextPlayer(player);
            fillLine(board[3][0], -1, player, Direction.BOTTOM_RIGHT);
            fillLine(board[4][0], -1, player, Direction.BOTTOM_RIGHT);
        } else if (_nbPlayer == 4) {
            fillLine(board[0][0], 4, player, Direction.RIGHT);
            fillLine(board[1][0], 4, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(board[4][8], 4, player, Direction.TOP_LEFT);
            fillLine(board[5][7], 4, player, Direction.TOP_LEFT);
            player = getNextPlayer(player);
            fillLine(board[3][0], 4, player, Direction.BOTTOM_RIGHT);
            fillLine(board[4][0], 4, player, Direction.BOTTOM_RIGHT);
            player = getNextPlayer(player);
            fillLine(board[7][5], 4, player, Direction.LEFT);
            fillLine(board[8][4], 4, player, Direction.LEFT);
        }
    }

    private void fillLine(Cell start, int length, Player player, Direction direction) {
        Cell tmp = start;
        if (length == -1) {
            while (tmp != null) {
                tmp.setPlayer(player);
                tmp = tmp.getNeighbour(direction);
            }
        } else {
            for (int i = 0; i < length; i++) {
                tmp.setPlayer(player);
                tmp = tmp.getNeighbour(direction);
            }
        }
    }

    public void setNbPlayer(int nbPlayer) {
        _nbPlayer = nbPlayer;
    }

    private int _nbPlayer;
}
