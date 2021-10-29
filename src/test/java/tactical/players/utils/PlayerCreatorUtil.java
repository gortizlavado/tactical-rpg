package tactical.players.utils;

import tactical.equipment.base.BaseBodyEquipment;
import tactical.equipment.base.BaseHandEquipment;
import tactical.models.Coordinate;
import tactical.players.base.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerCreatorUtil {

    public static Player createTestPlayerNoEquipment() {
        return createPlayerNoEquipment("John Doe", fetchInitialCoordinate());
    }

    public static Player createSecondTestPlayerNoEquipment() {
        return createPlayerNoEquipment("John Doe", new Coordinate(1,0));
    }

    private static Player createPlayerNoEquipment(String name, Coordinate coordinate) {
        Player player = new Player(false, name, 100, 30, 10, 5, coordinate);
        System.out.println(player.toString());
        return player;
    }

    public static Player createTestPlayerEquipmentWithSword() {
        Coordinate initialCoordinate = fetchInitialCoordinate();
        Player player = new Player(false, "John Doe", 100, 30, 10, 5, initialCoordinate);
        player.setEquipment(createTestHandEquipmentWithSword());
        System.out.println(player.toString());
        return player;
    }

    public static Player createTestPlayerEquipmentWithSwordAndShield() {
        Coordinate initialCoordinate = fetchInitialCoordinate();
        Player player = new Player(false, "John Doe", 100, 30, 10, 5, initialCoordinate);
        player.setEquipment(createTestHandEquipmentWithSwordAndShield());
        System.out.println(player.toString());
        return player;
    }

    public static Player createTestPlayerEquipmentWithSwordAndShieldAndRing() {
        Coordinate initialCoordinate = fetchInitialCoordinate();
        Player player = new Player(false, "John Doe", 100, 30, 10, 5, initialCoordinate);
        player.setEquipment(createTestHandEquipmentWithSwordAndShield());
        player.setEquipment(createTestHandEquipmentWithRing());
        System.out.println(player.toString());
        return player;
    }

    public static Player createTestPlayerEquipmentWithSwordAndShieldAndJacketAndRing() {
        Coordinate initialCoordinate = fetchInitialCoordinate();
        Player player = new Player(false, "John Doe", 100, 30, 10, 5, initialCoordinate);
        player.setEquipment(createTestHandEquipmentWithSwordAndShield());
        player.setEquipment(createTestBodyEquipmentWithJacketAndRing());
        System.out.println(player.toString());
        return player;
    }

    public static Coordinate fetchInitialCoordinate() {
        return new Coordinate(0,0);
    }

    public static Map<String, List<Player>> createListOfPCharactersWithRandomCoordinates() {
        Set<Coordinate> coordinateList = new HashSet<>();
        final List<Player> listOfPlayers = PlayerCreatorUtil.createListOfPlayersWithRandomCoordinates(coordinateList, 0, 20);
        final List<Player> listOfEnemies = PlayerCreatorUtil.createListOfEnemiesWithRandomCoordinates(coordinateList, 0, 20);
        return Map.of("players", listOfPlayers, "enemies", listOfEnemies);
    }

    private static List<Player> createListOfPlayersWithRandomCoordinates(Set<Coordinate> coordinateList, int min, int max) {
        return List.of(
                new Player(false, "Player 1", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)),
                new Player(false, "Player 2", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)),
                new Player(false, "Player 3", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)),
                new Player(false, "Player 4", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)),
                new Player(false, "Player 5", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)));
    }

    public static List<Player> createListOfEnemiesWithRandomCoordinates(Set<Coordinate> coordinateList, int min, int max) {
        return List.of(
                new Player(false, "Enemy 1", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)),
                new Player(false, "Enemy 2", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)),
                new Player(false, "Enemy 3", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)),
                new Player(false, "Enemy 4", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)),
                new Player(false, "Enemy 5", 100, 30, 10, 5, generateRandomCoordinate(coordinateList, min, max)));
    }

    public static List<Player> createListOfPlayersWithRandomCoordinates(int min, int max) {
        return List.of(
                new Player(false, "Player 1", 100, 30, 10, 5, generateRandomCoordinate(min, max)),
                new Player(false, "Player 2", 100, 30, 10, 5, generateRandomCoordinate(min, max)),
                new Player(false, "Player 3", 100, 30, 10, 5, generateRandomCoordinate(min, max)),
                new Player(false, "Player 4", 100, 30, 10, 5, generateRandomCoordinate(min, max)),
                new Player(false, "Player 5", 100, 30, 10, 5, generateRandomCoordinate(min, max)));
    }

    public static List<Player> createListOfEnemiesWithRandomCoordinates(int min, int max) {
        return List.of(
                new Player(false, "Enemy 1", 100, 30, 10, 5, generateRandomCoordinate(min, max)),
                new Player(false, "Enemy 2", 100, 30, 10, 5, generateRandomCoordinate(min, max)),
                new Player(false, "Enemy 3", 100, 30, 10, 5, generateRandomCoordinate(min, max)),
                new Player(false, "Enemy 4", 100, 30, 10, 5, generateRandomCoordinate(min, max)),
                new Player(false, "Enemy 5", 100, 30, 10, 5, generateRandomCoordinate(min, max)));
    }

    private static BaseHandEquipment[] createTestHandEquipmentWithSwordAndShield() {
        BaseHandEquipment[] equipments = new BaseHandEquipment[2];
        BaseHandEquipment sword = createTestHandEquipmentWithSword();
        BaseHandEquipment shield = new BaseHandEquipment("Shield", 1, 5, 1);
        equipments[0] = sword;
        equipments[1] = shield;
        return equipments;
    }

    private static BaseBodyEquipment[] createTestBodyEquipmentWithJacketAndRing() {
        BaseBodyEquipment[] equipments = new BaseBodyEquipment[2];
        BaseBodyEquipment ring = createTestHandEquipmentWithRing();
        BaseBodyEquipment jacket = new BaseBodyEquipment("Jacket", 0, 2);
        equipments[0] = ring;
        equipments[1] = jacket;
        return equipments;
    }

    private static BaseBodyEquipment createTestHandEquipmentWithRing() {
        return new BaseBodyEquipment("Ring", 1, 0);
    }

    public static BaseHandEquipment createTestHandEquipmentWithSword() {
        return new BaseHandEquipment("Sword", 10, 0, 1);
    }

    private static Coordinate generateRandomCoordinate(Set<Coordinate> coordinateList, int min, int max) {
        final int initialSize = coordinateList.size();
        int endSize = initialSize;

        Coordinate coordinate = null;
        while (initialSize == endSize) {
            coordinate = generateRandomCoordinate(min, max);
            coordinateList.add(coordinate);
            endSize = coordinateList.size();
        }
        return coordinate;
    }

    private static Coordinate generateRandomCoordinate(int min, int max) {
        int randomX = ThreadLocalRandom.current().nextInt(min, max);
        int randomY = ThreadLocalRandom.current().nextInt(min, max);
        return new Coordinate(randomX, randomY);
    }
}
