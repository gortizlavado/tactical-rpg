package tactical.equipment.hand.shield;

import tactical.equipment.hand.base.BaseHandEquipment;
import tactical.equipment.hand.base.constants.EquipmentConstant;

public class Wood extends BaseHandEquipment {

    private static final String NAME = "Wood";

    public Wood() {
        super(NAME,
                EquipmentConstant.NON_ATTACK_POWER,
                EquipmentConstant.DEFENSE_POWER_TWO,
                EquipmentConstant.RANGE_ONE);
    }
}
