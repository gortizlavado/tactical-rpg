package tactical.game.states;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tactical.game.board.model.SizeBoard;
import tactical.game.context.GameContext;
import tactical.game.states.impl.NewTurn;
import tactical.game.states.impl.PlayerTurn;
import tactical.models.Coordinate;
import tactical.players.base.Player;
import tactical.players.base.action.ActionEnum;
import tactical.players.base.character.BaseCharacter;
import tactical.players.utils.PlayerCreatorUtil;

import java.util.List;

class GameStateTest {

    private GameContext context;
    private GameState state;
    private Player playerTest;

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
        state.execute(context, null, null, null, null);
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
    void shouldPlayerMove_whenNewTurn() {
        playerTest.newTurn();
        state = new PlayerTurn();
        Assertions.assertTrue(state.apply(context));
        state.execute(context, playerTest, ActionEnum.MOVE, new Coordinate(0, 5), null);
        Assertions.assertFalse(playerTest.isMoveTurn());
        Assertions.assertFalse(playerTest.isFinishedTurn());
    }

    @Test
    void shouldPlayerNotMoveTwice_whenNewTurn() {
        playerTest.newTurn();
        state = new PlayerTurn();
        Assertions.assertTrue(state.apply(context));
        state.execute(context, playerTest, ActionEnum.MOVE, new Coordinate(0, 5), null);
        Assertions.assertFalse(playerTest.isMoveTurn());
        Assertions.assertFalse(playerTest.isFinishedTurn());
        state.execute(context, playerTest, ActionEnum.MOVE, new Coordinate(0, 6), null);
        Assertions.assertEquals(new Coordinate(0, 5), playerTest.getCoordinate());
    }

    @Test
    void shouldPlayerMoveAndAttack_whenNewTurn() {
        playerTest.newTurn();
        state = new PlayerTurn();
        Assertions.assertTrue(state.apply(context));
        state.execute(context, playerTest, ActionEnum.MOVE, new Coordinate(0, 5), null);
        state.execute(context, playerTest, ActionEnum.ATTACK, new Coordinate(0, 6), playerTest.getHandEquipment()[0]);
        Assertions.assertTrue(playerTest.isFinishedTurn());
    }

    @Test
    void shouldPlayerMoveAndAttackAndHeartEnemy_whenNewTurn() {
        playerTest.newTurn();
        final Coordinate attackCoordinate = new Coordinate(0, 6);
        final List<Player> enemies = context.getEnemies();
        Player enemyTest = enemies.get(0);
        int initialHealth = enemyTest.getHealth();
        enemyTest.setCoordinate(attackCoordinate);

        state = new PlayerTurn();
        Assertions.assertTrue(state.apply(context));
        state.execute(context, playerTest, ActionEnum.MOVE, new Coordinate(0, 5), null);
        state.execute(context, playerTest, ActionEnum.ATTACK, attackCoordinate, playerTest.getHandEquipment()[0]);
        Assertions.assertTrue(initialHealth > enemyTest.getHealth());
        Assertions.assertTrue(playerTest.isFinishedTurn());
    }

    @Test
    void shouldPlayerMoveAndAttackAndNotHeartAnyOne_whenNewTurn() {
        playerTest.newTurn();
        final Coordinate attackCoordinate = new Coordinate(1, 5);
        final List<Player> enemies = context.getEnemies();
        Player enemyTest = enemies.get(0);
        int initialHealth = enemyTest.getHealth();
        enemyTest.setCoordinate(attackCoordinate);

        state = new PlayerTurn();
        Assertions.assertTrue(state.apply(context));
        state.execute(context, playerTest, ActionEnum.MOVE, new Coordinate(0, 5), null);
        state.execute(context, playerTest, ActionEnum.ATTACK, attackCoordinate, playerTest.getHandEquipment()[0]);
        Assertions.assertEquals(initialHealth, enemyTest.getHealth());
        Assertions.assertTrue(playerTest.isFinishedTurn());
    }

}