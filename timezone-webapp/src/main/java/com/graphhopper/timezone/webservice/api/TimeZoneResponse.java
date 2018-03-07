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


import javax.validation.constraints.NotNull;

/**
 * Created by schroeder on 01/03/17.
 */
public class TimeZoneResponse {

    @NotNull
    private String timeZoneId;

    private String displayName;

    private LocalTime localTime;

    public TimeZoneResponse(String timeZoneId, LocalTime localTime, String displayName) {
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
    public LocalTime getLocalTime() {
        return localTime;
    }

    public String toString() {
        return timeZoneId + " (" + displayName + " : " + localTime.getOffsetString() + ")";
    }
}
