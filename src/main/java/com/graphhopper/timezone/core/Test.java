package com.graphhopper.timezone.core;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.index.quadtree.Quadtree;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by schroeder on 01/03/17.
 */
public class Test {

    static GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

    public static void main(String[] args) throws IOException {
//        File file = new File("/Users/schroeder/Downloads/world/tz_world.shp");
//
//        Quadtree quadtree = new Quadtree();
//        new TZShapeReader(quadtree).read(file);
//
//        long start = System.currentTimeMillis();
//        String berlinTZID = getTimeZone(52.522018, 13.425591, quadtree);
//        System.out.println("Berlin " + berlinTZID + " " + getLocalTime(berlinTZID));
//        String englandTZID = getTimeZone(51.536086, -1.605468, quadtree);
//        System.out.println("England " + englandTZID + " " + getLocalTime(englandTZID));
//        String rumanienTZID = getTimeZone(44.574817, 28.453125, quadtree);
//        System.out.println("Rumänien " + rumanienTZID + " " + getLocalTime(rumanienTZID));
//        String moskauTZID = getTimeZone(55.733296,37.583496, quadtree);
//        System.out.println("Moskau " + moskauTZID + " " + getLocalTime(moskauTZID));
//        String washTZID = getTimeZone(38.479395, -77.610351, quadtree);
//        System.out.println("Washington " + washTZID + " " + getLocalTime(washTZID));
//        String hondTZID = getTimeZone(14.306969, -87.322265, quadtree);
//        System.out.println("Honduras " + hondTZID + " " + getLocalTime(hondTZID));
//        String chileTZID = getTimeZone(-24.046464, -69.304687, quadtree);
//        System.out.println("Chile " + chileTZID + " " + getLocalTime(chileTZID));
//
//        System.out.println("took: " + (System.currentTimeMillis() - start));
//
////        System.out.println("Berlin " + getTimeZone(52.522018,13.425591,quadtree));
////        System.out.println("Berlin " + getTimeZone(52.522018,13.425591,quadtree));
////        System.out.println("Berlin " + getTimeZone(52.522018,13.425591,quadtree));
////        System.out.println("Berlin " + getTimeZone(52.522018,13.425591,quadtree));
////        System.out.println("Berlin " + getTimeZone(52.522018,13.425591,quadtree));
////        System.out.println("Berlin " + getTimeZone(52.522018,13.425591,quadtree));
////        System.out.println("Berlin " + getTimeZone(52.522018,13.425591,quadtree));



    }

    static LocalTime getLocalTime(String tzId){
        return LocalTime.now(TimeZone.getTimeZone(tzId).toZoneId());
    }

    static String getTimeZone(double lat, double lon, Quadtree quadtree){
        Point point = geometryFactory.createPoint(new Coordinate(lon,lat));
        List<Object> regions = quadtree.query(point.getEnvelopeInternal());
        for(Object o : regions){
            SimpleFeature feature = (SimpleFeature) o;
            Geometry geom = (Geometry) feature.getDefaultGeometry();
            if(point.within(geom)) {
                return (String)(feature.getAttribute("TZID"));
            }
        }
        return "";
    }
}
