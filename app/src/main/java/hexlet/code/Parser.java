package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.IOException;
import java.util.Map;

public class Parser {

    public static Map<String, Object> getMap(String content, String typeContent) throws IOException,
            IllegalArgumentException {

        ObjectMapper objectMapper;

        switch (typeContent) {
            case "json":
                objectMapper = new ObjectMapper();
                break;
            case "yml", "yaml":
                objectMapper = new YAMLMapper(new YAMLFactory());
                break;
            default:
                throw new IllegalArgumentException(String.format("Расширение файла %s не подходит для обработки.",
                        typeContent));
        }

        return objectMapper.readValue(content, new TypeReference<>() { });
    }
}
