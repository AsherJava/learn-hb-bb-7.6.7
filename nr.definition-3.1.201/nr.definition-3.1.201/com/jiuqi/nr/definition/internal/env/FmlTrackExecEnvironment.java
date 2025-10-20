/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.FmlTrackColumnLinkFinder;
import com.jiuqi.nr.definition.internal.env.FmlTrackDataLinkFinder;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;

public class FmlTrackExecEnvironment
extends ReportFmlExecEnvironment {
    private IDataLinkFinder datalinkFinder;
    private IDataModelLinkFinder columnlinkFinder;

    public FmlTrackExecEnvironment(IRunTimeViewController controller, IDataDefinitionRuntimeController runtimeController, IEntityViewRunTimeController entityViewRunTimeController, String formSchemeKey) {
        super(controller, runtimeController, entityViewRunTimeController, formSchemeKey);
    }

    @Override
    public IDataLinkFinder getDataLinkFinder() {
        if (this.datalinkFinder == null) {
            this.datalinkFinder = new FmlTrackDataLinkFinder(this.formSchemeKey);
        }
        return this.datalinkFinder;
    }

    @Override
    public IDataModelLinkFinder getDataModelLinkFinder() {
        if (this.columnlinkFinder == null) {
            this.columnlinkFinder = new FmlTrackColumnLinkFinder(this.formSchemeKey);
        }
        return this.columnlinkFinder;
    }
}

