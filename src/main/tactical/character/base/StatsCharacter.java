package tactical.character.base;

import tactical.models.Coordinate;
import java.util.Arrays;

public class StatsCharacter {

    protected String name;
    protected int level;
    protected int health;
    protected int attackPower;
    protected int defensePower;
    protected int move;
    protected Coordinate coordinate;
    protected String[] equipment = new String[2];

    public StatsCharacter(String name, int level, int health, int attackPower, int defensePower, int move, Coordinate coordinate) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.move = move;
        this.coordinate = coordinate;
    }

    public StatsCharacter(String name, int level, int health, int attackPower, int defensePower, int move, Coordinate coordinate, String... equipment) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.move = move;
        this.coordinate = coordinate;
        this.setEquipment(equipment);
    }

    public void setEquipment(String[] equipment) {
        if (equipment.length <= 2) {
            this.equipment = equipment;
        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String[] getEquipment() {
        return equipment;
    }

    @Override
    public String toString() {
        return "StatsCharacter{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", health=" + health +
                ", attackPower=" + attackPower +
                ", defensePower=" + defensePower +
                ", move=" + move +
                ", coordinate=" + coordinate +
                ", equipment=" + Arrays.toString(equipment) +
                '}';
    }
}
