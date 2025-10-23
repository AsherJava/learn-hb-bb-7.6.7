/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 */
package com.jiuqi.nr.subdatabase.controller.Impl;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseController;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubDataBaseInfoProviderImpl
implements SubDataBaseInfoProvider {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private SubDataBaseController subDataBaseController;

    @Override
    public void setCurDataBase(String taskKey, String code) {
        ContextExtension contextDataCache = NpContextHolder.getContext().getDefaultExtension();
        DesignTaskDefine task = this.nrDesignTimeController.queryTaskDefine(taskKey);
        SubDataBase subDataBase = this.subDataBaseController.getSubDataBaseObjByCode(task.getDataScheme(), code);
        if (subDataBase == null) {
            throw new RuntimeException("subDataBase '" + code + "' not found.");
        }
        contextDataCache.put("SUBDATABASE", (Serializable)subDataBase);
    }

    @Override
    public boolean isSubDataBase() {
        ContextExtension contextDataCache = NpContextHolder.getContext().getDefaultExtension();
        SubDataBase subDataBase = (SubDataBase)contextDataCache.get("SUBDATABASE");
        return subDataBase != null;
    }

    @Override
    public SubDataBase getCurDataBase() {
        ContextExtension contextDataCache = NpContextHolder.getContext().getDefaultExtension();
        return (SubDataBase)contextDataCache.get("SUBDATABASE");
    }
}

