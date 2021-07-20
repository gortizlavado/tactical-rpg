package tactical.game;

import lombok.Getter;
import lombok.Setter;
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
import tactical.players.Thanosh;
import tactical.players.base.Player;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
            if (state.apply(context)) {
                System.out.println(state.fetchTurn());
                Player player = null;
                if (state instanceof PlayerTurn) {
                    player = askForPlayer(context.getPlayers()
                            .stream()
                            .filter(Predicate.not(Player::isFinishedTurn))
                            .collect(Collectors.toList()));

                } else if (state instanceof EnemyTurn) {
                    player = askForPlayer(context.getEnemies()
                            .stream()
                            .filter(Predicate.not(Player::isFinishedTurn))
                            .collect(Collectors.toList()));
                }
                state.execute(context, player);
            }
            state.next(this);
        }
    }

    private Player askForPlayer(List<Player> playerList) {
        System.out.println(playerList);
        System.out.println("Choose one of player in this list: ");
        int playerChosen = input.nextInt();
        return playerList.get(playerChosen);
    }

}
