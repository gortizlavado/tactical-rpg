package tactical.game.states.impl;

import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;
import tactical.players.base.Player;

public class EndGame implements GameState {

    public static final String END_GAME = "END GAME";

    @Override
    public String fetchTurn() {
        return END_GAME;
    }

    @Override
    public boolean apply(GameContext context) {
        return context.getPlayers().size() == 0 || context.getEnemies().size() == 0;
    }

    @Override
    public void execute(GameContext context, Player player) {
        context.setEndGame(Boolean.TRUE);
    }

    @Override
    public void next(TacticalGame tacticalGame) {
        tacticalGame.setState(new NewTurn());
    }
}
