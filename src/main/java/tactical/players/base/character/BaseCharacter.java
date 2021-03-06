package tactical.players.base.character;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.BaseEquipment;
import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;

@Getter
@ToString(callSuper = true)
public class BaseCharacter extends StatsCharacter {

    @Setter
    private boolean finishedTurn;
    @Setter
    private boolean moveTurn;
    @Setter
    private Coordinate coordinate;
    private BaseHandEquipment[] handEquipment = new BaseHandEquipment[2];
    private BaseBodyEquipment[] bodyEquipment = new BaseBodyEquipment[2];

    public BaseCharacter(boolean finishedTurn, String name, int level, int health,
                         int attackPower, int defensePower, int move, Coordinate coordinate,
                         BaseHandEquipment[] handEquipment, BaseBodyEquipment[] bodyEquipment) {
        super(name, level, health, attackPower, defensePower, move);
        this.finishedTurn = finishedTurn;
        this.coordinate = coordinate;
        this.handEquipment = handEquipment;
        this.bodyEquipment = bodyEquipment;
    }

    public BaseCharacter(boolean finishedTurn, String name, int level, int health, int attackPower,
                         int defensePower, int move, Coordinate coordinate) {
        super(name, level, health, attackPower, defensePower, move);
        this.finishedTurn = finishedTurn;
        this.coordinate = coordinate;
    }

    public BaseCharacter(boolean finishedTurn, String name, int level, int health, int attackPower,
                         int defensePower, int move) {
        super(name, level, health, attackPower, defensePower, move);
        this.finishedTurn = finishedTurn;
        this.coordinate = new Coordinate(0, 0);
    }

    public BaseCharacter(boolean finishedTurn, String name, int level, int health, int attackPower,
                         int defensePower) {
        super(name, level, health, attackPower, defensePower);
        this.finishedTurn = finishedTurn;
        this.coordinate = new Coordinate(0, 0);
    }

    public BaseCharacter(String name, int level) {
        super(name, level);
    }

    public void setEquipment(BaseEquipment[] equipments) {
        if (handEquipment.length > 2) {
            return;
        }

        if (equipments instanceof BaseBodyEquipment[]) {
            this.bodyEquipment = (BaseBodyEquipment[]) equipments;
        } else if (equipments instanceof BaseHandEquipment[]) {
            this.handEquipment = (BaseHandEquipment[]) equipments;
        }
    }

    public void setEquipment(BaseEquipment equipment, int position) {
        if (equipment instanceof BaseBodyEquipment) {
            this.bodyEquipment[position] = (BaseBodyEquipment) equipment;
        } else if (equipment instanceof BaseHandEquipment) {
            this.handEquipment[position] = (BaseHandEquipment) equipment;
        }
    }

    public void setEquipment(BaseEquipment equipment) {
        BaseEquipment[] equipmentArray = new BaseEquipment[2];
        if (equipment instanceof BaseBodyEquipment) {
            equipmentArray = this.bodyEquipment;
        } else if (equipment instanceof BaseHandEquipment) {
            equipmentArray = this.handEquipment;
        }

        int i = 0;
        boolean found = Boolean.FALSE;
        while (!found && i < equipmentArray.length) {
            if (null == equipmentArray[i]) {
                equipmentArray[i] = equipment;
                found = Boolean.TRUE;
            }
            i++;
        }
    }

    public boolean isEquipped(BaseHandEquipment handEquipment) {
        int i = 0;
        boolean found = Boolean.FALSE;
        while (!found && i < this.handEquipment.length) {
            if (this.handEquipment[i].equals(handEquipment)) {
                found = Boolean.TRUE;
            }
            i++;
        }

        return found;
    }

}
