package tactical.players.base;

import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;
import tactical.players.base.character.ActionCharacter;
import tactical.players.base.character.BaseCharacter;

import java.util.Arrays;


public class Player extends BaseCharacter implements ActionCharacter {

    private final static int BEGINNING_LEVEL = 1;

    public Player(boolean isFinishedTurn, String name, int health, int attackPower, int defensePower,
                  int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(isFinishedTurn, name, BEGINNING_LEVEL, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    public Player(boolean isFinishedTurn, String name, int health, int attackPower, int defensePower,
                  int move, Coordinate coordinate) {
        super(isFinishedTurn, name, BEGINNING_LEVEL, health, attackPower, defensePower, move, coordinate);
    }

    public Player(boolean isFinishedTurn, String name, int health, int attackPower, int defensePower) {
        super(isFinishedTurn, name, BEGINNING_LEVEL, health, attackPower, defensePower);
    }

    public Player(String name, int level, int health, int attackPower, int defensePower, int move) {
        super(false, name, level, health, attackPower, defensePower, move);
    }

    public Player(boolean isFinishedTurn, String name, int level, int health, int attackPower, int defensePower,
                  int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(isFinishedTurn, name, level, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    public Player(String name, int level) {
        super(name, level);
    }

    @Override
    public final boolean move(final Coordinate destination) {
        if (!this.isMoveTurn()) {
            System.out.println("Not have more movement ¬¬");
            return false;
        }

        int amountOfMovement = this.move;
        if (this.canMove(destination, amountOfMovement)) {
            this.setCoordinate(destination);
            this.setMoveTurn(false);
            System.out.println("Movement successful!");
            return true;
        } else {
            System.out.println("Impossible Movement ¬¬. Try with another destination...");
            return false;
        }
    }

    @Override
    public final int attack(final BaseHandEquipment handEquipment) {
        if (null == handEquipment) {
            printAttack(this.attackPower);
            return this.attackPower;
        }

        int totalAttackPower = this.fetchTotalAttackPower(handEquipment);
        printAttack(totalAttackPower);
        return totalAttackPower;
    }

    private int fetchTotalAttackPower(final BaseHandEquipment handEquipment) {
        int increaseAttackPower = 0;
        if (null != handEquipment) {
            increaseAttackPower = handEquipment.getAttackPower();
        }
        for (BaseBodyEquipment equipment : this.getBodyEquipment()) {
            if (null != equipment) {
                increaseAttackPower = increaseAttackPower + equipment.getAttackPower();
            }
        }

        return this.attackPower + increaseAttackPower;
    }

    private int fetchTotalAttackPower() {
        return fetchTotalAttackPower(null);
    }

    @Override
    public final int defense() {
        if (Arrays.equals(new BaseHandEquipment[2], this.getHandEquipment()) &&
                Arrays.equals(new BaseBodyEquipment[2], this.getBodyEquipment())) {
            printDefense(this.defensePower);
            return this.defensePower;
        }

        int totalDefensePower = fetchTotalDefensePower();
        printDefense(totalDefensePower);
        return totalDefensePower;
    }

    private int fetchTotalDefensePower() {
        int increaseDefensePower = 0;
        for (BaseHandEquipment equipment : this.getHandEquipment()) {
            if (null != equipment) {
                increaseDefensePower = increaseDefensePower + equipment.getDefensePower();
            }
        }
        for (BaseBodyEquipment equipment : this.getBodyEquipment()) {
            if (null != equipment) {
                increaseDefensePower = increaseDefensePower + equipment.getDefensePower();
            }
        }

        return this.defensePower + increaseDefensePower;
    }

    @Override
    public final void modifyHealth(int healthToModify) {
        this.health = this.health + healthToModify;
    }

    @Override
    public final void endTurn() {
        this.setFinishedTurn(Boolean.TRUE);
        this.setMoveTurn(Boolean.FALSE);
    }

    @Override
    public final void newTurn() {
        this.setFinishedTurn(Boolean.FALSE);
        this.setMoveTurn(Boolean.TRUE);
    }

    public final boolean canAttack(Coordinate destination, int handRange) {
        Coordinate origin = this.getCoordinate();
        if (origin.equals(destination)) {
            return false;
        }
        return handRange >= origin.diff(destination);
    }

    private boolean canMove(Coordinate destination, int maxAmountOfMovement) {
        Coordinate origin = this.getCoordinate();
        if (origin.equals(destination)) {
            return false;
        }
        return maxAmountOfMovement >= origin.diff(destination);
    }

    private void printAttack(int power) {
        printAction("attack", power);
    }

    private void printDefense(int power) {
        printAction("defense", power);
    }

    private void printAction(String action, int power) {
        System.out.printf("Player %s %s with power %d%n", this.name, action, power);
    }

    @Override
    public String toString() {
        return getCoordinate() + " " + name + " " + "lvl" + level + "\n" +
                "\t\tHP " + health + " Atk " + this.fetchTotalAttackPower() + " Def " + this.fetchTotalDefensePower();
    }

}
