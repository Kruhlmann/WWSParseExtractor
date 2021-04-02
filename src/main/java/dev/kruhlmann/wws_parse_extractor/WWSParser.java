package main.java.dev.kruhlmann.wws_parse_extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WWSParser {
    private final String SPELL_ROWS_SELECTOR = "table.struct tr table tr";
    private String pageSource;

    public WWSParser(String pageSource) {
        this.pageSource = pageSource;
    }

    public Parse readParse(String fightId) {
        Document doc = Jsoup.parse(this.pageSource);
        Elements allRows = doc.select(this.SPELL_ROWS_SELECTOR);
        List<Element> damageRows = this.removeNonDamageRowsFromElementList(allRows);

        Player player = this.getPlayerFromDocument(doc);
        List<SpellReport> spellReports = this.getSpellReportsFromDamageRows(damageRows);
        return new Parse(fightId, player, spellReports);
    }

    private List<SpellReport> getSpellReportsFromDamageRows(List<Element> rows) {
        List<SpellReport> spellReports = new ArrayList<>();
        for (Element row : rows) {
            SpellReport spellReport = this.getSpellReportFromRow(row);
            spellReports.add(spellReport);
        }
        return spellReports;
    }

    private String getCellText(Element row, int index) {
        return row.selectFirst(String.format("td:nth-child(%d)", index)).text();
    }

    private int getCellIntValue(Element row, int index) {
        try {
            return Integer.parseInt(this.getCellText(row, index));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double getCellPercentageDoubleValue(Element row, int index) {
        try {
            return Double.parseDouble(this.getCellText(row, index).replaceFirst(" %", "")) / 100.d;
        } catch (NumberFormatException e) {
            return 0.d;
        }
    }

    private SpellDamage getSpellDamageFromRowWithOffset(Element row, int index) {
        int count = this.getCellIntValue(row, index);
        int avg = this.getCellIntValue(row, index + 1);
        int max = this.getCellIntValue(row, index + 2);
        return new SpellDamage(count, avg, max);
    }

    private SpellReport getSpellReportFromRow(Element row) {
        String name = this.getCellText(row, 1);
        int totalDamage = this.getCellIntValue(row, 2);

        SpellDamage normalHits = this.getSpellDamageFromRowWithOffset(row, 4);
        SpellDamage damageOverTimeHits = this.getSpellDamageFromRowWithOffset(row, 7);
        SpellDamage criticalHits = this.getSpellDamageFromRowWithOffset(row, 10);

        double criticalHitPercentage  = this.getCellPercentageDoubleValue(row, 13);
        double missPercentage = this.getCellPercentageDoubleValue(row, 14);
        double resistPercentage = this.getCellPercentageDoubleValue(row, 15);

        return new SpellReport(name, totalDamage, criticalHitPercentage, missPercentage, resistPercentage, normalHits, damageOverTimeHits, criticalHits);
    }

    private Player getPlayerFromDocument(Document doc) {
        Element nameNode = doc.selectFirst("center h1 span");
        String name = nameNode.text();
        String cls = nameNode.className().replaceFirst("c-", "");
        return new Player(cls, name);
    }

    private List<Element> removeNonDamageRowsFromElementList(Elements rows) {
        List<Element> damageRows = new ArrayList<>();
        for (Element row : rows) {
            if (this.isRowHeader(row) || !this.isDamageRow(row)) {
                continue;
            }
            damageRows.add(row);
        }
        return damageRows;
    }

    private boolean isDamageRow(Element row) {
        boolean isRegularDamageRow = row.select(".n span.o").size() > 0;
        boolean isGlancingDamageRow = row.text().contains("% glancing");
        return isRegularDamageRow || isGlancingDamageRow;
    }

    private boolean isRowHeader(Element row) {
        return row.select("th").size() > 0;
    }
}
