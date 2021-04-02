package main.dev.kruhlmann.wws_parse_extractor;

import main.dev.kruhlmann.wws_parse_extractor.exception.InsufficientArgumentsException;
import main.dev.kruhlmann.wws_parse_extractor.exception.InvalidURLException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void printUsageString() {
        System.out.println("Usage: wpe <root_wws_link> <output_dir>");
    }

    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser(args);

        try {
            CLIArgumentsCollection cliArgs = argumentParser.parseAsCLIArgumentsCollection();
            Main.startParserWithCLIArguments(cliArgs);
        } catch (InsufficientArgumentsException e) {
            System.out.println("Insufficient arguments.");
            Main.printUsageString();
            System.exit(2);
        } catch (InvalidURLException e) {
            System.out.println("Invalid URL.");
            System.exit(3);
        }
    }

    private static void startParserWithCLIArguments(CLIArgumentsCollection cliArgs) {
        RemoteHTMLSourceReader htmlLinkSourceReader = new RemoteHTMLSourceReader(cliArgs.getRootWWSURL());
        try {
            List<Parse> parses = Main.readAndParseLinksFromRemoteHTMLSourceReader(htmlLinkSourceReader, cliArgs);
            CSVParseFactory csvParseFactory = new CSVParseFactory(parses, cliArgs.getOutDir());
            csvParseFactory.generateCSVFiles();
        } catch (IOException e) {
            System.out.println("Error occurred while saving CSV file.");
            System.exit(4);
        }
    }

    private static List<Parse> readAndParseLinksFromRemoteHTMLSourceReader(RemoteHTMLSourceReader htmlLinkSourceReader, CLIArgumentsCollection cliArgs) {
        try {
            String result = htmlLinkSourceReader.read();
            ParseLinkHTMLExtractor linkHTMLExtractor = new ParseLinkHTMLExtractor(result);
            List<String> ambiguousLinks = linkHTMLExtractor.getParseLinks();
            AbsoluteHrefConverter absoluteHrefConverter = new AbsoluteHrefConverter(cliArgs.getRootWWSURL());
            List<String> absoluteLinks = absoluteHrefConverter.relativeURLsToAbsolute(ambiguousLinks);
            return Main.createParseListFromAbsoluteLinks(cliArgs, absoluteLinks);
        } catch (IOException e) {
            System.out.println("Error parsing main HTML source.");
            System.exit(5);
        }
        return new ArrayList<>();
    }

    private static List<Parse> createParseListFromAbsoluteLinks(CLIArgumentsCollection cliArgs, List<String> absoluteLinks) {
        List<Parse> parses = new ArrayList<>();
        for (String link: absoluteLinks) {
            RemoteHTMLSourceReader reader = new RemoteHTMLSourceReader(link);
            try {
                String source = reader.read();
                WWSParser wwsParser = new WWSParser(source);
                Parse parse = wwsParser.readParse(cliArgs.getFightId());
                parses.add(parse);
            } catch (IOException e) {
                System.out.println("Unable to parse link " + link + ". Skipping.");
            }
        }
        return parses;
    }
}
