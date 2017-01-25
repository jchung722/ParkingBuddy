package com.dev.ada.parkingbuddy.model;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;

/**
 * Created by Jessica on 1/19/17.
 */

@SodaEntity
public class Category {

    @SodaField("objectid")
    private Integer objectId;

    @SodaField("blockid")
    private String blockId;

    @SodaField("rownbr")
    private Integer rownbr;

    @SodaField("block_st")
    private Double blockSt;

    @SodaField("block_end")
    private Double blockEnd;

    @SodaField("width_offset")
    private Integer widthOffset;

    @SodaField("geobasys")
    private Integer geoBasys;

    @SodaField("spacelength")
    private Integer spaceLength;

    @SodaField("spacetype")
    private String spaceType;

    @SodaField("spacetypedesc")
    private String spaceTypeDesc;

    @SodaField("time_limit")
    private String timeLimit;

    @SodaField("space_nb")
    private String spaceNb;

    @SodaField("category")
    private String category;

    @SodaField("shape")
    private Shape shape;

    @SodaField("side")
    private String side;

    @SodaField("current_status")
    private String currentStatus;

    @SodaField("shape_length")
    private Double shapeLength;

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Integer getRownbr() {
        return rownbr;
    }

    public void setRownbr(Integer rownbr) {
        this.rownbr = rownbr;
    }

    public Double getBlockSt() {
        return blockSt;
    }

    public void setBlockSt(Double blockSt) {
        this.blockSt = blockSt;
    }

    public Double getBlockEnd() {
        return blockEnd;
    }

    public void setBlockEnd(Double blockEnd) {
        this.blockEnd = blockEnd;
    }

    public Integer getWidthOffset() {
        return widthOffset;
    }

    public void setWidthOffset(Integer widthOffset) {
        this.widthOffset = widthOffset;
    }

    public Integer getGeoBasys() {
        return geoBasys;
    }

    public void setGeoBasys(Integer geoBasys) {
        this.geoBasys = geoBasys;
    }

    public Integer getSpaceLength() {
        return spaceLength;
    }

    public void setSpaceLength(Integer spaceLength) {
        this.spaceLength = spaceLength;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public String getSpaceTypeDesc() {
        return spaceTypeDesc;
    }

    public void setSpaceTypeDesc(String spaceTypeDesc) {
        this.spaceTypeDesc = spaceTypeDesc;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getSpaceNb() {
        return spaceNb;
    }

    public void setSpaceNb(String spaceNb) {
        this.spaceNb = spaceNb;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Double getShapeLength() {
        return shapeLength;
    }

    public void setShapeLength(Double shapeLength) {
        this.shapeLength = shapeLength;
    }

}
