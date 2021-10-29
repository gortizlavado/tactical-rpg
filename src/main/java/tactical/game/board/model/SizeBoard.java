package tactical.game.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SizeBoard {

    private int length;
    private int height;

    @Override
    public String toString() {
        return "length(x)=" + length + ", " + "height(y)=" + height;
    }
}
