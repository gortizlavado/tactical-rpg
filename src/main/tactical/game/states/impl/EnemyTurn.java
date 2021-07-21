package tactical.game.states.impl;

import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.players.base.Player;

import java.util.function.Predicate;

public class EnemyTurn implements GameState {

    public static final String TURN_ENEMY = "--- TURN ENEMY ---";

    @Override
    public String fetchTurn() {
        return TURN_ENEMY;
    }

    @Override
    public boolean apply(GameContext context) {
        System.out.println("Any Enemy can do an action?");
        final long numberOfEnemyReady = context.getEnemies()
                .stream()
                .filter(Predicate.not(Player::isFinishedTurn))
                .count();
        System.out.printf("There are a number of enemies ready: %s%n", numberOfEnemyReady);
        return numberOfEnemyReady > 0;
    }

    @Override
    public void execute(GameContext context, Player player) {
        System.out.println("Enemy do an action");
        player.endTurn();
    }

    @Override
    public void next(TacticalGame tacticalGame) {
        tacticalGame.setState(new EndGame());
    }
}
