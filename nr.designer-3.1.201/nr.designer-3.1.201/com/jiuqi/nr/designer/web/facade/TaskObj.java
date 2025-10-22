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
import com.jiuqi.nr.designer.web.facade.FieldGroupObj;
import com.jiuqi.nr.designer.web.facade.FlowsObj;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FunctionObj;
import com.jiuqi.nr.designer.web.facade.NoTimePeriod;
import com.jiuqi.nr.designer.web.facade.OperatorObj;
import com.jiuqi.nr.designer.web.facade.SplitTableObj;
import com.jiuqi.nr.designer.web.facade.TaskOrgListVO;
import com.jiuqi.nr.designer.web.facade.unusual.TableSupportDatedVersion;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TaskObj {
    @JsonProperty(value="Forms")
    private FormObj forms;
    @JsonProperty(value="EntityTables")
    private EntityTables entityTables;
    @JsonProperty(value="FormGroups")
    private Map<String, FormGroupObject> formGroups;
    @JsonProperty(value="FiledGroups")
    private FieldGroupObj filedGroups;
    @JsonProperty(value="TaskFileFix")
    private String taskFileFix;
    @JsonProperty(value="EnumDataBlobUrl")
    private String enumDataBlobUrl;
    @JsonProperty(value="IsFromTemplate")
    private boolean fromTemplate;
    @JsonProperty(value="SystemFields")
    private Object systemFields;
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="TaskPeriodOffset")
    private int taskPeriodOffset;
    @JsonProperty(value="FromPeriod")
    private String fromPeriod;
    @JsonProperty(value="ToPeriod")
    private String toPeriod;
    @JsonProperty(value="GatherType")
    private int gatherType;
    @JsonProperty(value="Description")
    private String description;
    @JsonProperty(value="EntityList")
    private List<EntityTables> entityList;
    @JsonProperty(value="FunctionList")
    private HashMap<String, FunctionObj> functionList;
    @JsonProperty(value="FlowsObject")
    private FlowsObj flowsObject;
    @JsonProperty(value="OperatorList")
    private HashMap<String, List<OperatorObj>> operatorList;
    @JsonProperty(value="MeasureUnit")
    private String measureUnit;
    @JsonProperty(value="IsNew")
    private boolean isNew = false;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted = false;
    @JsonProperty(value="IsDirty")
    private boolean isDirty = false;
    @JsonProperty(value="FormulaSyntaxStyle")
    private int formulaSyntaxStyle;
    @JsonProperty(value="DueDateOffset")
    private int dueDateOffset;
    @JsonProperty(value="EntityViewInEfdc")
    private String entityViewInEfdc;
    @JsonProperty(value="NoTimePeriod")
    private List<NoTimePeriod> noTimePeriod;
    @JsonProperty(value="UseFieldAuthority")
    private boolean useFieldAuthority;
    @JsonProperty(value="TaskType")
    private int taskType;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    private SplitTableObj splitTableObj;
    @JsonProperty(value="EfdcSwitch")
    private boolean efdcSwitch;
    private TableSupportDatedVersion tableSupportDatedVersion;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    @JsonProperty(value="Dw")
    private String dw;
    @JsonProperty(value="Dims")
    private String dims;
    @JsonProperty(value="Datetime")
    private String datetime;
    @JsonProperty(value="OrgListObj")
    private TaskOrgListVO orgListObj;
    @JsonProperty(value="TaskParamStatus")
    private boolean taskParamStatus;
    @JsonProperty(value="FilterSettingType")
    private Integer filterSettingType;
    @JsonProperty(value="FilterExpression")
    private String filterExpression;
    @JsonProperty(value="FilterTemplate")
    private String filterTemplate;
    @JsonProperty(value="FillingDateType")
    private int fillingDateType;
    @JsonProperty(value="FillingDateDays")
    private int fillingDateDays;
    @JsonProperty(value="DataScheme")
    private String dataScheme;

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

    public boolean isTaskParamStatus() {
        return this.taskParamStatus;
    }

    public void setTaskParamStatus(boolean taskParamStatus) {
        this.taskParamStatus = taskParamStatus;
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

    public TaskOrgListVO getOrgList() {
        return this.orgListObj;
    }

    public void setOrgList(TaskOrgListVO orgListObj) {
        this.orgListObj = orgListObj;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public List<NoTimePeriod> getNoTimePeriod() {
        return this.noTimePeriod;
    }

    public void setNoTimePeriod(List<NoTimePeriod> noTimePeriod) {
        this.noTimePeriod = noTimePeriod;
    }

    public int getFormulaSyntaxStyle() {
        return this.formulaSyntaxStyle;
    }

    public String getEntityViewInEfdc() {
        return this.entityViewInEfdc;
    }

    public void setEntityViewInEfdc(String entityViewInEfdc) {
        this.entityViewInEfdc = entityViewInEfdc;
    }

    public void setFormulaSyntaxStyle(int FormulaSyntaxStyle2) {
        this.formulaSyntaxStyle = FormulaSyntaxStyle2;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isFromTemplate() {
        return this.fromTemplate;
    }

    public void setFromTemplate(boolean fromTemplate) {
        this.fromTemplate = fromTemplate;
    }

    public String getEnumDataBlobUrl() {
        return this.enumDataBlobUrl;
    }

    public void setEnumDataBlobUrl(String enumDataBlobUrl) {
        this.enumDataBlobUrl = enumDataBlobUrl;
    }

    public String getTaskFileFix() {
        return this.taskFileFix;
    }

    public void setTaskFileFix(String taskFileFix) {
        this.taskFileFix = taskFileFix;
    }

    public FieldGroupObj getFiledGroups() {
        return this.filedGroups;
    }

    public void setFiledGroups(FieldGroupObj filedGroups) {
        this.filedGroups = filedGroups;
    }

    public Map<String, FormGroupObject> getFormGroups() {
        return this.formGroups;
    }

    public void setFormGroups(Map<String, FormGroupObject> formGroups) {
        this.formGroups = formGroups;
    }

    public EntityTables getEntityTables() {
        return this.entityTables;
    }

    public void setEntityTables(EntityTables entityTables) {
        this.entityTables = entityTables;
    }

    public FormObj getForms() {
        return this.forms;
    }

    public void setForms(FormObj forms) {
        this.forms = forms;
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

    public String getID() {
        return this.id;
    }

    public void setID(String iD) {
        this.id = iD;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getSystemFields() {
        return this.systemFields;
    }

    public void setSystemFields(Object systemFields) {
        this.systemFields = systemFields;
    }

    public int getTaskPeriodOffset() {
        return this.taskPeriodOffset;
    }

    public void setTaskPeriodOffset(int taskPeriodOffset) {
        this.taskPeriodOffset = taskPeriodOffset;
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

    public String getTkMasterKey() {
        if (null != this.dims && !"".equals(this.dims)) {
            return this.dw + ";" + this.dims + ";" + this.datetime;
        }
        return this.dw + ";" + this.datetime;
    }

    public void setTkMasterKey(String tkMasterKey) {
    }

    public List<EntityTables> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<EntityTables> entityList) {
        this.entityList = entityList;
    }

    public HashMap<String, FunctionObj> getFunctionList() {
        return this.functionList;
    }

    public void setFunctionList(HashMap<String, FunctionObj> functionObjs) {
        this.functionList = functionObjs;
    }

    public HashMap<String, List<OperatorObj>> getOperatorList() {
        return this.operatorList;
    }

    public void setOperatorList(HashMap<String, List<OperatorObj>> operatorObjs) {
        this.operatorList = operatorObjs;
    }

    public FlowsObj getFlowsObj() {
        return this.flowsObject;
    }

    public void setFlowsObj(FlowsObj flowsObj) {
        this.flowsObject = flowsObj;
    }

    public int getDueDateOffset() {
        return this.dueDateOffset;
    }

    public void setDueDateOffset(int dueDateOffset) {
        this.dueDateOffset = dueDateOffset;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonIgnore
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @JsonIgnore
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public boolean getUseFieldAuthority() {
        return this.useFieldAuthority;
    }

    public void setUseFieldAuthority(boolean useFieldAuthority) {
        this.useFieldAuthority = useFieldAuthority;
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

    @JsonIgnore
    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    @JsonIgnore
    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }

    public SplitTableObj getSplitTableObj() {
        return this.splitTableObj;
    }

    public void setSplitTableObj(SplitTableObj splitTableObj) {
        this.splitTableObj = splitTableObj;
    }

    public boolean getEfdcSwitch() {
        return this.efdcSwitch;
    }

    public void setEfdcSwitch(boolean efdcSwitch) {
        this.efdcSwitch = efdcSwitch;
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

    public Integer getFilterSettingType() {
        return this.filterSettingType;
    }

    public void setFilterSettingType(Integer filterSettingType) {
        this.filterSettingType = filterSettingType;
    }

    public String getFilterTemplate() {
        return this.filterTemplate;
    }

    public void setFilterTemplate(String filterTemplate) {
        this.filterTemplate = filterTemplate;
    }
}

