package dev.pr.habittracker.model.enums;

import lombok.Getter;

import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Optional;

@Getter
public enum TimeOfDay {
    MORNING( 6, 12),
    NOON( 12, 14),
    AFTERNOON( 14, 18),
    EVENING( 18, 22),
    NIGHT( 22, 6),
    ALL_DAY( 0, 0);

    private final int startTime;
    private final int endTime;

    TimeOfDay(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeOfDay getTimeOfDay(boolean current) {
        LocalTime currentTime = LocalTime.now();
        EnumSet<TimeOfDay> timesOfDay = EnumSet.allOf(TimeOfDay.class);

        Optional<TimeOfDay> matchedTime = timesOfDay.stream()
                .filter(timeOfDay -> {
                    LocalTime startTime = LocalTime.of(timeOfDay.getStartTime(), 0);
                    LocalTime endTime = LocalTime.of(timeOfDay.getEndTime(), 0);
                    return current == (currentTime.equals(startTime) || currentTime.isAfter(startTime)) && currentTime.isBefore(endTime);
                })
                .findFirst();

        return matchedTime.orElse(TimeOfDay.ALL_DAY);
    }
}

