package tactical.game;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tactical.equipment.base.BaseHandEquipment;
import tactical.equipment.body.armor.Jacket;
import tactical.equipment.body.jewel.Ring;
import tactical.equipment.hand.shield.Wood;
import tactical.equipment.hand.weapon.sword.Short;
import tactical.game.board.model.SizeBoard;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.game.states.impl.EnemyTurn;
import tactical.game.states.impl.NewTurn;
import tactical.game.states.impl.PlayerTurn;
import tactical.models.Coordinate;
import tactical.players.Thanosh;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Setter
@NoArgsConstructor
public class TacticalGame {

    private GameState state;
    @Getter
    private GameContext context;

    public void init(String name) {
        Player thanosh = new Thanosh();
        thanosh.setEquipment(new Jacket());
        thanosh.setEquipment(new Ring());
        thanosh.setEquipment(new Short());
        thanosh.setEquipment(new Wood());
        // Ask for players
        List<Player> playerList = List.of(thanosh);
        context = new GameContext(playerList);
        // Ask for size board
        SizeBoard sizeBoard = new SizeBoard(30, 30);
        // Ask for numbers of enemies and level
        int enemiesNumbers = 10;
        int enemiesLevel = 5;
        context.initiateGameContext(name, sizeBoard, enemiesNumbers, enemiesLevel);
    }

    public void play() {
        state = new NewTurn();
        while(!context.isEndGame()) {
            Player player = null;
            ActionEnum action = null;
            Coordinate coordinate = null;
            BaseHandEquipment handEquipment = null;
            if (state.apply(context)) {
                System.out.println(state.fetchTurn());
                if (state instanceof PlayerTurn) {
                    final List<Player> playerList = context.getPlayers();
                    System.out.println(playerList);
                    System.out.println("Choose one of player in this list: ");
                    Scanner input = new Scanner(System.in);
                    int playerChosen = input.nextInt();
                    player = playerList.get(playerChosen);
                    System.out.println(Arrays.toString(ActionEnum.values()));
                    System.out.println("Choose one of action: ");
                    String actionString = input.next();
                    action = ActionEnum.valueOf(actionString);
                    System.out.println("Choose coordinate...");
                    System.out.println("x: ");
                    int x = input.nextInt();
                    System.out.println("y: ");
                    int y = input.nextInt();
                    coordinate = new Coordinate(x, y);
                    if (ActionEnum.ATTACK == action) {
                        System.out.println("Choose hand equipment...");
                        System.out.println(Arrays.toString(player.getHandEquipment()));
                        int handChosen = input.nextInt();
                        handEquipment = player.getHandEquipment()[handChosen];
                    }
                }
                if (state instanceof EnemyTurn) {
                    System.out.println("Ask enemy");
                }
                state.execute(context, player, action, coordinate, handEquipment);
            }
            if (null == player || player.isFinishedTurn()) {
                state.next(this);
            }
        }
    }

}
