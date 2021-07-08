package tactical.character.base;

import tactical.models.Coordinate;

public interface ActionCharacter {

    void move(final Coordinate coordinate);

    int attack();

    int defense();

    void endTurn();

}
