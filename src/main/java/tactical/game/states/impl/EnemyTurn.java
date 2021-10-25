package tactical.game.states.impl;

import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.services.InputService;
import tactical.game.states.GameState;
import tactical.game.states.PlayerState;
import tactical.players.base.Player;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EnemyTurn implements GameState, PlayerState {

    public static final String TURN_ENEMY = "--- TURN ENEMY ---";

    private final InputService inputService;

    public EnemyTurn() {
        this.inputService = new InputService();
    }

    @Override
    public String fetchTurn() {
        return TURN_ENEMY;
    }

    @Override
    public boolean apply(GameContext context) {
        System.out.println("Any Enemy can do an action?");
        final long numberOfEnemyReady = context.getCharactersMap().get(GameContext.ENEMY_KEY)
                .stream()
                .filter(Predicate.not(Player::isFinishedTurn))
                .count();
        System.out.printf("There are a number of enemies ready: %s%n", numberOfEnemyReady);
        return numberOfEnemyReady > 0;
    }

    @Override
    public void execute(GameContext context) {
        System.out.println("Enemy do an action");
        context.getPlayerChoose().endTurn();
    }

    @Override
    public void next(TacticalGame tacticalGame) {
        tacticalGame.setState(new EndGame());
    }

    @Override
    public void choosePlayer(GameContext context) {
        Player enemyChosen = inputService.askForPlayer(context.getCharactersMap().get(GameContext.ENEMY_KEY)
                .stream()
                .filter(Predicate.not(Player::isFinishedTurn))
                .collect(Collectors.toList()));
        context.setPlayerChoose(enemyChosen);
    }
}
