package hexlet.code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContentFile {
    public static String getContentFile(String filePath) throws IllegalArgumentException, IOException {
        Path pathToFile = Paths.get(filePath);
        Path absolutePathToFile = pathToFile.isAbsolute() ? pathToFile : pathToFile.toAbsolutePath().normalize();

        if (!Files.exists(absolutePathToFile)) {
            throw new FileNotFoundException(String.format("File %s does not exist", pathToFile));
        }

        return Files.readString(absolutePathToFile).trim();
    }

    public static String getFileExtension(String pathToFile) throws IllegalArgumentException {
        String[] splitPath = pathToFile.split("\\.");
        if (splitPath.length < 2) {
            throw new IllegalArgumentException(String.format("Для файла %s не указано расширение.", pathToFile));
        }

        return splitPath[splitPath.length - 1].toLowerCase();
    }
}
