/*
 * Licensed to GraphHopper GmbH under one or more contributor
 * license agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * GraphHopper GmbH licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.graphhopper.timezone.webservice.api;

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
