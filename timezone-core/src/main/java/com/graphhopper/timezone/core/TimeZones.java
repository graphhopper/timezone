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

package com.graphhopper.timezone.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.index.quadtree.Quadtree;

public class TimeZones {

    private GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

    private Quadtree quadtree;

    public void initWithWorldData(URL worldDataShp) throws IOException {
        this.quadtree = new Quadtree();
        new TZShapeReader(quadtree).read(worldDataShp);
    }

    public Quadtree getQuadtree() {
        return this.quadtree;
    }

    public OffsetDateTime getOffsetDateTime(long epochSecond, double lat, double lon){
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneId.of(getTimeZone(lat,lon).getID()));
        return offsetDateTime;
    }

    public OffsetDateTime getOffsetDateTime(long epochSecond, TimeZone timeZone){
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneId.of(timeZone.getID()));
        return offsetDateTime;
    }


    public TimeZone getTimeZone(double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lon,lat));
        List<Object> regions = quadtree.query(point.getEnvelopeInternal());
        for(Object o : regions){
            SimpleFeature feature = (SimpleFeature) o;
            Geometry geom = (Geometry) feature.getDefaultGeometry();
            if(point.within(geom)) {
                return TimeZone.getTimeZone((String)(feature.getAttribute("TZID")));
            }
        }
        //not found, thus find nearest time zone
        double minDistance = Double.MAX_VALUE;
        SimpleFeature minFeature = null;
        for(Object o : regions){
            SimpleFeature feature = (SimpleFeature) o;
            Geometry geom = (Geometry) feature.getDefaultGeometry();
            double distance = point.distance(geom);
            if(distance < minDistance){
                minFeature = feature;
                minDistance = distance;
            }
        }
        if(minFeature != null) {
            return TimeZone.getTimeZone((String) (minFeature.getAttribute("TZID")));
        }
        throw new IllegalStateException("could not determine a time zone for location: " + lat + ", " + lon);
    }
}
