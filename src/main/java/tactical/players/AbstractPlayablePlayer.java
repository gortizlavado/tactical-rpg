package tactical.players;

import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;
import tactical.players.base.Player;

public abstract class AbstractPlayablePlayer extends Player {

    public AbstractPlayablePlayer(String name, int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(Boolean.FALSE, name, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    public AbstractPlayablePlayer(String name, int level, int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(Boolean.FALSE, name, level, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    public AbstractPlayablePlayer(String name, int baseHealth, int baseAttack, int baseDefense) {
        super(Boolean.FALSE, name, baseHealth, baseAttack, baseDefense);
    }

    public AbstractPlayablePlayer(String name, int level) {
        super(name, level);
    }

    abstract int calculateHealthBy(int level);

    abstract int calculateAttackPowerBy(int level);

    abstract int calculateDefensePowerBy(int level);
}
