package tactical.character.base;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tactical.character.utils.CharacterCreatorUtil;
import tactical.models.Coordinate;

class BaseCharacterTest {

    @Test
    void shouldMoveCharacter() {
        BaseCharacter testCharacter = CharacterCreatorUtil.createTestCharacter();
        Coordinate destinationCoordinate = new Coordinate(2,1);

        testCharacter.move(destinationCoordinate);

        Assertions.assertEquals(destinationCoordinate, testCharacter.getCoordinate());
    }

    @Test
    void shouldNotMoveCharacter_whenDistanceIsHigherThanIstMovement() {
        BaseCharacter testCharacter = CharacterCreatorUtil.createTestCharacter();
        Coordinate destinationCoordinate = new Coordinate(5,3);

        testCharacter.move(destinationCoordinate);

        Coordinate expectedCoordinate = CharacterCreatorUtil.fetchInitialCoordinate();
        Assertions.assertEquals(expectedCoordinate, testCharacter.getCoordinate());
    }

}