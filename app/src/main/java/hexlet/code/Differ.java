package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import static hexlet.code.Parser.getMapFromFile;


public class Differ {

    public static String generate(String filepath1, String filepath2) throws IOException {
        Map<String, Object> mapFile1 = getMapFromFile(filepath1);
        Map<String, Object> mapFile2 = getMapFromFile(filepath2);

        return getDifferent(mapFile1, mapFile2);
    }

    private static String getDifferent(Map<String, Object> mapJson1, Map<String, Object> mapJson2) {
        List<String> listAllKeysSort = getListAllKeysSort(Set.copyOf(mapJson1.keySet()), Set.copyOf(mapJson2.keySet()));

        StringBuilder builder = new StringBuilder("{\n");
        for (var key : listAllKeysSort) {
            String resultOfCompareOneKey = compareOneKey(key, mapJson1, mapJson2);
            builder.append(resultOfCompareOneKey);
        }
        builder.append("}");

        return builder.toString();
    }

    private static String compareOneKey(String key, Map<String, Object> mapJson1, Map<String, Object> mapJson2) {
        StringBuilder builder = new StringBuilder();

        if (mapJson1.containsKey(key)) {
            if (mapJson2.containsKey(key)) {
                if (mapJson1.get(key).equals(mapJson2.get(key))) {
                    builder.append("    " + key + ": " + mapJson1.get(key).toString());
                } else {
                    builder.append("  - " + key + ": " + mapJson1.get(key).toString());
                    builder.append("\n");
                    builder.append("  + " + key + ": " + mapJson2.get(key).toString());
                }
            } else {
                builder.append("  - " + key + ": " + mapJson1.get(key).toString());
            }
        } else {
            builder.append("  + " + key + ": " + mapJson2.get(key).toString());
        }
        builder.append("\n");

        return builder.toString();
    }

    private static List<String> getListAllKeysSort(Set<String> keySet1, Set<String> keySet2) {
        Set<String> resultSet = new HashSet<>();
        resultSet.addAll(keySet1);
        resultSet.addAll(keySet2);

        List<String> resultList = resultSet.stream()
                        .sorted()
                        .toList();

        return resultList;
    }

}
