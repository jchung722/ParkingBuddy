package com.dev.ada.parkingbuddy.model;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;

/**
 * Created by Jessica on 1/24/17.
 */


@SodaEntity
public class Shape {

    @SodaField("latitude")
    private Double latitude;

    @SodaField("geometry")
    private Geometry geometry;

    @SodaField("needsRecoding")
    private Boolean needsRecoding;

    @SodaField("longitude")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Boolean getNeedsRecoding() {
        return needsRecoding;
    }

    public void setNeedsRecoding(Boolean needsRecoding) {
        this.needsRecoding = needsRecoding;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
