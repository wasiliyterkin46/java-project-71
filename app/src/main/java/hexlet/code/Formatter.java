package hexlet.code;

import java.util.List;
import lombok.Getter;
import lombok.Builder;

public class Formatter {
    public static String getDifferent(List<Dif> difList, String formatting) {
        String result = null;
        Format format = new Format.FormatBuilder()
                .startString("{\n").endString("}").prefixLine("  ").opAdd(DifOperation.ADD.setSign("+ "))
                .opDel(DifOperation.DELETE.setSign("- ")).opNeutral(DifOperation.NEUTRAL.setSign("  "))
                .separatorValue(": ").separatorLine("\n")
                .build();

        switch (formatting) {
            case "stylish":
                return makeDifferent(difList, format);
            default:
                return makeDifferent(difList, format);
        }
    }

    private static String makeDifferent(List<Dif> difList, Format format) {
        StringBuilder builder = new StringBuilder(format.getStartString());
        for (Dif dif : difList) {
            builder.append(format.getPrefixLine());
            builder.append(dif.getOperation().getSign());
            builder.append(dif.getKey());
            builder.append(format.getSeparatorValue());
            builder.append(dif.getValue() == null ? "null" : dif.getValue());
            builder.append(format.getSeparatorLine());
        }
        builder.append(format.getEndString());

        return builder.toString();
    }

    @Getter
    @Builder
    private static final class Format {
        private String startString;
        private String endString;
        private String prefixLine;
        private DifOperation opAdd;
        private DifOperation opDel;
        private DifOperation opNeutral;
        private String separatorValue;
        private String separatorLine;
    }
}
