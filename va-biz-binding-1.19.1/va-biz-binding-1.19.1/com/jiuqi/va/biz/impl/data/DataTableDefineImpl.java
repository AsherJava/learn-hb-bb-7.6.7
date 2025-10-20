/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 *  com.jiuqi.va.i18n.utils.VaI18nParamUtils
 */
package com.jiuqi.va.biz.impl.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.i18n.utils.VaI18nParamUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.context.i18n.LocaleContextHolder;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class DataTableDefineImpl
implements DataTableDefine {
    private UUID id;
    private String name;
    private String title;
    private transient Map<String, String> titleI18nMap = new HashMap<String, String>();
    private DataTableType tableType;
    private String tableName;
    private UUID parentId;
    private boolean required;
    private boolean readonly;
    private boolean addRow = true;
    private boolean delRow = true;
    private boolean single;
    private boolean fixed;
    private boolean enableFilter;
    private boolean filterCondition;
    private int initRows;
    private boolean blankRow = true;
    private List<Map<String, String>> sortFields;
    private List<DataFieldDefineImpl> fields = new ArrayList<DataFieldDefineImpl>();
    private transient NamedContainerImpl<? extends DataFieldDefineImpl> fieldContainer;
    private boolean solidified;
    private boolean hideCkeckRowNum;
    private transient String multiName;

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

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        String i18nTitle = this.titleI18nMap.get(LocaleContextHolder.getLocale().toLanguageTag());
        if (VaI18nParamUtils.getTranslationEnabled().booleanValue() && i18nTitle != null) {
            return i18nTitle;
        }
        return this.title;
    }

    public String getOriginalTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getTitleI18nMap() {
        return this.titleI18nMap;
    }

    public void setTitleI18nMap(Map<String, String> titleI18nMap) {
        this.titleI18nMap = titleI18nMap;
    }

    @Override
    public DataTableType getTableType() {
        return this.tableType;
    }

    void setTableType(DataTableType tableType) {
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

    void setParentId(UUID parentId) {
        this.parentId = parentId;
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
    public boolean isRequired() {
        return this.required;
    }

    void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean isReadonly() {
        return this.readonly;
    }

    void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    @Override
    public boolean isSingle() {
        return this.single;
    }

    void setSingle(boolean single) {
        this.single = single;
    }

    @Override
    public boolean isFixed() {
        return this.fixed;
    }

    void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public boolean isEnableFilter() {
        return this.enableFilter;
    }

    void setEnableFilter(boolean enableFilter) {
        this.enableFilter = enableFilter;
    }

    @Override
    public boolean isFilterCondition() {
        return this.filterCondition;
    }

    void setFilterCondition(boolean filterCondition) {
        this.filterCondition = filterCondition;
    }

    public int getInitRows() {
        return this.initRows;
    }

    public void setInitRows(int initRows) {
        this.initRows = initRows;
    }

    public boolean isBlankRow() {
        return this.blankRow;
    }

    public void setBlankRow(boolean blankRow) {
        this.blankRow = blankRow;
    }

    List<DataFieldDefineImpl> getFieldList() {
        return this.fields;
    }

    void setFieldList(List<DataFieldDefineImpl> fields) {
        this.fields = fields;
        this.fieldContainer = null;
    }

    public NamedContainerImpl<? extends DataFieldDefineImpl> getFields() {
        if (this.fieldContainer == null) {
            this.fieldContainer = new NamedContainerImpl<DataFieldDefineImpl>(this.fields);
        }
        return this.fieldContainer;
    }

    public void addField(DataFieldDefineImpl field) {
        field.setTable(this);
        this.fields.add(field);
        this.fieldContainer = null;
    }

    public void addFieldByIndex(DataFieldDefineImpl field, Integer index) {
        field.setTable(this);
        this.fields.add(index, field);
        this.fieldContainer = null;
    }

    public List<Map<String, String>> getSortFields() {
        return this.sortFields;
    }

    public void setSortFields(List<Map<String, String>> sortFields) {
        this.sortFields = sortFields;
    }

    public boolean isSolidified() {
        return this.solidified;
    }

    public void setSolidified(boolean solidified) {
        this.solidified = solidified;
    }

    public String getMultiName() {
        if (this.multiName == null) {
            this.multiName = this.name + "_M";
        }
        return this.multiName;
    }

    @Override
    public boolean isHideCkeckRowNum() {
        return this.hideCkeckRowNum;
    }

    public void setHideCkeckRowNum(boolean hideCkeckRowNum) {
        this.hideCkeckRowNum = hideCkeckRowNum;
    }
}

