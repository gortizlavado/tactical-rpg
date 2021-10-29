package tactical.game.board.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import tactical.players.base.Player;

@Data
@RequiredArgsConstructor
public class BoardGame {

    private final String name;
    private final Player[][] field;
}
