package com.dev.ada.parkingbuddy.model;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;
import com.socrata.android.soql.datatypes.Location;

/**
 * Created by Jessica on 1/18/17.
 */

@SodaEntity
public class Sign {

    @SodaField("objectid")
    private Integer objectId;

    @SodaField("compkey")
    private Integer compKey;

    @SodaField("comptype")
    private Integer compType;

    @SodaField("segkey")
    private Integer segKey;

    @SodaField("elmntkey")
    private Integer elmntKey;

    @SodaField("distance")
    private Double distance;

    @SodaField("width")
    private Double width;

    @SodaField("unitid")
    private String unitId;

    @SodaField("unittype")
    private String unitType;

    @SodaField("signtype")
    private String signType;

    @SodaField("standardtext")
    private String standardText;

    @SodaField("category")
    private String category;

    @SodaField("categorydescr")
    private String categoryDescr;

    @SodaField("unitdesc")
    private String unitDesc;

    @SodaField("facing")
    private String facing;

    @SodaField("support")
    private String support;

    @SodaField("supportdescr")
    private String supportDescr;

    @SodaField("color1")
    private String color1;

    @SodaField("signsz")
    private String signsz;

    @SodaField("reflectiveyn")
    private String reflectiveyn;

    @SodaField("fieldnotes")
    private String fieldNotes;

    @SodaField("custom")
    private String custom;

    @SodaField("customtext")
    private String customText;

    @SodaField("startday")
    private String startDay;

    @SodaField("starttime")
    private String startTime;

    @SodaField("endtime")
    private String endTime;

    @SodaField("shape")
    private Location shape;

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getCompKey() {
        return compKey;
    }

    public void setCompKey(Integer compKey) {
        this.compKey = compKey;
    }

    public Integer getCompType() {
        return compType;
    }

    public void setCompType(Integer compType) {
        this.compType = compType;
    }

    public Integer getSegKey() {
        return segKey;
    }

    public void setSegKey(Integer segKey) {
        this.segKey = segKey;
    }

    public Integer getElmntKey() {
        return elmntKey;
    }

    public void setElmntKey(Integer elmntKey) {
        this.elmntKey = elmntKey;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getStandardText() {
        return standardText;
    }

    public void setStandardText(String standardText) {
        this.standardText = standardText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryDescr() {
        return categoryDescr;
    }

    public void setCategoryDescr(String categoryDescr) {
        this.categoryDescr = categoryDescr;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getSupportDescr() {
        return supportDescr;
    }

    public void setSupportDescr(String supportDescr) {
        this.supportDescr = supportDescr;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getSignsz() {
        return signsz;
    }

    public void setSignsz(String signsz) {
        this.signsz = signsz;
    }

    public String getReflectiveyn() {
        return reflectiveyn;
    }

    public void setReflectiveyn(String reflectiveyn) {
        this.reflectiveyn = reflectiveyn;
    }

    public String getFieldNotes() {
        return fieldNotes;
    }

    public void setFieldNotes(String fieldNotes) {
        this.fieldNotes = fieldNotes;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getCustomText() {
        return customText;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Location getShape() {
        return shape;
    }

    public void setShape(Location shape) {
        this.shape = shape;
    }

}