package tactical.game.services;

import tactical.equipment.base.BaseHandEquipment;
import tactical.exception.ActionNotFoundException;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputService {

    private Scanner input;

    public InputService() {
        input = new Scanner(System.in);
    }

    public Player askForPlayer(List<Player> playerList) {
        System.out.println(playerList);
        System.out.println("Choose one of player in this list: ");
        int playerChosen = input.nextInt();
        return playerList.get(playerChosen);
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

}
