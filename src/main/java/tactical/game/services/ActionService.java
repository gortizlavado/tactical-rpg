package tactical.game.services;

import tactical.equipment.base.BaseHandEquipment;
import tactical.game.board.helper.BoardPrint;
import tactical.game.context.GameContext;
import tactical.models.Coordinate;
import tactical.players.base.Player;

import java.util.Objects;

public class ActionService {

    public void doMoveAction(GameContext context, Player player, Coordinate coordinate) {
        final Coordinate oldCoordinate = player.getCoordinate();
        if (player.move(coordinate)) {
            final Player[][] board = context.getBoard().getBoard();
            board[oldCoordinate.getX()][oldCoordinate.getY()] = null;
            board[coordinate.getX()][coordinate.getY()] = player;
            BoardPrint.print(board);
        }
    }

    public void doAttackAction(GameContext context, Player player, Coordinate coordinate, BaseHandEquipment handEquipment) {
        final Player[][] board = context.getBoard().getBoard();
        final Player enemy = board[coordinate.getX()][coordinate.getY()];
        player.endTurn();
        if (Objects.equals(null, enemy)) {
            System.out.println("There is not an enemy... :(");
            return;
        }

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
            context.getCharactersMap().get(GameContext.ENEMY_KEY).remove(enemy);
        }
    }
}
