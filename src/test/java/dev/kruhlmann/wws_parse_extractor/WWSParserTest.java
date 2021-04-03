package dev.kruhlmann.wws_parse_extractor;

import dev.kruhlmann.wws_parse_extractor.parse.Parse;
import dev.kruhlmann.wws_parse_extractor.parse.WWSParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class WWSParserTest {

    @Test
    void shouldConstructParseFromHTMLSource() throws IOException {
        String pageSource = Files.readString(Path.of("src/test/resources/example_parse.html"));
        WWSParser wwsParser = new WWSParser(pageSource);
        Parse parse = wwsParser.readParse("id");

        assertEquals(parse.getFightId(), "id");
        assertEquals(parse.getPlayer().getCls(), "rog");
        assertEquals(parse.getPlayer().getName(), "Powerforward");
        assertEquals(parse.getSpellReports().size(), 8);
        assertEquals(parse.getSpellReports().get(1).getNormalHits().getAverageDamage(), 275);
        assertEquals(parse.getSpellReports().get(3).getDamageOverTimeHits().getCount(), 179);
    }
}