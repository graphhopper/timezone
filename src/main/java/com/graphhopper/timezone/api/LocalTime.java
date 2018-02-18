package com.graphhopper.timezone.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Created by schroeder on 02/03/17.
 */
public class LocalTime {

    private final int offset;
    private final String offsetString;
    private final int year;
    private final String month;
    private final int dayOfMonth;
    private final String dayOfWeek;
    private final int monthValue;
    private final int hour;
    private final int minute;
    private final int second;
    private final int nano;

    public LocalTime(OffsetDateTime offsetDateTime, Locale locale) {
        offset = offsetDateTime.getOffset().getTotalSeconds();
        offsetString = offsetDateTime.getOffset().toString();
        year = offsetDateTime.getYear();
        month = offsetDateTime.getMonth().getDisplayName(TextStyle.FULL,locale);
        dayOfMonth = offsetDateTime.getDayOfMonth();
        dayOfWeek = offsetDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL,locale);
        monthValue = offsetDateTime.getMonthValue();
        hour = offsetDateTime.getHour();
        minute = offsetDateTime.getMinute();
        second = offsetDateTime.getSecond();
        nano = offsetDateTime.getNano();
    }

    public LocalTime(OffsetDateTime offsetDateTime) {
        this(offsetDateTime, Locale.forLanguageTag("en"));
    }

    @JsonProperty("offset")
    public int getOffset() {
        return offset;
    }
    
    @JsonProperty("offset_string")
    public String getOffsetString() {
    	return "GMT" + offsetString;
    }

    @JsonProperty("day_of_month")
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    @JsonProperty("day_of_week")
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @JsonProperty("month_value")
    public int getMonthValue() {
        return monthValue;
    }

    @JsonProperty("hour")
    public int getHour() {
        return hour;
    }

    @JsonProperty("minute")
    public int getMinute() {
        return minute;
    }

    @JsonProperty("second")
    public int getSecond() {
        return second;
    }

    @JsonProperty("nano")
    public int getNano() {
        return nano;
    }

    @JsonProperty("year")
    public int getYear() {
        return year;
    }

    @JsonProperty("month")
    public String getMonth() {
        return month;
    }
}
