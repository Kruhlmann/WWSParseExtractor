package dev.kruhlmann.wws_parse_extractor;

import dev.kruhlmann.wws_parse_extractor.cli.ArgumentParser;
import dev.kruhlmann.wws_parse_extractor.cli.CLIArgumentsCollection;
import dev.kruhlmann.wws_parse_extractor.exception.InsufficientArgumentsException;
import dev.kruhlmann.wws_parse_extractor.exception.InvalidURLException;
import dev.kruhlmann.wws_parse_extractor.html.ParseLinkHTMLExtractor;
import dev.kruhlmann.wws_parse_extractor.html.RemoteHTMLSourceReader;
import dev.kruhlmann.wws_parse_extractor.parse.CSVParseFileFactory;
import dev.kruhlmann.wws_parse_extractor.parse.Parse;
import dev.kruhlmann.wws_parse_extractor.parse.WWSParser;

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
            CSVParseFileFactory csvParseFileFactory = new CSVParseFileFactory(parses, cliArgs.getOutDir());
            csvParseFileFactory.generateCSVFiles();
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
