package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RandomUtils {

    public static String readFile(String filePath) throws Exception {
        Path pathToFile = Paths.get(filePath);
        Path absolutePathToFile = pathToFile.isAbsolute() ? pathToFile : pathToFile.toAbsolutePath().normalize();
        // Проверяем существование файла
        if (!Files.exists(absolutePathToFile)) {
            throw new RuntimeException("File '" + absolutePathToFile + "' does not exist");
        }
        // Читаем файл
        String content = Files.readString(absolutePathToFile).trim();
        return content;
    }

}
