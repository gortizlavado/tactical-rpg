package tactical.equipment.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class BaseBodyEquipment extends BaseEquipment {

    public BaseBodyEquipment(String name, int attackPower, int defensePower) {
        super(name, attackPower, defensePower);
    }
}
