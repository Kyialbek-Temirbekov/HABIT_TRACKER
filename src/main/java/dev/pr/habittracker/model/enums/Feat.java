package dev.pr.habittracker.model.enums;

public enum Feat {
    DILIGENT_PERFORMER("Complete 50% of the days without missing."),
    PERFECT_STREAK("Achieve a flawless 100% completion rate without any misses."),
    FLAWLESS_MONTH("Successfully complete an entire month without any misses."),
    MASTER_OF_PERSISTENCE("Maintain a streak of six months without any misses, showcasing your commitment and dedication.");
    private final String description;

    Feat(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
