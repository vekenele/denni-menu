package cz.muni.fi.pb138.app.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * App utils.
 *
 * @author Peter Neupauer
 */
public class Utils {

    /**
     * Gets date time formatter from default patterns
     *
     * @return the date time formatter
     */
    public static DateTimeFormatter getDateTimeFormatter() {
        return getDateTimeFormatter("dd.MM.yyyy", "dd. MM. yyyy", "yyyy-MM-dd");
    }

    /**
     * Gets date time formatter from given patterns.
     *
     * @param patterns the patterns
     * @return the date time formatter
     */
    public static DateTimeFormatter getDateTimeFormatter(String... patterns) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        for (String pattern : patterns) {
            builder.appendOptional(DateTimeFormatter.ofPattern(pattern));
        }
        return builder.toFormatter();
    }

    /**
     * Checks a valid date.
     *
     * @param date the date
     * @return the boolean
     */
    public static boolean isValidDate(String date) {
        return isValidDate(date, getDateTimeFormatter());
    }

    /**
     * Checks a valid date.
     *
     * @param date      the date
     * @param formatter the formatter
     * @return the boolean
     */
    public static boolean isValidDate(String date, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }
}
