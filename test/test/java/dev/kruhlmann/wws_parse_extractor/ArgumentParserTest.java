package test.java.dev.kruhlmann.wws_parse_extractor;

import main.java.dev.kruhlmann.wws_parse_extractor.cli.ArgumentParser;
import main.java.dev.kruhlmann.wws_parse_extractor.cli.CLIArgumentsCollection;
import main.java.dev.kruhlmann.wws_parse_extractor.exception.InsufficientArgumentsException;
import main.java.dev.kruhlmann.wws_parse_extractor.exception.InvalidURLException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {
    @Test
    void parseAsCLIArgumentsCollection() throws InvalidURLException, InsufficientArgumentsException {
        String[] args = new String[2];
        args[0] = "https://example.org/fight_id/";
        args[1] = "/directory/";
        ArgumentParser argumentParser = new ArgumentParser(args);
        CLIArgumentsCollection cliArgumentsCollection = argumentParser.parseAsCLIArgumentsCollection();
        assertEquals(cliArgumentsCollection.getRootWWSURL(), "https://example.org/fight_id/");
        assertEquals(cliArgumentsCollection.getOutDir(), "/directory/");
        assertEquals(cliArgumentsCollection.getFightId(), "fight_id");
    }

    @Test
    void parseAsCLIArgumentsCollectionInvalidURL() {
        String[] args = new String[2];
        args[0] = "non_url";
        args[1] = "/directory/";
        ArgumentParser argumentParser = new ArgumentParser(args);
        assertThrows(InvalidURLException.class, () -> argumentParser.parseAsCLIArgumentsCollection());
    }

    @Test
    void parseAsCLIArgumentsCollectionInsufficientArguments() {
        String[] args = new String[1];
        ArgumentParser argumentParser = new ArgumentParser(args);
        assertThrows(InsufficientArgumentsException.class, () -> argumentParser.parseAsCLIArgumentsCollection());
    }
}