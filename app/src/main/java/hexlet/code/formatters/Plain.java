package hexlet.code.formatters;

import hexlet.code.Dif;
import hexlet.code.DifOperation;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class Plain {
    private static final String PREFIX_LINE = "Property '";

    private Plain() { }

    public static String getStringDif(List<Dif> list) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder();

        for (Dif dif : list) {
            if (dif.getOperation() == DifOperation.NEUTRAL) {
                continue;
            }
            switch (dif.getOperation()) {
                case DifOperation.ADD:
                    builder.append(getViewAddOperation(dif));
                    break;
                case DifOperation.DELETE:
                    builder.append(getViewDelOperation(dif));
                    break;
                case DifOperation.UPDATE:
                    builder.append(getViewUpdateOperation(dif));
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Невозможно создать текстовое "
                            + "представление для вида операции '%s'", dif.getOperation()));
            }
        }
        String result = builder.toString();
        result = result.substring(0, result.length() - 1);

        return result;
    }

    private static String getViewAddOperation(Dif dif) {
        StringBuilder builder = new StringBuilder(PREFIX_LINE);
        builder.append(dif.getKey());
        builder.append("' was added with value: ");
        Object newValue = dif.getNewValue();
        builder.append(getValueString(newValue));
        builder.append("\n");

        return builder.toString();
    }

    private static String getValueString(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof Collection<?> || obj instanceof Map<?, ?>) {
            return "[complex value]";
        }
        if (obj instanceof String) {
            return "'" + obj.toString() + "'";
        }

        return obj.toString();
    }

    private static String getViewDelOperation(Dif dif) {
        StringBuilder builder = new StringBuilder(PREFIX_LINE);
        builder.append(dif.getKey());
        builder.append("' was removed");
        builder.append("\n");

        return builder.toString();
    }

    private static String getViewUpdateOperation(Dif dif) {
        StringBuilder builder = new StringBuilder(PREFIX_LINE);
        builder.append(dif.getKey());
        builder.append("' was updated. From ");
        builder.append(getValueString(dif.getOldValue()));
        builder.append(" to ");
        builder.append(getValueString(dif.getNewValue()));
        builder.append("\n");

        return builder.toString();
    }
}
