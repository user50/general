package general.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadWithScanner {

    private final Path fFilePath;
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    public ReadWithScanner(String aFileName){
        fFilePath = Paths.get(aFileName);
    }

    public <T> List<T>  processLineByLine(LineParser<T> lineParser) throws IOException {
        List<T> parseResult = new ArrayList<>();

        try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
            while (scanner.hasNextLine()){
                parseResult.add(lineParser.parse(scanner.nextLine()));
            }
        }

        return parseResult;
    }
}