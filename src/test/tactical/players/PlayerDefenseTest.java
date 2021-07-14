package tactical.players;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tactical.players.base.Player;
import tactical.players.utils.PlayerCreatorUtil;

public class PlayerDefenseTest {

    @Test
    void playerShouldDefenseHigher_whenEquippedWithOneShield() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerEquipmentWithSwordAndShield();
        int playerDefensePower = testPlayer.getDefensePower();
        int totalDefensePower = testPlayer.defense();

        Assertions.assertTrue(totalDefensePower > playerDefensePower);
        Assertions.assertEquals(15, totalDefensePower);
    }

    @Test
    void playerShouldDefenseHigher_whenEquippedWithOneShieldAndJacket() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerEquipmentWithSwordAndShieldAndJacketAndRing();
        int playerDefensePower = testPlayer.getDefensePower();
        int totalDefensePower = testPlayer.defense();

        Assertions.assertTrue(totalDefensePower > playerDefensePower);
        Assertions.assertEquals(17, totalDefensePower);
    }

    @Test
    void playerShouldDefense_whenNotEquipped() {
        Player testPlayer = PlayerCreatorUtil.createTestPlayerNoEquipment();
        int playerDefensePower = testPlayer.getDefensePower();
        int totalDefensePower = testPlayer.defense();

        Assertions.assertEquals(playerDefensePower, totalDefensePower);
    }
}
