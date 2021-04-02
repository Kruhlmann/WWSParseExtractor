package main.dev.kruhlmann.wws_parse_extractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: wws_parse_extractor <root_wws_link> <output_dir>");
            System.exit(1);
        }
        String rootWWSURL = args[0];
        String outDir = args[1];
        String[] splitUrl = rootWWSURL.split("/");
        String fightId = splitUrl[splitUrl.length - 1];

        RemoteHTMLSourceReader htmlLinkSourceReader = new RemoteHTMLSourceReader(rootWWSURL);
        String result = htmlLinkSourceReader.read();
        ParseLinkHTMLExtractor linkHTMLExtractor = new ParseLinkHTMLExtractor(result);
        List<String> ambiguousLinks = linkHTMLExtractor.getParseLinks();
        AbsoluteHrefConverter absoluteHrefConverter = new AbsoluteHrefConverter(rootWWSURL);
        List<String> absoluteLinks = absoluteHrefConverter.relativeURLsToAbsolute(ambiguousLinks);
        List<Parse> parses = new ArrayList<>();

        for (String link: absoluteLinks) {
            RemoteHTMLSourceReader reader = new RemoteHTMLSourceReader(link);
            String source = reader.read();
            WWSParser wwsParser = new WWSParser(source);
            Parse parse = wwsParser.readParse(fightId);
            parses.add(parse);
        }
        CSVParseFactory csvParseFactory = new CSVParseFactory(parses, outDir);
        csvParseFactory.generateCSVFiles();
    }
}
