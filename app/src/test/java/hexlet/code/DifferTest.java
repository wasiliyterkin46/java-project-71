package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Test;
import  org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.FileNotFoundException;
import static hexlet.code.Differ.generate;

public class DifferTest {

    @ParameterizedTest
    @ValueSource(strings = { "json", "yml" })
    void correctFileRelativePathStylish(String extensionFile) throws Exception {
        String file1Path = "src/test/resources/file1." + extensionFile;
        String file2Path = "src/test/resources/file2." + extensionFile;

        String expectedString = ContentFile.getContentFile("src/test/resources/correctCompareResult.txt");
        String actual = generate(file1Path, file2Path, "stylish");
        assertEquals(expectedString, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = { "json", "yml" })
    void correctFileRelativePathPlain(String extensionFile) throws Exception {
        String file1Path = "src/test/resources/file1." + extensionFile;
        String file2Path = "src/test/resources/file2." + extensionFile;

        String expectedString = ContentFile.getContentFile("src/test/resources/correctCompareFormatPlain.txt");
        String actual = generate(file1Path, file2Path, "plain");
        assertEquals(expectedString, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = { "json", "yml" })
    void correctFileRelativePathJson(String extensionFile) throws Exception {
        String file1Path = "src/test/resources/file1." + extensionFile;
        String file2Path = "src/test/resources/file2." + extensionFile;

        String expectedString = ContentFile.getContentFile("src/test/resources/correctCompareFormatJson.txt");
        String actual = generate(file1Path, file2Path, "json");
        assertEquals(expectedString, actual);
    }

    @Test
    void pathNotFound() {
        String json1Path = "src/test/resources/file3.json";
        String json2Path = "src/test/resources/file1.json";

        assertThrows(FileNotFoundException.class, () -> generate(json1Path, json2Path, "stylish"));
    }

    @Test
    void formatOfFileNotJSON() {
        String json1Path = "src/test/resources/file1.json";
        String json2Path = "src/test/resources/formatNotJSON.json";

        assertThrows(JsonParseException.class, () -> generate(json1Path, json2Path, "stylish"));
    }

    @Test
    void formatOfFileNotYML() {
        String yml1Path = "src/test/resources/file1.yml";
        String yml2Path = "src/test/resources/formatNotYML.yml";

        assertThrows(MismatchedInputException.class, () -> generate(yml1Path, yml2Path, "stylish"));
    }

    @Test
    void extensionNotValid() {
        String path1 = "src/test/resources/file1.yml";
        String path2 = "src/test/resources/fileUncorrectExtension.xxx";

        assertThrows(IllegalArgumentException.class, () -> generate(path1, path2, "stylish"));
    }

    @Test
    void fileWithoutExtension() {
        String path1 = "src/test/resources/file1.yml";
        String path2 = "src/test/resources/fileWithoutExtension";

        assertThrows(IllegalArgumentException.class, () -> generate(path1, path2, "stylish"));
    }

}
