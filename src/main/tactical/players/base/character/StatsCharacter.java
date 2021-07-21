package tactical.players.base.character;

import lombok.Data;

@Data
public class StatsCharacter {

    public static final int BASE_MOVE = 5;

    protected String name;
    protected int level;
    protected int health;
    protected int attackPower;
    protected int defensePower;
    protected int move;

    public StatsCharacter(String name, int level, int health, int attackPower, int defensePower, int move) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.move = move;
    }

    public StatsCharacter(String name, int level, int health, int attackPower, int defensePower) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.move = BASE_MOVE;
    }

    public StatsCharacter(String name, int level) {
        this.name = name;
        this.level = level;
        this.move = BASE_MOVE;
    }

}
