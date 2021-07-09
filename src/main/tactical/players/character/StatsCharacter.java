package tactical.players.character;

import lombok.Data;

@Data
public class StatsCharacter {

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

}
