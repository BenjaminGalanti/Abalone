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

    public void initPlayerCells() {
        Player player = getNextPlayer(null);

        if (_nbPlayer == 2) {
            fillLine(_board[0][0], -1, player, Direction.RIGHT);
            fillLine(_board[1][0], -1, player, Direction.RIGHT);
            fillLine(_board[2][2], 3, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(_board[6][2], 3, player, Direction.RIGHT);
            fillLine(_board[7][0], -1, player, Direction.RIGHT);
            fillLine(_board[8][0], -1, player, Direction.RIGHT);
        } else if (_nbPlayer == 3) {
            fillLine(_board[0][0], -1, player, Direction.RIGHT);
            fillLine(_board[1][0], -1, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(_board[3][7], -1, player, Direction.BOTTOM_LEFT);
            fillLine(_board[4][8], -1, player, Direction.BOTTOM_LEFT);
            player = getNextPlayer(player);
            fillLine(_board[3][0], -1, player, Direction.BOTTOM_RIGHT);
            fillLine(_board[4][0], -1, player, Direction.BOTTOM_RIGHT);
        } else if (_nbPlayer == 4 || _nbPlayer == 5) {
            fillLine(_board[0][0], 4, player, Direction.RIGHT);
            fillLine(_board[1][0], 4, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(_board[4][8], 4, player, Direction.TOP_LEFT);
            fillLine(_board[5][7], 4, player, Direction.TOP_LEFT);
            player = getNextPlayer(player);
            fillLine(_board[7][5], 4, player, Direction.LEFT);
            fillLine(_board[8][4], 4, player, Direction.LEFT);
            player = getNextPlayer(player);
            fillLine(_board[3][0], 4, player, Direction.BOTTOM_RIGHT);
            fillLine(_board[4][0], 4, player, Direction.BOTTOM_RIGHT);
            if (_nbPlayer == 5) {
                player = getNextPlayer(player);
                fillLine(_board[3][3], 2, player, Direction.RIGHT);
                fillLine(_board[4][3], 3, player, Direction.RIGHT);
                fillLine(_board[5][3], 2, player, Direction.RIGHT);
            }
        } else if (_nbPlayer == 6) {
            fillLine(_board[0][1], 3, player, Direction.RIGHT);
            fillLine(_board[1][2], 2, player, Direction.RIGHT);
            fillLine(_board[2][3], 1, player, Direction.RIGHT);
            player = getNextPlayer(player);
            fillLine(_board[1][5], 3, player, Direction.BOTTOM_RIGHT);
            fillLine(_board[2][5], 2, player, Direction.BOTTOM_RIGHT);
            fillLine(_board[3][5], 1, player, Direction.BOTTOM_RIGHT);
            player = getNextPlayer(player);
            fillLine(_board[5][7], 3, player, Direction.BOTTOM_LEFT);
            fillLine(_board[5][6], 2, player, Direction.BOTTOM_LEFT);
            fillLine(_board[5][5], 1, player, Direction.BOTTOM_LEFT);
            player = getNextPlayer(player);
            fillLine(_board[8][3], 3, player, Direction.LEFT);
            fillLine(_board[7][3], 2, player, Direction.LEFT);
            fillLine(_board[6][3], 1, player, Direction.LEFT);
            player = getNextPlayer(player);
            fillLine(_board[7][0], 3, player, Direction.TOP_LEFT);
            fillLine(_board[6][1], 2, player, Direction.TOP_LEFT);
            fillLine(_board[5][2], 1, player, Direction.TOP_LEFT);
            player = getNextPlayer(player);
            fillLine(_board[3][0], 3, player, Direction.TOP_RIGHT);
            fillLine(_board[3][1], 2, player, Direction.TOP_RIGHT);
            fillLine(_board[3][2], 1, player, Direction.TOP_RIGHT);
        }
    }

    private void fillLine(Cell cell, int length, Player player, Direction direction) {
        if (length == -1) {
            while (cell != null) {
                cell.setPlayer(player);
                cell = cell.getNeighbour(direction);
            }
        } else {
            for (int i = 0; i < length; i++) {
                cell.setPlayer(player);
                cell = cell.getNeighbour(direction);
            }
        }
    }

    public boolean canBeMoved(Direction direction, Cell cell) {
        int countPlayer = 0;
        int countOthers = 0;
        Player player = cell.getPlayer();

        while (cell != null && cell.getPlayer() == player) {
            countPlayer++;
            cell = cell.getNeighbour(direction);
        }
        if (countPlayer > 3 || cell == null) {
            return false;
        }
        if (cell.getPlayer() == null) {
            return true;
        }
        while (cell != null && cell.getPlayer() != null) {
            countOthers++;
            if (cell.getPlayer() == player || countOthers >= countPlayer) {
                return false;
            }
            cell = cell.getNeighbour(direction);
        }
        return true;
    }

    public boolean canBeMoved(Direction direction, Cell mandatoryCell, Cell... otherCells) {
        boolean canMove = canBeMoved(direction, mandatoryCell);

        for (Cell otherCell : otherCells) {
            canMove &= canBeMoved(direction, otherCell);
        }
        return canMove;
    }

    public boolean isPieceOut(Cell cell, Direction direction) {
        Player player = cell.getPlayer();

        while (cell.getPlayer() == player) {
            cell = cell.getNeighbour(direction);
        }
        while (cell != null && cell.getPlayer() != null) {
            cell = cell.getNeighbour(direction);
        }
        return cell == null;
    }

    public boolean isWinner(Player player) {
        return _scores[player.ordinal()] == 6;
    }

    public void setNbPlayer(int nbPlayer) {
        _nbPlayer = nbPlayer;
    }

    public void setScores(int[] scores) {
        _scores = scores;
    }

    public void setBoard(Cell[][] board) {
        _board = board;
    }

    public void addScore(Player player) {
        _scores[player.ordinal()]++;
    }

    private int _nbPlayer;
    private int[] _scores;
    private Cell[][] _board;
}
