package tactical.game.states;

import tactical.game.TacticalGame;
import tactical.game.context.GameContext;

public interface GameState {

    String fetchTurn();

    boolean apply(GameContext context);

    void execute(GameContext context);

    void next(TacticalGame tacticalGame);
}
