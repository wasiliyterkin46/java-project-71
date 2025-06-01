package hexlet.code;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class Dif {
    private DifOperation operation;
    private String key;
    private Object value;
}
