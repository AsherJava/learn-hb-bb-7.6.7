/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.log.ComparePropertyAnno;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_PARAM_FORMSCHEME_DES")
@ComparePropertyAnno.CompareType
public class DesignFormSchemeDefineImpl
implements DesignFormSchemeDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="fc_task_key", isPk=false)
    private String taskKey;
    @ComparePropertyAnno.CompareField(title="\u65b9\u6848\u6807\u8bc6")
    @DBAnno.DBField(dbField="fc_code")
    private String formSchemeCode;
    private PeriodType periodType = PeriodType.DEFAULT;
    @DBAnno.DBField(dbField="fc_key", isPk=true)
    private String key;
    @ComparePropertyAnno.CompareField(title="\u65b9\u6848\u6807\u9898")
    @DBAnno.DBField(dbField="fc_title")
    private String title;
    @DBAnno.DBField(dbField="fc_order", isOrder=true)
    private String order = OrderGenerator.newOrder();
    @DBAnno.DBField(dbField="fc_version")
    private String version;
    @DBAnno.DBField(dbField="fc_level")
    private String ownerLevelAndId;
    @DBAnno.DBField(dbField="fc_updatetime", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;
    @DBAnno.DBField(dbField="fc_desc")
    private String description;
    @DBAnno.DBField(dbField="fc_taskprefix")
    private String taskPrefix;
    @DBAnno.DBField(dbField="fc_fileprefix")
    private String filePrefix;
    @DBAnno.DBField(dbField="fc_period_offset")
    private int periodOffset;
    private DesignTaskFlowsDefine designTaskFlowsDefine;
    @DBAnno.DBField(dbField="fc_unit")
    private String measureUnit;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    @DBAnno.DBField(dbField="FC_AUTOMATICALLY_DUE_TYPE")
    private int automaticallyDueType = FillInAutomaticallyDue.Type.DEFAULT.getValue();
    @DBAnno.DBField(dbField="FC_AUTOMATICALLY_DUE_DAYS")
    private int automaticallyDueDays;
    @DBAnno.DBField(dbField="FC_AUTOMATIC_TERMINATION", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean automaticTermination = true;
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

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    @Override
    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    @Override
    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    @Override
    public void setFromPeriod(String fromPeriod) {
    }

    @Override
    public void setToPeriod(String toPeriod) {
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getMasterEntitiesKey() {
        return null;
    }

    @Override
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

    @Override
    public void setTaskPrefix(String taskPrefix) {
        this.taskPrefix = taskPrefix;
    }

    @Override
    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    @Override
    public TaskFlowsDefine getFlowsSetting() {
        return null;
    }

    @Override
    public void setFlowsSetting(DesignTaskFlowsDefine designTaskFlowsDefine) {
        this.designTaskFlowsDefine = designTaskFlowsDefine;
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

    @Override
    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    @Override
    public void setDueDateOffset(int dueDateOffset) {
    }

    @Override
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

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
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

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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

