package tactical.players;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tactical.players.base.Player;
import tactical.players.utils.PlayerCreatorUtil;
import tactical.models.Coordinate;

class PlayerMoveTest {

    @Test
    void playerShouldMove_whenDistanceIsLessThanItsMovement() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerNoEquipment();
        Coordinate destinationCoordinate = new Coordinate(2,1);

        testPlayer.move(destinationCoordinate);

        Assertions.assertEquals(destinationCoordinate, testPlayer.getCoordinate());
    }

    @Test
    void playerShouldMove_whenDistanceIsEqualThanItsMovement() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerNoEquipment();
        Coordinate destinationCoordinate = new Coordinate(2,3);

        testPlayer.move(destinationCoordinate);

        Assertions.assertEquals(destinationCoordinate, testPlayer.getCoordinate());
    }

    @Test
    void playerShouldNotMove_whenDistanceIsHigherThanIstMovement() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerNoEquipment();
        Coordinate destinationCoordinate = new Coordinate(5,3);

        testPlayer.move(destinationCoordinate);

        Coordinate expectedCoordinate = PlayerCreatorUtil.fetchInitialCoordinate();
        Assertions.assertEquals(expectedCoordinate, testPlayer.getCoordinate());
    }

    @Test
    void playerShouldNotMove_whenAlreadyDidAnAction() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerNoEquipment();
        testPlayer.setFinishedTurn(true);
        Coordinate destinationCoordinate = new Coordinate(5,3);

        testPlayer.move(destinationCoordinate);

        Coordinate expectedCoordinate = PlayerCreatorUtil.fetchInitialCoordinate();
        Assertions.assertEquals(expectedCoordinate, testPlayer.getCoordinate());
    }

}