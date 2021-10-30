package tactical.game.states;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import tactical.game.board.model.SizeBoard;
import tactical.game.context.GameContext;
import tactical.game.services.InputService;
import tactical.game.states.impl.EndGame;
import tactical.game.states.impl.NewTurn;
import tactical.game.states.impl.PlayerTurn;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;
import tactical.players.base.character.BaseCharacter;
import tactical.players.utils.PlayerCreatorUtil;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class GameStateTest {

    private GameContext context;
    private Player playerTest;

    @InjectMocks
    private PlayerTurn playerTurn;

    @InjectMocks
    private NewTurn newTurn;

    @InjectMocks
    private EndGame endGame;

    @Mock
    private InputService inputService;

    @BeforeEach
    void setUp() {
        playerTest = PlayerCreatorUtil.createTestPlayerEquipmentWithSword();
        createGameContext(new SizeBoard(10, 7), 3, 2);
    }

    @Test
    void shouldNewTurnApply_whenInitiateGame() {
        Assertions.assertTrue(newTurn.apply(context));
        Assertions.assertTrue(context.getCharactersMap().get(GameContext.PLAYER_KEY).stream().allMatch(Player::isFinishedTurn));
        Assertions.assertTrue(context.getCharactersMap().get(GameContext.PLAYER_KEY).stream().noneMatch(BaseCharacter::isMoveTurn));
        Assertions.assertTrue(context.getCharactersMap().get(GameContext.ENEMY_KEY).stream().allMatch(Player::isFinishedTurn));
        Assertions.assertTrue(context.getCharactersMap().get(GameContext.ENEMY_KEY).stream().noneMatch(BaseCharacter::isMoveTurn));
        newTurn.execute(context);
        Assertions.assertEquals(1, context.getTurnNumber());
        Assertions.assertTrue(context.getCharactersMap().get(GameContext.PLAYER_KEY).stream().noneMatch(BaseCharacter::isFinishedTurn));
        Assertions.assertTrue(context.getCharactersMap().get(GameContext.PLAYER_KEY).stream().allMatch(Player::isMoveTurn));
        Assertions.assertTrue(context.getCharactersMap().get(GameContext.ENEMY_KEY).stream().noneMatch(BaseCharacter::isFinishedTurn));
        Assertions.assertTrue(context.getCharactersMap().get(GameContext.ENEMY_KEY).stream().allMatch(Player::isMoveTurn));
    }

    @Test
    void shouldNewTurnNotApply_whenPlayerStillCanMove() {
        playerTest.newTurn();
        newTurn = new NewTurn();
        Assertions.assertFalse(newTurn.apply(context));
    }

    @Test
    void shouldEndGameApply_whenNoPlayer() {
        Assertions.assertFalse(endGame.apply(context));
        context.getCharactersMap().get(GameContext.PLAYER_KEY).remove(playerTest);
        Assertions.assertTrue(endGame.apply(context));
        endGame.execute(context);
        Assertions.assertTrue(context.isEndGame());
    }

    @Test
    void shouldPlayerMoveAndFinishTurn_whenNewTurn() {
        mockTypeAction(ActionEnum.MOVE, ActionEnum.END);
        Mockito.when(inputService.askForCoordinateToMove(eq(context.getBoard().getField()))).thenReturn(new Coordinate(0, 5));
        playerTest.newTurn();

        Assertions.assertTrue(playerTurn.apply(context));
        context.setPlayerChoose(playerTest);
        playerTurn.execute(context);
        final Player[][] board = context.getBoard().getField();
        Assertions.assertNull(board[0][0]);
        Assertions.assertNotNull(board[5][0]);
        Assertions.assertEquals(playerTest.getName(), board[5][0].getName());
    }

    @Test
    void shouldPlayerMoveAndAttackAndNotHeartAnyOne_whenNewTurn() {
        mockTypeAction(ActionEnum.MOVE, ActionEnum.ATTACK);
        mockMoveAndAttackActionsAndAlsoChooseHandEquipment(new Coordinate(0, 5), new Coordinate(0, 6));
        playerTest.newTurn();
        final List<Player> enemies = context.getCharactersMap().get(GameContext.ENEMY_KEY);
        Player enemyTest = enemies.get(0);
        int initialHealth = enemyTest.getHealth();

        Assertions.assertTrue(playerTurn.apply(context));
        context.setPlayerChoose(playerTest);
        playerTurn.execute(context);
        Assertions.assertTrue(playerTest.isFinishedTurn());
        Assertions.assertEquals(initialHealth, enemyTest.getHealth());
    }

    @Test
    void shouldPlayerMoveAndAttack_whenNewTurn() {
        final Coordinate attackCoordinate = new Coordinate(0, 6);
        mockTypeAction(ActionEnum.MOVE, ActionEnum.ATTACK);
        mockMoveAndAttackActionsAndAlsoChooseHandEquipment(new Coordinate(0, 5), attackCoordinate);
        playerTest.newTurn();
        final Player enemyTest = fetchOneEnemyAndPlaceInto(attackCoordinate);
        int initialHealth = enemyTest.getHealth();

        Assertions.assertTrue(playerTurn.apply(context));
        context.setPlayerChoose(playerTest);
        playerTurn.execute(context);
        Assertions.assertTrue(initialHealth > enemyTest.getHealth());
        Assertions.assertTrue(playerTest.isFinishedTurn());
    }

    @Test
    void shouldPlayerAttackAndKill_whenNewTurn() {
        final Coordinate attackCoordinate = new Coordinate(0, 4);
        mockTypeAction(ActionEnum.MOVE, ActionEnum.ATTACK);
        mockMoveAndAttackActionsAndAlsoChooseHandEquipment(new Coordinate(0, 3), attackCoordinate);
        createGameContext(new SizeBoard(5, 5), 1, 1);
        final Player enemyTest = fetchOneEnemyAndPlaceInto(attackCoordinate);
        enemyTest.setHealth(10);
        playerTest.newTurn();

        Assertions.assertTrue(playerTurn.apply(context));
        context.setPlayerChoose(playerTest);
        playerTurn.execute(context);
        Assertions.assertTrue(playerTest.isFinishedTurn());
        Assertions.assertEquals(0, context.getCharactersMap().get(GameContext.ENEMY_KEY).size());
    }

    @Test
    void shouldRequestConfidenceCoordinate_whenPlayerAskForPossibleCoordinateForAttack() {
        createGameContext(new SizeBoard(5, 5), 1, 1);
        context.setPlayerChoose(playerTest);
        final List<Coordinate> coordinateList = context.fetchPossibleCoordinateForAttack(1);
        Assertions.assertEquals(2, coordinateList.size());
        Assertions.assertTrue(coordinateList.containsAll(List.of(new Coordinate(1, 0), new Coordinate(0, 1))));
        playerTest.setCoordinate(new Coordinate(0, 1));
        final List<Coordinate> coordinateList2 = context.fetchPossibleCoordinateForAttack(1);
        Assertions.assertEquals(3, coordinateList2.size());
        Assertions.assertTrue(coordinateList2.containsAll(
                List.of(new Coordinate(0, 2), new Coordinate(1, 1), new Coordinate(0, 0))));
        playerTest.setCoordinate(new Coordinate(0, 4));
        final List<Coordinate> coordinateList3 = context.fetchPossibleCoordinateForAttack(1);
        Assertions.assertEquals(2, coordinateList3.size());
        Assertions.assertTrue(coordinateList3.containsAll(List.of(new Coordinate(1, 4), new Coordinate(0, 3))));
        playerTest.setCoordinate(new Coordinate(2, 2));
        final List<Coordinate> coordinateList4 = context.fetchPossibleCoordinateForAttack(1);
        Assertions.assertEquals(4, coordinateList4.size());
        Assertions.assertTrue(coordinateList4.containsAll(List.of(
                new Coordinate(3, 2), new Coordinate(2, 3), new Coordinate(1, 2), new Coordinate(2, 1))));
        final List<Coordinate> coordinateList5 = context.fetchPossibleCoordinateForAttack(2);
        Assertions.assertEquals(8, coordinateList5.size());
        Assertions.assertTrue(coordinateList5.containsAll(List.of(
                new Coordinate(3, 2), new Coordinate(2, 3), new Coordinate(1, 2), new Coordinate(2, 1),
                new Coordinate(4, 2), new Coordinate(2, 4), new Coordinate(0, 2), new Coordinate(2, 0))));
    }

    private void createGameContext(SizeBoard sizeBoard, int enemiesNumber, int enemiesLevel) {
        List<Player> playerList = new ArrayList<>();
        playerList.add(playerTest);
        GameContext context = new GameContext(playerList);
        context.initiateGameContext("Game Test", sizeBoard, enemiesNumber, enemiesLevel);
        this.context = context;
    }

    private Player fetchOneEnemyAndPlaceInto(Coordinate coordinate) {
        final Player enemyTest = context.getCharactersMap().get(GameContext.ENEMY_KEY).get(0);
        resetCoordinateInBoard(enemyTest, coordinate);
        return enemyTest;
    }

    private void resetCoordinateInBoard(Player player, Coordinate coordinate) {
        final Player[][] board = context.getBoard().getField();
        board[player.getCoordinate().getY()][player.getCoordinate().getX()] = null;
        player.setCoordinate(coordinate);
        board[coordinate.getY()][coordinate.getX()] = player;
    }

    private void mockTypeAction(ActionEnum... actionEnums) {
        Mockito.when(inputService.askForAction(any(Player.class))).thenAnswer(new Answer<>() {
            private int count = 0;
            public Object answer(InvocationOnMock invocation) {
                if (count++ == 1) {
                    return actionEnums[1];
                }
                return actionEnums[0];
            }
        });
    }

    private void mockMoveAndAttackActionsAndAlsoChooseHandEquipment(Coordinate... coordinates) {
        Mockito.when(inputService.askForCoordinateToMove(any())).thenReturn(coordinates[0]);
        Mockito.when(inputService.askForCoordinateToAttack(any())).thenReturn(coordinates[1]);
        Mockito.when(inputService.askForHandEquipment(eq(playerTest))).thenReturn(playerTest.getHandEquipment()[0]);
    }
}