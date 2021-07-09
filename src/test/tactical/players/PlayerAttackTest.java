package tactical.players;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tactical.players.base.Player;
import tactical.players.utils.PlayerCreatorUtil;

public class PlayerAttackTest {

    @Test
    void playerShouldAttackHigher_whenFullEquipped() {

        Player testPlayer = PlayerCreatorUtil.createTestPlayerEquipmentWithSwordAndShield();
        int playerAttackPower = testPlayer.getAttackPower();
        int totalAttackPower = testPlayer.attack();

        Assertions.assertTrue(totalAttackPower > playerAttackPower);
        Assertions.assertEquals(41, totalAttackPower);
    }

    @Test
    void playerShouldAttackHigher_whenEquipped() {

        Player testPlayer = PlayerCreatorUtil.createTestPlayerEquipmentWithSword();
        int totalAttackPower = testPlayer.attack();

        Assertions.assertEquals(40, totalAttackPower);
    }

    @Test
    void playerShouldAttack_whenNotEquipped() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerNoEquipment();
        int totalAttackPower = testPlayer.attack();

        Assertions.assertEquals(testPlayer.getAttackPower(), totalAttackPower);
    }
}
