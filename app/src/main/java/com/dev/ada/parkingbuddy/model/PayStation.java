package com.dev.ada.parkingbuddy.model;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;
import com.socrata.android.soql.datatypes.Location;

/**
 * Created by Jessica on 1/25/17.
 */

@SodaEntity
public class PayStation {

    @SodaField("objectid")
    private Integer objectId;

    @SodaField("wkd_rate1")
    private Double weekDayRate;

    @SodaField("start_time_wkd")
    private String startTimeWkd;

    @SodaField("end_time_wkd")
    private String endTimeWkd;

    @SodaField("pay_by_phone")
    private String payByPhone;

    @SodaField("shape")
    private Location shape;

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Double getWeekDayRate() {
        return weekDayRate;
    }

    public void setWeekDayRate(Double weekDayRate) {
        this.weekDayRate = weekDayRate;
    }

    public String getStartTimeWkd() {
        return startTimeWkd;
    }

    public void setStartTimeWkd(String startTimeWkd) {
        this.startTimeWkd = startTimeWkd;
    }

    public String getEndTimeWkd() {
        return endTimeWkd;
    }

    public void setEndTimeWkd(String endTimeWkd) {
        this.endTimeWkd = endTimeWkd;
    }

    public String getPayByPhone() {
        return payByPhone;
    }

    public void setPayByPhone(String payByPhone) {
        this.payByPhone = payByPhone;
    }

    public Location getShape() {
        return shape;
    }

    public void setShape(Location shape) {
        this.shape = shape;
    }

}