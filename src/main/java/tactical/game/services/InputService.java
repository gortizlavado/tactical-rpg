package tactical.game.services;

import tactical.equipment.base.BaseHandEquipment;
import tactical.exception.ActionNotFoundException;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class InputService {

    private Scanner input;

    public InputService() {
        input = new Scanner(System.in);
    }

    public Player askForPlayer(List<Player> playerList) {
        System.out.println(printListOfThings(playerList));
        System.out.print("Choose one of player in this list: ");
        int playerChosen = input.nextInt();
        return playerList.get(playerChosen);
    }

    public ActionEnum askForAction(Player player) {
        ActionEnum[] actionEnums = ActionEnum.fetchAllValues();
        if (!player.isMoveTurn()) {
            actionEnums = ActionEnum.fetchAttackValues();
        }
        System.out.println(ActionEnum.printAction(actionEnums));
        System.out.print("Choose one of action: ");
        String actionString = input.next();
        try {
            return ActionEnum.findActionBy(actionString);
        } catch (ActionNotFoundException e) {
            System.out.println(e.getMessage() + ". Try again...");
            return askForAction(player);
        }
    }

    public Coordinate askForCoordinateToMove(Player[][] board) {
        Coordinate coordinate = askForCoordinate(board);

        if (Objects.nonNull(board[coordinate.getY()][coordinate.getX()])) {
            System.out.println("Impossible Coordinate ¬¬. Try with another...");
            askForCoordinateToMove(board);
        }

        return coordinate;
    }

    public Coordinate askForCoordinateToAttack(List<Coordinate> coordinates) {
        System.out.println(Coordinate.printCoordinate(coordinates.toArray(new Coordinate[0])));
        System.out.print("Choose one of coordinate in this list: ");
        int playerChosen = input.nextInt();
        return coordinates.get(playerChosen);
    }

    private Coordinate askForCoordinate(Player[][] board) {
        System.out.println("Choose coordinate...");
        System.out.print("x: ");
        int x = input.nextInt();
        System.out.print("y: ");
        int y = input.nextInt();

        if (x > board[0].length || y > board.length) {
            System.out.println("Impossible Coordinate ¬¬. Try with another...");
            askForCoordinate(board);
        }

        return new Coordinate(x, y);
    }

    public BaseHandEquipment askForHandEquipment(Player player) {
        if (player.hasNotHandEquipment()) {
            return null;
        }

        System.out.println(printListOfThings(Arrays.asList(player.getHandEquipment())));
        System.out.print("Choose one of hand equipment in this list: ");
        int handChosen = input.nextInt();

        BaseHandEquipment handEquipment = null;
        try {
            handEquipment = player.getHandEquipment()[handChosen];
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.out.println("Impossible Equipment ¬¬. Try with another...");
            askForHandEquipment(player);
        }
        return handEquipment;
    }

    private String printListOfThings(List<?> listOfThings) {
        StringBuilder result = new StringBuilder();
        int totalSize = listOfThings.size();
        int lastItem = totalSize - 1;
        for (int i = 0; i < totalSize; i++) {
            result.append("#").append(i).append(" -> ").append(listOfThings.get(i));
            if (i != lastItem) {
                result.append("\n");
            }
        }
        return result.toString();
    }

}
