package main.dev.kruhlmann.wws_parse_extractor;

public class Player {
    private final String cls;
    private final String name;

    public Player(String cls, String name) {
        this.cls = cls;
        this.name = name;
    }

    public String getCls() {
        return cls;
    }

    public String getName() {
        return name;
    }
}
