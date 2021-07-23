package tactical.equipment.base;

import lombok.Data;

@Data
public class BaseEquipment {

    protected String name;
    protected int attackPower;
    protected int defensePower;

    public BaseEquipment() {
    }

    public BaseEquipment(String name, int attackPower, int defensePower) {
        this.name = name;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
    }
}
