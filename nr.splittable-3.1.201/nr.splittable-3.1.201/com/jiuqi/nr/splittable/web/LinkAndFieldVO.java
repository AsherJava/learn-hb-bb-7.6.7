/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.web;

import com.jiuqi.nr.splittable.bean.CellObj;

public class LinkAndFieldVO {
    private String id;
    private String regionId;
    private String linkId;
    private String uniqueCode;
    private CellObj newCell;
    private CellObj oldCell;
    private String zbId;
    private String zbTitle;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"id\":").append(this.id).append(", \"regionId\":").append(this.regionId).append(", \"linkId\":").append(this.linkId).append(", \"uniqueCode\":").append(this.uniqueCode).append(", \"newCell\":").append(this.newCell).append(", \"oldCell\":").append(this.oldCell).append(", \"zbId\":").append(this.zbId).append(", \"zbTitle\":").append(this.zbTitle).append('}');
        return sb.toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getLinkId() {
        return this.linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public CellObj getNewCell() {
        return this.newCell;
    }

    public void setNewCell(CellObj newCell) {
        this.newCell = newCell;
    }

    public CellObj getOldCell() {
        return this.oldCell;
    }

    public void setOldCell(CellObj oldCell) {
        this.oldCell = oldCell;
    }

    public String getZbId() {
        return this.zbId;
    }

    public void setZbId(String zbId) {
        this.zbId = zbId;
    }

    public String getZbTitle() {
        return this.zbTitle;
    }

    public void setZbTitle(String zbTitle) {
        this.zbTitle = zbTitle;
    }
}

