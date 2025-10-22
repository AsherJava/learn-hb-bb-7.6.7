/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FieldInfo
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FieldInfo;
import java.io.Serializable;

public class FieldInfoDTO
implements FieldInfo,
Serializable {
    private static final long serialVersionUID = 1L;
    private String fieldKey;
    private String fieldCode;
    private String fieldTitle;
    private String row;
    private String col;
    private boolean floatRegion;
    private String regionKey;

    public FieldInfoDTO() {
    }

    public FieldInfoDTO(String fieldKey, String fieldCode, String fieldTitle, String row, String col) {
        this.fieldKey = fieldKey;
        this.fieldCode = fieldCode;
        this.fieldTitle = fieldTitle;
        this.row = row;
        this.col = col;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getRow() {
        return this.row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return this.col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public boolean isFloatRegion() {
        return this.floatRegion;
    }

    public void setFloatRegion(boolean floatRegion) {
        this.floatRegion = floatRegion;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }
}

