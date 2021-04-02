package main.dev.kruhlmann.wws_parse_extractor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVParseFactory {
    private final String CSV_HEADER = "SPELL_NAME;TOTAL_DMG;CRIT_PCT;MISS_PCT;RESIST_PCT;HIT_COUNT;HIT_AVG_DMG;HIT_MAX_DMG;DOT_TICKS_COUNT;AVG_DOT_TICK_DMG;MAX_DOT_TICK_DMG;CRIT_COUNT;CRIT_AVG_DMG;CRIT_MAX_DMG;\n";
    private final String template = "%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n";
    private final List<Parse> parses;
    private final String outDir;

    public CSVParseFactory(List<Parse> parses, String outDir) {
        this.parses = parses;
        this.outDir = outDir;
    }

    public void generateCSVFiles() throws IOException {
        for (Parse parse : this.parses) {
            generateCSVFileFromParse(parse);
        }
    }

    private void generateCSVFileFromParse(Parse parse) throws IOException {
        String result = generateCSVParseResult(parse);
        writeCSVResultToFile(parse, result);
    }

    private void writeCSVResultToFile(Parse parse, String result) throws IOException {
        if (result == this.CSV_HEADER) {
            return;
        }
        String filePath = this.outDir + this.generateFileName(parse);
        FileOutputStream os = new FileOutputStream(filePath);
        os.write(result.getBytes(StandardCharsets.UTF_8));
        System.out.println("Wrote file " + filePath);
        os.close();
    }

    private String generateCSVParseResult(Parse parse) {
        String result = this.CSV_HEADER;
        List<SpellReport> spellReports = parse.getSpellReports();
        for (SpellReport spellReport : spellReports) {
            result += this.createCSVLine(parse, spellReport);
        }
        return result;
    }

    private String generateFileName(Parse parse) {
        Player player = parse.getPlayer();
        return String.format("%s_%s_%s.csv", player.getCls(), parse.getFightId(), player.getName());
    }

    private String createCSVLine(Parse parse, SpellReport spellReport) {
        SpellDamage normalHits = spellReport.getNormalHits();
        SpellDamage damageOverTimeHits = spellReport.getDamageOverTimeHits();
        SpellDamage criticalHits = spellReport.getCriticalHits();
        return String.format(this.template,
                spellReport.getName(),
                spellReport.getTotalDamage(),
                spellReport.getCriticalHitPercentage(),
                spellReport.getMissPercentage(),
                spellReport.getResistPercentage(),
                normalHits.getCount(),
                normalHits.getAverageDamage(),
                normalHits.getMaxDamage(),
                damageOverTimeHits.getCount(),
                damageOverTimeHits.getAverageDamage(),
                damageOverTimeHits.getMaxDamage(),
                criticalHits.getCount(),
                criticalHits.getAverageDamage(),
                criticalHits.getMaxDamage());
    }
}
