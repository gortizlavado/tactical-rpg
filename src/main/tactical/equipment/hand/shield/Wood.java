package tactical.equipment.hand.shield;

import tactical.equipment.base.BaseHandEquipment;
import tactical.equipment.base.constants.EquipmentConstant;

public final class Wood extends BaseHandEquipment {

    private static final String NAME = "Wood";

    public Wood() {
        super(NAME,
                EquipmentConstant.NON_ATTACK_POWER,
                EquipmentConstant.DEFENSE_POWER_TWO,
                EquipmentConstant.RANGE_ONE);
    }
}
