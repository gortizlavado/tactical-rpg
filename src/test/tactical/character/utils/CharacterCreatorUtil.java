package tactical.character.utils;

import tactical.character.base.BaseCharacter;
import tactical.models.Coordinate;

public class CharacterCreatorUtil {

    public static BaseCharacter createTestCharacter() {
        Coordinate initialCoordinate = fetchInitialCoordinate();
        String[] equipment = new String[2];
        equipment[0] = "sword";
        equipment[0] = "shield";
        return new BaseCharacter(false, "Jonh Doe", 100, 30, 10, 5, initialCoordinate, equipment);
    }

    public static Coordinate fetchInitialCoordinate() {
        return new Coordinate(0,0);
    }
}
