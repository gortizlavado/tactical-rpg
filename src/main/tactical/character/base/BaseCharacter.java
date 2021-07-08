package tactical.character.base;

import tactical.models.Coordinate;

public class BaseCharacter extends StatsCharacter implements ActionCharacter {

    private boolean isFinishedTurn;
    
    private final static int BEGINNING_LEVEL = 1;

    public BaseCharacter(boolean isFinishedTurn, String name, int level, int health, int attackPower,
                         int defensePower, int move, Coordinate coordinate, String... equipment) {
        super(name, level, health, attackPower, defensePower, move, coordinate, equipment);
        this.isFinishedTurn = isFinishedTurn;
    }

    public BaseCharacter(boolean isFinishedTurn, String name, int health, int attackPower,
                         int defensePower, int move, Coordinate coordinate, String... equipment) {
        super(name, BEGINNING_LEVEL, health, attackPower, defensePower, move, coordinate, equipment);
        this.isFinishedTurn = isFinishedTurn;
    }

    @Override
    public void move(final Coordinate destination) {
        Coordinate initial = this.coordinate;
        int amountOfMovement = this.move;

        if (this.canMove(initial, destination, amountOfMovement)) {
            this.coordinate = destination;
            System.out.println("Movement successful!");
        } else {
            System.out.println("Impossible Movement ¬¬");
        }
    }

    @Override
    public int attack() {
        return this.attackPower;
    }

    @Override
    public int defense() {
        return this.defensePower;
    }

    @Override
    public void endTurn() {
        System.out.println("End Turn");
        this.isFinishedTurn = true;
    }

    private boolean canMove(Coordinate origin, Coordinate destination, int maxAmountOfMovement) {
        return maxAmountOfMovement >= origin.diff(destination);
    }
}
