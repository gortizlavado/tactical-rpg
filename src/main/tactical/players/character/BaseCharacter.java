package tactical.players.character;

import lombok.Getter;
import tactical.equipment.hand.base.BaseHandEquipment;
import tactical.models.Coordinate;

@Getter
public class BaseCharacter extends StatsCharacter {

    protected boolean isFinishedTurn;
    protected Coordinate coordinate;
    protected BaseHandEquipment[] handEquipment = new BaseHandEquipment[2];
    protected String[] bodyEquipment = new String[2];

    public BaseCharacter(boolean isFinishedTurn, String name, int level, int health, int attackPower,
                         int defensePower, int move, Coordinate coordinate, BaseHandEquipment[] handEquipment, String[] bodyEquipment) {
        super(name, level, health, attackPower, defensePower, move);
        this.isFinishedTurn = isFinishedTurn;
        this.coordinate = coordinate;
        this.handEquipment = handEquipment;
        this.bodyEquipment = bodyEquipment;
    }

    public BaseCharacter(boolean isFinishedTurn, String name, int level, int health, int attackPower,
                         int defensePower, int move, Coordinate coordinate) {
        super(name, level, health, attackPower, defensePower, move);
        this.isFinishedTurn = isFinishedTurn;
        this.coordinate = coordinate;
    }

    public void setHandEquipment(BaseHandEquipment[] handEquipment) {
        if (handEquipment.length > 2) {
            return;
        }
        this.handEquipment = handEquipment;
    }

    public void setHandEquipment(BaseHandEquipment handEquipment) {
        int i = 0;
        boolean found = false;
        while (!found && i < this.handEquipment.length) {
            if (null == this.handEquipment[i]) {
                this.handEquipment[i] = handEquipment;
                found = true;
            }
            i++;
        }
    }

    public void setHandEquipment(BaseHandEquipment handEquipment, int position) {
        this.handEquipment[position] = handEquipment;
    }

    public void setBodyEquipment(String[] bodyEquipment) {
        if (bodyEquipment.length > 2) {
            return;
        }
        this.bodyEquipment = bodyEquipment;
    }

    public void setFinishedTurn(boolean finishedTurn) {
        isFinishedTurn = finishedTurn;
    }

}
