package io.github.pashazz.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Utils {
    public static String formatDuration(Duration dur) {
        List<String> parts = new ArrayList<>();
        if (dur.toDaysPart() > 0) {
            parts.add(format("%s days", dur.toDaysPart()));
        }
        if (dur.toHoursPart() > 0) {
            parts.add(format("%s hours", dur.toHoursPart()));
        }
        if (dur.toMinutesPart() > 0) {
            parts.add(format("%s mins", dur.toMinutesPart()));
        }
        if (dur.toSecondsPart() > 0) {
            parts.add(format("%s secs", dur.toSecondsPart()));
        }
        if (dur.toMillisPart() > 0) {
            parts.add(format("%s millis", dur.toMillisPart()));
        }
        if (dur.toNanosPart() > 0) {
            parts.add(format("%s nanos", dur.toNanosPart()));
        }
        return String.join(",", parts);
    }
}
