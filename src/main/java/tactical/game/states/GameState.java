package tactical.game.states;

import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.players.base.Player;

public interface GameState {

    String fetchTurn();

    boolean apply(GameContext context);

    void execute(GameContext context, Player player);

    void next(TacticalGame tacticalGame);
}
