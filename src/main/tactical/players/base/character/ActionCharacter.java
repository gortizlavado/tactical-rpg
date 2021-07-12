package tactical.players.base.character;

import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;

public interface ActionCharacter {

    void move(final Coordinate coordinate);

    int attack(final BaseHandEquipment equipment);

    int defense();

    void reduceHealth(int healthToReduce);

    void endTurn();

}
