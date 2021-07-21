package tactical.equipment.hand.weapon.sword;

import tactical.equipment.base.BaseHandEquipment;
import tactical.equipment.base.constants.EquipmentConstant;

public final class SuperShort extends BaseHandEquipment {

    private static final String NAME = "Short";

    public SuperShort() {
        super(NAME,
                EquipmentConstant.ATTACK_POWER_SEVENTY,
                EquipmentConstant.NON_DEFENSE_POWER,
                EquipmentConstant.RANGE_ONE);
    }

}
