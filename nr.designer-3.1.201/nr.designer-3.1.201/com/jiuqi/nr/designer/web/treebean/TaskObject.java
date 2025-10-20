/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 */
package com.jiuqi.nr.designer.web.treebean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.designer.web.facade.EntityLinkageObject;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.FlowsObj;
import com.jiuqi.nr.designer.web.facade.NoTimePeriod;
import com.jiuqi.nr.designer.web.facade.TaskOrgListVO;
import com.jiuqi.nr.designer.web.facade.unusual.TableSupportDatedVersion;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import java.util.List;
import java.util.Map;

public class TaskObject {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="PeriodType")
    private int periodType;
    @JsonProperty(value="SystemFields")
    private Object systemFields;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="TaskPeriodOffset")
    private int taskPeriodOffset;
    @JsonProperty(value="FromPeriod")
    private String fromPeriod;
    @JsonProperty(value="ToPeriod")
    private String toPeriod;
    @JsonProperty(value="EntityTablesList")
    private Map<String, EntityTables> entityTablesList;
    @JsonProperty(value="EntityLinkageObject")
    private EntityLinkageObject entityLinkageObject;
    @JsonProperty(value="GatherType")
    private int gatherType;
    @JsonProperty(value="Description")
    private String description;
    @JsonProperty(value="TkEntityKey")
    private String tkEntityKey;
    @JsonProperty(value="FormulaSyntaxStyle")
    private FormulaSyntaxStyle formulaSyntaxStyle;
    @JsonProperty(value="FlowsObject")
    private FlowsObj flowsObject;
    @JsonProperty(value="DueDateOffset")
    private int dueDateOffset;
    @JsonProperty(value="EntityViewInEfdc")
    private String entityViewInEfdc;
    @JsonProperty(value="DisplayAsFormEntityViews")
    private String displayAsFormEntityViews;
    @JsonProperty(value="CanInsertEntityViews")
    private String canInsertEntityViews;
    @JsonProperty(value="IsNew")
    private boolean isNew;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted;
    @JsonProperty(value="IsDirty")
    private boolean isDirty;
    @JsonProperty(value="TaskLinks")
    private TaskLinkObject taskLinks;
    @JsonProperty(value="MeasureUnit")
    private String measureUnit;
    @JsonProperty(value="dimession")
    private String[] dimession;
    @JsonProperty(value="NoTimePeriod")
    private List<NoTimePeriod> noTimePeriod;
    @JsonProperty(value="UseFieldAuthority")
    private boolean useFieldAuthority;
    @JsonProperty(value="HiddenEntitiesWhenSingleRecord")
    private String hiddenEntitiesWhenSingleRecord;
    @JsonProperty(value="InputAbleAddEntity")
    private String inputAbleAddEntity;
    @JsonProperty(value="InputShowEntity")
    private String inputShowEntity;
    @JsonProperty(value="TaskType")
    private int taskType;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="EfdcSwitch")
    private boolean efdcSwitch;
    @JsonProperty(value="DataScheme")
    private String dataScheme;
    @JsonProperty(value="Dw")
    private String dw;
    @JsonProperty(value="Dims")
    private String dims;
    @JsonProperty(value="Datetime")
    private String datetime;
    @JsonProperty(value="OrgListObj")
    private TaskOrgListVO orgListObj;
    @JsonProperty(value="FilterSettingType")
    private Integer filterSettingType;
    @JsonProperty(value="FilterExpression")
    private String filterExpression;
    @JsonProperty(value="FilterTemplate")
    private String filterTemplate;
    @JsonProperty(value="DimensionFilter")
    private String dimensionFilter;
    @JsonProperty(value="FillingDateType")
    private int fillingDateType;
    @JsonProperty(value="FillingDateDays")
    private int fillingDateDays;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    private TableSupportDatedVersion tableSupportDatedVersion;

    public int getFillingDateType() {
        return this.fillingDateType;
    }

    public void setFillingDateType(int fillingDateType) {
        this.fillingDateType = fillingDateType;
    }

    public int getFillingDateDays() {
        return this.fillingDateDays;
    }

    public void setFillingDateDays(int fillingDateDays) {
        this.fillingDateDays = fillingDateDays;
    }

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

    public TaskOrgListVO getOrgListObj() {
        return this.orgListObj;
    }

    public void setOrgListObj(TaskOrgListVO orgListObj) {
        this.orgListObj = orgListObj;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public boolean getEfdcSwitch() {
        return this.efdcSwitch;
    }

    public void setEfdcSwitch(boolean efdcSwitch) {
        this.efdcSwitch = efdcSwitch;
    }

    public List<NoTimePeriod> getNoTimePeriod() {
        return this.noTimePeriod;
    }

    public void setNoTimePeriod(List<NoTimePeriod> noTimePeriod) {
        this.noTimePeriod = noTimePeriod;
    }

    public int getTaskPeriodOffset() {
        return this.taskPeriodOffset;
    }

    public void setTaskPeriodOffset(int taskPeriodOffset) {
        this.taskPeriodOffset = taskPeriodOffset;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public Object getSystemFields() {
        return this.systemFields;
    }

    public void setSystemFields(Object systemFields) {
        this.systemFields = systemFields;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public int getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(int gatherType) {
        this.gatherType = gatherType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTkEntityKey() {
        return this.tkEntityKey;
    }

    public void setTkEntityKey(String tkEntityKey) {
        this.tkEntityKey = tkEntityKey;
    }

    public FormulaSyntaxStyle getFormulaSyntaxStyle() {
        return this.formulaSyntaxStyle;
    }

    public void setFormulaSyntaxStyle(FormulaSyntaxStyle formulaSyntaxStyle) {
        this.formulaSyntaxStyle = formulaSyntaxStyle;
    }

    public int getDueDateOffset() {
        return this.dueDateOffset;
    }

    public void setDueDateOffset(int dueDateOffset) {
        this.dueDateOffset = dueDateOffset;
    }

    public FlowsObj getFlowsObject() {
        return this.flowsObject;
    }

    public void setFlowsObject(FlowsObj flowsObject) {
        this.flowsObject = flowsObject;
    }

    public boolean isIsNew() {
        return this.isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isIsDirty() {
        return this.isDirty;
    }

    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    public Map<String, EntityTables> getEntityTablesList() {
        return this.entityTablesList;
    }

    public void setEntityTablesList(Map<String, EntityTables> entityTablesList) {
        this.entityTablesList = entityTablesList;
    }

    public String getEntityViewInEfdc() {
        return this.entityViewInEfdc;
    }

    public void setEntityViewInEfdc(String entityViewInEfdc) {
        this.entityViewInEfdc = entityViewInEfdc;
    }

    public String getDisplayAsFormEntityViews() {
        return this.displayAsFormEntityViews;
    }

    public void setDisplayAsFormEntityViews(String displayAsFormEntityViews) {
        this.displayAsFormEntityViews = displayAsFormEntityViews;
    }

    public String getCanInsertEntityViews() {
        return this.canInsertEntityViews;
    }

    public void setCanInsertEntityViews(String canInsertEntityViews) {
        this.canInsertEntityViews = canInsertEntityViews;
    }

    public EntityLinkageObject getEntityLinkageObject() {
        return this.entityLinkageObject;
    }

    public void setEntityLinkageObject(EntityLinkageObject entityLinkageObject) {
        this.entityLinkageObject = entityLinkageObject;
    }

    public TaskLinkObject getTaskLinks() {
        return this.taskLinks;
    }

    public void setTaskLinks(TaskLinkObject taskLinks) {
        this.taskLinks = taskLinks;
    }

    @JsonIgnore
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @JsonIgnore
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String[] getDimession() {
        return this.dimession;
    }

    public void setDimession(String[] dimession) {
        this.dimession = dimession;
    }

    public boolean getUseFieldAuthority() {
        return this.useFieldAuthority;
    }

    public void setUseFieldAuthority(boolean useFieldAuthority) {
        this.useFieldAuthority = useFieldAuthority;
    }

    public String getHiddenEntitiesWhenSingleRecord() {
        return this.hiddenEntitiesWhenSingleRecord;
    }

    public void setHiddenEntitiesWhenSingleRecord(String hiddenEntitiesWhenSingleRecord) {
        this.hiddenEntitiesWhenSingleRecord = hiddenEntitiesWhenSingleRecord;
    }

    public String getInputAbleAddEntity() {
        return this.inputAbleAddEntity;
    }

    public void setInputAbleAddEntity(String inputAbleAddEntity) {
        this.inputAbleAddEntity = inputAbleAddEntity;
    }

    public String getInputShowEntity() {
        return this.inputShowEntity;
    }

    public void setInputShowEntity(String inputShowEntity) {
        this.inputShowEntity = inputShowEntity;
    }

    public int getTaskType() {
        return this.taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

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

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    public String getDimensionFilter() {
        return this.dimensionFilter;
    }

    public void setDimensionFilter(String dimensionFilter) {
        this.dimensionFilter = dimensionFilter;
    }

    public String getFilterTemplate() {
        return this.filterTemplate;
    }

    public void setFilterTemplate(String filterTemplate) {
        this.filterTemplate = filterTemplate;
    }

    public Integer getFilterSettingType() {
        return this.filterSettingType;
    }

    public void setFilterSettingType(Integer filterSettingType) {
        this.filterSettingType = filterSettingType;
    }
}

