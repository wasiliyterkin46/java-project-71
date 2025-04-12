package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public class Parser {

    private enum FileType { JSON, YML, UNDEFINED };

    public static Map<String, Object> getMapFromFile(String pathToFile) throws IOException, IllegalArgumentException {
        Map<String, Object> resultMap;
        FileType fileType = getFileType(pathToFile);
        resultMap = getMap(pathToFile, fileType);

        return resultMap;
    }

    private static FileType getFileType(String pathToFile) throws IllegalArgumentException {
        String[] splitPath = pathToFile.split("\\.");
        if (splitPath.length < 2) {
            throw new IllegalArgumentException(String.format("Для файла %s не указано расширение.", pathToFile));
        }
        String extension = splitPath[splitPath.length - 1].toLowerCase();

        switch (extension) {
            case "json":
                return FileType.JSON;
            case "yml", "yaml":
                return FileType.YML;
            default:
                return FileType.UNDEFINED;
        }
    }

    private static Map<String, Object> getMap(String pathToFile, FileType type) throws IOException,
            IllegalArgumentException {

        ObjectMapper objectMapper;

        switch (type) {
            case FileType.JSON:
                objectMapper = new ObjectMapper();
                break;
            case FileType.YML:
                objectMapper = new YAMLMapper(new YAMLFactory());
                break;
            default:
                throw new IllegalArgumentException(String.format("Расширение файла %s не подходит для обработки.",
                        pathToFile));
        }

        String contentFile = RandomUtils.readFile(pathToFile);
        return objectMapper.readValue(contentFile, new TypeReference<>() { });
    }

}
