package tactical.game.states;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tactical.players.base.Player;

@Disabled
class StatesServiceTest {

    private StatesService service = new StatesService();

    @Test
    void shouldDoHappy_whenCoordinateIsCorrect() {
        service.askForCoordinate(new Player[5][5]);
    }
}