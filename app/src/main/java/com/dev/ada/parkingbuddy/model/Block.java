package com.dev.ada.parkingbuddy.model;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;

/**
 * Created by Jessica on 1/19/17.
 */

@SodaEntity
public class Block {

    @SodaField("objectid")
    private Integer objectId;

    @SodaField("parking_category")
    private String parkingCategory;

    @SodaField("parking_time_limit")
    private Integer parkingTimeLimit;

    @SodaField("shape")
    private Shape shape;


    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getParkingCategory() {
        return parkingCategory;
    }

    public void setParkingCategory(String parkingCategory) {
        this.parkingCategory = parkingCategory;
    }

    public Integer getParkingTimeLimit() {
        return parkingTimeLimit;
    }

    public void setParkingTimeLimit(Integer parkingTimeLimit) {
        this.parkingTimeLimit = parkingTimeLimit;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

}
