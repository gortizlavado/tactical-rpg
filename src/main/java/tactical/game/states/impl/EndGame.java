package tactical.game.states.impl;

import tactical.game.TacticalGame;
import tactical.game.context.GameContext;
import tactical.game.states.GameState;

public class EndGame implements GameState {

    public static final String END_GAME = "END GAME";

    @Override
    public String fetchTurn() {
        return END_GAME;
    }

    @Override
    public boolean apply(GameContext context) {
        return context.getCharactersMap().get(GameContext.PLAYER_KEY).size() == 0 ||
                context.getCharactersMap().get(GameContext.ENEMY_KEY).size() == 0;
    }

    @Override
    public void execute(GameContext context) {
        context.setEndGame(Boolean.TRUE);
    }

    @Override
    public void next(TacticalGame tacticalGame) {
        tacticalGame.setState(new NewTurn());
    }
}
