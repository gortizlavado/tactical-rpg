package tactical.players.base.character;

import tactical.models.Coordinate;

public interface ActionCharacter {

    void move(final Coordinate coordinate);

    int attack();

    int defense();

    void endTurn();

}
