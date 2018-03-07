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

import com.graphhopper.timezone.core.TimeZones;

import com.graphhopper.timezone.webservice.resources.TimeZoneService;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by schroeder on 22/12/14.
 */
public class App extends Application<AppConfig> {

    /*

     @ToDo remove meta info
     @ToDo more stats such as expected waiting time queue

     */
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "timezone app";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {

        // we need only the spec json to be in sync with repo
        bootstrap.addBundle(new AssetsBundle("/assets", "/lib", null, "lib"));
    }

    @Override
    public void run(AppConfig configuration, Environment environment) throws IOException {

        // Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        TimeZones timeZones = new TimeZones();
        timeZones.initWithWorldData(new File(configuration.getWorldDataLocation()).toURI().toURL());
        TimeZoneService timeZoneService = new TimeZoneService(timeZones);

        environment.jersey().register(timeZoneService);

//        // healthChecks
//        environment.healthChecks().register("app-health-check", new AppHealthCheck());
//
//        // filter
//        environment.servlets().addFilter("ip-filter", new IPFilter(configuration.getIPWhiteList(), configuration.getIPBlackList())).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
