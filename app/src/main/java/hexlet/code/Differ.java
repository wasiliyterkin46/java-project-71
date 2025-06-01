package hexlet.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Differ {

    public static List<Dif> generate(String filepath1, String filepath2) throws IOException {
        Map<String, Object> mapFile1 = Parser.getMapFromFile(filepath1);
        Map<String, Object> mapFile2 = Parser.getMapFromFile(filepath2);

        return getDifferent(mapFile1, mapFile2);
    }

    private static List<Dif> getDifferent(Map<String, Object> mapJson1, Map<String, Object> mapJson2) {
        List<String> listAllKeysSort = getListAllKeysSort(mapJson1.keySet(),
                mapJson2.keySet());
        List<Dif> result = new ArrayList<>();
        for (var key : listAllKeysSort) {
            compareOneKey(result, key, mapJson1, mapJson2);
        }

        return result;
    }

    private static void compareOneKey(List<Dif> list, String key, Map<String, Object> mapJson1,
                                      Map<String, Object> mapJson2) {
        if (mapJson1.containsKey(key)) {
            if (mapJson2.containsKey(key)) {
                if (isEquals(mapJson1.get(key), mapJson2.get(key))) {
                    list.add(new Dif(DifOperation.NEUTRAL, key, mapJson1.get(key)));
                } else {
                    list.add(new Dif(DifOperation.DELETE, key, mapJson1.get(key)));
                    list.add(new Dif(DifOperation.ADD, key, mapJson2.get(key)));
                }
            } else {
                list.add(new Dif(DifOperation.DELETE, key, mapJson1.get(key)));
            }
        } else {
            list.add(new Dif(DifOperation.ADD, key, mapJson2.get(key)));
        }
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

    private static boolean isEquals(Object value1, Object value2) {
        if (value1 == null) {
            return value2 == null;
        } else {
            if (value2 == null) {
                return false;
            }
        }
        return value1.equals(value2);
    }
}
