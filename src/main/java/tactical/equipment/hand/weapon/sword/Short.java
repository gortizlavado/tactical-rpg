package tactical.equipment.hand.weapon.sword;

import tactical.equipment.base.BaseHandEquipment;
import tactical.equipment.base.constants.EquipmentConstant;

public final class Short extends BaseHandEquipment {

    private static final String NAME = "Short";

    public Short() {
        super(NAME,
                EquipmentConstant.ATTACK_POWER_TWO,
                EquipmentConstant.NON_DEFENSE_POWER,
                EquipmentConstant.RANGE_ONE);
    }

}
