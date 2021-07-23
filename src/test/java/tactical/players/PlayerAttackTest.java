package tactical.players;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tactical.players.base.Player;
import tactical.players.utils.PlayerCreatorUtil;

public class PlayerAttackTest {

    @Test
    void playerShouldAttackHigher_whenAttackWithSword() {

        Player testPlayer = PlayerCreatorUtil.createTestPlayerEquipmentWithSword();
        int playerAttackPower = testPlayer.getAttackPower();
        int totalAttackPower = testPlayer.attack(testPlayer.getHandEquipment()[0]);

        Assertions.assertTrue(totalAttackPower > playerAttackPower);
        Assertions.assertEquals(40, totalAttackPower);
    }

    @Test
    void playerShouldAttackHigher_whenAttackWithShield() {

        Player testPlayer = PlayerCreatorUtil.createTestPlayerEquipmentWithSwordAndShield();
        int totalAttackPower = testPlayer.attack(testPlayer.getHandEquipment()[1]);

        Assertions.assertEquals(31, totalAttackPower);
    }

    @Test
    void playerShouldAttackHigher_whenAttackWithSwordAndWearRing() {

        Player testPlayer = PlayerCreatorUtil.createTestPlayerEquipmentWithSwordAndShieldAndRing();
        int totalAttackPower = testPlayer.attack(testPlayer.getHandEquipment()[0]);

        Assertions.assertEquals(41, totalAttackPower);
    }

    @Test
    void playerShouldAttack_whenNotEquipped() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerNoEquipment();
        int totalAttackPower = testPlayer.attack(testPlayer.getHandEquipment()[0]);

        Assertions.assertEquals(testPlayer.getAttackPower(), totalAttackPower);
    }
}
