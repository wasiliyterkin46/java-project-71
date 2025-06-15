package hexlet.code;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class Dif {
    private final DifOperation operation;
    private final String key;
    private final Object oldValue;
    private final Object newValue;
}
