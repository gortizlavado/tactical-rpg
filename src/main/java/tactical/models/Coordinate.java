package tactical.models;

import lombok.Getter;
import lombok.Setter;

import java.util.stream.IntStream;

@Setter
@Getter
public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int diff(Coordinate anotherCoordinate) {
        int x = Math.abs(this.x - anotherCoordinate.getX());
        int y = Math.abs(this.y - anotherCoordinate.getY());

        return x + y;
    }

    public static String printCoordinate(Coordinate... coordinates) {
        StringBuilder sb = new StringBuilder();
        final int lastCoordinate = coordinates.length - 1;
        IntStream.range(0, coordinates.length).forEach(i -> {
            if (i == 0) {
                sb.append("[");
            }
            sb.append(coordinates[i].toString());
            if (i != lastCoordinate) {
                sb.append(", ");
            }
        });
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
