package com.dev.ada.parkingbuddy;


import com.google.android.gms.maps.model.LatLng;

public class MarkerLocation {
    public double latitude;
    public double longitude;

    public MarkerLocation() {
    }

    public MarkerLocation(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    LatLng toLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }
}