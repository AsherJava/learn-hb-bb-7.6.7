/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.vo;

public class TemplateFieldSettingVO {
    private String id;
    private String templateId;
    private String name;
    private String title;
    private String dataType;
    private String expression;
    private String sumExpression;
    private String width;
    private String autoWidth;
    private String align;
    private String displayFormat;
    private boolean showFullPath;
    private Integer decimalLength;
    private String gatherType;
    private boolean useGroup;
    private boolean visibleFlag;
    private boolean fixedFlag;
    private int sortOrder;
    private String baseDataTable;
    private String config;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAutoWidth() {
        return this.autoWidth;
    }

    public void setAutoWidth(String autoWidth) {
        this.autoWidth = autoWidth;
    }

    public String getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(String gatherType) {
        this.gatherType = gatherType;
    }

    public boolean isVisibleFlag() {
        return this.visibleFlag;
    }

    public void setVisibleFlag(boolean visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getDecimalLength() {
        return this.decimalLength;
    }

    public void setDecimalLength(Integer decimalLength) {
        this.decimalLength = decimalLength;
    }

    public String getDisplayFormat() {
        return this.displayFormat;
    }

    public void setDisplayFormat(String displayFormat) {
        this.displayFormat = displayFormat;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getBaseDataTable() {
        return this.baseDataTable;
    }

    public void setBaseDataTable(String baseDataTable) {
        this.baseDataTable = baseDataTable;
    }

    public boolean isUseGroup() {
        return this.useGroup;
    }

    public void setUseGroup(boolean useGroup) {
        this.useGroup = useGroup;
    }

    public boolean isFixedFlag() {
        return this.fixedFlag;
    }

    public void setFixedFlag(boolean fixedFlag) {
        this.fixedFlag = fixedFlag;
    }

    public boolean isShowFullPath() {
        return this.showFullPath;
    }

    public void setShowFullPath(boolean showFullPath) {
        this.showFullPath = showFullPath;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getSumExpression() {
        return this.sumExpression;
    }

    public void setSumExpression(String sumExpression) {
        this.sumExpression = sumExpression;
    }
}

