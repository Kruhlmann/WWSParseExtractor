package dev.kruhlmann.wws_parse_extractor.cli;

import dev.kruhlmann.wws_parse_extractor.exception.InsufficientArgumentsException;
import dev.kruhlmann.wws_parse_extractor.exception.InvalidURLException;

public class ArgumentParser {
    private String[] args;

    public ArgumentParser(String[] args) {
        this.args = args;
    }

    private void validateArgumentsOrThrowException() throws InvalidURLException, InsufficientArgumentsException {
        if (args.length < 2) {
            throw new InsufficientArgumentsException();
        }
        if (args[0].split("/").length < 2) {
            throw new InvalidURLException();
        }
    }

    public CLIArgumentsCollection parseAsCLIArgumentsCollection() throws InvalidURLException, InsufficientArgumentsException {
        this.validateArgumentsOrThrowException();
        String rootWWSURL = args[0];
        String[] splitUrl = rootWWSURL.split("/");
        String fightId = splitUrl[splitUrl.length - 1];
        String outDir = args[1];
        return new CLIArgumentsCollection(rootWWSURL, fightId, outDir);
    }
}
