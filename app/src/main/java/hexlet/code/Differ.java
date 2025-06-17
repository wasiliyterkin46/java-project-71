package hexlet.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

public class Differ {

    public static String generate(String filepath1, String filepath2, String format)
            throws IOException, IllegalArgumentException {
        String contentFile1 = ContentFile.getContentFile(filepath1);
        String contentFile2 = ContentFile.getContentFile(filepath2);
        String extensionFile1 = ContentFile.getFileExtension(filepath1);
        String extensionFile2 = ContentFile.getFileExtension(filepath2);

        Map<String, Object> mapFile1 = Parser.getMap(contentFile1, extensionFile1);
        Map<String, Object> mapFile2 = Parser.getMap(contentFile2, extensionFile2);

        List<Dif> listDif = getDifferent(mapFile1, mapFile2);
        Function<List<Dif>, String> formatter = Formatter.getFormatter(format);

        return formatter.apply(listDif);
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    private static List<Dif> getDifferent(Map<String, Object> mapJson1, Map<String, Object> mapJson2) {
        List<String> listAllKeysSort = getListAllKeysSort(mapJson1.keySet(),
                mapJson2.keySet());
        List<Dif> result = new ArrayList<>();
        for (var key : listAllKeysSort) {
            result.add(compareOneKey(key, mapJson1, mapJson2));
        }

        return result;
    }

    private static Dif compareOneKey(String key, Map<String, Object> mapJson1, Map<String, Object> mapJson2) {
        if (mapJson1.containsKey(key)) {
            if (mapJson2.containsKey(key)) {
                if (Objects.equals(mapJson1.get(key), mapJson2.get(key))) {
                    return new Dif(DifOperation.NEUTRAL, key, mapJson1.get(key), null);
                } else {
                    return new Dif(DifOperation.UPDATE, key, mapJson1.get(key), mapJson2.get(key));
                }
            } else {
                return new Dif(DifOperation.DELETE, key, mapJson1.get(key), null);
            }
        }
        return new Dif(DifOperation.ADD, key, null, mapJson2.get(key));
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
