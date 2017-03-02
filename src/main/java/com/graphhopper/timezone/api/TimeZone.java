package com.graphhopper.timezone.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * Created by schroeder on 01/03/17.
 */
public class TimeZone {

    @NotNull
    private String timeZoneId;

    private String displayName;

    private OffsetDateTime localTime;

    public TimeZone(String timeZoneId, OffsetDateTime localTime, String displayName) {
        this.timeZoneId = timeZoneId;
        this.localTime = localTime;
        this.displayName = displayName;
    }

    @JsonProperty("timezone")
    public String getTimeZoneId() {
        return timeZoneId;
    }

    @JsonProperty("timezone_name")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("local_time")
    public OffsetDateTime getLocalTime() {
        return localTime;
    }


}
