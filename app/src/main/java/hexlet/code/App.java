package hexlet.code;


import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "gendiff", version = "gendiff 1.0", mixinStandardHelpOptions = true)
public class App implements Callable<Integer> {

    @Option(names = {"-f", "--format"}, paramLabel = "format", defaultValue = "stylish",
            description = "output format [default: ${DEFAULT-VALUE}]")
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
    public Integer call() {
        try {
            List<Dif> difList = Differ.generate(filepath1, filepath2);
            System.out.println(Formatter.getDifferent(difList, format));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }


}
