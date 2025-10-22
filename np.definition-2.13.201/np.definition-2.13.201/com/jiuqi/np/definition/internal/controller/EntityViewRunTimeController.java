/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.controller;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.definition.proxy.EntityMetaServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityViewRunTimeController
implements IEntityViewRunTimeController {
    @Autowired(required=false)
    private EntityMetaServiceProxy metaServiceProxy;
    private final InnerClass innerClass = new InnerClass();

    @Override
    public EntityViewDefine buildEntityView(String entityId) {
        return this.innerClass.buildEntityView(entityId, null, true);
    }

    @Override
    public EntityViewDefine buildEntityView(String entityId, String rowFilterExpression) {
        return this.innerClass.buildEntityView(entityId, rowFilterExpression, true);
    }

    @Override
    public EntityViewDefine buildEntityView(String entityId, String filter, boolean filterRowByAuthority) {
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        entityViewDefine.setEntityId(entityId);
        entityViewDefine.setRowFilterExpression(filter);
        entityViewDefine.setFilterRowByAuthority(filterRowByAuthority);
        return entityViewDefine;
    }

    @Override
    public EntityViewDefine buildEntityView(IDimensionFilter dimensionFilter) {
        return this.buildEntityView(dimensionFilter, true);
    }

    @Override
    public EntityViewDefine buildEntityView(IDimensionFilter dimensionFilter, boolean filterRowByAuthority) {
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        String expression = this.metaServiceProxy.getExpression(dimensionFilter);
        entityViewDefine.setRowFilterExpression(expression);
        entityViewDefine.setEntityId(dimensionFilter.getEntityId());
        entityViewDefine.setFilterRowByAuthority(filterRowByAuthority);
        return entityViewDefine;
    }

    private static class InnerClass {
        private InnerClass() {
        }

        private EntityViewDefine buildEntityView(String entityId, String filter, boolean filterRowByAuthority) {
            RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
            entityViewDefine.setEntityId(entityId);
            entityViewDefine.setRowFilterExpression(filter);
            entityViewDefine.setFilterRowByAuthority(filterRowByAuthority);
            return entityViewDefine;
        }
    }
}

