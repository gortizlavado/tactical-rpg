package tactical.game.context;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tactical.game.board.BoardProvider;
import tactical.game.board.model.BoardGame;
import tactical.game.board.model.SizeBoard;
import tactical.game.enemy.EnemiesProvider;
import tactical.models.Coordinate;
import tactical.players.base.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
public class GameContext {

    private int turnNumber;
    private BoardGame board;
    private List<Player> players;
    private List<Player> enemies;
    private boolean endGame;

    public GameContext(List<Player> players) {
        this.players = players;
        this.turnNumber = 0;
    }

    public void initiateGameContext(String name, SizeBoard sizeBoard, int enemiesNumbers, int enemiesLevel) {
        EnemiesProvider enemiesProvider = new EnemiesProvider();
        BoardProvider boardProvider = new BoardProvider();
        this.enemies = enemiesProvider.createEnemiesStatsBy(enemiesNumbers, enemiesLevel);
        //TODO better way to do this
        AtomicInteger i = new AtomicInteger();
        enemies.forEach(enemy -> enemy.setCoordinate(
                new Coordinate(sizeBoard.getHeight() - 1 - i.getAndIncrement(), sizeBoard.getLength() - 1)));
        this.board = boardProvider.createBoardGameBy(name, sizeBoard, this.players, this.enemies);

        this.players.forEach(Player::endTurn);
        this.enemies.forEach(Player::endTurn);
        this.endGame = false;
    }
}
