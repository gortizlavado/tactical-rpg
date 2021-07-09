package tactical.equipment.hand.base;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseHandEquipment {

    protected String name;
    protected int attackPower;
    protected int defensePower;
    protected int range;

    public BaseHandEquipment(String name, int attackPower, int defensePower, int range) {
        this.name = name;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
        this.range = range;
    }
}
