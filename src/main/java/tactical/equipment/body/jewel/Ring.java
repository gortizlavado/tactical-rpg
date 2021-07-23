package tactical.equipment.body.jewel;

import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.constants.EquipmentConstant;

public final class Ring extends BaseBodyEquipment {

    private static final String RING = "Ring";

    public Ring() {
        super(RING, EquipmentConstant.ATTACK_POWER_ONE, EquipmentConstant.NON_DEFENSE_POWER);
    }
}
