package abalone;

/**
 * Created by benja on 22/11/2016.
 */
public class GameLogic {
    private GameLogic() {
        _nbPlayer = 2;
        _score = new int[_nbPlayer];
    }

    private static GameLogic _instance = new GameLogic();

    public static GameLogic getInstance() {
        return _instance;
    }

    public Player getNextPlayer(Player player) {
        if (player == Player.BLACK) {
            return Player.WHITE;
        } else if (player == Player.WHITE) {
            return _nbPlayer > 2 ? Player.RED : Player.BLACK;
        } else if (player == Player.RED) {
            return _nbPlayer > 3 ? Player.BLUE : Player.BLACK;
        } else if (player == Player.BLUE) {
            return _nbPlayer > 4 ? Player.YELLOW : Player.BLACK;
        } else if (player == Player.YELLOW) {
            return _nbPlayer > 5 ? Player.GREEN : Player.BLACK;
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
        } else if (_nbPlayer == 4 || _nbPlayer == 5) {
            fillLine(board[0][0], 4, player, Direction.RIGHT);
            fillLine(board[1][0], 4, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(board[4][8], 4, player, Direction.TOP_LEFT);
            fillLine(board[5][7], 4, player, Direction.TOP_LEFT);
            player = getNextPlayer(player);
            fillLine(board[7][5], 4, player, Direction.LEFT);
            fillLine(board[8][4], 4, player, Direction.LEFT);
            player = getNextPlayer(player);
            fillLine(board[3][0], 4, player, Direction.BOTTOM_RIGHT);
            fillLine(board[4][0], 4, player, Direction.BOTTOM_RIGHT);
            if (_nbPlayer == 5) {
                player = getNextPlayer(player);
                fillLine(board[3][3], 2, player, Direction.RIGHT);
                fillLine(board[4][3], 3, player, Direction.RIGHT);
                fillLine(board[5][3], 2, player, Direction.RIGHT);
            }
        } else if (_nbPlayer == 6) {
            fillLine(board[0][1], 3, player, Direction.RIGHT);
            fillLine(board[1][2], 2, player, Direction.RIGHT);
            fillLine(board[2][3], 1, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(board[1][5], 3, player, Direction.BOTTOM_RIGHT);
            fillLine(board[2][5], 2, player, Direction.BOTTOM_RIGHT);
            fillLine(board[3][5], 1, player, Direction.BOTTOM_RIGHT);
            player = getNextPlayer(player);
            fillLine(board[5][7], 3, player, Direction.BOTTOM_LEFT);
            fillLine(board[5][6], 2, player, Direction.BOTTOM_LEFT);
            fillLine(board[5][5], 1, player, Direction.BOTTOM_LEFT);
            player = getNextPlayer(player);
            fillLine(board[8][3], 3, player, Direction.LEFT);
            fillLine(board[7][3], 2, player, Direction.LEFT);
            fillLine(board[6][3], 1, player, Direction.LEFT);
            player = getNextPlayer(player);
            fillLine(board[7][0], 3, player, Direction.TOP_LEFT);
            fillLine(board[6][1], 2, player, Direction.TOP_LEFT);
            fillLine(board[5][2], 1, player, Direction.TOP_LEFT);
            player = getNextPlayer(player);
            fillLine(board[3][0], 3, player, Direction.TOP_RIGHT);
            fillLine(board[3][1], 2, player, Direction.TOP_RIGHT);
            fillLine(board[3][2], 1, player, Direction.TOP_RIGHT);
        }
    }

    public void pushPiece(Cell cell, Direction direction) {
        if (cell.getNeighbour(direction) != null) {
            if (cell.getNeighbour(direction).getPlayer() != null) {
                pushPiece(cell.getNeighbour(direction), direction);
            }
            cell.getNeighbour(direction).setPlayer(cell.getPlayer());
            cell.resetCell();
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
        _score = new int[_nbPlayer];
        for (int i = 0; i < _nbPlayer; i++) {
            _score[i] = 0;
        }
    }

    private void addScore(Player player) {
        _score[player.ordinal()]++;
    }

    private int _nbPlayer;
    private int[] _score;
}
