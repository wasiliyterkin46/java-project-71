package hexlet.code.formatters;

import hexlet.code.Dif;
import hexlet.code.DifOperation;
import java.util.List;

public final class Stylish {
    private Stylish() { }

    public static String getStringDif(List<Dif> list) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder("{");
        if (!list.isEmpty()) {
            builder.append("\n");
        }

        for (Dif dif : list) {
            switch (dif.getOperation()) {
                case DifOperation.ADD:
                    builder.append(getString(getSign(dif.getOperation()), dif.getKey(), dif.getNewValue()));
                    break;
                case DifOperation.DELETE, DifOperation.NEUTRAL:
                    builder.append(getString(getSign(dif.getOperation()), dif.getKey(), dif.getOldValue()));
                    break;
                case DifOperation.UPDATE:
                    builder.append(getString(getSign(DifOperation.DELETE), dif.getKey(), dif.getOldValue()));
                    builder.append(getString(getSign(DifOperation.ADD), dif.getKey(), dif.getNewValue()));
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Необрабатываемое значение операции: %s", dif));
            }
        }
        builder.append("}");

        return builder.toString();
    }

    private static String getSign(DifOperation difOperation) throws IllegalArgumentException {
        switch (difOperation) {
            case DifOperation.ADD:
                return "+ ";
            case DifOperation.DELETE:
                return "- ";
            case DifOperation.NEUTRAL:
                return "  ";
            default:
                throw new IllegalArgumentException(String.format("Операция '%s' не может быть выражена "
                        + "текстовым значением", difOperation));
        }
    }

    private static String getString(String sign, String key, Object value) {
        StringBuilder builder = new StringBuilder("  ");
        builder.append(sign);
        builder.append(key);
        builder.append(": ");
        builder.append(value == null ? "null" : value.toString());
        builder.append("\n");

        return builder.toString();
    }
}
