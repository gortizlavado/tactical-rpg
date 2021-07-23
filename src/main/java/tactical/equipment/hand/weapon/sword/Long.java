package tactical.equipment.hand.weapon.sword;

import tactical.equipment.base.BaseHandEquipment;
import tactical.equipment.base.constants.EquipmentConstant;

public final class Long extends BaseHandEquipment {

    private static final String NAME = "Long";

    public Long() {
        super(NAME,
                EquipmentConstant.ATTACK_POWER_FOUR,
                EquipmentConstant.NON_DEFENSE_POWER,
                EquipmentConstant.RANGE_ONE);
    }

}
