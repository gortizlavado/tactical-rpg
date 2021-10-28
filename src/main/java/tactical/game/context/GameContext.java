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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@ToString
public class GameContext {

    public final static String PLAYER_KEY = "players";
    public final static String ENEMY_KEY = "enemies";

    private int turnNumber;
    private BoardGame board;
    private Map<String, List<Player>> charactersMap;
    private boolean endGame;
    private Player playerChoose;

    public GameContext(List<Player> players) {
        charactersMap = new HashMap<>();
        this.charactersMap.put(PLAYER_KEY, players);
        this.turnNumber = 0;
    }

    public void initiateGameContext(String name, SizeBoard sizeBoard, int enemiesNumbers, int enemiesLevel) {
        EnemiesProvider enemiesProvider = new EnemiesProvider();
        BoardProvider boardProvider = new BoardProvider();
        this.charactersMap.put(ENEMY_KEY, enemiesProvider.createEnemiesStatsBy(enemiesNumbers, enemiesLevel));
        //TODO better way to do this
        AtomicInteger i = new AtomicInteger();
        this.charactersMap.get(ENEMY_KEY).forEach(enemy -> enemy.setCoordinate(
                new Coordinate(sizeBoard.getLength() - 1 - i.getAndIncrement(), sizeBoard.getHeight() - 1)));
        this.board = boardProvider.createBoardGameBy(name, sizeBoard, this.charactersMap);

        this.charactersMap.get(PLAYER_KEY).forEach(Player::endTurn);
        this.charactersMap.get(ENEMY_KEY).forEach(Player::endTurn);
        this.endGame = false;
    }
}
