package dev.kruhlmann.wws_parse_extractor.spell;

public class SpellDamage {
    private final int count;
    private final int averageDamage;
    private final int maxDamage;

    public SpellDamage(int count, int averageDamage, int maxDamage) {
        this.count = count;
        this.averageDamage = averageDamage;
        this.maxDamage = maxDamage;
    }

    public int getCount() {
        return count;
    }

    public int getAverageDamage() {
        return averageDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }
}
