package tactical.game.enemy;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tactical.players.base.Player;

import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class EnemiesProviderTest {

    @InjectMocks
    private EnemiesProvider enemiesProvider;

    @ParameterizedTest
    @MethodSource("createEnemiesArguments")
    void shouldGenerateListOfEnemies(int level, int health, int attack, int defense) {
        final List<Player> enemies = enemiesProvider.createEnemiesStatsBy(5, level);

        for (Player enemy: enemies) {
            MatcherAssert.assertThat(enemy.getName(), CoreMatchers.containsString("Enemy"));
            Assertions.assertEquals(level, enemy.getLevel());
            Assertions.assertEquals(health, enemy.getHealth());
            Assertions.assertEquals(attack, enemy.getAttackPower());
            Assertions.assertEquals(defense, enemy.getDefensePower());
            Assertions.assertEquals(5, enemy.getMove());
        }
    }

    private static Stream<Arguments> createEnemiesArguments() {
        return Stream.of(
                Arguments.of(1, 135, 35, 12),
                Arguments.of(2, 182, 40, 13),
                Arguments.of(3, 246, 47, 16),
                Arguments.of(4, 332, 55, 18),
                Arguments.of(5, 448, 64, 21),
                Arguments.of(6, 605, 74, 25),
                Arguments.of(7, 817, 86, 29),
                Arguments.of(8, 1102, 100, 33),
                Arguments.of(9, 1488, 116, 39),
                Arguments.of(10, 2009, 134, 45));
    }

}