package tactical.equipment.body.armor;

import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.constants.EquipmentConstant;

public class Jacket extends BaseBodyEquipment {

    private static final String JACKET = "Jacket";

    public Jacket() {
        super(JACKET, EquipmentConstant.NON_ATTACK_POWER, EquipmentConstant.DEFENSE_POWER_TWO);
    }
}
