/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.definition.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class LineProp
implements Serializable {
    private static final long serialVersionUID = -3926694551845328266L;
    @JsonProperty(value="dataBaseKey")
    private String dataBaseKey;
    @JsonProperty(value="rowNumber")
    private int rowNumber;
    @JsonProperty(value="isDropDown")
    private boolean isDropDown;
    @JsonProperty(value="isInsertRow")
    private boolean isInsertRow;

    @JsonIgnore
    public String getDataBaseKey() {
        return this.dataBaseKey;
    }

    @JsonIgnore
    public void setDataBaseKey(String dataBaseKey) {
        this.dataBaseKey = dataBaseKey;
    }

    @JsonIgnore
    public int getRowNumber() {
        return this.rowNumber;
    }

    @JsonIgnore
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @JsonIgnore
    public boolean isDropDown() {
        return this.isDropDown;
    }

    @JsonIgnore
    public void setDropDown(boolean isDropDown) {
        this.isDropDown = isDropDown;
    }

    @JsonIgnore
    public boolean isInsertRow() {
        return this.isInsertRow;
    }

    @JsonIgnore
    public void setInsertRow(boolean isInsertRow) {
        this.isInsertRow = isInsertRow;
    }
}

