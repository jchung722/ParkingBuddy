package com.dev.ada.parkingbuddy.model;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;

import org.json.JSONArray;

/**
 * Created by Jessica on 1/24/17.
 */

@SodaEntity
public class Geometry {

    @SodaField("paths")
    private JSONArray paths;

    public JSONArray getPaths() {
        return paths;
    }

    public void setPaths(JSONArray paths) {
        this.paths = paths;
    }

}
