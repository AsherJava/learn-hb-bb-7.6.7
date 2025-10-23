/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ReverseModeRegionDTO {
    private String regionKey;
    private Integer regionKind = 0;
    private Integer regionTop = 0;
    private Integer regionLeft = 0;
    private String tableKey;
    private String tableCode;
    private Integer fieldNum;
    private Integer fieldKind;
    private Integer fieldType;
    private List<String> fieldCodes;
    private List<Integer> fieldTypes;

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public Integer getRegionKind() {
        return this.regionKind;
    }

    public void setRegionKind(Integer regionKind) {
        this.regionKind = regionKind;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public Integer getFieldNum() {
        return this.fieldNum;
    }

    public void setFieldNum(Integer fieldNum) {
        this.fieldNum = fieldNum;
    }

    public Integer getFieldKind() {
        return this.fieldKind;
    }

    public void setFieldKind(Integer fieldKind) {
        this.fieldKind = fieldKind;
    }

    public Integer getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public List<String> getFieldCodes() {
        return this.fieldCodes;
    }

    public void setFieldCodes(List<String> fieldCodes) {
        this.fieldCodes = fieldCodes;
    }

    public Integer getRegionTop() {
        return this.regionTop;
    }

    public void setRegionTop(Integer regionTop) {
        this.regionTop = regionTop;
    }

    public Integer getRegionLeft() {
        return this.regionLeft;
    }

    public void setRegionLeft(Integer regionLeft) {
        this.regionLeft = regionLeft;
    }

    public List<Integer> getFieldTypes() {
        if (this.fieldTypes == null) {
            this.fieldTypes = new ArrayList<Integer>();
        }
        return this.fieldTypes;
    }

    public void setFieldTypes(List<Integer> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }
}

