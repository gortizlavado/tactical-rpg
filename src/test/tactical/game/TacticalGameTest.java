package tactical.game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class TacticalGameTest {

    private TacticalGame tacticalGame = new TacticalGame();

    @Test
    void play() {
        tacticalGame.init("Game test");
        tacticalGame.play();
    }

}