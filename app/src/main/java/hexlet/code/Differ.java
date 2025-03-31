package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;


public class Differ {

    public static String generate(String filepath1, String filepath2) throws Exception {
        String json1 = RandomUtils.readFile(filepath1);
        Map<String, Object> mapJson1 = getMapFromJson(json1);
        String json2 = RandomUtils.readFile(filepath2);
        Map<String, Object> mapJson2 = getMapFromJson(json2);

        String dif = getDifferent(mapJson1, mapJson2);
        return dif;
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

    private static Map<String, Object> getMapFromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = objectMapper.readValue(json, new TypeReference<>() { });

        return resultMap;
    }
}
