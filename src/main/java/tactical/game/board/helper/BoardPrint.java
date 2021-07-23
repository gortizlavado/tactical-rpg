package tactical.game.board.helper;

import tactical.players.base.Player;

final public class BoardPrint {

    private BoardPrint() {
    }

    private static void printRow(Player[] row) {
        for (Player player : row) {
            System.out.print((null == player) ? "_" : player.getName().substring(0, 1));
            System.out.print("\t");
        }
        System.out.println();
    }

    public static void print(Player[][] board) {
        System.out.println();
        for (int i = board.length - 1; i>=0; i--) {
            printRow(board[i]);
        }
        System.out.println();
    }
}
