/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import java.util.List;

public class EntityFmlExecEnvironment
extends ReportFmlExecEnvironment {
    private EntityViewData unitView;
    private List<EntityViewData> entityList;
    private IEntityViewRunTimeController controller;

    public EntityFmlExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey, List<EntityViewData> entityList) {
        super(controller, runtimeController, entityViewRunTimeController, formSchemeKey);
        this.controller = entityViewRunTimeController;
        this.entityList = entityList;
        for (EntityViewData entity : entityList) {
            if (!entity.isMasterEntity()) continue;
            this.unitView = entity;
            break;
        }
    }

    public String getUnitDimesion(ExecutorContext context) {
        if (this.unitView != null) {
            return this.unitView.getDimensionName();
        }
        return "";
    }

    public EntityViewDefine getEntityViewDefine(ExecutorContext context, String dimensionName) {
        for (EntityViewData entity : this.entityList) {
            if (!entity.getDimensionName().equals(dimensionName)) continue;
            EntityViewDefine entityViewDefine = this.controller.buildEntityView(entity.getKey(), entity.getRowFilter());
            return entityViewDefine;
        }
        return null;
    }
}

