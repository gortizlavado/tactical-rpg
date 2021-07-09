package tactical.equipment.hand.weapon.sword;

import tactical.equipment.hand.base.BaseHandEquipment;
import tactical.equipment.hand.base.constants.EquipmentConstant;

public class Long extends BaseHandEquipment {

    private static final String NAME = "Long";

    public Long() {
        super(NAME,
                EquipmentConstant.ATTACK_POWER_FOUR,
                EquipmentConstant.NON_DEFENSE_POWER,
                EquipmentConstant.RANGE_ONE);
    }

}
