package tactical.game.states.impl;

import tactical.equipment.base.BaseHandEquipment;
import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

import java.util.Objects;
import java.util.function.Predicate;

public class PlayerTurn implements GameState {

    public static final String TURN_PLAYER = "--- TURN PLAYER ---";

    @Override
    public String fetchTurn() {
        return TURN_PLAYER;
    }

    @Override
    public boolean apply(GameContext context) {
        System.out.println("Any Player can do an action?");
        final long numberOfPlayerReady = context.getPlayers()
                .stream()
                .filter(Predicate.not(Player::isFinishedTurn))
                .count();
        System.out.printf("There are a number of players ready: %s%n", numberOfPlayerReady);
        return numberOfPlayerReady > 0;
    }

    @Override
    public void execute(GameContext context, Player player, ActionEnum action, Coordinate coordinate, BaseHandEquipment handEquipment) {
        System.out.printf("Player do an action: %s%n", player.getName());

        switch (action) {
            case MOVE:
                System.out.println("Move action");
                final Coordinate oldCoordinate = player.getCoordinate();
                if (player.move(coordinate)) {
                    final Player[][] board = context.getBoard().getBoard();
                    board[oldCoordinate.getX()][oldCoordinate.getY()] = null;
                    board[coordinate.getX()][coordinate.getY()] = player;
                }
                break;
            case ATTACK:
                System.out.println("Attack action");
                final Player[][] board = context.getBoard().getBoard();
                final Player enemy = board[coordinate.getX()][coordinate.getY()];
                if (Objects.equals(null, enemy)) {
                    System.out.println("There is not an enemy... :(");
                } else {
                    System.out.println("Enemy health: " + enemy.getHealth());
                    final int attackPower = player.attack(handEquipment);
                    final int defensePower = enemy.defense();
                    final int healthToModify = defensePower - attackPower;
                    if (healthToModify <= 0) {
                        System.out.println("Damage: " + healthToModify);
                        enemy.modifyHealth(healthToModify);
                    }

                    final int enemyHealthAfter = enemy.getHealth();
                    if (enemyHealthAfter > 0) {
                        System.out.println("Enemy health: " + enemyHealthAfter);
                    } else {
                        System.out.println("Enemy killed!");
                        board[coordinate.getX()][coordinate.getY()] = null;
                        context.getEnemies().remove(enemy);
                    }
                }
                player.endTurn();
                break;
            case END:
                System.out.println("End action");
                player.endTurn();
                break;
            default:
                System.out.println("Unknown action");
                break;
        }
    }

    @Override
    public void next(TacticalGame tacticalGame) {
        System.out.println("End turn for this player");
        tacticalGame.setState(new EnemyTurn());
    }
}
