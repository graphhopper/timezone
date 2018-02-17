package com.graphhopper.timezone;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.TimeZone;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;

import com.graphhopper.timezone.api.LocalTime;
import com.graphhopper.timezone.core.TZShapeReader;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.index.quadtree.Quadtree;

public class TimeZoneReader {

    private GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

    private Quadtree quadtree;

    public TimeZoneReader() throws IOException {
        this.quadtree = new Quadtree();
        URL world = this.getClass().getResource("tz_world.shp");
        new TZShapeReader(quadtree).read(world);
    }

    public Quadtree getQuadtree() {
        return this.quadtree;
    }

    public OffsetDateTime getLocalTime(TimeZone timeZone, long timestamp){
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of(timeZone.getID()));
        return offsetDateTime;
    }

    public com.graphhopper.timezone.api.TimeZone getTimeZone(double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lon,lat));
        List<Object> regions = quadtree.query(point.getEnvelopeInternal());
        for(Object o : regions){
            SimpleFeature feature = (SimpleFeature) o;
            Geometry geom = (Geometry) feature.getDefaultGeometry();
            if(point.within(geom)) {
                String timeZoneId = (String)(feature.getAttribute("TZID"));
                long timestamp = new java.util.Date().getTime();
                TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
                OffsetDateTime localTime = getLocalTime(timeZone,timestamp);

                return new com.graphhopper.timezone.api.TimeZone(timeZoneId, new LocalTime(localTime), timeZone.getDisplayName());
            }
        }        
        return null;
    }
}
