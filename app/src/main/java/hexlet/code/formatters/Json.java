package hexlet.code.formatters;

import hexlet.code.Dif;
import hexlet.code.DifOperation;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;

public final class Json {
    private static final int INDENT = 2;
    private static int spaceBeforeElem = INDENT;

    private Json() { }

    public static String getStringDif(List<Dif> list) {
        StringBuilder builder = new StringBuilder("{\n");
        builder.append(" ".repeat(spaceBeforeElem));
        builder.append(String.format("\"%s\": [\n", "differs"));
        spaceBeforeElem += INDENT;
        for (Dif dif : list) {
            builder.append(startElem());
            builder.append(addElem(dif));
            builder.append(endElem());
        }
        builder.delete(builder.length() - 2, builder.length() - 1);
        spaceBeforeElem -= INDENT;
        builder.append(" ".repeat(spaceBeforeElem));
        builder.append("]\n}\n");

        return builder.toString();
    }

    private static String startElem() {
        String result = " ".repeat(spaceBeforeElem) + "{\n";
        spaceBeforeElem += INDENT;
        return result;
    }

    private static String endElem() {
        return " ".repeat(spaceBeforeElem) + "},\n";
    }

    private static String addElem(Dif dif) {
        StringBuilder builder = new StringBuilder();
        builder.append(getKeyView(dif.getKey()));
        builder.append(getTypeOperationView(getNameOperation(dif.getOperation())));

        switch (dif.getOperation()) {
            case DifOperation.ADD:
                builder.append(getOperationView("value", dif.getNewValue(), "\n"));
                break;
            case DifOperation.DELETE, DifOperation.NEUTRAL:
                builder.append(getOperationView("value", dif.getOldValue(), "\n"));
                break;
            default:
                builder.append(getOperationView("value1", dif.getOldValue(), ",\n"));
                builder.append(getOperationView("value2", dif.getNewValue(), "\n"));
        }
        spaceBeforeElem -= INDENT;

        return builder.toString();
    }

    private static String getKeyView(String keyValue) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ".repeat(spaceBeforeElem));
        builder.append(String.format("\"%s\": \"%s\",\n", "key", keyValue));

        return builder.toString();
    }

    private static String getTypeOperationView(String nameOperation) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ".repeat(spaceBeforeElem));
        builder.append(String.format("\"%s\": \"%s\",\n", "type", nameOperation));

        return builder.toString();
    }

    private static String getOperationView(String nameValue, Object value, String lineSeparator) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ".repeat(spaceBeforeElem));
        builder.append(String.format("\"%s\": ", nameValue));
        builder.append(getValueView(value));
        builder.append(lineSeparator);

        return builder.toString();
    }

    private static String getNameOperation(DifOperation operation) {
        return switch (operation) {
            case DifOperation.ADD -> "added";
            case DifOperation.DELETE -> "deleted";
            case DifOperation.NEUTRAL -> "unchanged";
            default -> "changed";
        };
    }

    private static String getValueView(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof List<?>) {
            return getListView((List<?>) value);
        }
        if (value instanceof Map<?, ?>) {
            return getMapView((Map<?, ?>) value);
        }
        if (NumberUtils.isCreatable(value.toString()) || value instanceof Boolean) {
            return value.toString();
        }

        return String.format("\"%s\"", value.toString());
    }

    private static String getListView(List<?> list) {
        StringBuilder builder = new StringBuilder("[");
        for (Object elem : list) {
            builder.append(String.format("%s, ", getValueView(elem)));
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append("]");

        return builder.toString();
    }

    private static String getMapView(Map<?, ?> map) {
        StringBuilder builder = new StringBuilder("{");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            builder.append(String.format("\"%s\": ", entry.getKey().toString()));
            builder.append(getValueView(entry.getValue()));
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append("}");

        return builder.toString();
    }
}
