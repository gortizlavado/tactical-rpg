package tactical.game;

import lombok.Getter;
import lombok.Setter;
import tactical.equipment.body.armor.Jacket;
import tactical.equipment.body.jewel.Ring;
import tactical.equipment.hand.shield.Wood;
import tactical.equipment.hand.weapon.sword.SuperShort;
import tactical.game.board.helper.BoardPrint;
import tactical.game.board.model.SizeBoard;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.game.states.PlayerState;
import tactical.game.states.impl.NewTurn;
import tactical.models.Coordinate;
import tactical.players.PlayerTwo;
import tactical.players.Thanosh;
import tactical.players.base.Player;

import java.util.List;
import java.util.Scanner;

@Setter
public class TacticalGame {

    @Getter
    private GameContext context;
    private GameState state;
    final private Scanner input;

    public TacticalGame() {
        this.input = new Scanner(System.in);
    }

    public void init(String name) {
        SizeBoard sizeBoard = this.askForBoardSize();
        System.out.print("Select Size Board: " + sizeBoard);
        Player thanosh = new Thanosh(7);
        thanosh.setEquipment(new Jacket());
        thanosh.setEquipment(new Ring());
        thanosh.setEquipment(new SuperShort());
        thanosh.setEquipment(new Wood());
        thanosh.setCoordinate(new Coordinate(0, 0));
        Player playerTwo = new PlayerTwo();
        playerTwo.setCoordinate(new Coordinate(0, 1));
        // Ask for players and coordinate
        List<Player> playerList = List.of(thanosh, playerTwo);
        context = new GameContext(playerList);

        int enemiesNumbers = askForEnemiesNumber();
        int enemiesLevel = askForEnemiesLevel();
        context.initiateGameContext(name, sizeBoard, enemiesNumbers, enemiesLevel);
        BoardPrint.print(context.getBoard().getBoard());
    }

    public void play() {
        state = new NewTurn();
        while(!context.isEndGame()) {
            if (state.apply(context)) {
                System.out.println(state.fetchTurn());
                if (state instanceof PlayerState) {
                    ((PlayerState) state).choosePlayer(context);
                }
                state.execute(context);
            }
            state.next(this);
        }
    }

    private SizeBoard askForBoardSize() {
        System.out.println("Choose size of board...");
        System.out.print("length(x): ");
        int x = input.nextInt();
        if (x < 1) {
            System.out.println("Board Size MUST be higher than zero");
            askForBoardSize();
        }
        System.out.print("height(y): ");
        int y = input.nextInt();
        if (y < 1) {
            System.out.println("Board Size MUST be higher than zero");
            askForBoardSize();
        }

        return new SizeBoard(x, y);
    }

    private int askForEnemiesNumber() {
        System.out.println("Choose number of enemies...");
        System.out.print("enemies: ");
        int enemies = input.nextInt();

        if (enemies < 1 ) {
            System.out.println("There will be at least one enemy");
            askForEnemiesNumber();
        }

        return enemies;
    }

    private int askForEnemiesLevel() {
        System.out.println("Choose level of enemies...");
        System.out.print("level: ");
        int enemiesLevel = input.nextInt();

        if (enemiesLevel < 1 ) {
            System.out.println("Enemies should be at least level one");
            askForEnemiesLevel();
        }

        return enemiesLevel;
    }
}
