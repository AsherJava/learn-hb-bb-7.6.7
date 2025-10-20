/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.designer.web.factory.DesignerFactory;
import java.util.Date;

public class UReportTask {
    private boolean isDetilModel;
    private String key;
    private String code;
    private String title;
    private String prefix;
    private int periodType;
    private String fromPeriod;
    private String toPeriod;
    private int periodOffset;
    private String meKey;
    private int meCnt;
    private String xDataTargetViewKey;
    private String measureUnit;
    private String paraLevel;
    private String order;
    private String version;
    private Date updateTime;
    private String desc;
    private boolean bigDataChanged;
    private String groupKey;

    public UReportTask() {
    }

    public UReportTask(DesignTaskDefine task, DesignerFactory dsFactory) throws JQException {
        this();
        this.fromDbObject(task, dsFactory);
    }

    public boolean isDetilModel() {
        return this.isDetilModel;
    }

    public void setDetilModel(boolean isDetilModel) {
        this.isDetilModel = isDetilModel;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType.type();
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

    public int getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    public String getMeKey() {
        return this.meKey;
    }

    public void setMeKey(String meKey) {
        this.meKey = meKey;
    }

    public int getMeCnt() {
        return this.meCnt;
    }

    public void setMeCnt(int meCnt) {
        this.meCnt = meCnt;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getParaLevel() {
        return this.paraLevel;
    }

    public void setParaLevel(String paraLevel) {
        this.paraLevel = paraLevel;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isBigDataChanged() {
        return this.bigDataChanged;
    }

    public void setBigDataChanged(boolean bigDataChanged) {
        this.bigDataChanged = bigDataChanged;
    }

    public String getxDataTargetViewKey() {
        return this.xDataTargetViewKey;
    }

    public void setxDataTargetViewKey(String xDataTargetViewKey) {
        this.xDataTargetViewKey = xDataTargetViewKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public void fromDbObject(DesignTaskDefine task, DesignerFactory dsFactory) throws JQException {
        this.setKey(task.getKey());
        this.setCode(task.getTaskCode());
        this.setTitle(task.getTitle());
        this.setPrefix(task.getTaskFilePrefix());
        this.setPeriodType(task.getPeriodType());
        this.setFromPeriod(task.getFromPeriod());
        this.setToPeriod(task.getToPeriod());
        this.setPeriodOffset(task.getTaskPeriodOffset());
        this.setMeKey(task.getMasterEntitiesKey());
        this.setMeCnt(task.getMasterEntityCount());
        this.setMeasureUnit(task.getMeasureUnit());
        this.setParaLevel(task.getOwnerLevelAndId());
        this.setOrder(task.getOrder());
        this.setUpdateTime(task.getUpdateTime());
        this.setDesc(task.getDescription());
        this.setBigDataChanged(task.getBigDataChanged());
    }

    public DesignTaskDefine toDBObject(DesignerFactory dsFactory) {
        DesignTaskDefine task = dsFactory.getDsCtller().createTaskDefine();
        task.setKey(this.getKey());
        task.setTaskCode(this.getCode());
        task.setTitle(this.getTitle());
        task.setTaskFilePrefix(this.getPrefix());
        task.setPeriodType(PeriodType.fromType((int)this.getPeriodType()));
        task.setFromPeriod(this.getFromPeriod());
        task.setToPeriod(this.getToPeriod());
        task.setTaskPeriodOffset(this.getPeriodOffset());
        task.setMasterEntitiesKey(this.getMeKey());
        task.setMasterEntityCount(this.getMeCnt());
        task.setMeasureUnit(this.getMeasureUnit());
        task.setOwnerLevelAndId(this.getParaLevel());
        task.setOrder(this.getOrder());
        task.setUpdateTime(new Date());
        task.setBigDataChanged(this.isBigDataChanged());
        return task;
    }
}

