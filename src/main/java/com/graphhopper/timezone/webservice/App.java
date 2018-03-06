package com.graphhopper.timezone.webservice;

import com.graphhopper.timezone.TimeZoneReader;

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

import com.graphhopper.timezone.webservice.TimeZoneService;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
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

        TimeZoneReader timeZoneReader = new TimeZoneReader();
        TimeZoneService timeZoneService = new TimeZoneService(timeZoneReader);

        environment.jersey().register(timeZoneService);

//        // healthChecks
//        environment.healthChecks().register("app-health-check", new AppHealthCheck());
//
//        // filter
//        environment.servlets().addFilter("ip-filter", new IPFilter(configuration.getIPWhiteList(), configuration.getIPBlackList())).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
