package tactical.game.states;

import tactical.equipment.base.BaseHandEquipment;
import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;

public interface GameState {

    String fetchTurn();

    boolean apply(GameContext context);

    void execute(GameContext context, Player player, ActionEnum action, Coordinate coordinate, BaseHandEquipment handEquipment);

    void next(TacticalGame tacticalGame);
}
