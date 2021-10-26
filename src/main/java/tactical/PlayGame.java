package tactical;

import tactical.game.TacticalGame;
import tactical.game.context.GameContext;

import java.io.IOException;

public class PlayGame {

    public static void main(String[] args) {
        TacticalGame tacticalGame = new TacticalGame();
        // Ask for game name.
        String name = "new game";
        System.out.println("Initiating tactical game...");
        tacticalGame.init(name);
        System.out.println("Initiate completed!");
        System.out.println("Press any key to start");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tacticalGame.play();

        if (tacticalGame.getContext().getCharactersMap().get(GameContext.ENEMY_KEY).isEmpty()) {
            System.out.println("Player WIN!");
        } else {
            System.out.println("Player LOSE...");
        }
    }
}
