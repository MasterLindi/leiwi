package commons.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Christoph on 26.05.2015.
 */
public class DateTimes {
    public static final String DATE_PATTERN = "dd.MM.yyyy"; // NON-NLS - this app currently used in Austria only
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN);
    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss"; // NON-NLS - this app currently used in Austria only
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(DATE_TIME_PATTERN);
    public static final String DATE_TIME_PATTERN_WITHOUT_SECONDS = "dd.MM.yyyy HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_WITHOUT_SECONDS = DateTimeFormat.forPattern(DATE_TIME_PATTERN_WITHOUT_SECONDS);
    public static final String TIME_PATTERN_WITHOUT_SECONDS = "HH:mm";
    public static final DateTimeFormatter TIME_FORMATTER_WITHOUT_SECONDS = DateTimeFormat.forPattern(TIME_PATTERN_WITHOUT_SECONDS);
    public static Boolean DateTimeNowUnderTest = false;
    public static DateTime DateTimeNowForTest = DateTimes.createDateTime(2013, 12, 10);

    private DateTimes() {
        //    private constructor to prevent instantiation
    }

    public static DateTime createDateTime(int year, int month, int day) {
        return new DateTime(year, month, day, 0, 0);
    }

    public static DateTime createDateTime(int year, int month, int day, int hours, int mins, int seconds) {
        return new DateTime(year, month, day, hours, mins, seconds);
    }

    public static DateTime createDateTime(String value) {
        return DATE_TIME_FORMATTER_WITHOUT_SECONDS.parseDateTime(value);
    }

    public static org.joda.time.LocalDateTime createLocalDateTime(String value) {
        return DATE_TIME_FORMATTER_WITHOUT_SECONDS.parseLocalDateTime(value);
    }

    public static LocalTime createTime(String value) {
        return TIME_FORMATTER_WITHOUT_SECONDS.parseLocalTime(value);
    }

    public static LocalDate today() {
        final DateTime now = now();
        return now.toLocalDate();
    }

    public static DateTime now() {
        if (DateTimeNowUnderTest) { return DateTimeNowForTest;}
        return DateTime.now();
    }

    public static LocalTime nowTime() {
        return DateTimes.now().toLocalTime();
    }

    public static void setTestDateTimeNow(DateTime testNow) {
        DateTimeNowForTest = testNow;
    }

    public static DateTime getBeginningOfDay(DateTime dateTime) {
        return dateTime.withTimeAtStartOfDay();
    }

    public static DateTime getBeginningOfDayForDatabase(DateTime dateTime) {
        DateTime beginningOfDay = getBeginningOfDay(dateTime);
        long offsetMsToGMT = beginningOfDay.getChronology().getZone().getOffset(beginningOfDay.toDate().getTime());
        return beginningOfDay.minus(offsetMsToGMT);
    }

    public static DateTime getEndOfDay(DateTime dateTime) {
        DateTime endOfDayDate = getBeginningOfDay(dateTime);
        endOfDayDate = endOfDayDate.plusHours(23).plusMinutes(59).plusSeconds(59);
        return endOfDayDate;
    }

    public static DateTime getEndOfDayForDatabase(DateTime dateTime) {
        DateTime endOfDayDate = getBeginningOfDayForDatabase(dateTime);
        endOfDayDate = endOfDayDate.plusHours(23).plusMinutes(59).plusSeconds(59);
        return endOfDayDate;
    }

    public static DateTime setToHourOfDay (DateTime dateTime, int hour) {
        DateTime result = dateTime.withTimeAtStartOfDay();
        result = result.plusHours(hour);
        return result;
    }

    public static String asFormattedString(DateTime dateTime) {
        return dateTime == null ? "" : DATE_TIME_FORMATTER.print(dateTime);
    }

    public static String asFormattedStringWithoutSeconds(DateTime dateTime) {
        return dateTime == null ? "" : DATE_TIME_FORMATTER_WITHOUT_SECONDS.print(dateTime);
    }

    public static String asFormattedStringWithoutTime(DateTime dateTime) {
        return dateTime == null ? "" : DATE_FORMATTER.print(dateTime);
    }

    public static String asFormattedTimeStringWithoutSeconds(DateTime dateTime) {
        return dateTime == null ? "" : TIME_FORMATTER_WITHOUT_SECONDS.print(dateTime);
    }

    public static String asFormattedTimeStringWithoutSeconds(LocalTime time) {
        return time == null ? "" : TIME_FORMATTER_WITHOUT_SECONDS.print(time);
    }

    public static void resetTestDateTime() {
        DateTimeNowUnderTest = false;
        DateTimeNowForTest = null;
    }

    public static boolean isToday(DateTime dateTime) {
        return DateTimes.now().toLocalDate().equals(dateTime.toLocalDate());
    }
}
