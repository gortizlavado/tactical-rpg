package tactical.character;

import tactical.character.base.BaseCharacter;

import tactical.models.Coordinate;

public class Thanosh extends BaseCharacter {

    private final static String NAME = "Thanosh";


    public Thanosh(boolean isYourTurn, int level, int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, String... equipment) {
        super(isYourTurn, NAME, level, health, attackPower, defensePower, move, coordinate, equipment);
    }

    public Thanosh(boolean isYourTurn, int health, int attackPower, int defensePower,
                   int move, Coordinate coordinate, String... equipment) {
        super(isYourTurn, NAME, health, attackPower, defensePower, move, coordinate, equipment);
    }
}
