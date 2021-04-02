package main.java.dev.kruhlmann.wws_parse_extractor;

public class CLIArgumentsCollection {
    private String rootWWSURL;
    private String fightId;
    private String outDir;

    public CLIArgumentsCollection(String rootWWSURL, String fightId, String outDir) {
        this.rootWWSURL = rootWWSURL;
        this.fightId = fightId;
        this.outDir = outDir;
    }

    public String getRootWWSURL() {
        return this.rootWWSURL;
    }

    public String getFightId() {
        return this.fightId;
    }

    public String getOutDir() {
        return this.outDir;
    }

}
