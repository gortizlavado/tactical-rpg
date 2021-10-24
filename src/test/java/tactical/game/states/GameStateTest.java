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
import tactical.game.states.impl.NewTurn;
import tactical.game.states.impl.PlayerTurn;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;
import tactical.players.base.character.BaseCharacter;
import tactical.players.utils.PlayerCreatorUtil;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class GameStateTest {

    private GameContext context;
    private GameState state;
    private Player playerTest;

    @InjectMocks
    private PlayerTurn playerTurn;

    @Mock
    private InputService inputService;

    @BeforeEach
    void setUp() {
        playerTest = PlayerCreatorUtil.createTestPlayerEquipmentWithSword();
        context = new GameContext(List.of(playerTest));
        context.initiateGameContext("Game Test", new SizeBoard(10, 7), 3, 2);
    }

    @Test
    void shouldNewTurnApply_whenInitiateGame() {
        state = new NewTurn();
        Assertions.assertTrue(state.apply(context));
        Assertions.assertTrue(context.getPlayers().stream().allMatch(Player::isFinishedTurn));
        Assertions.assertTrue(context.getPlayers().stream().noneMatch(BaseCharacter::isMoveTurn));
        Assertions.assertTrue(context.getEnemies().stream().allMatch(Player::isFinishedTurn));
        Assertions.assertTrue(context.getEnemies().stream().noneMatch(BaseCharacter::isMoveTurn));
        state.execute(context);
        Assertions.assertEquals(1, context.getTurnNumber());
        Assertions.assertTrue(context.getPlayers().stream().noneMatch(BaseCharacter::isFinishedTurn));
        Assertions.assertTrue(context.getPlayers().stream().allMatch(Player::isMoveTurn));
        Assertions.assertTrue(context.getEnemies().stream().noneMatch(BaseCharacter::isFinishedTurn));
        Assertions.assertTrue(context.getEnemies().stream().allMatch(Player::isMoveTurn));
    }

    @Test
    void shouldNewTurnNotApply_whenPlayerStillMove() {
        playerTest.newTurn();
        state = new NewTurn();
        Assertions.assertFalse(state.apply(context));
    }

    @Test
    void shouldPlayerMoveAndFinishTurn_whenNewTurn() {
        Mockito.when(inputService.askForAction(any(Player.class))).thenAnswer(new Answer<>() {
            private int count = 0;
            public Object answer(InvocationOnMock invocation) {
                if (count++ == 1) {
                    return ActionEnum.END;
                }
                return ActionEnum.MOVE;
            }
        });
        Mockito.when(inputService.askForCoordinate(eq(context.getBoard().getBoard()))).thenReturn(new Coordinate(0, 5));
        playerTest.newTurn();

        Assertions.assertTrue(playerTurn.apply(context));
        context.setPlayerChoose(playerTest);
        playerTurn.execute(context);
        final Player[][] board = context.getBoard().getBoard();
        Assertions.assertNull(board[0][0]);
        Assertions.assertEquals(playerTest.getName(), board[0][5].getName());
    }

    @Test
    void shouldPlayerMoveAndAttack_whenNewTurn() {
        Mockito.when(inputService.askForAction(any(Player.class))).thenAnswer(new Answer<>() {
            private int count = 0;
            public Object answer(InvocationOnMock invocation) {
                if (count++ == 1) {
                    return ActionEnum.ATTACK;
                }
                return ActionEnum.MOVE;
            }
        });
        Mockito.when(inputService.askForCoordinate(any())).thenAnswer(new Answer<>() {
            private int count = 0;
            public Object answer(InvocationOnMock invocation) {
                if (count++ == 1) {
                    return new Coordinate(0, 6);
                }
                return new Coordinate(0, 5);
            }
        });
        Mockito.when(inputService.askForHandEquipment(eq(playerTest))).thenReturn(playerTest.getHandEquipment()[0]);
        playerTest.newTurn();
        final List<Player> enemies = context.getEnemies();
        Player enemyTest = enemies.get(0);
        int initialHealth = enemyTest.getHealth();

        Assertions.assertTrue(playerTurn.apply(context));
        context.setPlayerChoose(playerTest);
        playerTurn.execute(context);
        Assertions.assertTrue(playerTest.isFinishedTurn());
        Assertions.assertEquals(initialHealth, enemyTest.getHealth());
    }

    @Test
    void shouldPlayerMoveAndAttackAndNotHeartAnyOne_whenNewTurn() {
        Mockito.when(inputService.askForAction(any(Player.class))).thenAnswer(new Answer<>() {
            private int count = 0;
            public Object answer(InvocationOnMock invocation) {
                if (count++ == 1) {
                    return ActionEnum.ATTACK;
                }
                return ActionEnum.MOVE;
            }
        });
        Mockito.when(inputService.askForCoordinate(any())).thenAnswer(new Answer<>() {
            private int count = 0;
            public Object answer(InvocationOnMock invocation) {
                if (count++ == 1) {
                    return new Coordinate(0, 6);
                }
                return new Coordinate(0, 5);
            }
        });
        Mockito.when(inputService.askForHandEquipment(eq(playerTest))).thenReturn(playerTest.getHandEquipment()[0]);
        playerTest.newTurn();
        final Coordinate attackCoordinate = new Coordinate(0, 6);
        final List<Player> enemies = context.getEnemies();
        final Player[][] board = context.getBoard().getBoard();
        Player enemyTest = enemies.get(0);
        board[enemyTest.getCoordinate().getX()][enemyTest.getCoordinate().getY()] = null;
        int initialHealth = enemyTest.getHealth();
        enemyTest.setCoordinate(attackCoordinate);
        board[attackCoordinate.getX()][attackCoordinate.getY()] = enemyTest;

        Assertions.assertTrue(playerTurn.apply(context));
        context.setPlayerChoose(playerTest);
        playerTurn.execute(context);
        Assertions.assertTrue(initialHealth > enemyTest.getHealth());
        Assertions.assertTrue(playerTest.isFinishedTurn());
    }

    @Test
    void shouldPlayerAttackAndKill_whenNewTurn() {
        Mockito.when(inputService.askForAction(any(Player.class))).thenAnswer(new Answer<>() {
            private int count = 0;
            public Object answer(InvocationOnMock invocation) {
                if (count++ == 1) {
                    return ActionEnum.ATTACK;
                }
                return ActionEnum.MOVE;
            }
        });
        Mockito.when(inputService.askForCoordinate(any())).thenAnswer(new Answer<>() {
            private int count = 0;
            public Object answer(InvocationOnMock invocation) {
                if (count++ == 1) {
                    return new Coordinate(0, 4);
                }
                return new Coordinate(0, 3);
            }
        });
        Mockito.when(inputService.askForHandEquipment(eq(playerTest))).thenReturn(playerTest.getHandEquipment()[0]);
        GameContext context = new GameContext(List.of(playerTest));
        context.initiateGameContext("Game Test Kill", new SizeBoard(5, 5), 1, 1);
        playerTest.newTurn();
        final Coordinate attackCoordinate = new Coordinate(0, 4);
        final List<Player> enemies = context.getEnemies();
        final Player[][] board = context.getBoard().getBoard();
        Player enemyTest = enemies.get(0);
        board[enemyTest.getCoordinate().getX()][enemyTest.getCoordinate().getY()] = null;
        enemyTest.setCoordinate(attackCoordinate);
        enemyTest.setHealth(10);
        board[attackCoordinate.getX()][attackCoordinate.getY()] = enemyTest;


        Assertions.assertTrue(playerTurn.apply(context));
        context.setPlayerChoose(playerTest);
        playerTurn.execute(context);
        Assertions.assertTrue(playerTest.isFinishedTurn());
        Assertions.assertEquals(0, context.getEnemies().size());
    }

}