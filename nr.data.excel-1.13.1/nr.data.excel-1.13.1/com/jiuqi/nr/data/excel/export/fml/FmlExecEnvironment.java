/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;

public class FmlExecEnvironment
extends ReportFmlExecEnvironment {
    private IDataModelLinkFinder dataModelLinkFinder;

    public FmlExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey) {
        super(controller, runtimeController, entityViewRunTimeController, formSchemeKey);
    }

    public void setDataModelLinkFinder(IDataModelLinkFinder dataModelLinkFinder) {
        this.dataModelLinkFinder = dataModelLinkFinder;
    }

    public IDataModelLinkFinder getDataModelLinkFinder() {
        return this.dataModelLinkFinder;
    }
}

