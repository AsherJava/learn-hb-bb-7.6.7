/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.zbselector.service.impl;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;

public class FieldSelectLinksData {
    private String key;
    private int colNum;
    private int rowNum;
    private int colNumber;
    private int rowNumber;
    private String linkExpression;
    private String fieldtitle;
    private String fieldcode;
    private FieldType datatype;
    private FieldGatherType gatherType;
    private String tableName;
    private String ownerRegion;
    private DataRegionKind regionKind;
    private String tableKey;
    private String dataSheet;
    private DataLinkType dataLinkType;
    private String formKey;
    private String formCode;
    private String refEntityKey;

    public FieldSelectLinksData() {
    }

    public FieldSelectLinksData(FieldSelectLinksData other) {
        this.key = other.key;
        this.colNum = other.colNum;
        this.rowNum = other.rowNum;
        this.colNumber = other.colNumber;
        this.rowNumber = other.rowNumber;
        this.linkExpression = other.linkExpression;
        this.fieldtitle = other.fieldtitle;
        this.fieldcode = other.fieldcode;
        this.datatype = other.datatype;
        this.gatherType = other.gatherType;
        this.tableName = other.tableName;
        this.ownerRegion = other.ownerRegion;
        this.regionKind = other.regionKind;
        this.tableKey = other.tableKey;
        this.dataSheet = other.dataSheet;
        this.dataLinkType = other.dataLinkType;
        this.formKey = other.formKey;
        this.formCode = other.formCode;
        this.refEntityKey = other.refEntityKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCol(int col) {
        this.colNum = col;
    }

    public int getCol() {
        return this.colNum;
    }

    public void setRow(int row) {
        this.rowNum = row;
    }

    public int getRow() {
        return this.rowNum;
    }

    public int getColNumber() {
        return this.colNumber;
    }

    public void setColNumber(int colNumber) {
        this.colNumber = colNumber;
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    public String getFieldTitle() {
        return this.fieldtitle;
    }

    public void setFieldTitle(String fieldtitle) {
        this.fieldtitle = fieldtitle;
    }

    public String getFieldCode() {
        return this.fieldcode;
    }

    public void setFieldCode(String fieldcode) {
        this.fieldcode = fieldcode;
    }

    public void setDataType(FieldType datatype) {
        this.datatype = datatype;
    }

    public void setGatherType(FieldGatherType gatherType) {
        this.gatherType = gatherType;
    }

    public FieldGatherType getGatherType() {
        return this.gatherType;
    }

    public FieldType getDataType() {
        return this.datatype;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOwnerRegion() {
        return this.ownerRegion;
    }

    public void setOwnerRegion(String ownerRegion) {
        this.ownerRegion = ownerRegion;
    }

    public DataRegionKind getRegionKind() {
        return this.regionKind;
    }

    public void setRegionKind(DataRegionKind regionKind) {
        this.regionKind = regionKind;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getDataSheet() {
        return this.dataSheet;
    }

    public void setDataSheet(String dataSheet) {
        this.dataSheet = dataSheet;
    }

    public DataLinkType getDataLinkType() {
        return this.dataLinkType;
    }

    public void setDataLinkType(DataLinkType dataLinkType) {
        this.dataLinkType = dataLinkType;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getRefEntityKey() {
        return this.refEntityKey;
    }

    public void setRefEntityKey(String refEntityKey) {
        this.refEntityKey = refEntityKey;
    }
}

