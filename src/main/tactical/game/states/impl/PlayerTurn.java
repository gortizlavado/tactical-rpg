package tactical.game.states.impl;

import tactical.equipment.base.BaseHandEquipment;
import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.game.states.StatesService;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

import java.util.function.Predicate;

public class PlayerTurn implements GameState {

    public static final String TURN_PLAYER = "--- TURN PLAYER ---";

    private final StatesService service;

    public PlayerTurn() {
        this.service = new StatesService();
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
    public void execute(GameContext context, Player player) {
        System.out.printf("Player choose for doing an action: %s%n", player.getName());
        ActionEnum action = service.askForAction(player);
        Coordinate coordinate;
        switch (action) {
            case MOVE:
                coordinate = service.askForCoordinate();
                service.doMoveAction(context, player, coordinate);
                execute(context, player);
                break;
            case ATTACK:
                coordinate = service.askForCoordinate();
                BaseHandEquipment handEquipment = service.askForHandEquipment(player);
                service.doAttackAction(context, player, coordinate, handEquipment);
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
        System.out.println("End turn for this player");
        tacticalGame.setState(new EnemyTurn());
    }
}
