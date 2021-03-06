package dev.kruhlmann.wws_parse_extractor;

import dev.kruhlmann.wws_parse_extractor.cli.ArgumentParser;
import dev.kruhlmann.wws_parse_extractor.cli.CLIArgumentsCollection;
import dev.kruhlmann.wws_parse_extractor.exception.InsufficientArgumentsException;
import dev.kruhlmann.wws_parse_extractor.exception.InvalidURLException;
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
    void shouldThrowErrorOnInvalidURL() {
        String[] args = new String[2];
        args[0] = "non_url";
        args[1] = "/directory/";
        ArgumentParser argumentParser = new ArgumentParser(args);
        assertThrows(InvalidURLException.class, () -> argumentParser.parseAsCLIArgumentsCollection());
    }

    @Test
    void ShouldThrowErrorOnInsufficientArguments() {
        String[] args = new String[1];
        ArgumentParser argumentParser = new ArgumentParser(args);
        assertThrows(InsufficientArgumentsException.class, () -> argumentParser.parseAsCLIArgumentsCollection());
    }
}