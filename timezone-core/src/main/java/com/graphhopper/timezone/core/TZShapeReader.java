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
