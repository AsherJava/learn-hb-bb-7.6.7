/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.gather.refactor.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.gather.bean.NodeCheckParam;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class NodeCheckInfo
extends NodeCheckParam {
    private String dwDimName;
    private String periodDimName;
    private DimensionValueSet dimensionValueSet;
    private String dataSchemeKey;
    private EntityViewDefine entityViewDefine;
    private ExecutorContext executorContext;
    private FormSchemeDefine formSchemeDefine;

    public NodeCheckInfo(NodeCheckParam nodeCheckParam) {
        super.setTaskKey(nodeCheckParam.getTaskKey());
        super.setFormSchemeKey(nodeCheckParam.getFormSchemeKey());
        super.setDimensionCollection(nodeCheckParam.getDimensionCollection());
        super.setRecursive(nodeCheckParam.isRecursive());
        super.setErrorRange(nodeCheckParam.getErrorRange());
        super.setFormKeys(nodeCheckParam.getFormKeys());
        super.setIgnoreAccessItems(nodeCheckParam.getIgnoreAccessItems());
    }

    public String getDwDimName() {
        return this.dwDimName;
    }

    public void setDwDimName(String dwDimName) {
        this.dwDimName = dwDimName;
    }

    public String getPeriodDimName() {
        return this.periodDimName;
    }

    public void setPeriodDimName(String periodDimName) {
        this.periodDimName = periodDimName;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public EntityViewDefine getEntityViewDefine() {
        return this.entityViewDefine;
    }

    public void setEntityViewDefine(EntityViewDefine entityViewDefine) {
        this.entityViewDefine = entityViewDefine;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }
}

