/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.itreebase.context.ITreeContext
 *  com.jiuqi.nr.itreebase.node.IBaseNodeData
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.uselector.checker.ctx;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.itreebase.context.ITreeContext;
import com.jiuqi.nr.itreebase.node.IBaseNodeData;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.Date;
import java.util.Map;
import org.json.JSONObject;

public class USelectorFilterExecuteContext
implements IUnitTreeContext {
    private final IUnitTreeContext context;
    private FormSchemeDefine schemeDefine;
    private Map<String, DimensionValue> dimValueSet;

    public USelectorFilterExecuteContext(IUnitTreeContext context) {
        this.context = context;
        this.schemeDefine = context.getFormScheme();
        this.dimValueSet = context.getDimValueSet();
    }

    public String getPeriod() {
        return this.context.getPeriod();
    }

    public String getRowFilterExpression() {
        return this.context.getRowFilterExpression();
    }

    public Date getVersionDate() {
        return this.context.getVersionDate();
    }

    public Map<String, DimensionValue> getDimValueSet() {
        return this.dimValueSet;
    }

    public void setDimValueSet(Map<String, DimensionValue> dimValueSet) {
        this.dimValueSet = dimValueSet;
    }

    public JSONObject getCustomVariable() {
        return this.context.getCustomVariable();
    }

    public ITreeContext getITreeContext() {
        return this.context.getITreeContext();
    }

    public TaskDefine getTaskDefine() {
        return this.context.getTaskDefine();
    }

    public FormSchemeDefine getFormScheme() {
        return this.schemeDefine;
    }

    public void setSchemeDefine(FormSchemeDefine schemeDefine) {
        this.schemeDefine = schemeDefine;
    }

    public IEntityDefine getEntityDefine() {
        return this.context.getEntityDefine();
    }

    public IPeriodEntity getPeriodEntity() {
        return this.context.getPeriodEntity();
    }

    public IconSourceProvider getIconProvider() {
        return this.context.getIconProvider();
    }

    public IBaseNodeData getActionNode() {
        return this.context.getActionNode();
    }

    public IEntityQueryPloy getEntityQueryPloy() {
        return this.context.getEntityQueryPloy();
    }

    public String getDataSourceId() {
        return this.context.getDataSourceId();
    }
}

