package tactical.game.states.impl;

import tactical.equipment.base.BaseHandEquipment;
import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

public class PlayerTurn implements GameState {

    public static final String TURN_PLAYER = "Turn Player";

    @Override
    public String fetchTurn() {
        return TURN_PLAYER;
    }

    @Override
    public boolean apply(GameContext context) {
        System.out.println("Any Player can do an action?");
        final long numberOfPlayerReady = context.getPlayers()
                .stream()
                .filter(player -> !player.isFinishedTurn())
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
                player.move(coordinate);
                break;
            case ATTACK:
                System.out.println("Attack action");
                final Player enemy = context.getBoard().getBoard()[coordinate.getX()][coordinate.getY()];
                if (null != enemy) {
                    System.out.println("Enemy health: " + enemy.getHealth());
                    final int attackPower = player.attack(handEquipment);
                    final int defensePower = enemy.defense();
                    final int healthToModify = defensePower - attackPower;
                    System.out.println("Damage: " + healthToModify);
                    enemy.modifyHealth(healthToModify);
                    System.out.println("Enemy health: " + enemy.getHealth());
                } else {
                    System.out.println("There is not an enemy... :(");
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
