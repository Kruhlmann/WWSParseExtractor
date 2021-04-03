package test.java.dev.kruhlmann.wws_parse_extractor;

import main.java.dev.kruhlmann.wws_parse_extractor.AbsoluteHrefConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


class AbsoluteHrefConverterTest {
    AbsoluteHrefConverter absoluteHrefConverter;

    @BeforeEach
    void setUp() {
        absoluteHrefConverter = new AbsoluteHrefConverter("https://example.org/");
    }

    @Test
    void shouldConvertRelativeURLsToAbsolute() {
        List<String> relativeLinks = Arrays.asList("/index.html", "https://example.org/index.html");
        List<String> absoluteLinks = absoluteHrefConverter.relativeURLsToAbsolute(relativeLinks);
        List<String> expectedLinks = Arrays.asList("https://example.org/index.html", "https://example.org/index.html");
        assertEquals(absoluteLinks, expectedLinks);
    }
}
