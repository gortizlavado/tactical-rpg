package tactical.game.states.impl;

import tactical.equipment.base.BaseHandEquipment;
import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

public class NewTurn implements GameState {

    public static final String NEW_TURN = "New Turn";
    public static final int INCREMENT_TURN = 1;

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
        //Function<GameContext, Integer> func = context::getTurnNumber, context::setTurnNumber;
        final int actualTurn = context.getTurnNumber();
        context.setTurnNumber(actualTurn + INCREMENT_TURN);
        //TODO bi-function newTurn
        context.getPlayers().forEach(Player::newTurn);
        context.getEnemies().forEach(Player::newTurn);
    }

    @Override
    public void next(TacticalGame tacticalGame) {
        tacticalGame.setState(new PlayerTurn());
    }
}
