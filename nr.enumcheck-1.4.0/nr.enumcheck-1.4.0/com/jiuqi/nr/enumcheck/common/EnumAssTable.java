/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.enumcheck.common.DataFdInfo;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public class EnumAssTable {
    private IEntityAttribute enumCodeField;
    private IEntityAttribute enumPCodeField;
    private List<IEntityAttribute> enumTitleField;
    private DataFdInfo dataField;
    private TableModelDefine table;
    private boolean allUndefineCode;
    private boolean onlyLeafNode;
    private int fixSize;
    private boolean canNull;
    private boolean allowMultipleSelect;
    private String dataLinkKey;
    private String regionId;
    private String entityViewKey;

    public IEntityAttribute getEnumCodeField() {
        return this.enumCodeField;
    }

    public void setEnumCodeField(IEntityAttribute enumCodeField) {
        this.enumCodeField = enumCodeField;
    }

    public IEntityAttribute getEnumPCodeField() {
        return this.enumPCodeField;
    }

    public void setEnumPCodeField(IEntityAttribute enumPCodeField) {
        this.enumPCodeField = enumPCodeField;
    }

    public List<IEntityAttribute> getEnumTitleField() {
        return this.enumTitleField;
    }

    public void setEnumTitleField(List<IEntityAttribute> enumTitleField) {
        this.enumTitleField = enumTitleField;
    }

    public DataFdInfo getDataField() {
        return this.dataField;
    }

    public void setDataField(DataFdInfo dataField) {
        this.dataField = dataField;
    }

    public TableModelDefine getTable() {
        return this.table;
    }

    public void setTable(TableModelDefine table) {
        this.table = table;
    }

    public boolean getAllUndefineCode() {
        return this.allUndefineCode;
    }

    public void setAllUndefineCode(boolean allUndefineCode) {
        this.allUndefineCode = allUndefineCode;
    }

    public boolean getOnlyLeafNode() {
        return this.onlyLeafNode;
    }

    public void setOnlyLeafNode(boolean onlyLeafNode) {
        this.onlyLeafNode = onlyLeafNode;
    }

    public void setAllowMultipleSelect(boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public boolean getAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public int getFixSize() {
        return this.fixSize;
    }

    public void setFixSize(int fixSize) {
        this.fixSize = fixSize;
    }

    public boolean getCanNull() {
        return this.canNull;
    }

    public void setCanNull(boolean canNull) {
        this.canNull = canNull;
    }

    public void setDataLinkKey(String key) {
        this.dataLinkKey = key;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getEntityViewKey() {
        return this.entityViewKey;
    }

    public void setEntityViewKey(String entityViewKey) {
        this.entityViewKey = entityViewKey;
    }
}

