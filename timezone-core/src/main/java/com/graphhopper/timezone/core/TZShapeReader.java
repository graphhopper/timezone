package com.graphhopper.timezone.core;

import com.vividsolutions.jts.index.quadtree.Quadtree;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by schroeder on 01/03/17.
 */
public class TZShapeReader {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TZShapeReader.class);

    private Quadtree quadtree;

    public TZShapeReader(Quadtree quadtree) {
        this.quadtree = quadtree;
    }

    public void read(URL file) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("url", file);
        FeatureIterator<SimpleFeature> features = null;
        DataStore dataStore = null;
        int count = 0;
        try{
            dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];
            FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
            FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures();
            features = collection.features();
            LOGGER.info("reading world time zones ...");
            while (features.hasNext()) {
                count++;
                SimpleFeature feature = features.next();
                ReferencedEnvelope referencedEnvelope = new ReferencedEnvelope(feature.getBounds());
                quadtree.insert(referencedEnvelope,feature);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (features != null)
                features.close();
            if (dataStore != null)
                dataStore.dispose();
        }
        LOGGER.info(count + " features read");
    }
}
