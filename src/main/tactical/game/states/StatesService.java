package tactical.game.states;

import tactical.equipment.base.BaseHandEquipment;
import tactical.exception.ActionNotFoundException;
import tactical.game.board.helper.BoardPrint;
import tactical.game.context.GameContext;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class StatesService {

    private Scanner input;

    public StatesService() {
        input = new Scanner(System.in);
    }

    public ActionEnum askForAction(Player player) {
        ActionEnum[] actionEnums = ActionEnum.fetchAllValues();
        if (!player.isMoveTurn()) {
            actionEnums = ActionEnum.fetchAttackValues();
        }
        System.out.println(ActionEnum.printAction(actionEnums));
        System.out.println("Choose one of action: ");
        String actionString = input.next();
        try {
            return ActionEnum.findActionBy(actionString);
        } catch (ActionNotFoundException e) {
            System.out.println(e.getMessage() + ". Try again...");
            return askForAction(player);
        }
    }

    public Coordinate askForCoordinate(Player[][] board) {
        System.out.println("Choose coordinate...");
        System.out.println("x: ");
        int x = input.nextInt();
        System.out.println("y: ");
        int y = input.nextInt();

        if (x > board[0].length || y > board.length) {
            System.out.println("Impossible Coordinate ¬¬. Try with another...");
            askForCoordinate(board);
        }

        return new Coordinate(x, y);
    }

    public BaseHandEquipment askForHandEquipment(Player player) {
        System.out.println("Choose hand equipment...");
        System.out.println(Arrays.toString(player.getHandEquipment()));
        int handChosen = input.nextInt();

        BaseHandEquipment handEquipment = null;
        try {
            handEquipment = player.getHandEquipment()[handChosen];
        } catch (ArrayIndexOutOfBoundsException iobe) {
            System.out.println("Impossible Equipment ¬¬. Try with another...");
            askForHandEquipment(player);
        }
        return handEquipment;
    }

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
            context.getEnemies().remove(enemy);
        }
    }

}
