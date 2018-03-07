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

package com.graphhopper.timezone.webservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by schroeder on 22/12/14.
 */
public class AppConfig extends io.dropwizard.Configuration {

//    private String ipBlackList = "";
//
//    private String ipWhiteList = "";

    @JsonProperty("worldDataLocation")
    private String worldDataLocation = "";

//    @JsonProperty(value = "ipBlackList")
//    public String getIPBlackList() {
//        return ipBlackList;
//    }
//
//    @JsonProperty(value = "ipBlackList")
//    public void setIPBlackList(String ipBlackList) {
//        this.ipBlackList = ipBlackList;
//    }
//
//    @JsonProperty(value = "ipWhiteList")
//    public String getIPWhiteList() {
//        return ipWhiteList;
//    }
//
//    @JsonProperty(value = "ipWhiteList")
//    public void setIPWhiteList(String ipWhiteList) {
//        this.ipWhiteList = ipWhiteList;
//    }

    public String getWorldDataLocation(){
        return worldDataLocation;
    }

    public void setWorldDataLocation(String worldDataLocation){
        this.worldDataLocation = worldDataLocation;
    }
}
