package tactical.players.base.character;

import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;

public interface ActionCharacter {

    boolean move(final Coordinate coordinate);

    int attack(final BaseHandEquipment equipment);

    int defense();

    void endTurn();

    void newTurn();

}
