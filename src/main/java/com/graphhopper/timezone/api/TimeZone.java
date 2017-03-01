package com.graphhopper.timezone.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * Created by schroeder on 01/03/17.
 */
public class TimeZone {

    @NotNull
    private String timeZoneId;

    private LocalTime localTime;

    private int offset;


    public TimeZone(String timeZoneId, LocalTime localTime, int offset) {
        this.timeZoneId = timeZoneId;
        this.localTime = localTime;
        this.offset = offset;

    }

    @JsonProperty("timezone")
    public String getTimeZoneId() {
        return timeZoneId;
    }

    @JsonProperty("local_time")
    public LocalTime getLocalTime() {
        return localTime;
    }

    @JsonProperty("offset")
    public int getOffset(){
        return offset;
    }

}
