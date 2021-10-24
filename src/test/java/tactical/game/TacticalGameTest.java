package tactical.game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

@Disabled
class TacticalGameTest {

    @InjectMocks
    private TacticalGame tacticalGame;

    @Test
    void play() {
        tacticalGame.init("Game test");
        tacticalGame.play();
    }

}