package tactical.players;

public final class PlayerTwo extends AbstractPlayablePlayer {

    private final static String NAME = "Player2";

    private static final int BASE_HEALTH = 99;
    private static final int BASE_ATTACK = 22;
    private static final int BASE_DEFENSE = 9;

    public PlayerTwo() {
        super(NAME, BASE_HEALTH, BASE_ATTACK, BASE_DEFENSE);
    }

    @Override
    int calculateHealthBy(int level) {
        return 0;
    }

    @Override
    int calculateAttackPowerBy(int level) {
        return 0;
    }

    @Override
    int calculateDefensePowerBy(int level) {
        return 0;
    }
}
