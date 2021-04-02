package main.java.dev.kruhlmann.wws_parse_extractor.parse;

import main.java.dev.kruhlmann.wws_parse_extractor.spell.SpellReport;

import java.util.List;

public class Parse {
    private final String fightId;
    private final Player player;
    private final List<SpellReport> spellReports;

    public Parse(String fightId, Player player, List<SpellReport> spellReports) {
        this.fightId = fightId;
        this.player = player;
        this.spellReports = spellReports;
    }

    public String getFightId() {
        return fightId;
    }

    public Player getPlayer() {
        return player;
    }

    public List<SpellReport> getSpellReports() {
        return spellReports;
    }
}
