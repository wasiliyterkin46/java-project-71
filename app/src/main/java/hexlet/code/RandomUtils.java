package hexlet.code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RandomUtils {

    public static String readFile(String filePath) throws IOException {
        Path pathToFile = Paths.get(filePath);
        Path absolutePathToFile = pathToFile.isAbsolute() ? pathToFile : pathToFile.toAbsolutePath().normalize();
        // Проверяем существование файла
        if (!Files.exists(absolutePathToFile)) {
            throw new FileNotFoundException(String.format("File %s does not exist", pathToFile));
        }
        // Читаем файл
        String content = Files.readString(absolutePathToFile).trim();
        return content;
    }

}
