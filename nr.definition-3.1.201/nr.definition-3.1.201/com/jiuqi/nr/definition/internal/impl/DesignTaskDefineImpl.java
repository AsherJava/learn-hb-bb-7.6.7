/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBLink
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.definition.internal.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.log.ComparePropertyAnno;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_PARAM_TASK")
@DBAnno.DBLink(linkWith=DesignTaskGroupLink.class, linkField="taskKey", field="key")
@ComparePropertyAnno.CompareType
@Deprecated
public class DesignTaskDefineImpl
implements DesignTaskDefine {
    private static final long serialVersionUID = 1L;
    private String masterEntitiesKey;
    private int masterEntityCount;
    @DBAnno.DBField(dbField="tk_unit")
    private String measureUnit;
    private PeriodType periodType = PeriodType.DEFAULT;
    @DBAnno.DBField(dbField="tk_from")
    @ComparePropertyAnno.CompareField(title="\u4efb\u52a1\u8d77\u59cb\u65f6\u95f4")
    private String fromPeriod;
    @DBAnno.DBField(dbField="tk_to")
    @ComparePropertyAnno.CompareField(title="\u4efb\u52a1\u7ec8\u6b62\u65f6\u95f4")
    private String toPeriod;
    @DBAnno.DBField(dbField="tk_period_offset")
    private int taskPeriodOffset;
    @ComparePropertyAnno.CompareField(title="\u4efb\u52a1\u6807\u8bc6")
    @DBAnno.DBField(dbField="tk_code")
    private String taskCode;
    @DBAnno.DBField(dbField="TK_FILE_PREFIX")
    private String taskFilePrefix;
    private String groupName;
    private boolean bigDataChanged;
    @DBAnno.DBField(dbField="tk_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="tk_title")
    @ComparePropertyAnno.CompareField(title="\u4efb\u52a1\u6807\u9898")
    private String title;
    @DBAnno.DBField(dbField="tk_order", isOrder=true)
    private String order = OrderGenerator.newOrder();
    @DBAnno.DBField(dbField="tk_version")
    private String version;
    @DBAnno.DBField(dbField="tk_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="tk_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;
    @DBAnno.DBField(dbField="tk_desc")
    private String description;
    private String taskExtension;
    @DBAnno.DBField(dbField="tk_gather_type", tranWith="transTaskGatherType", dbType=Integer.class, appType=TaskGatherType.class)
    private TaskGatherType taskGatherType;
    @DBAnno.DBField(dbField="tk_formula_syntax_style", tranWith="transFormulaSyntaxStyle", dbType=Integer.class, appType=FormulaSyntaxStyle.class)
    private FormulaSyntaxStyle formulaSyntaxStyle = FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION;
    private String commitEntitiesKey;
    @DBAnno.DBField(dbField="tk_due_date_offset")
    private int dueDateOffset;
    @DBAnno.DBField(dbField="tk_views_in_efdc")
    private String viewsInEFDC;
    private DesignTaskFlowsDefine designTaskFlowsDefine;
    @DBAnno.DBField(dbField="tk_type", tranWith="transTaskType", dbType=Integer.class, appType=TaskType.class)
    @ComparePropertyAnno.CompareField(title="\u4efb\u52a1\u7c7b\u578b")
    private TaskType taskType = TaskType.TASK_TYPE_DEFAULT;
    @DBAnno.DBField(dbField="TK_CREATE_USER_NAME")
    private String createUserName;
    @DBAnno.DBField(dbField="TK_CREATE_TIME")
    private String createTime;
    @DBAnno.DBField(dbField="TK_EFDCSWITCH", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean efdcSwitch;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    @DBAnno.DBField(dbField="TK_AUTOMATICALLY_DUE_TYPE")
    private int automaticallyDueType = FillInAutomaticallyDue.Type.CLOSE.getValue();
    @DBAnno.DBField(dbField="TK_AUTOMATICALLY_DUE_DAYS")
    private int automaticallyDueDays;
    @DBAnno.DBField(dbField="TK_AUTOMATIC_TERMINATION", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean automaticTermination = true;
    @DBAnno.DBField(dbField="tk_datascheme")
    private String dataScheme;
    @DBAnno.DBField(dbField="tk_dw")
    private String dw;
    @DBAnno.DBField(dbField="tk_datetime")
    private String datetime;
    @DBAnno.DBField(dbField="tk_dims")
    private String dims;
    @DBAnno.DBField(dbField="tk_filter_expression")
    private String filterExpression;
    @DBAnno.DBField(dbField="TK_FILLDTETYPE", tranWith="transFilldtetype", dbType=Integer.class, appType=FillDateType.class)
    private FillDateType fillingDateType = FillDateType.NONE;
    @DBAnno.DBField(dbField="TK_FILLDTEDAYS")
    private int fillingDateDays = 0;
    @DBAnno.DBField(dbField="TK_FILTER_TEMPLATE")
    private String filterTemplate;

    @Override
    public FillDateType getFillingDateType() {
        return this.fillingDateType;
    }

    @Override
    public void setFillingDateType(FillDateType fillingDateType) {
        this.fillingDateType = fillingDateType;
    }

    @Override
    public int getFillingDateDays() {
        return this.fillingDateDays;
    }

    @Override
    public String getFilterTemplate() {
        return this.filterTemplate;
    }

    @Override
    public void setFillingDateDays(int fillingDateDays) {
        this.fillingDateDays = fillingDateDays;
    }

    @Override
    public void setFilterTemplate(String filterTemplateID) {
        this.filterTemplate = filterTemplateID;
    }

    @Override
    public String getDw() {
        return this.dw;
    }

    @Override
    public String getDateTime() {
        return this.datetime;
    }

    @Override
    public String getDims() {
        return this.dims;
    }

    @Override
    public void setDw(String dw) {
        this.dw = dw;
    }

    @Override
    public void setDateTime(String dateTime) {
        this.datetime = dateTime;
    }

    @Override
    public void setDims(String dims) {
        this.dims = dims;
    }

    @Override
    public String getDataScheme() {
        return this.dataScheme;
    }

    @Override
    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    @Override
    public boolean getEfdcSwitch() {
        return this.efdcSwitch;
    }

    @Override
    public void setEfdcSwitch(boolean efdcSwitch) {
        this.efdcSwitch = efdcSwitch;
    }

    @Override
    public String getMasterEntitiesKey() {
        if (!StringUtils.hasText(this.dw) || !StringUtils.hasText(this.datetime)) {
            return null;
        }
        if (null != this.dims && !"".equals(this.dims)) {
            return this.dw + ";" + this.dims + ";" + this.datetime;
        }
        return this.dw + ";" + this.datetime;
    }

    @Override
    public void setMasterEntitiesKey(String masterEntitiesKey) {
    }

    @Override
    public int getMasterEntityCount() {
        return this.masterEntityCount;
    }

    @Override
    public void setMasterEntityCount(int count) {
        this.masterEntityCount = count;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getPeriodTypeDB() {
        return this.periodType == null ? PeriodType.YEAR.type() : this.periodType.type();
    }

    public void setPeriodTypeDB(Integer type) {
    }

    @Override
    public PeriodType getPeriodType() {
        try {
            PeriodEngineService bean;
            IPeriodEntity iPeriodByViewKey;
            if (this.periodType.type() == PeriodType.DEFAULT.type() && null != (iPeriodByViewKey = (bean = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class)).getPeriodAdapter().getPeriodEntity(this.datetime))) {
                this.periodType = iPeriodByViewKey.getPeriodType();
            }
        }
        catch (Exception e) {
            this.periodType = PeriodType.DEFAULT;
        }
        return this.periodType;
    }

    @Override
    public void setPeriodType(PeriodType periodType) {
    }

    @Override
    public String getFromPeriod() {
        return this.fromPeriod;
    }

    @Override
    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    @Override
    public String getToPeriod() {
        return this.toPeriod;
    }

    @Override
    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    @Override
    public int getTaskPeriodOffset() {
        return this.taskPeriodOffset;
    }

    @Override
    public void setTaskPeriodOffset(int taskPeriodOffset) {
        this.taskPeriodOffset = taskPeriodOffset;
    }

    @Override
    public String getTaskCode() {
        return this.taskCode;
    }

    @Override
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    @Override
    public String getTaskFilePrefix() {
        return this.taskFilePrefix;
    }

    @Override
    public void setTaskFilePrefix(String taskFilePrefix) {
        this.taskFilePrefix = taskFilePrefix;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    @Override
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean getBigDataChanged() {
        return this.bigDataChanged;
    }

    @Override
    public void setBigDataChanged(boolean bigDataChanged) {
        this.bigDataChanged = bigDataChanged;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getTaskExtension() {
        return this.taskExtension;
    }

    @Override
    public void setTaskExtension(String taskExtension) {
        this.taskExtension = taskExtension;
    }

    @Override
    public TaskGatherType getTaskGatherType() {
        return this.taskGatherType;
    }

    @Override
    public void setTaskGatherType(TaskGatherType gatherType) {
        this.taskGatherType = gatherType;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public FormulaSyntaxStyle getFormulaSyntaxStyle() {
        return this.formulaSyntaxStyle == null ? FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION : this.formulaSyntaxStyle;
    }

    @Override
    public void setFormulaSyntaxStyle(FormulaSyntaxStyle formulaSyntaxStyle) {
        this.formulaSyntaxStyle = formulaSyntaxStyle;
    }

    @Override
    public String getCommitEntitiesKey() {
        return this.commitEntitiesKey;
    }

    public void getFormulaSyntaxStyle(FormulaSyntaxStyle syntaxStyle) {
        this.formulaSyntaxStyle = syntaxStyle;
    }

    @Override
    public void setCommitEntitiesKey(String commitEntitiesKey) {
        this.commitEntitiesKey = commitEntitiesKey;
    }

    @Override
    public int getDueDateOffset() {
        return this.dueDateOffset;
    }

    @Override
    @Deprecated
    public int getPeriodBeginOffset() {
        return 0;
    }

    @Override
    @Deprecated
    public int getPeriodEndOffset() {
        return 0;
    }

    @Override
    @Deprecated
    public List<PeriodSetting> getPeriodSetting() {
        return null;
    }

    @Override
    public void setDueDateOffset(int dueDateOffset) {
        this.dueDateOffset = dueDateOffset;
    }

    @Override
    @Deprecated
    public void setPeriodBeginOffset(int periodBeginOffset) {
    }

    @Override
    @Deprecated
    public void setPeriodEndOffset(int periodEndOffset) {
    }

    @Override
    @Deprecated
    public void setPeriodSetting(List<PeriodSetting> periodSetting) {
    }

    @Override
    public DesignTaskFlowsDefine getFlowsSetting() {
        return this.designTaskFlowsDefine;
    }

    @Override
    public void setFlowsSetting(DesignTaskFlowsDefine designTaskFlowsDefine) {
        this.designTaskFlowsDefine = designTaskFlowsDefine;
    }

    @Override
    public String getEntityViewsInEFDC() {
        return this.viewsInEFDC;
    }

    @Override
    public void setEntityViewsInEFDC(String entityViews) {
        this.viewsInEFDC = entityViews;
    }

    @JsonIgnore
    @Deprecated
    public String getEntityLinkageDB() {
        return null;
    }

    @JsonIgnore
    @Deprecated
    public void setEntityLinkageDB(String fields) {
    }

    @Override
    public TaskType getTaskType() {
        if (null == this.taskType) {
            this.taskType = TaskType.TASK_TYPE_DEFAULT;
        }
        return this.taskType;
    }

    @Override
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String getCreateUserName() {
        return this.createUserName;
    }

    @Override
    public String getCreateTime() {
        return this.createTime;
    }

    @Override
    public void setCreateUserName(String username) {
        this.createUserName = username;
    }

    @Override
    public void setCreateTime(String createtime) {
        this.createTime = createtime;
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        if (this.fillInAutomaticallyDue == null) {
            this.fillInAutomaticallyDue = new FillInAutomaticallyDue(this.automaticallyDueType, this.automaticallyDueDays, this.automaticTermination);
        }
        return this.fillInAutomaticallyDue;
    }

    @Override
    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        if (fillInAutomaticallyDue != null) {
            this.fillInAutomaticallyDue = fillInAutomaticallyDue;
            this.automaticallyDueType = fillInAutomaticallyDue.getType();
            this.automaticallyDueDays = fillInAutomaticallyDue.getDays();
            this.automaticTermination = fillInAutomaticallyDue.getAutomaticTermination();
        }
    }

    @Override
    public String getFilterExpression() {
        return this.filterExpression;
    }

    @Override
    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }
}

