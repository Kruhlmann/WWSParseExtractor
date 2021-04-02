package main.java.dev.kruhlmann.wws_parse_extractor;

import java.util.ArrayList;
import java.util.List;

public class AbsoluteHrefConverter {
    private final String host;

    public AbsoluteHrefConverter(String host) {
        this.host = host;
    }

    public List<String> relativeURLsToAbsolute(List<String> links) {
        List<String> absoluteLinks = new ArrayList<>();

        for (String link: links) {
            if (this.host.endsWith("/") && link.startsWith("/")) {
                link = link.replaceFirst("/", "");
            }
            if (link.startsWith("http")) {
                absoluteLinks.add(link);
            } else {
                absoluteLinks.add(this.host + link);
            }
        }

        return absoluteLinks;
    }
}
