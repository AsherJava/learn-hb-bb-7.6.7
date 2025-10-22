/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 */
package com.jiuqi.nr.entity.engine.executors;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import java.util.Map;

public class ExecutorContext
extends com.jiuqi.np.dataengine.executors.ExecutorContext
implements IContext {
    private Map<String, Object> queryParam;
    private CountModel countModel;

    public ExecutorContext(IDataDefinitionRuntimeController runtimeController) {
        super(runtimeController);
    }

    @Deprecated
    public String getIsolateCondition() {
        return null;
    }

    @Deprecated
    public void setIsolateCondition(String isolateCondition) {
    }

    public CountModel getCountModel() {
        return this.countModel;
    }

    public void setCountModel(CountModel countModel) {
        this.countModel = countModel;
    }

    public void setPeriodView(String periodView) {
        super.setPeriodView(periodView);
    }

    @Deprecated
    public DimensionValueSet getMainDimension() {
        return null;
    }

    @Deprecated
    public void setMainDimension(DimensionValueSet mainDimension) {
    }

    @Deprecated
    public EntityViewDefine getMainView() {
        return null;
    }

    @Deprecated
    public void setMainView(EntityViewDefine mainView) {
    }

    public Map<String, Object> getQueryParam() {
        return this.queryParam;
    }

    public void setQueryParam(Map<String, Object> queryParam) {
        this.queryParam = queryParam;
    }

    public static enum CountModel {
        LEAF_ONLY,
        ALL;

    }
}

