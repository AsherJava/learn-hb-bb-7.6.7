/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMSCHEME")
public class RunTimeFormSchemeDefineImpl
implements FormSchemeDefine {
    private static final long serialVersionUID = -4875200052981366319L;
    public static final String TABLE_NAME = "NR_PARAM_FORMSCHEME";
    public static final String FIELD_NAME_KEY = "FC_KEY";
    public static final String FIELD_NAME_TASK_KEY = "FC_TASK_KEY";
    public static final String FIELD_NAME_CODE = "FC_CODE";
    @DBAnno.DBField(dbField="FC_TASK_KEY", isPk=false)
    private String taskKey;
    @DBAnno.DBField(dbField="FC_CODE")
    private String formSchemeCode;
    private PeriodType periodType = PeriodType.DEFAULT;
    @DBAnno.DBField(dbField="FC_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="fc_title")
    private String title;
    @DBAnno.DBField(dbField="fc_order", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="fc_version")
    private String version;
    @DBAnno.DBField(dbField="fc_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fc_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fc_desc")
    private String description;
    private String masterEntitiesKey;
    @DBAnno.DBField(dbField="fc_taskprefix")
    private String taskPrefix;
    @DBAnno.DBField(dbField="fc_fileprefix")
    private String filePrefix;
    @DBAnno.DBField(dbField="fc_period_offset")
    private int periodOffset;
    private TaskFlowsDefine taskFlowsDefine;
    @DBAnno.DBField(dbField="fc_unit")
    private String measureUnit;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    @DBAnno.DBField(dbField="FC_AUTOMATICALLY_DUE_TYPE")
    private int automaticallyDueType = FillInAutomaticallyDue.Type.DEFAULT.getValue();
    @DBAnno.DBField(dbField="FC_AUTOMATICALLY_DUE_DAYS")
    private int automaticallyDueDays;
    @DBAnno.DBField(dbField="FC_AUTOMATIC_TERMINATION", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean automaticTermination;
    private String dw;
    private String datetime;
    private String dims;
    private String filterExpression;

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
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    @Override
    public PeriodType getPeriodType() {
        if (!StringUtils.hasText(this.datetime)) {
            return this.periodType;
        }
        try {
            if (this.periodType.type() == PeriodType.DEFAULT.type()) {
                PeriodEngineService bean = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
                IPeriodEntity iPeriodByViewKey = bean.getPeriodAdapter().getPeriodEntity(this.datetime);
                this.periodType = iPeriodByViewKey.getPeriodType();
            }
        }
        catch (Exception e) {
            this.periodType = PeriodType.DEFAULT;
        }
        return this.periodType;
    }

    public int getPeriodTypeDB() {
        return this.periodType == null ? PeriodType.YEAR.type() : this.periodType.type();
    }

    public void setPeriodTypeDB(Integer type) {
    }

    @Override
    public String getFromPeriod() {
        return null;
    }

    @Override
    public String getToPeriod() {
        return null;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public void setPeriodType(PeriodType periodType) {
    }

    public void setFromPeriod(String fromPeriod) {
    }

    public void setToPeriod(String toPeriod) {
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getMasterEntitiesKey() {
        return null;
    }

    public void setMasterEntitiesKey(String masterEntitiesKey) {
    }

    @Override
    public String getTaskPrefix() {
        return this.taskPrefix;
    }

    @Override
    public String getFilePrefix() {
        return this.filePrefix;
    }

    public void setTaskPrefix(String taskPrefix) {
        this.taskPrefix = taskPrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    @Override
    public TaskFlowsDefine getFlowsSetting() {
        return null;
    }

    public void setFlowsSetting(TaskFlowsDefine taskFlowsDefine) {
        this.taskFlowsDefine = taskFlowsDefine;
    }

    @Override
    public int getPeriodOffset() {
        return this.periodOffset;
    }

    @Override
    public int getDueDateOffset() {
        return 0;
    }

    @Override
    public List<PeriodSetting> getPeriodSetting() {
        return null;
    }

    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    public void setDueDateOffset(int dueDateOffset) {
    }

    public void setPeriodSetting(List<PeriodSetting> periodSetting) {
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        if (this.fillInAutomaticallyDue == null) {
            this.fillInAutomaticallyDue = new FillInAutomaticallyDue(this.automaticallyDueType, this.automaticallyDueDays, this.automaticTermination);
        }
        return this.fillInAutomaticallyDue;
    }

    public void setMeasureUnit(String MeasureUnit) {
        this.measureUnit = MeasureUnit;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }

    @Override
    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }
}

