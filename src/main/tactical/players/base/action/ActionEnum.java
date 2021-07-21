package tactical.players.base.action;

import lombok.Getter;
import tactical.exception.ActionNotFoundException;

import java.util.Arrays;
import java.util.stream.IntStream;

@Getter
public enum ActionEnum {
    MOVE("m"),
    ATTACK("a"),
    END("e");

    private final String key;

    ActionEnum(String key) {
        this.key = key;
    }

    public static ActionEnum[] fetchAllValues() {
        return ActionEnum.values();
    }

    public static ActionEnum[] fetchAttackValues() {
        return new ActionEnum[]{ATTACK, END};
    }

    public static ActionEnum findActionBy(String key) throws ActionNotFoundException {
        return Arrays.stream(ActionEnum.values())
                .filter(actionEnum -> actionEnum.getKey().equals(key))
                .findFirst().orElseThrow(()-> new ActionNotFoundException("Unknown Action '" + key + "'"));
    }

    public static String printAction(ActionEnum[] actionEnums) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, actionEnums.length).forEach(i -> {
            if (i == 0) {
                sb.append("[");
            }
            sb.append(actionEnums[i].name()).append("(").append(actionEnums[i].getKey()).append(")");
            if (actionEnums[i] != END) {
                sb.append(", ");
            }
        });
        sb.append("]");
        return sb.toString();
    }
}
