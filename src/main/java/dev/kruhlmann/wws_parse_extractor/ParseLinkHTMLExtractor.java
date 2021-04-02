package main.java.dev.kruhlmann.wws_parse_extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParseLinkHTMLExtractor {
    private final String DAMAGE_DEALER_LINK_SELECTOR = "a[name='HITS'] + table.struct a";
    private final String overViewPageSource;

    public ParseLinkHTMLExtractor(String overViewPageSource) {
        this.overViewPageSource = overViewPageSource;
    }

    public List<String> getParseLinks() {
        Document doc = Jsoup.parse(this.overViewPageSource);
        Elements elements = doc.select(this.DAMAGE_DEALER_LINK_SELECTOR);
        return this.readNonRelativeLinksFromElementList(elements);
    }

    private List<String> readNonRelativeLinksFromElementList(Elements elements) {
        List<String> links = new ArrayList<>();
        for(Element e: elements) {
            links.add(e.attr("href"));
        }
        return links.stream().filter(link -> !link.startsWith("#")).collect(Collectors.toList());
    }
}
