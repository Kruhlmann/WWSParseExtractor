package main.java.dev.kruhlmann.wws_parse_extractor;

public class SpellReport {
    private String name;
    private final int totalDamage;
    private final double criticalHitPercentage;
    private final double missPercentage;
    private final double resistPercentage;
    private final SpellDamage normalHits;
    private final SpellDamage damageOverTimeHits;
    private final SpellDamage criticalHits;

    public SpellReport(String name, int totalDamage, double criticalHitPercentage, double missPercentage, double resistPercentage, SpellDamage normalHits, SpellDamage damageOverTimeHits, SpellDamage criticalHits) {
        this.setName(name);
        this.totalDamage = totalDamage;
        this.criticalHitPercentage = criticalHitPercentage;
        this.missPercentage = missPercentage;
        this.resistPercentage = resistPercentage;
        this.normalHits = normalHits;
        this.damageOverTimeHits = damageOverTimeHits;
        this.criticalHits = criticalHits;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name.contains("% glancing")) {
            this.name = "Melee (glancing)";
        } else {
            this.name = name;
        }
    }

    public double getCriticalHitPercentage() {
        return criticalHitPercentage;
    }

    public int getTotalDamage() {
        return this.totalDamage;
    }

    public double getMissPercentage() {
        return missPercentage;
    }

    public double getResistPercentage() {
        return resistPercentage;
    }

    public SpellDamage getNormalHits() {
        return normalHits;
    }

    public SpellDamage getDamageOverTimeHits() {
        return damageOverTimeHits;
    }

    public SpellDamage getCriticalHits() {
        return criticalHits;
    }
}
