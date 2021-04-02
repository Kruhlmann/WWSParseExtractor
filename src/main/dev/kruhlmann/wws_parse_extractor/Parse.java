package main.dev.kruhlmann.wws_parse_extractor;

import java.util.List;

class Parse {
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
