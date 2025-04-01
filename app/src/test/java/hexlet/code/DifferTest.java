package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import static hexlet.code.Differ.generate;

public class DifferTest {

    @Test
    void correctFileRelativePath() throws Exception {
        String json1Path = "src/test/resources/file1.json";
        String json2Path = "src/test/resources/file2.json";

        String expectedString = RandomUtils.readFile("src/test/resources/correctCompareResult.txt");
        String actual = generate(json1Path, json2Path);

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

}
