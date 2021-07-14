package tactical.game.enemy;

import tactical.players.base.Player;

import java.util.ArrayList;
import java.util.List;

public class EnemiesProvider {

    public static final int BASE_HEALTH = 100;
    public static final int BASE_ATTACK = 30;
    public static final int BASE_DEFENSE = 10;
    public static final int BASE_MOVE = 5;

    public List<Player> createEnemiesStatsBy(final int numbers, final int level) {
        ArrayList<Player> enemies = new ArrayList<>();

        for (int i = 0; i < numbers; i++) {
            String name = "Enemy " + (i+1);
            int health = calculateHealthBy(level);
            int attackPower = calculateAttackPowerBy(level);
            int defensePower = calculateDefensePowerBy(level);
            Player enemy = new Player(name, level, health, attackPower, defensePower, BASE_MOVE);
            enemies.add(enemy);
        }

        return enemies;
    }

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
