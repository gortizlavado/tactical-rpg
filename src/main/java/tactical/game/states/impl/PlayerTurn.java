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

import java.util.List;
import java.util.Objects;
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
        final long numberOfPlayerReady = context.getCharactersMap().get(GameContext.PLAYER_KEY)
                .stream()
                .filter(Predicate.not(Player::isFinishedTurn))
                .count();
        System.out.printf("There are a number of players ready: %s%n", numberOfPlayerReady);
        return numberOfPlayerReady > 0;
    }

    @Override
    public void execute(GameContext context) {
        Player player = context.getPlayerChoose();
        System.out.printf("The Player chosen for doing an action is '%s'%n", player.getName());
        ActionEnum action = inputService.askForAction(player);
        Coordinate coordinate;
        switch (action) {
            case MOVE:
                coordinate = inputService.askForCoordinateToMove(context.getBoard().getField());
                actionService.doMoveAction(context, player, coordinate);
                execute(context);
                break;
            case ATTACK:
                BaseHandEquipment handEquipment = inputService.askForHandEquipment(player);
                int rangeAttack = (Objects.isNull(handEquipment)) ? 1 : handEquipment.getRange();
                List<Coordinate> coordinateList = context.fetchPossibleCoordinateForAttack(rangeAttack);
                coordinate = inputService.askForCoordinateToAttack(coordinateList);
                if (player.canAttack(coordinate, rangeAttack)) {
                    actionService.doAttackAction(context, player, coordinate, handEquipment);
                } else {
                    System.out.println("Impossible Attack ????. Try with another near target...");
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
        Player playerChosen = inputService.askForPlayer(context.getCharactersMap().get(GameContext.PLAYER_KEY)
                .stream()
                .filter(Predicate.not(Player::isFinishedTurn))
                .collect(Collectors.toList()));
        context.setPlayerChoose(playerChosen);
    }
}
