/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 */
package com.jiuqi.gcreport.org.impl.util.base;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgQueryService;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GcOrgBaseModel {
    @Autowired
    protected FGcOrgQueryService<GcOrgCacheVO> queryService;
    @Autowired
    protected IDataDefinitionRuntimeController controller;
    @Autowired
    protected IEntityViewRunTimeController evc;

    public FGcOrgQueryService<GcOrgCacheVO> getQueryService() {
        return this.queryService;
    }

    public GcOrgTypeTool getOrgTypeService() {
        return GcOrgTypeTool.getInstance();
    }

    public GcOrgVerTool getOrgVersionService() {
        return GcOrgVerTool.getInstance();
    }

    public IDataDefinitionRuntimeController getController() {
        return this.controller;
    }

    public IEntityViewRunTimeController getEvc() {
        return this.evc;
    }
}

