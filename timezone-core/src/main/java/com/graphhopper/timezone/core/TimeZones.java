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

    public static void main(String[] args) throws IOException {
        TimeZones timeZones = new TimeZones();
        timeZones.initWithWorldData(new File("./world-data/tz_world.shp").toURI().toURL());
        TimeZone tz = timeZones.getTimeZone(40.713956,-75.767577);
        long unixTimeStamp = 1488363179;
        OffsetDateTime offsetDateTime = timeZones.getOffsetDateTime(unixTimeStamp,tz);
        System.out.println(offsetDateTime);
    }

    private GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

    private Quadtree quadtree;

    public void initWithWorldData(URL worldDataShp) throws IOException {
        this.quadtree = new Quadtree();
//        URL world = this.getClass().getResource("tz_world.shp");
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
