/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.json.JSONObject
 */
package com.jiuqi.nr.tag.manager.entitydata.query;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.tag.manager.entitydata.query.TagQueryParam;
import java.util.Date;
import java.util.Map;
import org.json.JSONObject;

public class TagQueryParamImpl
implements TagQueryParam {
    private Date versionDate;
    private String period;
    private String rowFilterExpression;
    private TaskDefine taskDefine;
    private FormSchemeDefine formScheme;
    private IEntityDefine entityDefine;
    private IPeriodEntity periodEntity;
    private Map<String, DimensionValue> dimValueSet;
    private JSONObject customVariable;

    @Override
    public Date getVersionDate() {
        return this.versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getRowFilterExpression() {
        return this.rowFilterExpression;
    }

    public void setRowFilterExpression(String rowFilterExpression) {
        this.rowFilterExpression = rowFilterExpression;
    }

    @Override
    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    @Override
    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(FormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }

    @Override
    public IEntityDefine getEntityDefine() {
        return this.entityDefine;
    }

    public void setEntityDefine(IEntityDefine entityDefine) {
        this.entityDefine = entityDefine;
    }

    @Override
    public IPeriodEntity getPeriodEntity() {
        return this.periodEntity;
    }

    public void setPeriodEntity(IPeriodEntity periodEntity) {
        this.periodEntity = periodEntity;
    }

    @Override
    public Map<String, DimensionValue> getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(Map<String, DimensionValue> dimValueSet) {
        this.dimValueSet = dimValueSet;
    }

    @Override
    public JSONObject getCustomVariable() {
        return this.customVariable;
    }

    public void setCustomVariable(JSONObject customVariable) {
        this.customVariable = customVariable;
    }
}

