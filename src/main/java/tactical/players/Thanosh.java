package tactical.players;

import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;
import tactical.players.base.Player;

public final class Thanosh extends Player {

    private final static String NAME = "Thanosh";

    public static final int BASE_HEALTH = 120;
    public static final int BASE_ATTACK = 32;
    public static final int BASE_DEFENSE = 11;

    public Thanosh() {
        super(Boolean.FALSE, NAME, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE);
    }

    public Thanosh(int level) {
        super(NAME, level);
        this.health = calculateHealthBy(level);
        this.attackPower = calculateAttackPowerBy(level);
        this.defensePower = calculateDefensePowerBy(level);
    }

    public Thanosh(int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(Boolean.FALSE, NAME, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    public Thanosh(int level, int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(Boolean.FALSE, NAME, level, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    //TODO implements
    private int calculateHealthBy(int level) {
        double calculatedHealth = BASE_HEALTH*Math.exp(0.3*level);
        return (int) Math.round(calculatedHealth);
    }

    private int calculateAttackPowerBy(int level) {
        double calculatedAttackPower = BASE_ATTACK*Math.exp(0.15*level);
        return (int) Math.round(calculatedAttackPower);
    }

    private int calculateDefensePowerBy(int level) {
        double calculatedAttackPower = BASE_DEFENSE*Math.exp(0.15*level);
        return (int) Math.round(calculatedAttackPower);
    }
}
