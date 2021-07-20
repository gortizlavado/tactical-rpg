package tactical;

import tactical.game.TacticalGame;

public class PlayGame {

    public static void main(String[] args) {
        TacticalGame tacticalGame = new TacticalGame();
        // Ask for game name.
        String name = "test";
        System.out.println("Initiating tactical game...");
        tacticalGame.init(name);
        System.out.println("Initiate completed!");
        // Any Key to start.
        tacticalGame.play();
    }
}
