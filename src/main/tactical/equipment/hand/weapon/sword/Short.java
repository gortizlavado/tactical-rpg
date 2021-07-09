package tactical.equipment.hand.weapon.sword;

import tactical.equipment.hand.base.BaseHandEquipment;
import tactical.equipment.hand.base.constants.EquipmentConstant;

public class Short extends BaseHandEquipment {

    private static final String NAME = "Short";

    public Short() {
        super(NAME,
                EquipmentConstant.ATTACK_POWER_TWO,
                EquipmentConstant.NON_DEFENSE_POWER,
                EquipmentConstant.RANGE_ONE);
    }

}
