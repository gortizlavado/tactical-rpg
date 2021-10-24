package tactical.game.states.impl;

import tactical.equipment.base.BaseHandEquipment;
import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.services.ActionService;
import tactical.game.services.InputService;
import tactical.game.states.GameState;
import tactical.game.states.PlayerState;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerTurn implements GameState, PlayerState {

    public static final String TURN_PLAYER = "--- TURN PLAYER ---";

    private InputService inputService;
    private final ActionService actionService;

    public PlayerTurn() {
        this.inputService = new InputService();
        this.actionService = new ActionService();
    }

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
    public void execute(GameContext context) {
        Player player = context.getPlayerChoose();
        System.out.printf("Player choose for doing an action: %s%n", player.getName());
        ActionEnum action = inputService.askForAction(player);
        Coordinate coordinate;
        switch (action) {
            case MOVE:
                coordinate = inputService.askForCoordinate(context.getBoard().getBoard());
                actionService.doMoveAction(context, player, coordinate);
                execute(context);
                break;
            case ATTACK:
                BaseHandEquipment handEquipment = inputService.askForHandEquipment(player);
                coordinate = inputService.askForCoordinate(context.getBoard().getBoard());
                if (player.canAttack(coordinate, handEquipment.getRange())) {
                    actionService.doAttackAction(context, player, coordinate, handEquipment);
                } else {
                    System.out.println("Impossible Attack ¬¬. Try with another near target...");
                    execute(context);
                }
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
        tacticalGame.setState(new EnemyTurn());
    }

    @Override
    public void choosePlayer(GameContext context) {
        Player playerChosen = inputService.askForPlayer(context.getPlayers()
                .stream()
                .filter(Predicate.not(Player::isFinishedTurn))
                .collect(Collectors.toList()));
        context.setPlayerChoose(playerChosen);
    }
}
