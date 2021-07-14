package tactical.game.board;

import tactical.exception.BoardCreationException;
import tactical.models.BoardGame;
import tactical.models.Coordinate;
import tactical.models.SizeBoard;
import tactical.players.base.Player;

import java.util.List;

public class BoardProvider {

    public BoardGame createBoardGameBy(String name, SizeBoard sizeBoard, List<Player> players, List<Player> enemies) {

        validateCreationBoardGame(name, sizeBoard, players, enemies);
        Player[][] board = new Player[sizeBoard.getHeight()][sizeBoard.getLength()];
        for (Player player : players) {
            final Coordinate coordinate = player.getCoordinate();
            board[coordinate.getX()][coordinate.getY()] = player;
        }
        for (Player enemy : enemies) {
            final Coordinate coordinate = enemy.getCoordinate();
            board[coordinate.getX()][coordinate.getY()] = enemy;
        }

        return new BoardGame(name, board);
    }

    private void validateCreationBoardGame(String name, SizeBoard sizeBoard, List<Player> players, List<Player> enemies) {
        if (null == name || name.length() < 1) {
            throw new BoardCreationException("failed to create a bord, it has to have a name");
        }

        if (sizeBoard.getLength() <= 0 || sizeBoard.getHeight() <= 0) {
            throw new BoardCreationException("failed to create a bord with " + sizeBoard);
        }

        if (players.isEmpty()) {
            throw new BoardCreationException("failed to create a bord, it has to have any players");
        }

        if (enemies.isEmpty()) {
            throw new BoardCreationException("failed to create a bord, it has to have any enemies");
        }
    }
}
