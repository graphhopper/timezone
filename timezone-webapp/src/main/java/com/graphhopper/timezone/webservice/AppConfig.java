package com.graphhopper.timezone.webservice;

/*
 * Copyright 2014-2016 GraphHopper GmbH
 *
 * NOTICE:  All information contained herein is, and remains the property
 * of GraphHopper GmbH. The intellectual and technical concepts contained
 * herein are proprietary to GraphHopper GmbH, and are protected by trade secret
 * or copyright law. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission
 * is obtained from GraphHopper GmbH.
 */

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
