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
        // Ask for size board
        SizeBoard sizeBoard = new SizeBoard(6, 5);
        System.out.printf("Select Size Board: height=%s length=%s%n", sizeBoard.getHeight(), sizeBoard.getLength());
        Player thanosh = new Thanosh(7);
        thanosh.setEquipment(new Jacket());
        thanosh.setEquipment(new Ring());
        thanosh.setEquipment(new SuperShort());
        thanosh.setEquipment(new Wood());
        thanosh.setCoordinate(new Coordinate(0, 0));
        // Ask for players and coordinate
        List<Player> playerList = List.of(thanosh);
        context = new GameContext(playerList);
        // Ask for numbers of enemies and level
        int enemiesNumbers = 3;
        int enemiesLevel = 1;
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
}
