package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.Test;
import  org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static hexlet.code.Differ.generate;

public class DifferTest {

    @ParameterizedTest
    @ValueSource(strings = { "json", "yml" })
    void correctFileRelativePath(String extensionFile) throws Exception {
        String file1Path = "src/test/resources/file1." + extensionFile;
        String file2Path = "src/test/resources/file2." + extensionFile;

        String expectedString = RandomUtils.readFile("src/test/resources/correctCompareResult.txt");
        String actual = generate(file1Path, file2Path);

        assertEquals(expectedString, actual);
    }

    @Test
    void pathNotFound() {
        String json1Path = "src/test/resources/file1.json";
        String json2Path = "src/test/resources/file3.json";

        assertThrows(RuntimeException.class, () -> generate(json1Path, json2Path));
    }

    @Test
    void formatOfFileNotJSON() {
        String json1Path = "src/test/resources/file1.json";
        String json2Path = "src/test/resources/formatNotJSON.json";

        assertThrows(JsonParseException.class, () -> generate(json1Path, json2Path));
    }

    @Test
    void formatOfFileNotYML() {
        String yml1Path = "src/test/resources/file1.yml";
        String yml2Path = "src/test/resources/formatNotYML.yml";

        assertThrows(MismatchedInputException.class, () -> generate(yml1Path, yml2Path));
    }

    @Test
    void extensionNotValid() {
        String path1 = "src/test/resources/file1.yml";
        String path2 = "src/test/resources/fileUncorrectExtension.xxx";

        assertThrows(RuntimeException.class, () -> generate(path1, path2));
    }

    @Test
    void fileWithoutExtension() {
        String path1 = "src/test/resources/file1.yml";
        String path2 = "src/test/resources/fileWithoutExtension";

        assertThrows(RuntimeException.class, () -> generate(path1, path2));
    }

}
