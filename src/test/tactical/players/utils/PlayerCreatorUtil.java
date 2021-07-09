package tactical.players.utils;

import tactical.equipment.hand.base.BaseHandEquipment;
import tactical.players.base.Player;
import tactical.models.Coordinate;

public class PlayerCreatorUtil {

    public static Player createTestPlayerNoEquipment() {
        Coordinate initialCoordinate = fetchInitialCoordinate();
        return new Player(false, "John Doe", 100, 30, 10, 5, initialCoordinate);
    }

    public static Player createTestPlayerEquipmentWithSword() {
        Coordinate initialCoordinate = fetchInitialCoordinate();
        Player player = new Player(false, "John Doe", 100, 30, 10, 5, initialCoordinate);
        player.setHandEquipment(createTestHandEquipmentWithSword());
        return player;
    }

    public static Player createTestPlayerEquipmentWithSwordAndShield() {
        Coordinate initialCoordinate = fetchInitialCoordinate();
        Player player = new Player(false, "John Doe", 100, 30, 10, 5, initialCoordinate);
        player.setHandEquipment(createTestHandEquipmentWithSwordAndShield());
        return player;
    }

    private static BaseHandEquipment[] createTestHandEquipmentWithSwordAndShield() {
        BaseHandEquipment[] equipments = new BaseHandEquipment[2];
        BaseHandEquipment sword = createTestHandEquipmentWithSword();
        BaseHandEquipment shield = new BaseHandEquipment("Shield", 1, 5, 1);
        equipments[0] = sword;
        equipments[1] = shield;
        return equipments;
    }

    private static BaseHandEquipment createTestHandEquipmentWithSword() {
        return new BaseHandEquipment("Sword", 10, 0, 1);
    }

    public static Coordinate fetchInitialCoordinate() {
        return new Coordinate(0,0);
    }
}
