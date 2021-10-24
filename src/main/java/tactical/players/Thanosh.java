package tactical.players;

import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;

public final class Thanosh extends AbstractPlayablePlayer {

    private final static String NAME = "Thanosh";

    private static final int BASE_HEALTH = 120;
    private static final int BASE_ATTACK = 32;
    private static final int BASE_DEFENSE = 11;

    public Thanosh() {
        super(NAME, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE);
    }

    public Thanosh(int level) {
        super(NAME, level);
        this.health = calculateHealthBy(level);
        this.attackPower = calculateAttackPowerBy(level);
        this.defensePower = calculateDefensePowerBy(level);
    }

    public Thanosh(int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(NAME, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    public Thanosh(int level, int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(NAME, level, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    int calculateHealthBy(int level) {
        double calculatedHealth = BASE_HEALTH*Math.exp(0.3*level);
        return (int) Math.round(calculatedHealth);
    }

    int calculateAttackPowerBy(int level) {
        double calculatedAttackPower = BASE_ATTACK*Math.exp(0.15*level);
        return (int) Math.round(calculatedAttackPower);
    }

    int calculateDefensePowerBy(int level) {
        double calculatedAttackPower = BASE_DEFENSE*Math.exp(0.15*level);
        return (int) Math.round(calculatedAttackPower);
    }
}
