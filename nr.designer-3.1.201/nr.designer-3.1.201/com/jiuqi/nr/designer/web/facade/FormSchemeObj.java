/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.FlowsObj;
import com.jiuqi.nr.designer.web.facade.NoTimePeriod;
import com.jiuqi.nr.designer.web.facade.unusual.TableSupportDatedVersion;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormSchemeObj {
    @JsonProperty(value="TaskId")
    private String taskId;
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="SchemePeriodOffset")
    private int schemePeriodOffset;
    @JsonProperty(value="EntityList")
    private List<EntityTables> entityList;
    @JsonProperty(value="PeriodType")
    private int periodType;
    @JsonProperty(value="DueDateOffset")
    private int dueDateOffset;
    @JsonProperty(value="FlowsObject")
    private FlowsObj flowsObject;
    @JsonProperty(value="IsNew")
    private boolean isNew = false;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted = false;
    @JsonProperty(value="IsDirty")
    private boolean isDirty = false;
    @JsonProperty(value="MeasureUnit")
    private String measureUnit;
    @JsonProperty(value="dimession")
    private String[] dimession;
    @JsonProperty(value="NoTimePeriod")
    private List<NoTimePeriod> noTimePeriod;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="EffectivePeriods")
    private List<String> effectivePeriods;
    @JsonProperty(value="EntitiesIsExtend")
    private boolean entitiesIsExtend = true;
    @JsonProperty(value="PeriodIsExtend")
    private boolean periodIsExtend = true;
    @JsonProperty(value="MeasureUnitIsExtend")
    private boolean measureUnitIsExtend = true;
    private TableSupportDatedVersion tableSupportDatedVersion;
    @JsonProperty(value="FillInAutomaticallyDueIsExtend")
    private boolean fillInAutomaticallyDueIsExtend = true;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    @JsonProperty(value="Dw")
    private String dw;
    @JsonProperty(value="Dims")
    private String dims;
    @JsonProperty(value="Datetime")
    private String datetime;
    @JsonProperty(value="FilterExpression")
    private String filterExpression;

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getDims() {
        return this.dims;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @JsonIgnore
    public boolean getEntitiesIsExtend() {
        return this.entitiesIsExtend;
    }

    @JsonIgnore
    public void setEntitiesIsExtend(boolean entitiesIsExtend) {
        this.entitiesIsExtend = entitiesIsExtend;
    }

    @JsonIgnore
    public boolean getPeriodIsExtend() {
        return this.periodIsExtend;
    }

    @JsonIgnore
    public void setPeriodIsExtend(boolean periodIsExtend) {
        this.periodIsExtend = periodIsExtend;
    }

    @JsonIgnore
    public boolean getMeasureUnitIsExtend() {
        return this.measureUnitIsExtend;
    }

    @JsonIgnore
    public void setMeasureUnitIsExtend(boolean measureUnitIsExtend) {
        this.measureUnitIsExtend = measureUnitIsExtend;
    }

    public boolean getFillInAutomaticallyDueIsExtend() {
        return this.fillInAutomaticallyDueIsExtend;
    }

    public void setFillInAutomaticallyDueIsExtend(boolean fillInAutomaticallyDueIsExtend) {
        this.fillInAutomaticallyDueIsExtend = fillInAutomaticallyDueIsExtend;
    }

    @JsonIgnore
    public String getTaskId() {
        return this.taskId;
    }

    @JsonIgnore
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @JsonIgnore
    public String getID() {
        return this.id;
    }

    @JsonIgnore
    public void setID(String iD) {
        this.id = iD;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public int getSchemePeriodOffset() {
        return this.schemePeriodOffset;
    }

    @JsonIgnore
    public void setSchemePeriodOffset(int schemePeriodOffset) {
        this.schemePeriodOffset = schemePeriodOffset;
    }

    @JsonIgnore
    public String getOrder() {
        return this.order;
    }

    @JsonIgnore
    public void setOrder(String order) {
        this.order = order;
    }

    @JsonIgnore
    public boolean isIsNew() {
        return this.isNew;
    }

    @JsonIgnore
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @JsonIgnore
    public boolean isIsDeleted() {
        return this.isDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonIgnore
    public boolean isIsDirty() {
        return this.isDirty;
    }

    @JsonIgnore
    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    @JsonIgnore
    public List<EntityTables> getEntityList() {
        return this.entityList;
    }

    @JsonIgnore
    public void setEntityList(List<EntityTables> entityList) {
        this.entityList = entityList;
    }

    @JsonIgnore
    public int getPeriodType() {
        return this.periodType;
    }

    @JsonIgnore
    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    @JsonIgnore
    public FlowsObj getFlowsObj() {
        return this.flowsObject;
    }

    @JsonIgnore
    public void setFlowsObj(FlowsObj flowsObject) {
        this.flowsObject = flowsObject;
    }

    @JsonIgnore
    public int getDueDateOffset() {
        return this.dueDateOffset;
    }

    @JsonIgnore
    public void setDueDateOffset(int dueDateOffset) {
        this.dueDateOffset = dueDateOffset;
    }

    @JsonIgnore
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @JsonIgnore
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @JsonIgnore
    public String[] getDimession() {
        return this.dimession;
    }

    @JsonIgnore
    public void setDimession(String[] dimession) {
        this.dimession = dimession;
    }

    @JsonIgnore
    public List<NoTimePeriod> getNoTimePeriod() {
        return this.noTimePeriod;
    }

    @JsonIgnore
    public void setNoTimePeriod(List<NoTimePeriod> noTimePeriod) {
        this.noTimePeriod = noTimePeriod;
    }

    @JsonIgnore
    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @JsonIgnore
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @JsonIgnore
    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    @JsonIgnore
    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }

    public TableSupportDatedVersion getTableSupportDatedVersion() {
        return this.tableSupportDatedVersion;
    }

    public void setTableSupportDatedVersion(TableSupportDatedVersion tableSupportDatedVersion) {
        this.tableSupportDatedVersion = tableSupportDatedVersion;
    }

    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.fillInAutomaticallyDue;
    }

    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        this.fillInAutomaticallyDue = fillInAutomaticallyDue;
    }

    @JsonIgnore
    public List<String> getEffectivePeriods() {
        return this.effectivePeriods;
    }

    @JsonIgnore
    public void setEffectivePeriods(List<String> effectivePeriods) {
        this.effectivePeriods = effectivePeriods;
    }

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }
}

