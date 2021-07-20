package tactical.game.states.impl;

import lombok.NonNull;
import tactical.equipment.base.BaseHandEquipment;
import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class NewTurn implements GameState {

    public static final String NEW_TURN = "--- NEW TURN ---";
    public static final int PLUS_ONE = 1;

    @Override
    public String fetchTurn() {
        return NEW_TURN;
    }

    @Override
    public boolean apply(GameContext context) {
        return context.getPlayers()
                    .stream().allMatch(Player::isFinishedTurn) &&
                context.getEnemies()
                    .stream().allMatch(Player::isFinishedTurn);
    }

    @Override
    public void execute(GameContext context, Player player, ActionEnum action, Coordinate coordinate, BaseHandEquipment handEquipment) {
        incrementTurn(context::getTurnNumber, context::setTurnNumber);
        context.getPlayers().forEach(Player::newTurn);
        context.getEnemies().forEach(Player::newTurn);
        System.out.println("--- TURN " + context.getTurnNumber() + " ---");
    }

    private void incrementTurn(@NonNull Supplier<Integer> source, @NonNull Consumer<Integer> destination) {
        destination.accept(source.get() + PLUS_ONE);
    }

    @Override
    public void next(TacticalGame tacticalGame) {
        tacticalGame.setState(new PlayerTurn());
    }
}
