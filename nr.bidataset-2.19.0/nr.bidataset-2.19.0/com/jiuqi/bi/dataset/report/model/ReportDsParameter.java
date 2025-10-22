/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.bi.dataset.report.model;

import com.jiuqi.bi.dataset.report.model.DefaultValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;

public class ReportDsParameter {
    private String name;
    private String title;
    private int dataType;
    private ParameterSelectMode selectMode;
    private DefaultValueMode defaultValueMode = DefaultValueMode.NONE;
    private String[] defaultValues;
    private DefaultValueMode defaultMaxValueMode;
    private String defaultMaxValue;
    private String messageAlias;
    private String entityId;
    private String order;

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }

    public int getDataType() {
        return this.dataType;
    }

    public ParameterSelectMode getSelectMode() {
        return this.selectMode;
    }

    public String getMessageAlias() {
        return this.messageAlias;
    }

    public String getOrder() {
        return this.order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public void setSelectMode(ParameterSelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public void setMessageAlias(String messageAlias) {
        this.messageAlias = messageAlias;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public DefaultValueMode getDefaultValueMode() {
        return this.defaultValueMode;
    }

    public void setDefaultValueMode(DefaultValueMode defaultValueMode) {
        this.defaultValueMode = defaultValueMode;
    }

    public String[] getDefaultValues() {
        return this.defaultValues;
    }

    public void setDefaultValues(String[] defaultValues) {
        this.defaultValues = defaultValues;
    }

    public DefaultValueMode getDefaultMaxValueMode() {
        return this.defaultMaxValueMode;
    }

    public void setDefaultMaxValueMode(DefaultValueMode defaultMaxValueMode) {
        this.defaultMaxValueMode = defaultMaxValueMode;
    }

    public String getDefaultMaxValue() {
        return this.defaultMaxValue;
    }

    public void setDefaultMaxValue(String defaultMaxValue) {
        this.defaultMaxValue = defaultMaxValue;
    }
}

