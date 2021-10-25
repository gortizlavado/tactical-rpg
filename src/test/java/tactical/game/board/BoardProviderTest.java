package tactical.game.board;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tactical.exception.BoardCreationException;
import tactical.game.board.model.BoardGame;
import tactical.game.board.model.SizeBoard;
import tactical.game.context.GameContext;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.utils.PlayerCreatorUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class BoardProviderTest {

    @InjectMocks
    BoardProvider boardProvider;

    @Test
    void shouldCreateBoardGame() {
        final Map<String, List<Player>> lisOfCharactersMap = PlayerCreatorUtil.createListOfPCharactersWithRandomCoordinates();
        List<Player> listOfPlayers = lisOfCharactersMap.get(GameContext.PLAYER_KEY);
        List<Player> listOfEnemies = lisOfCharactersMap.get(GameContext.ENEMY_KEY);
        final BoardGame boardTest = boardProvider.createBoardGameBy(
                "boardTest", 
                new SizeBoard(20, 20),
                lisOfCharactersMap);

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
        final Map<String, List<Player>> lisOfCharactersMap = PlayerCreatorUtil.createListOfPCharactersWithRandomCoordinates();

        BoardCreationException exception =
                Assertions.assertThrows(BoardCreationException.class, () ->
                        boardProvider.createBoardGameBy(
                                "",
                                new SizeBoard(20, 20),
                                lisOfCharactersMap));

        MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.containsString("it has to have a name"));
    }

    @Test
    void shouldNotCreateBoardGame_whenIntroduceIncorrectSize() {
        final Map<String, List<Player>> lisOfCharactersMap = PlayerCreatorUtil.createListOfPCharactersWithRandomCoordinates();

        BoardCreationException exception = Assertions.assertThrows(BoardCreationException.class, () -> boardProvider.createBoardGameBy(
                "boardTest",
                new SizeBoard(0, 20),
                lisOfCharactersMap));

        MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.containsString("height=0, length=20"));
    }

    @Test
    void shouldNotCreateBoardGame_whenIntroduceIncorrectListOfPlayers() {
        final Map<String, List<Player>> lisOfCharactersMap = new HashMap<>();
        lisOfCharactersMap.put(GameContext.PLAYER_KEY, new ArrayList<>());
        lisOfCharactersMap.put(GameContext.ENEMY_KEY, PlayerCreatorUtil.createListOfEnemiesWithRandomCoordinates(0, 20));


        BoardCreationException exception = Assertions.assertThrows(BoardCreationException.class, () -> boardProvider.createBoardGameBy(
                "boardTest",
                new SizeBoard(20, 20),
                lisOfCharactersMap));

        MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.containsString("it has to have any players"));
    }

    @Test
    void shouldNotCreateBoardGame_whenIntroduceIncorrectListOfEnemies() {
        final Map<String, List<Player>> lisOfCharactersMap = new HashMap<>();
        lisOfCharactersMap.put(GameContext.ENEMY_KEY, new ArrayList<>());
        lisOfCharactersMap.put(GameContext.PLAYER_KEY, PlayerCreatorUtil.createListOfEnemiesWithRandomCoordinates(0, 20));

        BoardCreationException exception = Assertions.assertThrows(BoardCreationException.class, () -> boardProvider.createBoardGameBy(
                "boardTest",
                new SizeBoard(20, 20),
                lisOfCharactersMap));

        MatcherAssert.assertThat(exception.getMessage(), CoreMatchers.containsString("it has to have any enemies"));
    }

    private void assertCoordinate(Player player, Player[][] board) {
        Coordinate coordinate = player.getCoordinate();
        Player playerSituated = board[coordinate.getX()][coordinate.getY()];
        Assertions.assertEquals(playerSituated, player);
    }

}