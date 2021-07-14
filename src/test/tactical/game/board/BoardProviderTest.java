package tactical.game.board;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tactical.exception.BoardCreationException;
import tactical.models.BoardGame;
import tactical.models.Coordinate;
import tactical.models.SizeBoard;
import tactical.players.base.Player;
import tactical.players.utils.PlayerCreatorUtil;

import java.util.ArrayList;
import java.util.List;

class BoardProviderTest {

    BoardProvider boardProvider = new BoardProvider();

    @Test
    void shouldCreateBoardGame() {
        final List<Player> listOfPlayers = PlayerCreatorUtil.createListOfPlayersWithRandomCoordinates(0, 20);
        final List<Player> listOfEnemies = PlayerCreatorUtil.createListOfEnemiesWithRandomCoordinates(0, 20);
        final BoardGame boardTest = boardProvider.createBoardGameBy(
                "boardTest", 
                new SizeBoard(20, 20),
                listOfPlayers,
                listOfEnemies);

        Player[][] board = boardTest.getBoard();
        for (Player player : listOfPlayers) {
            assertCoordinate(player, board);
        }

        for (Player enemy : listOfEnemies) {
            assertCoordinate(enemy, board);
        }
    }

    @Test
    void shouldNotCreateBoardGame_whenNotHaveName() {
        final List<Player> listOfPlayers = PlayerCreatorUtil.createListOfPlayersWithRandomCoordinates(0, 20);
        final List<Player> listOfEnemies = PlayerCreatorUtil.createListOfEnemiesWithRandomCoordinates(0, 20);


        BoardCreationException exception = Assertions.assertThrows(BoardCreationException.class, () -> boardProvider.createBoardGameBy(
                "",
                new SizeBoard(20, 20),
                listOfPlayers,
                listOfEnemies));

        Assert.assertThat(exception.getMessage(), CoreMatchers.containsString("it has to have a name"));
    }

    @Test
    void shouldNotCreateBoardGame_whenIntroduceIncorrectSize() {
        final List<Player> listOfPlayers = PlayerCreatorUtil.createListOfPlayersWithRandomCoordinates(0, 20);
        final List<Player> listOfEnemies = PlayerCreatorUtil.createListOfEnemiesWithRandomCoordinates(0, 20);


        BoardCreationException exception = Assertions.assertThrows(BoardCreationException.class, () -> boardProvider.createBoardGameBy(
                "boardTest",
                new SizeBoard(0, 20),
                listOfPlayers,
                listOfEnemies));

        Assert.assertThat(exception.getMessage(), CoreMatchers.containsString("height=0, length=20"));
    }

    @Test
    void shouldNotCreateBoardGame_whenIntroduceIncorrectListOfPlayers() {
        final List<Player> listOfEnemies = PlayerCreatorUtil.createListOfEnemiesWithRandomCoordinates(0, 20);

        BoardCreationException exception = Assertions.assertThrows(BoardCreationException.class, () -> boardProvider.createBoardGameBy(
                "boardTest",
                new SizeBoard(20, 20),
                new ArrayList<>(),
                listOfEnemies));

        Assert.assertThat(exception.getMessage(), CoreMatchers.containsString("numbers of players: 0"));
    }

    @Test
    void shouldNotCreateBoardGame_whenIntroduceIncorrectListOfEnemies() {
        final List<Player> listOfPlayers = PlayerCreatorUtil.createListOfPlayersWithRandomCoordinates(0, 20);

        BoardCreationException exception = Assertions.assertThrows(BoardCreationException.class, () -> boardProvider.createBoardGameBy(
                "boardTest",
                new SizeBoard(20, 20),
                listOfPlayers,
                new ArrayList<>()));

        Assert.assertThat(exception.getMessage(), CoreMatchers.containsString("numbers of enemies: 0"));
    }

    private void assertCoordinate(Player player, Player[][] board) {
        Coordinate coordinate = player.getCoordinate();
        Player playerSituated = board[coordinate.getX()][coordinate.getY()];
        Assertions.assertEquals(playerSituated, player);
    }

}