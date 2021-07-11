package tactical.players;

import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.BaseHandEquipment;
import tactical.players.base.Player;
import tactical.models.Coordinate;

public class Thanosh extends Player {

    private final static String NAME = "Thanosh";

    public Thanosh(int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(false, NAME, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    public Thanosh(int level, int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(false, NAME, level, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }
}
