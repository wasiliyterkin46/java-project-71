package hexlet.code;

import java.util.List;
import java.util.function.Function;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

public class Formatter {
    public static Function<List<Dif>, String> getFormatter(String format) {
        switch (format) {
            case "plain":
                return Plain::getStringDif;
            default:
                return Stylish::getStringDif;
        }
    }
}
