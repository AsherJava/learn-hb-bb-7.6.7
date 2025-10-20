/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 */
package com.jiuqi.va.biz.front;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTableInfo;
import com.jiuqi.va.biz.intf.data.DataTableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class FrontDataTableDefine
implements DataTableInfo {
    private UUID id;
    private String name;
    private String title;
    private DataTableType tableType;
    private String tableName;
    private UUID parentId;
    private boolean required;
    private boolean readonly;
    private boolean addRow;
    private boolean delRow;
    private boolean single;
    private boolean fixed;
    private boolean hideCkeckRowNum;
    private boolean enableFilter;
    private boolean filterCondition;
    List<DataFieldDefineImpl> fields = new ArrayList<DataFieldDefineImpl>();
    private transient Map<String, DataFieldDefine> nameMap = new HashMap<String, DataFieldDefine>();

    boolean addField(DataFieldDefineImpl field) {
        boolean result;
        boolean bl = result = this.nameMap.put(field.getName(), field) == null;
        if (result) {
            this.fields.add(field);
        }
        return result;
    }

    public List<DataFieldDefineImpl> getFields() {
        return this.fields;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public DataTableType getTableType() {
        return this.tableType;
    }

    public void setTableType(DataTableType tableType) {
        this.tableType = tableType;
    }

    @Override
    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public UUID getParentId() {
        return this.parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    @Override
    public boolean isAddRow() {
        return this.addRow;
    }

    public void setAddRow(boolean addRow) {
        this.addRow = addRow;
    }

    @Override
    public boolean isDelRow() {
        return this.delRow;
    }

    public void setDelRow(boolean delRow) {
        this.delRow = delRow;
    }

    @Override
    public boolean isSingle() {
        return this.single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    @Override
    public boolean isFixed() {
        return this.fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public boolean isEnableFilter() {
        return this.enableFilter;
    }

    public void setEnableFilter(boolean enableFilter) {
        this.enableFilter = enableFilter;
    }

    @Override
    public boolean isFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(boolean filterCondition) {
        this.filterCondition = filterCondition;
    }

    @Override
    public boolean isHideCkeckRowNum() {
        return this.hideCkeckRowNum;
    }

    public void setHideCkeckRowNum(boolean hideCkeckRowNum) {
        this.hideCkeckRowNum = hideCkeckRowNum;
    }

    public static FrontDataTableDefine create(DataTableInfo dataTableInfo) {
        FrontDataTableDefine frontDataTableDefine = new FrontDataTableDefine();
        frontDataTableDefine.setId(dataTableInfo.getId());
        frontDataTableDefine.setName(dataTableInfo.getName());
        frontDataTableDefine.setTitle(dataTableInfo.getTitle());
        frontDataTableDefine.setTableType(dataTableInfo.getTableType());
        frontDataTableDefine.setTableName(dataTableInfo.getTableName());
        frontDataTableDefine.setParentId(dataTableInfo.getParentId());
        frontDataTableDefine.setRequired(dataTableInfo.isRequired());
        frontDataTableDefine.setReadonly(dataTableInfo.isReadonly());
        frontDataTableDefine.setAddRow(dataTableInfo.isAddRow());
        frontDataTableDefine.setDelRow(dataTableInfo.isDelRow());
        frontDataTableDefine.setSingle(dataTableInfo.isSingle());
        frontDataTableDefine.setFixed(dataTableInfo.isFixed());
        frontDataTableDefine.setEnableFilter(dataTableInfo.isEnableFilter());
        frontDataTableDefine.setFilterCondition(dataTableInfo.isFilterCondition());
        frontDataTableDefine.setHideCkeckRowNum(dataTableInfo.isHideCkeckRowNum());
        return frontDataTableDefine;
    }
}

