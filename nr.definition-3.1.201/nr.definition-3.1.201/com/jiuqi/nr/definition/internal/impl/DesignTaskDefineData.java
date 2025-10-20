/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.PeriodSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import java.util.Date;
import java.util.List;

public class DesignTaskDefineData
implements DesignTaskDefine {
    private DesignTaskDefine designTaskDefine;
    private DesignBigDataService designBigDataService;

    public DesignTaskDefineData(DesignTaskDefine designTaskDefine, DesignBigDataService designBigDataService) {
        this.designTaskDefine = designTaskDefine;
        this.designBigDataService = designBigDataService;
    }

    @Override
    public TaskFlowsDefine getFlowsSetting() {
        if (null != this.designTaskDefine.getFlowsSetting()) {
            return this.designTaskDefine.getFlowsSetting();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "FLOWSETTING");
            if (null == bigData) {
                return null;
            }
            this.designTaskDefine.setFlowsSetting(DesignTaskFlowsDefine.bytesToTaskFlowsData(bigData));
            return this.designTaskDefine.getFlowsSetting();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.TASK_FLOWS_QUERY, (Throwable)e);
        }
    }

    public DesignTaskDefine getDesignTaskDefine() {
        return this.designTaskDefine;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.designTaskDefine.setUpdateTime(updateTime);
    }

    @Override
    public void setKey(String key) {
        this.designTaskDefine.setKey(key);
    }

    @Override
    public void setOrder(String order) {
        this.designTaskDefine.setOrder(order);
    }

    @Override
    public void setVersion(String version) {
        this.designTaskDefine.setVersion(version);
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.designTaskDefine.setOwnerLevelAndId(ownerLevelAndId);
    }

    @Override
    public void setTitle(String title) {
        this.designTaskDefine.setTitle(title);
    }

    @Override
    public String getDescription() {
        return this.designTaskDefine.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.designTaskDefine.setDescription(description);
    }

    @Override
    public void setMasterEntitiesKey(String masterEntitiesKey) {
        this.designTaskDefine.setMasterEntitiesKey(masterEntitiesKey);
    }

    @Override
    public void setMasterEntityCount(int count) {
        this.designTaskDefine.setMasterEntityCount(count);
    }

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.designTaskDefine.setMeasureUnit(measureUnit);
    }

    @Override
    public void setPeriodType(PeriodType periodType) {
        this.designTaskDefine.setPeriodType(periodType);
    }

    @Override
    public void setFromPeriod(String fromPeriod) {
        this.designTaskDefine.setFromPeriod(fromPeriod);
    }

    @Override
    public void setToPeriod(String toPeriod) {
        this.designTaskDefine.setToPeriod(toPeriod);
    }

    @Override
    public void setTaskPeriodOffset(int taskPeriodOffset) {
        this.designTaskDefine.setTaskPeriodOffset(taskPeriodOffset);
    }

    @Override
    public void setTaskCode(String taskCode) {
        this.designTaskDefine.setTaskCode(taskCode);
    }

    @Override
    public void setTaskFilePrefix(String taskFilePrefix) {
        this.designTaskDefine.setTaskFilePrefix(taskFilePrefix);
    }

    @Override
    public void setGroupName(String groupName) {
        this.designTaskDefine.setGroupName(groupName);
    }

    @Override
    public void setBigDataChanged(boolean bigDataChanged) {
        this.designTaskDefine.setBigDataChanged(bigDataChanged);
    }

    @Override
    public void setTaskExtension(String taskExtension) {
        this.designTaskDefine.setTaskExtension(taskExtension);
    }

    @Override
    public void setTaskGatherType(TaskGatherType gatherType) {
        this.designTaskDefine.setTaskGatherType(gatherType);
    }

    @Override
    public FormulaSyntaxStyle getFormulaSyntaxStyle() {
        return this.designTaskDefine.getFormulaSyntaxStyle();
    }

    @Override
    public void setFormulaSyntaxStyle(FormulaSyntaxStyle syntaxStyle) {
        this.designTaskDefine.setFormulaSyntaxStyle(syntaxStyle);
    }

    @Override
    public void setCommitEntitiesKey(String commitEntitiesKey) {
        this.designTaskDefine.setCommitEntitiesKey(commitEntitiesKey);
    }

    @Override
    public void setDueDateOffset(int dueDateOffset) {
        this.designTaskDefine.setDueDateOffset(dueDateOffset);
    }

    @Override
    public void setPeriodBeginOffset(int periodBeginOffset) {
        this.designTaskDefine.setPeriodBeginOffset(periodBeginOffset);
    }

    @Override
    public void setPeriodEndOffset(int periodEndOffset) {
        this.designTaskDefine.setPeriodEndOffset(periodEndOffset);
    }

    @Override
    public void setPeriodSetting(List<PeriodSetting> periodSetting) {
        this.designTaskDefine.setPeriodSetting(periodSetting);
    }

    @Override
    public void setFlowsSetting(DesignTaskFlowsDefine designTaskFlowsDefine) {
        this.designTaskDefine.setFlowsSetting(designTaskFlowsDefine);
    }

    @Override
    public void setEntityViewsInEFDC(String viewsInEFDC) {
        this.designTaskDefine.setEntityViewsInEFDC(viewsInEFDC);
    }

    @Override
    public void setTaskType(TaskType taskType) {
        this.designTaskDefine.setTaskType(taskType);
    }

    @Override
    public void setCreateUserName(String username) {
        this.designTaskDefine.setCreateUserName(username);
    }

    @Override
    public void setCreateTime(String createtime) {
        this.designTaskDefine.setCreateTime(createtime);
    }

    @Override
    public void setEfdcSwitch(boolean efdcSwitch) {
        this.designTaskDefine.setEfdcSwitch(efdcSwitch);
    }

    @Override
    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        this.designTaskDefine.setFillInAutomaticallyDue(fillInAutomaticallyDue);
    }

    @Override
    public void setDataScheme(String datascheme) {
        this.designTaskDefine.setDataScheme(datascheme);
    }

    @Override
    public void setDw(String dw) {
        this.designTaskDefine.setDw(dw);
    }

    @Override
    public void setDateTime(String dateTime) {
        this.designTaskDefine.setDateTime(dateTime);
    }

    @Override
    public void setDims(String dims) {
        this.designTaskDefine.setDims(dims);
    }

    @Override
    public void setFilterExpression(String expression) {
        this.designTaskDefine.setFilterExpression(expression);
    }

    @Override
    public void setFillingDateType(FillDateType fillingDateType) {
        this.designTaskDefine.setFillingDateType(fillingDateType);
    }

    @Override
    public void setFillingDateDays(int fillingDateDays) {
        this.designTaskDefine.setFillingDateDays(fillingDateDays);
    }

    @Override
    public void setFilterTemplate(String filterTemplateID) {
        this.designTaskDefine.setFilterTemplate(filterTemplateID);
    }

    @Override
    public String getMasterEntitiesKey() {
        return this.designTaskDefine.getMasterEntitiesKey();
    }

    @Override
    public int getMasterEntityCount() {
        return this.designTaskDefine.getMasterEntityCount();
    }

    @Override
    public String getMeasureUnit() {
        return this.designTaskDefine.getMeasureUnit();
    }

    @Override
    public PeriodType getPeriodType() {
        return this.designTaskDefine.getPeriodType();
    }

    @Override
    public String getFromPeriod() {
        return this.designTaskDefine.getFromPeriod();
    }

    @Override
    public String getToPeriod() {
        return this.designTaskDefine.getToPeriod();
    }

    @Override
    public int getTaskPeriodOffset() {
        return this.designTaskDefine.getTaskPeriodOffset();
    }

    @Override
    public String getTaskCode() {
        return this.designTaskDefine.getTaskCode();
    }

    @Override
    public String getTaskFilePrefix() {
        return this.designTaskDefine.getTaskFilePrefix();
    }

    @Override
    public String getGroupName() {
        return this.designTaskDefine.getGroupName();
    }

    @Override
    public boolean getBigDataChanged() {
        return this.designTaskDefine.getBigDataChanged();
    }

    @Override
    public String getTaskExtension() {
        return this.designTaskDefine.getTaskExtension();
    }

    @Override
    public TaskGatherType getTaskGatherType() {
        return this.designTaskDefine.getTaskGatherType();
    }

    @Override
    public String getCommitEntitiesKey() {
        return this.designTaskDefine.getCommitEntitiesKey();
    }

    @Override
    public int getDueDateOffset() {
        return this.designTaskDefine.getDueDateOffset();
    }

    @Override
    public int getPeriodBeginOffset() {
        return this.designTaskDefine.getPeriodBeginOffset();
    }

    @Override
    public int getPeriodEndOffset() {
        return this.designTaskDefine.getPeriodEndOffset();
    }

    @Override
    public List<PeriodSetting> getPeriodSetting() {
        return this.designTaskDefine.getPeriodSetting();
    }

    @Override
    public String getEntityViewsInEFDC() {
        return this.designTaskDefine.getEntityViewsInEFDC();
    }

    @Override
    public TaskType getTaskType() {
        return this.designTaskDefine.getTaskType();
    }

    @Override
    public String getCreateUserName() {
        return this.designTaskDefine.getCreateUserName();
    }

    @Override
    public String getCreateTime() {
        return this.designTaskDefine.getCreateTime();
    }

    @Override
    public boolean getEfdcSwitch() {
        return this.designTaskDefine.getEfdcSwitch();
    }

    @Override
    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.designTaskDefine.getFillInAutomaticallyDue();
    }

    @Override
    public String getDataScheme() {
        return this.designTaskDefine.getDataScheme();
    }

    @Override
    public String getDw() {
        return this.designTaskDefine.getDw();
    }

    @Override
    public String getDateTime() {
        return this.designTaskDefine.getDateTime();
    }

    @Override
    public String getDims() {
        return this.designTaskDefine.getDims();
    }

    @Override
    public String getFilterExpression() {
        return this.designTaskDefine.getFilterExpression();
    }

    @Override
    public FillDateType getFillingDateType() {
        return this.designTaskDefine.getFillingDateType();
    }

    @Override
    public int getFillingDateDays() {
        return this.designTaskDefine.getFillingDateDays();
    }

    @Override
    public String getFilterTemplate() {
        return this.designTaskDefine.getFilterTemplate();
    }

    public Date getUpdateTime() {
        return this.designTaskDefine.getUpdateTime();
    }

    public String getKey() {
        return this.designTaskDefine.getKey();
    }

    public String getTitle() {
        return this.designTaskDefine.getTitle();
    }

    public String getOrder() {
        return this.designTaskDefine.getOrder();
    }

    public String getVersion() {
        return this.designTaskDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.designTaskDefine.getOwnerLevelAndId();
    }
}

