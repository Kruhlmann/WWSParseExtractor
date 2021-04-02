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
            String preformattedLink = this.preformatLink(link);
            absoluteLinks.add(preformattedLink);
        }

        return absoluteLinks;
    }

    private String preformatLink(String link) {
        String nonConflictingLink = this.removeSuffixPrefixSlashConflictFromLink(link);
        String absoluteLink = this.convertLinkToAbsoluteIfRelative(nonConflictingLink);
        return absoluteLink;
    }

    private String convertLinkToAbsoluteIfRelative(String link) {
        boolean linkIsAbsolute = link.startsWith("http");
        if (linkIsAbsolute) {
            return link;
        } else {
            return this.host + link;
        }
    }

    private String removeSuffixPrefixSlashConflictFromLink(String link) {
        if (this.host.endsWith("/") && link.startsWith("/")) {
            return link.replaceFirst("/", "");
        }
        return link;
    }
}
