package tactical.players.base;

import lombok.ToString;
import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;
import tactical.players.base.character.ActionCharacter;
import tactical.players.base.character.BaseCharacter;

import java.util.Arrays;

@ToString(callSuper = true)
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

    public Player(boolean isFinishedTurn, String name, int level, int health, int attackPower, int defensePower,
                  int move, Coordinate coordinate, BaseHandEquipment[] weapon, BaseBodyEquipment[] armor) {
        super(isFinishedTurn, name, level, health, attackPower, defensePower, move, coordinate, weapon, armor);
    }

    @Override
    public void move(final Coordinate destination) {
        if (this.isFinishedTurn) {
            return;
        }

        Coordinate initial = this.coordinate;
        int amountOfMovement = this.move;

        if (this.canMove(initial, destination, amountOfMovement)) {
            this.coordinate = destination;
            System.out.println("Movement successful!");
        } else {
            System.out.println("Impossible Movement ¬¬. Try with another destination...");
        }
    }

    @Override
    public int attack(final BaseHandEquipment handEquipment) {
        if (null == handEquipment) {
            printAttack(this.attackPower);
            return this.attackPower;
        }

        int increaseAttackPower = handEquipment.getAttackPower();
        for (BaseBodyEquipment equipment : this.bodyEquipment) {
            if (null != equipment) {
                increaseAttackPower = increaseAttackPower + equipment.getAttackPower();
            }
        }

        int totalAttackPower = this.attackPower + increaseAttackPower;
        printAttack(totalAttackPower);
        return totalAttackPower;
    }

    @Override
    public int defense() {
        if (Arrays.equals(new BaseHandEquipment[2], this.handEquipment) &&
                Arrays.equals(new BaseBodyEquipment[2], this.bodyEquipment)) {
            printDefense(this.defensePower);
            return this.defensePower;
        }

        int increaseDefensePower = 0;
        for (BaseHandEquipment equipment : this.handEquipment) {
            if (null != equipment) {
                increaseDefensePower = increaseDefensePower + equipment.getDefensePower();
            }
        }
        for (BaseBodyEquipment equipment : this.bodyEquipment) {
            if (null != equipment) {
                increaseDefensePower = increaseDefensePower + equipment.getDefensePower();
            }
        }

        int totalDefensePower = this.defensePower + increaseDefensePower;
        printDefense(totalDefensePower);
        return totalDefensePower;
    }

    @Override
    public void reduceHealth(int healthToReduce) {
        this.health = this.health - healthToReduce;
    }

    @Override
    public void endTurn() {
        System.out.println("End Turn");
        this.isFinishedTurn = true;
    }

    private boolean canMove(Coordinate origin, Coordinate destination, int maxAmountOfMovement) {
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

}