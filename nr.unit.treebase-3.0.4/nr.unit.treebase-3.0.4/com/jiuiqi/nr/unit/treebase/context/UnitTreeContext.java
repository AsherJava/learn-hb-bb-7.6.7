/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
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
package com.jiuiqi.nr.unit.treebase.context;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuqi.bi.util.StringUtils;
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

public class UnitTreeContext
implements IUnitTreeContext {
    private final UnitTreeContextData contextData;
    private Date versionDate;
    private TaskDefine taskDefine;
    private FormSchemeDefine formScheme;
    private IEntityDefine entityDefine;
    private IPeriodEntity periodEntity;
    private IconSourceProvider iconProvider;
    private IBaseNodeData actionNode;
    private IEntityQueryPloy entityQueryPloy;
    private String dataSourceId;

    public UnitTreeContext(UnitTreeContextData contextData) {
        this.contextData = contextData;
    }

    @Override
    public String getPeriod() {
        String period = this.contextData.getPeriod();
        if (StringUtils.isEmpty((String)period)) {
            DimensionValue dimensionValue;
            Map<String, DimensionValue> dimValueSet = this.getDimValueSet();
            IPeriodEntity periodEntity = this.getPeriodEntity();
            if (dimValueSet != null && periodEntity != null && dimValueSet.containsKey(periodEntity.getDimensionName()) && (dimensionValue = dimValueSet.get(periodEntity.getDimensionName())) != null) {
                return dimensionValue.getValue();
            }
        }
        return this.contextData.getPeriod();
    }

    @Override
    public String getRowFilterExpression() {
        return this.contextData.getRowFilterExpression();
    }

    @Override
    public Map<String, DimensionValue> getDimValueSet() {
        return this.contextData.getDimValueSet();
    }

    @Override
    public JSONObject getCustomVariable() {
        return this.contextData.getCustomVariable();
    }

    @Override
    public Date getVersionDate() {
        return this.versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    @Override
    public ITreeContext getITreeContext() {
        return this.contextData;
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
    public IBaseNodeData getActionNode() {
        return this.actionNode;
    }

    public void setActionNode(IBaseNodeData actionNode) {
        this.actionNode = actionNode;
    }

    @Override
    public IconSourceProvider getIconProvider() {
        return this.iconProvider;
    }

    public void setIconProvider(IconSourceProvider iconProvider) {
        this.iconProvider = iconProvider;
    }

    @Override
    public IEntityQueryPloy getEntityQueryPloy() {
        return this.entityQueryPloy;
    }

    public void setEntityQueryPloy(IEntityQueryPloy entityQueryPloy) {
        this.entityQueryPloy = entityQueryPloy;
    }

    @Override
    public String getDataSourceId() {
        return this.dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }
}

