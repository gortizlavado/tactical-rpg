package tactical.equipment.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseHandEquipment extends BaseEquipment {

    protected int range;

    public BaseHandEquipment(String name, int attackPower, int defensePower, int range) {
        super(name, attackPower, defensePower);
        this.range = range;
    }

    @Override
    public String toString() {
        return name + "\n" +
                "\t\tAtk " + attackPower + " Rang " + range;
    }
}
