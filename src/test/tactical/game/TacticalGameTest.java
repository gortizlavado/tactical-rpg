package tactical.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TacticalGameTest {

    private TacticalGame tacticalGame = new TacticalGame();

    @Test
    void play() {
        tacticalGame.init("Game test");
        tacticalGame.play();
    }

}