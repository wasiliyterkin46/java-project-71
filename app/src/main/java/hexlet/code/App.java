package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Command(name = "gendiff", version = "gendiff 1.0", mixinStandardHelpOptions = true)
public class App implements Runnable {

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    String format;

    @Parameters(paramLabel = "filepath1", description = "path to first file")
    String filepath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    String filepath2;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            String json1 = readJson(filepath1);
            Map<String, Object> mapJson1 = getMapFromJson(json1);
            String json2 = readJson(filepath2);
            Map<String, Object> mapJson2 = getMapFromJson(json1);

            // Выводим результаты
            System.out.println(mapJson1.toString());
            System.out.println(mapJson2.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String readJson(String path) throws Exception {
        Path pathToFile = Paths.get(path);
        Path absolutePathToFile = pathToFile.isAbsolute() ? pathToFile : pathToFile.toAbsolutePath().normalize();
        // Проверяем существование файла
        if (!Files.exists(absolutePathToFile)) {
            throw new Exception("File '" + absolutePathToFile + "' does not exist");
        }
        // Читаем файл
        String content = Files.readString(absolutePathToFile);
        return content;
    }

    private Map<String, Object> getMapFromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = objectMapper.readValue(json, new TypeReference<>(){});
        return resultMap;
    }
}
